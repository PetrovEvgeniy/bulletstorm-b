package models;

import abstracts.A_World;
import abstracts.GameObject;
import utils.GlobalConsts;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Gam20_Grenade extends GameObject
{
  double life = GlobalConsts.LIFE_GRENADE;
  
  public Gam20_Grenade(double x, double y)
  {
    super(x,y,0,0,15,Color.ORANGE);
  }
  
  public void move(double diffSeconds)
  {
    life -= diffSeconds;
    if(life<0)
    { this.isLiving=false;
      return;
    }
    
  }


  public void draw(Graphics graphics, A_World world) {

        int x = (int) (this.x - this.radius - world.worldPartX);
        int y = (int) (this.y - this.radius - world.worldPartY);
        int d = (this.radius * 2);

        if (objectImage == null) {
            graphics.setColor(color);
            graphics.fillOval(x, y, d, d);
            graphics.setColor(Color.DARK_GRAY);
            graphics.drawOval(x, y, d, d);
        } else {
            graphics.drawImage(objectImage.getScaledInstance(width, height, Image.SCALE_FAST), (int) x, (int) y, null);
        }

    }

  public int type() { return GlobalConsts.TYPE_GRENADE; }

  @Override
  public Shape getBounds() {
    int x = (int) (this.x - this.radius - world.worldPartX);
    int y = (int) (this.y - this.radius - world.worldPartY);
    int d = (this.radius * 2);
    return new Ellipse2D.Double(x, y, d, d);
  }
}
