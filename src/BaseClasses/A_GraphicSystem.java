package BaseClasses;

/**
 * Interface for implementing the methods for the graphic system
 */
public interface A_GraphicSystem {

    void clear();

    void draw(A_GameObject obj);

    void draw(A_TextObject txtObj);

    void redraw();

}
