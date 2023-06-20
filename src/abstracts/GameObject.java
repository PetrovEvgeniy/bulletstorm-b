package abstracts;

import utils.GlobalConsts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class GameObject {
    // Public so we can access it easier and faster because it is accessed all the time
    public double x;
    public double y;
    protected double alfa = 0;
    protected double speed = 0;


    protected File imageFile;
    protected BufferedImage objectImage;
    public int height;
    public int width;

    //TODO: remove radius and Color once not needed
    public int radius = 25;
    public Color color;

    // If the object is existing, moving etc
    public boolean isLiving = true;
    protected boolean isMoving = true;

    // Destination the object will move to. Also, old coordinates
    private double destX, destY;
    private boolean hasDestination = false;
    public double xOld, yOld;


    // Objects for calling the physics methods
    protected static A_World world;

    BufferedImage background;

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

    //TODO: Make this the only constructor at some point
    public GameObject(double x_, double y_,
                      double a_, double s_,
                      String pathToImage) {
        x = x_;
        y = y_;
        xOld = x;
        yOld = y;
        alfa = a_;
        speed = s_;
        imageFile = new File(pathToImage);


        try {
            objectImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        //Setting height and width here according to screen size constants. We can change later
        height = GlobalConsts.WORLDPART_HEIGHT / 10;
        width = GlobalConsts.WORLDPART_WIDTH / 18;
    }


    /**
     * Method that's called when the object has to move in direction of alfa
     */
    public void move(double x, double y) {
        this.yOld = this.y;
        this.xOld = this.x;
        this.x += x;
        this.y += y;
    }


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
     * I moved the all the drawing of GameObjects into this base class so its easier to make the sprite animations and overall readability
     */
    public abstract void draw(Graphics graphics, A_World world) ;//{
//
//        int x = (int) (this.x - this.radius - world.worldPartX);
//        int y = (int) (this.y - this.radius - world.worldPartY);
//        int d = (this.radius * 2);
//
//        if (objectImage == null) {
//            graphics.setColor(color);
//            graphics.fillOval(x, y, d, d);
//            graphics.setColor(Color.DARK_GRAY);
//            graphics.drawOval(x, y, d, d);
//        } else {
//            graphics.drawImage(objectImage.getScaledInstance(width, height, Image.SCALE_FAST), (int) x, (int) y, null);
//        }
//
//    }


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


    public abstract int type();

    public static void setWorld(A_World w) {
        world = w;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public Image getImage() {
        return objectImage;
    }

    public void setImage(BufferedImage image) {
        this.objectImage = image;
    }


    public abstract Shape getBounds();

}
