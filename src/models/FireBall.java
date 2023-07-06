package models;

import abstracts.A_World;
import abstracts.GameObject;
import utils.GlobalConsts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FireBall extends GameObject {
    double life = GlobalConsts.LIFE_FIREBALL;


    public FireBall(double x, double y) {
        super(x, y, 0, 0, 15, "resourses/sprites/foliage/fireball.png");
        this.width = 50;
        this.height = 50;
        this.radius = this.width / 2;

    }

    public void move(double diffSeconds) {
        life -= diffSeconds;
        if (life < 0) {
            this.isLiving = false;
            return;
        }
    }


    public void draw(Graphics graphics, A_World world) {

        int x = (int) (this.x - this.radius - world.worldPartX);
        int y = (int) (this.y - this.radius - world.worldPartY);
        int d = (this.radius * 2);
        graphics.drawImage(objectImage.getScaledInstance(width, height, Image.SCALE_FAST), x, y, null);
    }

    public int type() {
        return GlobalConsts.TYPE_FIREBALL;
    }

    @Override
    public Shape getBounds() {
        int x = (int) (this.x - this.radius - A_World.worldPartX) + this.radius;
        int y = (int) (this.y - this.radius - A_World.worldPartY) + this.radius;
        int d = (this.radius * 2);
        return new Ellipse2D.Double(x, y, d, d);
    }
}
