
// (c) Thorsten Hasbargen


import java.util.ArrayList;

abstract class A_World
{
  private  A_GraphicSystem graphicSystem;
  private  A_PhysicsSystem physicsSystem;
  private  A_InputSystem   inputSystem;  
  private  A_UserInput     userInput;
  
  // top left corner of the displayed pane of the world
  double worldPartX = 0;
  double worldPartY = 0;
  
  // defines maximum frame rate
  private static final int FRAME_MINIMUM_MILLIS = 10;
  
  // if game is over
  boolean gameOver = false;
  
  // all objects in the game, including the Avatar
  A_GameObjectList        gameObjects = new A_GameObjectList();
  GameObject avatar;
  ArrayList<A_TextObject> textObjects = new ArrayList<A_TextObject>();
    
  
  A_World()
  { physicsSystem = new Gam20_PhysicsSystem(this);
  }
  
  
  //
  // the main GAME LOOP
  //
  final void run()
  {
	long lastTick =  System.currentTimeMillis();
	
	while(true)
	{
	  // calculate elapsed time
	  //
	  long currentTick = System.currentTimeMillis();
	  long millisDiff  = currentTick-lastTick;
	  
	  // don�t run faster then MINIMUM_DIFF_SECONDS per frame
	  //
	  if(millisDiff<FRAME_MINIMUM_MILLIS)
	  {
	    try{ Thread.sleep(FRAME_MINIMUM_MILLIS-millisDiff);} catch(Exception ex){}
		currentTick = System.currentTimeMillis();
		millisDiff  = currentTick-lastTick;
	  }
	  
	  lastTick = currentTick;

	  
	  // process User Input
	  userInput = inputSystem.getUserInput();
	  processUserInput(userInput,millisDiff/1000.0);
	  userInput.clear();

	  // no actions if game is over
	  if(gameOver) { continue;}
	  
	  // move all Objects, maybe collide them etc...
	  int gameSize = gameObjects.size();
	  for(int i=0; i<gameSize; i++)
	  { 
        GameObject obj = gameObjects.get(i);
        if(obj.isLiving)  obj.move(millisDiff/1000.0);
	  }
	  
	  
      // delete all Objects which are not living anymore
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
	  
      
      // adjust displayed pane of the world
      this.adjustWorldPart();
      
      // draw all Objects
      graphicSystem.clear();
      for(int i=0; i<gameSize; i++)
      { graphicSystem.draw(gameObjects.get(i));
      }

	  
      // draw all TextObjects
	  for(int i=0; i<textObjects.size(); i++)
	  { graphicSystem.draw(textObjects.get(i));
	  }
	  
	  // redraw everything
	  graphicSystem.redraw();
	  	  
	  // create new objects if needed
	  createNewObjects(millisDiff/1000.0);
	}
  }
  
  
  // adjust the displayed pane of the world according to Avatar and Bounds
  //
  private final void adjustWorldPart()
  {
    final int RIGHT_END  = GlobalConsts.WORLD_WIDTH- GlobalConsts.WORLDPART_WIDTH;
    final int BOTTOM_END = GlobalConsts.WORLD_HEIGHT- GlobalConsts.WORLDPART_HEIGHT;
	  	  
    
	// if avatar is too much right in display ...
    if(avatar.x > worldPartX+ GlobalConsts.WORLDPART_WIDTH- GlobalConsts.SCROLL_BOUNDS)
    {
      // ... adjust display
      worldPartX = avatar.x+ GlobalConsts.SCROLL_BOUNDS- GlobalConsts.WORLDPART_WIDTH;
      if(worldPartX >= RIGHT_END)
      { worldPartX = RIGHT_END;
      }
    }
    
    // same left
    else if(avatar.x < worldPartX+ GlobalConsts.SCROLL_BOUNDS)
    {
      worldPartX = avatar.x- GlobalConsts.SCROLL_BOUNDS;
      if(worldPartX <=0)
      { worldPartX = 0;
      }
    }
    
    // same bottom
    if(avatar.y > worldPartY+ GlobalConsts.WORLDPART_HEIGHT- GlobalConsts.SCROLL_BOUNDS)
    {
        worldPartY = avatar.y+ GlobalConsts.SCROLL_BOUNDS- GlobalConsts.WORLDPART_HEIGHT;
        if(worldPartY >= BOTTOM_END)
        { worldPartY = BOTTOM_END;
        }   	
    }
    
    // same top
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
