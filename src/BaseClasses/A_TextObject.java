package BaseClasses;

/**
 * Base class for text objects (maybe tips, tutorials etc. ?)
 */
abstract class A_TextObject {
    protected static A_World world;

    protected int x,y;
    public A_Shape shape;

    public A_TextObject(int x, int y, A_Shape shape){
        this.x = x;
        this.y = y;
        this.shape = shape;
    }

    public abstract String toString();

    protected static void setWorld(A_World _world){
        world = _world;
    }

}
