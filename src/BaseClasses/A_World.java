package BaseClasses;

import ZOMBIESFUCKSHITFUCK.PhysicsEngine;
import java.util.ArrayList;

public abstract class A_World {

    private A_GraphicSystem graphicSystem;
    private A_PhysicsSystem physicsSystem;
    private A_UserInput userInput;
    private A_InputSystem inputSystem;

    private static final int FRAME_MINIMUM_MILLIS = 10; //  If 10 then each frame will be 10 milliseconds min

    // All game objects
    A_GameObject mainCharacter;
    public A_GameObjectList gameObjects = new A_GameObjectList();
    ArrayList<A_TextObject> textObjects = new ArrayList<>();

    public A_World(){
        physicsSystem = new PhysicsEngine(this);
    }


    /**
     * Main loop of the game. Very Important yes
     */
    public final void run(){
        long lastTick = System.currentTimeMillis();

        while(true){
            // Calculating elapsed time so that each frame doesn't run faster than min diff seconds *per frame*
            long currentTick = System.currentTimeMillis();
            long difference = currentTick - lastTick;
            //That time is defined in the int FRAME_MINIMUM_MILLIS
            if(difference < FRAME_MINIMUM_MILLIS){
                try{
                    Thread.sleep(FRAME_MINIMUM_MILLIS - difference);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                currentTick = System.currentTimeMillis();
                difference = currentTick - lastTick;
            }
            lastTick = currentTick;


            // Processing user input here
            userInput = inputSystem.getUserInput();
            processUserInput(userInput,difference/1000.0);
            userInput.clear();

            // Moving all objects. Zombies/Enemies will follow the player in their move method
            int numberOfObjects = gameObjects.size();
            for(int i = 0; i < numberOfObjects; i++){
                A_GameObject temp = gameObjects.get(i);
                if(temp.isLiving) temp.move(difference/1000.0);
            }

            // Delete the dead objects
            int num = 0;
            while(num < numberOfObjects){
                if(!gameObjects.get(num).isLiving){
                    gameObjects.remove(num);
                    numberOfObjects--;
                }else num++;
            }

            // Draw whatever objects needs to be drawn
            graphicSystem.clear();
            for(int i = 0; i < numberOfObjects;i++){
                graphicSystem.draw(gameObjects.get(i));
            }

            // Draw text objects. Not sure what it is, but I added the base class so will test later
            for(int i = 0; i < textObjects.size();i++){
                graphicSystem.draw(textObjects.get(i));
            }

            // Redraw everything here
            graphicSystem.redraw();

            // Spawn any new objects here
            createNewObjects(difference/1000.0);
        }
    }

    protected void setGraphicSystem(A_GraphicSystem p) { graphicSystem = p; }
    protected void setInputSystem(A_InputSystem p)     { inputSystem   = p; }

    protected A_PhysicsSystem getPhysicsSystem()       { return physicsSystem; }

    //Some abstract methods for classes that extend this
    protected abstract void init();
    protected abstract void processUserInput(A_UserInput input, double diffSec);
    protected abstract void createNewObjects(double diffSeconds);
    protected abstract void gameOver();
}
