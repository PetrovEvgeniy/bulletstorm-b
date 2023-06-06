

import java.awt.event.*;

class B_InputSystem
        implements A_InputSystem, KeyListener, MouseListener, MouseMotionListener {

    // UserInput variables
    private A_UserInput userInput = new A_UserInput();

    public void mousePressed(MouseEvent evt) {
        // when an input event occurs...
        userInput.isMouseEvent = true;
        userInput.mousePressedX = evt.getX();
        userInput.mousePressedY = evt.getY();
        userInput.mouseButton = evt.getButton();
        userInput.isMousePressed = true;
    }

    public void mouseReleased(MouseEvent evt) {
        userInput.isMousePressed = false;
    }


    public void mouseMoved(MouseEvent evt) {
        userInput.mouseMovedX = evt.getX();
        userInput.mouseMovedY = evt.getY();
    }


    public void mouseDragged(MouseEvent evt) {
        userInput.mouseMovedX = evt.getX();
        userInput.mouseMovedY = evt.getY();
    }


    public void keyPressed(KeyEvent evt) {
        userInput.isKeyEvent = true;


        userInput.keyPressed.add(evt.getKeyChar());


     //   System.out.println(evt.getKeyChar());
    }


    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }

    public void mouseClicked(MouseEvent evt) {
    }

    public void keyReleased(KeyEvent evt) {
        userInput.keyPressed.remove(evt.getKeyChar());
    }

    public void keyTyped(KeyEvent evt) {
    }


    public A_UserInput getUserInput() {
        return userInput;
    }
}
