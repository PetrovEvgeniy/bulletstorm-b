package models;

import abstracts.A_World;
import abstracts.GameObject;

import java.awt.*;

public abstract class Foliage extends GameObject {

    public Foliage(double x_, double y_, double alfa, double speed, String pathToImage) {
        super(x_, y_, alfa, speed, pathToImage);
        this.isLiving = true;
        this.isMoving = false;
        this.width = objectImage.getWidth();
        this.height = objectImage.getHeight();
    }

    @Override
    public void draw(Graphics graphics, A_World world) {
        int x = (int) (this.x - world.worldPartX);
        int y = (int) (this.y - world.worldPartY);
        graphics.drawImage(objectImage.getScaledInstance(width, height, Image.SCALE_FAST), x, y, null);
    }

    @Override
    public Shape getBounds() {
        int x = (int) (this.x - world.worldPartX);
        int y = (int) (this.y - world.worldPartY);
        return new Rectangle(x, y, width, height);
    }
}
