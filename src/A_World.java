
import java.util.ArrayList;

abstract class A_World
{
  private  A_GraphicSystem graphicSystem;
  private  A_PhysicsSystem physicsSystem;
  private  A_InputSystem   inputSystem;  
  private  A_UserInput     userInput;
  
  // Top left corner of the displayed pane of the world
  double worldPartX = 0;
  double worldPartY = 0;
  
  // Define maximum frame rate
  private static final int FRAME_MINIMUM_MILLIS = 10;
  
  // Define if game is over
  boolean gameOver = false;
  
  // All objects in the game, including the Avatar
  A_GameObjectList        gameObjects = new A_GameObjectList();
  GameObject avatar;
  ArrayList<A_TextObject> textObjects = new ArrayList<A_TextObject>();
    
  
  A_World()
  { physicsSystem = new Gam20_PhysicsSystem(this);
  }
  
  
  //
  // The main GAME LOOP
  //
  final void run()
  {
	long lastTick =  System.currentTimeMillis();
	
	while(true)
	{
	  // Calculating elapsed time so that each frame doesn't run faster than min diff seconds *per frame*
	  long currentTick = System.currentTimeMillis();
	  long millisDiff  = currentTick-lastTick;
	  
	  //That time is defined in the int FRAME_MINIMUM_MILLIS
	  if(millisDiff<FRAME_MINIMUM_MILLIS)
	  {
	    try{ Thread.sleep(FRAME_MINIMUM_MILLIS-millisDiff);} catch(Exception ex){}
		currentTick = System.currentTimeMillis();
		millisDiff  = currentTick-lastTick;
	  }
	  
	  lastTick = currentTick;

	  
	   // Processing user input here
	  userInput = inputSystem.getUserInput();
	  processUserInput(userInput,millisDiff/1000.0);
	  userInput.clear();

	  // No actions if game is over
	  if(gameOver) { continue;}
	  
	   // Moving all objects. Zombies/Enemies will follow the player in their move method
	  int gameSize = gameObjects.size();
	  for(int i=0; i<gameSize; i++)
	  { 
        GameObject obj = gameObjects.get(i);
        if(obj.isLiving)  obj.move(millisDiff/1000.0);
	  }
	  
	  
      // Delete the dead objects
      int num=0;
      while(num<gameSize)
      {
        if(gameObjects.get(num).isLiving==false)
        { gameObjects.remove(num);
          gameSize--;
        }
        else
        { num++;
        }
      }	  
	  
      
      // Adjust displayed pane of the world
      this.adjustWorldPart();
      
      // Draw whatever objects needs to be drawn
      graphicSystem.clear();
      for(int i=0; i<gameSize; i++)
      { graphicSystem.draw(gameObjects.get(i));
      }

	  
    // Draw text objects
	  for(int i=0; i<textObjects.size(); i++)
	  { graphicSystem.draw(textObjects.get(i));
	  }
	  
	  // Redraw everything here
	  graphicSystem.redraw();
	  	  
	  // Spawn any new objects here
	  createNewObjects(millisDiff/1000.0);
	}
  }
  
  
  // Adjust the displayed pane of the world according to Avatar and Bounds
  //
  private final void adjustWorldPart()
  {
    final int RIGHT_END  = GlobalConsts.WORLD_WIDTH- GlobalConsts.WORLDPART_WIDTH;
    final int BOTTOM_END = GlobalConsts.WORLD_HEIGHT- GlobalConsts.WORLDPART_HEIGHT;
	  	  
    
	// If avatar is too much RIGHT in display ...
    if(avatar.x > worldPartX+ GlobalConsts.WORLDPART_WIDTH- GlobalConsts.SCROLL_BOUNDS)
    {
      // ... adjust display
      worldPartX = avatar.x+ GlobalConsts.SCROLL_BOUNDS- GlobalConsts.WORLDPART_WIDTH;
      if(worldPartX >= RIGHT_END)
      { worldPartX = RIGHT_END;
      }
    }
    
    // Same on the LEFT side
    else if(avatar.x < worldPartX+ GlobalConsts.SCROLL_BOUNDS)
    {
      worldPartX = avatar.x- GlobalConsts.SCROLL_BOUNDS;
      if(worldPartX <=0)
      { worldPartX = 0;
      }
    }
    
    // Same at the BOTTOM
    if(avatar.y > worldPartY+ GlobalConsts.WORLDPART_HEIGHT- GlobalConsts.SCROLL_BOUNDS)
    {
        worldPartY = avatar.y+ GlobalConsts.SCROLL_BOUNDS- GlobalConsts.WORLDPART_HEIGHT;
        if(worldPartY >= BOTTOM_END)
        { worldPartY = BOTTOM_END;
        }   	
    }
    
    // Same at the TOP
    else if(avatar.y < worldPartY+ GlobalConsts.SCROLL_BOUNDS)
    {
      worldPartY = avatar.y- GlobalConsts.SCROLL_BOUNDS;
      if(worldPartY <=0)
      { worldPartY = 0;
      }
    }    
    
  }
  
    
  protected void setGraphicSystem(A_GraphicSystem p) { graphicSystem = p; }
  protected void setInputSystem(A_InputSystem p)     { inputSystem   = p; }
  
  protected A_PhysicsSystem getPhysicsSystem()       { return physicsSystem; }
  
  
  protected abstract void init();
  protected abstract void processUserInput(A_UserInput input, double diffSec);
  protected abstract void createNewObjects(double diffSeconds);
  
}
