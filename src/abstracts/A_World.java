package abstracts;

import models.Background;
import utils.Gam20_PhysicsSystem;
import utils.GlobalConsts;
import utils.Gam20_HelpText;

import java.util.ArrayList;

public abstract class A_World {
    private A_GraphicSystem graphicSystem;
    private A_PhysicsSystem physicsSystem;
    private A_InputSystem inputSystem;
    private A_UserInput userInput;



    // Level of the game
    public int level = 1;

    // Number of enemies kills
    public int enemiesKilled = 0;
    
    // number of enemies killed (in the previous itteration)
    private int _prevEnemiesKilled = 0;

    private double _multikillTime = GlobalConsts.MULTI_KILL_DELAY;

    // Top left corner of the displayed pane of the world
    public static double worldPartX = 0;
    public static double worldPartY = 0;

    // Define maximum frame rate
    private static final int FRAME_MINIMUM_MILLIS = 10;

    // Define if game is over
    public boolean gameOver = false;

    public Gam20_HelpText gameOverHelpText;

    // All objects in the game, including the Avatar
    public A_GameObjectList gameObjects = new A_GameObjectList();
    public GameObject avatar;
    public ArrayList<A_TextObject> textObjects = new ArrayList<A_TextObject>();

    Background background;
    Background background_death;


    // Define sound system (for sound multikill effects)
    public A_SoundSystem mkSoundSystem;

    // Define sound system (for sound music)
    public A_SoundSystem musicSoundSystem;

    // Define sound system (for sound abilities effects)
    public A_SoundSystem abilitySoundSystem;

    public A_World() {
        physicsSystem = new Gam20_PhysicsSystem(this);
        mkSoundSystem = new A_SoundSystem();
        abilitySoundSystem = new A_SoundSystem();
        musicSoundSystem = new A_SoundSystem();
        background = new Background("resourses/backgrounds/backgrounddetailed1.png");
        background_death = new Background("resourses/backgrounds/backgrounddetailed1-death.png");


        //Loading ability sounds
        abilitySoundSystem.loadSound("swordThrow", "resourses/sounds/sword/sword_throw.wav");

        // Loading multikill sounds 
        mkSoundSystem.loadSound("gameOver", "resourses/sounds/game_over.wav");
        mkSoundSystem.loadSound("explosion", "resourses/sounds/explosion.wav");
        mkSoundSystem.loadSound("pickup", "resourses/sounds/pickup.wav");
        mkSoundSystem.loadSound("firstBlood", "resourses/sounds/multikill/firstblood.wav");
        mkSoundSystem.loadSound("monsterKill", "resourses/sounds/multikill/mmonster_kill.wav");
        mkSoundSystem.loadSound("killingSpree", "resourses/sounds/multikill/killingspree.wav");
        mkSoundSystem.loadSound("rampage", "resourses/sounds/multikill/rampage.wav");
        mkSoundSystem.loadSound("dominating", "resourses/sounds/multikill/dominating.wav");
        mkSoundSystem.loadSound("wickedSick", "resourses/sounds/multikill/wicked_sick.wav");
        mkSoundSystem.loadSound("unstoppable", "resourses/sounds/multikill/unstoppable.wav");
        mkSoundSystem.loadSound("godlike", "resourses/sounds/multikill/godlike.wav");

        //Loading music
        musicSoundSystem.loadSound("inGame", "resourses/sounds/music/ingame_music.wav");

        //Start the ingame music immediately
        musicSoundSystem.playSound("inGame");
    }


    //
    // The main GAME LOOP
    //
    public final void run() {


        long lastTick = System.currentTimeMillis();

        while (true) {
           // System.out.println("One loop");
            // Calculating elapsed time so that each frame doesn't run faster than min diff seconds *per frame*
            long currentTick = System.currentTimeMillis();
            long millisDiff = currentTick - lastTick;

            //That time is defined in the int FRAME_MINIMUM_MILLIS
            if (millisDiff < FRAME_MINIMUM_MILLIS) {
                try {
                    Thread.sleep(FRAME_MINIMUM_MILLIS - millisDiff);
                } catch (Exception ex) {
                }
                currentTick = System.currentTimeMillis();
                millisDiff = currentTick - lastTick;
            }

            lastTick = currentTick;


            // Processing user input here
            userInput = inputSystem.getUserInput();

            // If game is not over process the user input
            processUserInput(userInput, millisDiff / 1000.0);
          
            userInput.clear();

            // If game is over display the game over screen
            if (gameOver) {
            
                //Stop music
                musicSoundSystem.stopAllSounds();

                //[Game Over] Stop the game
               endGame();
               
            }

            // Moving all objects. Zombies/Enemies will follow the player in their move method. The objects will only move if the game is not over 
            int gameSize = gameObjects.size();

            for (int i = 0; i < gameSize; i++) {
                GameObject obj = gameObjects.get(i);
                if (obj.isLiving && obj != avatar) obj.move(millisDiff / 1000.0);
            }
        
            


            // Delete the dead objects
            int num = 0;
            while (num < gameSize) {
                if (!gameObjects.get(num).isLiving) {
                    gameObjects.remove(num);
                    gameSize--;
                } else {
                    num++;
                }
            }

            //Handle if multiple enemies were killed at the same time
            handleMultiKill(millisDiff / 1000.0);


            // Adjust displayed pane of the world
            this.adjustWorldPart();

            //First drawing the background



            // Draw whatever objects needs to be drawn
            graphicSystem.clear();
            graphicSystem.draw(background);
            for (int i = 0; i < gameSize; i++) {
                graphicSystem.draw(gameObjects.get(i));
            }


            // Draw text objects
            for (int i = 0; i < textObjects.size(); i++) {
                graphicSystem.draw(textObjects.get(i));
            }

            // Redraw everything here
            graphicSystem.redraw();

            // Spawn any new objects here
            createNewObjects(millisDiff / 1000.0);
            
            // Spawn any new zombies here (depending on level)
            createNewZombies(millisDiff / 1000.0);


        }
    }


