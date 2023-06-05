

abstract class A_PhysicsSystem 
{
  protected A_World world;
  
  public A_PhysicsSystem(A_World w)
  { world = w;
  }

  /**
  * Abstract method so that any class extending this has to define it
  */
  protected abstract A_GameObjectList getCollisions(GameObject object);
  
  
  /**
  * Returns distance between two objects
  */
  protected double distance(double x1, double y1, double x2, double y2)
  {
    double xd = x1-x2;
    double yd = y1-y2;
    return Math.sqrt(xd*xd+yd*yd);
  }
  
  
  /**
  * Moves objects if it collides and gets stuck to object
  */
  public void moveBackToUncollide(GameObject object)
  {
    double dx = Math.cos(object.alfa);
    double dy = Math.sin(object.alfa);
    
    while(true)
    {
      object.x -= dx;
      object.y -= dy;
      
      A_GameObjectList collisions = getCollisions(object);
      if(collisions.size()==0) break;
    }
  }
  
}
