package abstracts;

import java.util.HashSet;

/**
 * This class holds the key and mouse moved. Variables will prob be updated
 */
public final class A_UserInput {
    // Everything a user can press on keyboard or mouse
    public int mousePressedX;
    public int mousePressedY;
    public int mouseMovedX;
    public int mouseMovedY;
    public int mouseButton;

    public HashSet<Character> keyPressed = new HashSet<>();


    // If the mouse was clicked, key was pressed or mouse is still hold down
    public boolean isMouseEvent;
    public boolean isKeyEvent;
    public boolean isMousePressed;

    // ... is returned as a data set
    public A_UserInput() {
        this.clear();
    }


    // VERY IMPORTANT THE GAME IS MORE SMOOTH WITHOUT isKeyEvent = false
    // IF SOMETHING BREAKS UNCOMMENT IT
    final void clear() {
        isMouseEvent = false;
      //  isKeyEvent = false;

    }
}
