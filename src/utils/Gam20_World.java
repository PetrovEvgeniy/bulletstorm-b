package utils;

import abstracts.A_GameObjectList;
import abstracts.A_UserInput;
import abstracts.A_World;
import abstracts.GameObject;
import models.*;

public class Gam20_World extends A_World {
    private double timePassed = 0;
    private double timeSinceLastShot = 0;
    private final double charSpeed = 5.0;

    // For fireballs
    private int fireballs = 5;

    private Gam20_CounterLevel counterL;
    private Gam20_CounterFireballs counterG;
    private Gam20_CounterEnemies counterE;

    private Gam20_HelpText helpText;

    private double spawnFireball = 0;

    private double spawnZombie = 0;

    private double lifeHelpText = 4.0;

    private double levelUpTime = GlobalConsts.LEVEL_UP_TIME;

    public void init() {
        // Add the Avatar
        //TODO: Later on the path to the image should be decided when user selects character(if we have multiple characters)
        avatar = new Wizard(2500, 2000,"resourses/sprites/fire_wizard/idle/wizard_1.png");
        gameObjects.add(avatar);


        // Set WorldPart position
        worldPartX = 1500;
        worldPartY = 1500;

        // [REMOVED] Add a little forest

        for (int i = 0; i < 50; i++) {
            int x = (int) (Math.random() * (GlobalConsts.WORLD_WIDTH - 50)) + 1;
            int y = (int) (Math.random() * (GlobalConsts.WORLD_HEIGHT - 50)) + 1;
            gameObjects.add(new Bush(x,y));
        }

          for (int x = 0; x < 5000; x += 1000) {

              for (int y = 0; y < 4000; y += 800) {
                  gameObjects.add(new Rock(x + 300, y + 200));
                  gameObjects.add(new Rock(x + 600, y + 370));
                  gameObjects.add(new Rock(x + 200, y + 600));
              }
          }


        // Add one single zombie
        //gameObjects.add(new Gam20_ZombieAI(100, 100));


        counterL = new Gam20_CounterLevel(400, 40);
        counterE = new Gam20_CounterEnemies(20, 40);
        counterG = new Gam20_CounterFireballs(770, 40);
        helpText = new Gam20_HelpText(100, 400);

        counterG.setNumber(fireballs);
        textObjects.add(counterE);
        textObjects.add(counterG);
        textObjects.add(counterL);
        textObjects.add(helpText);
    }

    protected void processUserInput(A_UserInput userInput, double diffSeconds) {
        // Distinguish if Avatar shall move or shoots
        int button = userInput.mouseButton;

        //
        // Mouse events
        //
//        if(userInput.isMouseEvent){
//            // move
//            if(button==1) { avatar.setDestination(userInput.mousePressedX+worldPartX,
//                    userInput.mousePressedY+worldPartY);
//            }
//        }

        //
        // Mouse still pressed?
        //
        if(userInput.isMousePressed && button==1) {
            avatar.isShooting = true;
            // only 1 shot every ... seconds:
            timeSinceLastShot += diffSeconds;
            if(timeSinceLastShot > 0.3)
            {
                timeSinceLastShot = 0;

                Wizard_Fireball shot = new Wizard_Fireball(
                        avatar.x,avatar.y,userInput.mouseMovedX+worldPartX,userInput.mouseMovedY+worldPartY);
                this.gameObjects.add(shot);
            }
        }

        if (userInput.isMousePressed && button == 3) {
            avatar.isShooting = true;
            throwFireball(userInput.mouseMovedX + worldPartX, userInput.mouseMovedY + worldPartY);
        }

        if(!userInput.isMousePressed){
            avatar.isShooting = false;
        }

        //
        // Keyboard events
        //

        double denom = 1.7;
        if (userInput.isKeyEvent) {
            // this way is not perfect because you should implement all possible combinations
            // for now it is ok

           // if(userInput.keyPressed.size()

            if (userInput.keyPressed.contains('w') && userInput.keyPressed.contains('a')) {
                avatar.move(-charSpeed/denom, -charSpeed/denom);
                avatar.isFacingRight = false;
            }
            else if (userInput.keyPressed.contains('s') && userInput.keyPressed.contains('a')) {
                avatar.move(-charSpeed/denom, charSpeed/denom);
                avatar.isFacingRight = false;
            }
            else if (userInput.keyPressed.contains('w') && userInput.keyPressed.contains('d')) {
                avatar.move(charSpeed/denom, -charSpeed/denom);
                avatar.isFacingRight = true;
            }
            else if (userInput.keyPressed.contains('s') && userInput.keyPressed.contains('d')) {
                avatar.move(charSpeed/denom, charSpeed/denom);
                avatar.isFacingRight = true;
            }
            else if (userInput.keyPressed.contains('w')) {
                avatar.move(0, -charSpeed);
            } else if(userInput.keyPressed.contains('a')){
                avatar.move(-charSpeed, 0);
                avatar.isFacingRight = false;
            }
            else if (userInput.keyPressed.contains('s')) {
                avatar.move(0, charSpeed);
            }else if(userInput.keyPressed.contains('d')){
                avatar.move(charSpeed, 0);
                avatar.isFacingRight = true;
            }
            // TODO
             if (userInput.keyPressed.contains(' ')) {
                 throwSword();
                //throwFireball(userInput.mouseMovedX + worldPartX, userInput.mouseMovedY + worldPartY);
            }
          
          

//
//            } else if (userInput.keyPressed == (char) 27) {
//                //TODO MAKE PAUSE
//                //System.exit(0);
//            }else if(userInput.keyPressed == 'w' && userInput.keyPressed == 'a'){
//
//            }

        }
    }

