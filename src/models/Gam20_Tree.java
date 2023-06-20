package models;

import abstracts.A_World;
import abstracts.GameObject;
import utils.GlobalConsts;

import java.awt.*;

public class Gam20_Tree extends GameObject
{
  public Gam20_Tree(double x, double y, int r)
  {
    super(x,y,0,0,r,new Color(64,160,64));
    this.isMoving = false;
  }
  
  public int type() { return GlobalConsts.TYPE_TREE; }


  public void draw(Graphics graphics, A_World world) {
    int x = (int) (this.x  - world.worldPartX);
    int y = (int) (this.y  - world.worldPartY);


    graphics.drawRect(x, y, width, height);

    //Changing argument width here ^ to -width flips character horizontally
  }
  @Override
  public Shape getBounds() {
    return new Rectangle((int) x, (int) y, radius, radius);
  }
}
