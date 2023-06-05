/**
 * Interface defines methods for the frame
 */
interface A_Frame 
{
  /**
  * Appear on screen
  */
  void displayOnScreen();
  
  /**
  * Getters for graphic subsystems
  */
  A_GraphicSystem getGraphicSystem();
  A_InputSystem   getInputSystem();
}