    private void throwSword() {
        GameObject sword = new SpinningSword("resourses/sprites/sword/SwordH.png",avatar);
       // abilitySoundSystem.playSound("swordThrow");
        gameObjects.add(sword);
    }


    private void moveChar(double x, double y) {
        avatar.x += x;
        avatar.y += y;
    }

    private void throwFireball(double x, double y) {
        if (fireballs <= 0) return;

        // Throw a fireball
        for (int i = 0; i < 50; i++) {
            double alfa = Math.random() * Math.PI * 2;
            double speed = 50 + Math.random() * 200;
            double time = 0.1 + Math.random() * 0.4;
            Gam20_Shot shot = new Gam20_Shot(x, y, alfa, speed, time);
            this.gameObjects.add(shot);
        }

        //Play explosion sound
        mkSoundSystem.playSound("explosion");

        // Adjust fireball counter
        fireballs--;
        counterG.setNumber(fireballs);


    }


    protected void createNewObjects(double diffSeconds) {
        createFireball(diffSeconds);

        // delete HelpText after ... seconds
        if (helpText != null) {
            lifeHelpText -= diffSeconds;
            if (lifeHelpText < 0) {
                textObjects.remove(helpText);
                helpText = null;
            }
        }

    
    }

      protected void createNewZombies(double diffSeconds) {

    
        double INTERVAL = level <= 7 ? GlobalConsts.SPAWN_ZOMBIE_INTERVAL_PER_LEVEL[level-1] : GlobalConsts.SPAWN_ZOMBIE_INTERVAL_PER_LEVEL[6];

      //  System.out.println("Zombie Spawn INTERVAL: " + INTERVAL);

        spawnZombie += diffSeconds;
        if (spawnZombie > INTERVAL) {
            spawnZombie -= INTERVAL;

            createZombie(diffSeconds);
        }


        //Level UP after ... seconds
         levelUpTime -= diffSeconds;
            if (levelUpTime < 0) {
                level++;
                counterL.increment();
                levelUpTime = GlobalConsts.LEVEL_UP_TIME;
            }

    }


    private void createFireball(double diffSeconds) {
        final double INTERVAL = GlobalConsts.SPAWN_FIREBALL;

        spawnFireball += diffSeconds;
        if (spawnFireball > INTERVAL) {
            spawnFireball -= INTERVAL;

            // create new Fireball
            double x = worldPartX + Math.random() * GlobalConsts.WORLDPART_WIDTH;
            double y = worldPartY + Math.random() * GlobalConsts.WORLDPART_HEIGHT;


            // if too close to Avatar, cancel
            double dx = x - avatar.x;
            double dy = y - avatar.y;
            if (dx * dx + dy * dy < 200 * 200) {
                spawnFireball = INTERVAL;
                return;
            }


            // if collisions occur, cancel
            FireBall fireball = new FireBall(x, y);
            A_GameObjectList list = getPhysicsSystem().getCollisions(fireball);
            if (list.size() != 0) {
                spawnFireball = INTERVAL;
                return;
            }

            // add Fireball
            this.gameObjects.add(fireball);
            counterG.setNumber(fireballs);

        }

    }


    private void createZombie(double diffSeconds) {

        //Current interval depending on level
        final double INTERVAL = GlobalConsts.SPAWN_INTERVAL;

        timePassed += diffSeconds;
        if (timePassed > INTERVAL) {
            timePassed -= INTERVAL;

            // create new Zombie; preference to current screen
            double x, y;
            if (Math.random() < 0.7) {
                x = Math.random() * GlobalConsts.WORLD_WIDTH;
                y = Math.random() * GlobalConsts.WORLD_HEIGHT;
            } else {
                x = worldPartX + Math.random() * GlobalConsts.WORLDPART_WIDTH;
                y = worldPartY + Math.random() * GlobalConsts.WORLDPART_HEIGHT;
            }


            // if too close to Avatar, cancel
            double dx = x - avatar.x;
            double dy = y - avatar.y;
            if (dx * dx + dy * dy < 400 * 400) {
                timePassed = INTERVAL;
                return;
            }

            // if collisions occur, cancel
            Gam20_ZombieAI zombie = new Gam20_ZombieAI(x, y);
            A_GameObjectList list = getPhysicsSystem().getCollisions(zombie);
            if (list.size() != 0) {
                timePassed = INTERVAL;
                return;
            }

            // else add zombie to world
            this.gameObjects.add(zombie);
            zombie.setDestination(avatar);
            Gam20_CounterEnemies counter = (Gam20_CounterEnemies) textObjects.get(0);
            counter.increment();
        }

    }


    public void addFireball() {
        if (fireballs < 999) {
            fireballs++;
        }
        counterG.setNumber(fireballs);
    }

}
