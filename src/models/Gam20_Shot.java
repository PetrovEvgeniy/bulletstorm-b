package models;

import abstracts.A_GameObjectList;
import abstracts.GameObject;
import utils.GlobalConsts;

import java.awt.Color;

public class Gam20_Shot extends GameObject
{ 
  private double lifeTime = 1.2;

  public Gam20_Shot(double x, double y, double xDest, double yDest)
  {
    super(x,y,Math.atan2(yDest-y, xDest-x),500,4,Color.YELLOW);
    this.isMoving = true;
  }
  
  public Gam20_Shot(double x, double y, double a, double s, double time)
  { super(x,y,a,s,4,Color.YELLOW);
    lifeTime = time;
    this.isMoving = true;
  }
  
  
  public void move(double diffSeconds)
  { 
	lifeTime -= diffSeconds;
	if(lifeTime<=0)
	{ this.isLiving=false;
	  return;
	}
	
	
    // Handle collisions of the zombie (dependng on the type of the object)
	A_GameObjectList collisions = world.getPhysicsSystem().getCollisions(this);
	for(int i=0; i<collisions.size(); i++)
	{
	  GameObject obj = collisions.get(i);
	  
	  int type = obj.type();
	  
	  // [Tree]: shot is deleted
	  if(type== GlobalConsts.TYPE_TREE)
	  { this.isLiving=false;
	  }
	  // [Zombie]: inform Zombie it is hit
	  else if(type== GlobalConsts.TYPE_ZOMBIE && obj.isLiving)
	  { 
	    Gam20_ZombieAI zombie = (Gam20_ZombieAI)obj;
	    zombie.hasBeenShot();
        this.isLiving=false;
	  }
	}  
	
	super.move(diffSeconds);
  }
    
  public final int type() { return GlobalConsts.TYPE_SHOT;}
}