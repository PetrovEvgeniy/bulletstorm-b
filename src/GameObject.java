

import java.awt.Color;

abstract class GameObject {
    // Public so we can access it easier and faster because it is accessed all the time
    protected double x, y;
    protected double alfa = 0;
    protected double speed = 0;
    protected int radius = 7;
    protected Color color;

    // If the object is existing, moving etc
    protected boolean isLiving = true;
    protected boolean isMoving = true;

    // Destination the object will move to. Also, old coordinates
    private double destX, destY;
    private boolean hasDestination = false;
    private double xOld, yOld;


    // Objects for calling the physics methods
    protected static A_World world;


    // Constructor
    public GameObject(double x_, double y_,
                      double a_, double s_,
                      int radius_, Color color_) {
        x = x_;
        y = y_;
        xOld = x;
        yOld = y;
        alfa = a_;
        speed = s_;
        radius = radius_;
        color = color_;
    }


    /**
     * Method that's called when the object has to move in direction of alfa
     */
    public void move(double diffSeconds) {
        if (!isMoving) return;

        // Stop if destination is reached
        if (hasDestination) {
            double diffX = Math.abs(x - destX);
            double diffY = Math.abs(y - destY);
            if (diffX < 3 && diffY < 3) {
                isMoving = false;
                return;
            }
        }

        // Setting it as the old position so its remembered
        xOld = x;
        yOld = y;

        // Finally, moving one step
        x += Math.cos(alfa) * speed * diffSeconds;
        y += Math.sin(alfa) * speed * diffSeconds;
    }


    /**
     * Collides with border on screen. In hindsight, we probably won't even need this, so it's just for testing here
     */
    protected void reflectOnBorders() {
        double rad = radius;
        double PI = Math.PI;

        if (x < rad && (alfa > PI / 2 && alfa < PI * 3 / 2)) {
            alfa = Math.PI - alfa;
        }
        if (y < rad && alfa > PI) {
            alfa = PI * 2 - alfa;
        }
        if (x > GlobalConsts.WORLD_WIDTH - rad) {
            alfa = Math.PI - alfa;
        }
        if (y > GlobalConsts.WORLD_HEIGHT - rad) {
            alfa = PI * 2 - alfa;
        }


        if (alfa < 0) alfa += PI * 2;
        if (alfa > PI * 2) alfa -= PI * 2;
    }


    /**
     * Sets the destination using atan2 method
     */
    public final void setDestination(double dx, double dy) {
        isMoving = true;
        hasDestination = true;
        destX = dx;
        destY = dy;

        alfa = Math.atan2(dy - y, dx - x);
    }


    /**
     * Overwritten setDestination method which takes an object as input and calls the other setDestination method with its properties
     */
    public void setDestination(GameObject obj) {
        setDestination(obj.x, obj.y);
    }


    /**
     * Moves back to old position
     */
    protected void moveBack() {
        x = xOld;
        y = yOld;
    }


    abstract int type();

    static void setWorld(A_World w) {
        world = w;
    }

}
