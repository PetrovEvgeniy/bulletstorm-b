
import java.awt.Color;

/**
 * Base class for text objects (maybe tips, tutorials etc. ?)
 */
abstract class A_TextObject
{
  protected static A_World world;
  
  // Public so we can access it easier and faster because it is accessed all the time
  protected int     x,y;
  protected Color color;
  
  public A_TextObject(int x_, int y_, Color color_)
  { x=x_; y=y_; color=color_;
  }
  
  public abstract String toString();
  
  protected static void setWorld(A_World w){world=w;}
}
