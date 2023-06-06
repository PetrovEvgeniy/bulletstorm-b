package abstracts;

import abstracts.A_World;
import abstracts.GameObject;

/**
 * Interface for implementing the methods for the graphic system
 */
public interface A_GraphicSystem
{
  // Clear the current screen and prepares to draw a new screen
  void clear();
  
  // Draw a single abstracts.GameObject on the screen
  void draw(GameObject dot);
 
  // Draw a single TextObject on the screen
  void draw(A_TextObject obj);
  
  // Display the completed screen
  void redraw();
  
  // Set the world
  void setWorld(A_World world);
}
