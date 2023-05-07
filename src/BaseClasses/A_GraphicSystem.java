package BaseClasses;

/**
 * Interface for implementing the methods for the graphic system
 */
public interface A_GraphicSystem {

    // Clear the current screen and prepares to draw a new screen
    void clear();

    // Draw a single GameObject on the screen
    void draw(A_GameObject obj);

    // Draw a single TextObject on the screen
    void draw(A_TextObject txtObj);

    // Display the completed screen
    void redraw();

}
