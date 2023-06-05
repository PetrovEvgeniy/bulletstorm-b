
/**
 * This class holds the key and mouse moved. Variables will prob be updated
 */
final class A_UserInput 
{
  // Everything a user can press on keyboard or mouse	
  int mousePressedX, mousePressedY, 
      mouseMovedX,   mouseMovedY, mouseButton;
  
  char keyPressed;
  
  // If the mouse was clicked, key was pressed or mouse is still hold down
  boolean isMouseEvent, isKeyEvent, isMousePressed; 
  
  // ... is returned as a data set
  A_UserInput()
  { this.clear();
  }
  
  final void clear()
  { isMouseEvent=false; isKeyEvent=false;
  }
}
