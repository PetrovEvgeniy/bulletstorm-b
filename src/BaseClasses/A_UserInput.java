package BaseClasses;

/**
 * This class holds the key and mouse moved. Variables will prob be updated
 */
public final class A_UserInput {

    int mousePressedX, mousePressedY, mouseMovedX, mouseMovedY, mouseButton;

    char keyPressed;

    boolean isMouseEvent, isKeyEvent, isMousePressed;

    public A_UserInput(){
        this.clear();
    }

    final void clear(){
        isMouseEvent = false;
        isKeyEvent = false;
    }
}
