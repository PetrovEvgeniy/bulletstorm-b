
/**
 * Interface for implementing the methods for the graphic system
 */
interface A_GraphicSystem 
{
  // Clear the current screen and prepares to draw a new screen
  void clear();
  
  // Draw a single GameObject on the screen
  void draw(GameObject dot);
 
  // Draw a single TextObject on the screen
  void draw(A_TextObject obj);
  
  // Display the completed screen
  void redraw();
  
  // Set the world
  void setWorld(A_World world);  
}
