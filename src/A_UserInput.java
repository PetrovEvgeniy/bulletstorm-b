import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class holds the key and mouse moved. Variables will prob be updated
 */
final class A_UserInput {
    // Everything a user can press on keyboard or mouse
    int mousePressedX, mousePressedY,
            mouseMovedX, mouseMovedY, mouseButton;

    HashSet<Character> keyPressed = new HashSet<>();
    int index = 0;

    // If the mouse was clicked, key was pressed or mouse is still hold down
    boolean isMouseEvent, isKeyEvent, isMousePressed;

    // ... is returned as a data set
    A_UserInput() {
        this.clear();
    }


    // VERY IMPORTANT THE GAME IS MORE SMOOTH WITHOUT isKeyEvent = false
    // IF SOMETHING BREAKS UNCOMMENT IT
    final void clear() {
        isMouseEvent = false;
      //  isKeyEvent = false;

    }
}