    // Adjust the displayed pane of the world according to Avatar and Bounds
    //
    private final void adjustWorldPart() {
        final int RIGHT_END = GlobalConsts.WORLD_WIDTH - GlobalConsts.WORLDPART_WIDTH;
        final int BOTTOM_END = GlobalConsts.WORLD_HEIGHT - GlobalConsts.WORLDPART_HEIGHT;


        // If avatar is too much RIGHT in display ...
        if (avatar.x > worldPartX + GlobalConsts.WORLDPART_WIDTH - GlobalConsts.SCROLL_BOUNDS) {
            // ... adjust display
            worldPartX = avatar.x + GlobalConsts.SCROLL_BOUNDS - GlobalConsts.WORLDPART_WIDTH;
            if (worldPartX >= RIGHT_END) {
                worldPartX = RIGHT_END;
            }
        }

        // Same on the LEFT side
        else if (avatar.x < worldPartX + GlobalConsts.SCROLL_BOUNDS) {
            worldPartX = avatar.x - GlobalConsts.SCROLL_BOUNDS;
            if (worldPartX <= 0) {
                worldPartX = 0;
            }
        }

        // Same at the BOTTOM
        if (avatar.y > worldPartY + GlobalConsts.WORLDPART_HEIGHT - GlobalConsts.SCROLL_BOUNDS) {
            worldPartY = avatar.y + GlobalConsts.SCROLL_BOUNDS - GlobalConsts.WORLDPART_HEIGHT;
            if (worldPartY >= BOTTOM_END) {
                worldPartY = BOTTOM_END;
            }
        }

        // Same at the TOP
        else if (avatar.y < worldPartY + GlobalConsts.SCROLL_BOUNDS) {
            worldPartY = avatar.y - GlobalConsts.SCROLL_BOUNDS;
            if (worldPartY <= 0) {
                worldPartY = 0;
            }
        }

    }


    public void setGraphicSystem(A_GraphicSystem p) {
        graphicSystem = p;
    }

    public void setInputSystem(A_InputSystem p) {
        inputSystem = p;
    }

    public A_PhysicsSystem getPhysicsSystem() {
        return physicsSystem;
    }

    // This method ends the game and freezes the objects
    private void endGame() {

        // Stop all sounds 
        mkSoundSystem.stopAllSounds();

        // Play sound game over sound
          mkSoundSystem.playSound("gameOver");

         // Display the game over screen

          gameOverHelpText = new Gam20_HelpText(400, 400, "Game Over! You slayed " + enemiesKilled + " enemies and reached level " + level);
          
          textObjects.clear();
          textObjects.add(gameOverHelpText);

          // Pause the game for 2 seconds (GTA 5 style)
          Object lock = new Object();
            try {
            synchronized (lock) {
                // Pause the program
                lock.wait(2000);
            }

            // Continue execution after the pause
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         while (true) {
             graphicSystem.clear();

             //Draw the background when you are dead
              graphicSystem.draw(background_death);

            // Draw ONLY SOME objects so they stll appear on the screen (depending on their type)
            for (int i = 0; i < gameObjects.size(); i++) {

                GameObject obj = gameObjects.get(i);

                // When the game is ended only display those objects
                switch(obj.type()){
                    case GlobalConsts.TYPE_AVATAR:
                        case GlobalConsts.TYPE_ZOMBIE:
                            graphicSystem.draw(obj);
                    break;
                    default: 
                    
                    break;
                }

            }

            //TODO play death animation

            // Draw text objects
            for (int i = 0; i < textObjects.size(); i++) {
                graphicSystem.draw(textObjects.get(i));
            }

            // Redraw everything here
            graphicSystem.redraw();

            }
    }

    private void handleMultiKill(double diffSeconds){

    
        //Check if the number of enemies killed has changed
        if(_prevEnemiesKilled != enemiesKilled){
            //Calculate the difference of kills
            int killDiff = enemiesKilled - _prevEnemiesKilled;
            boolean isMultiKill = killDiff > 2;

            //If there was a multi kill
            if(isMultiKill && level < 9 ){
                switch(killDiff){
                    case 3:
                        System.out.println("Monster Kill!");
                        mkSoundSystem.stopAllSounds();
                        mkSoundSystem.playSound("monsterKill");
                        break;
                    default: break;
                }
            }
            
            //Play the sound for the multi kill
            switch(enemiesKilled){
                case 1:   mkSoundSystem.playSound("firstBlood"); break;
                case 20:  mkSoundSystem.playSound("killingSpree"); break;
                case 60:  mkSoundSystem.playSound("rampage"); break;
                case 150:  mkSoundSystem.playSound("dominating"); break;
                case 250:  mkSoundSystem.playSound("wickedSick"); break;
                case 300:  mkSoundSystem.playSound("unstoppable"); break;
                case 350:  mkSoundSystem.playSound("godlike"); break;
            }
            
            
            //Account multi kill after ... seconds
         _multikillTime -= diffSeconds;
            if (_multikillTime < 0) {
                 _prevEnemiesKilled = enemiesKilled;
                _multikillTime = GlobalConsts.MULTI_KILL_DELAY;
            }
           
            

            
        }
    }
    public abstract void init();

    protected abstract void processUserInput(A_UserInput input, double diffSec);

    protected abstract void createNewObjects(double diffSeconds);

    protected abstract void createNewZombies(double diffSeconds);
}
