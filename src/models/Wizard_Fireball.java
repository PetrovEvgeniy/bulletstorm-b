package models;

import abstracts.A_GameObjectList;
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

public class Wizard_Fireball extends GameObject {


    private double lifeTime = 1.2;
    private int spriteCounter = 0;
    private int spriteNumber = 0;


    ArrayList<BufferedImage> shootingAnimation;

    public Wizard_Fireball(double x, double y, double xDest, double yDest) {
        super(x, y, Math.atan2(yDest - y, xDest - x), 500, 4, Color.RED);   //Color in case there's issue with loading the animation
        this.isMoving = true;
        shootingAnimation = new ArrayList<>();
        width = 35;
        height = 25;
        loadAnimations();
    }

    public void loadAnimations() {
        String path = "resourses/sprites/fire_wizard/bullets";
        for (int i = 1; i < 5; i++) {
            String fileName = "bullet_" + i + ".png";
            try {
                shootingAnimation.add(ImageIO.read(new File(path + "/" + fileName)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public void move(double diffSeconds) {
        lifeTime -= diffSeconds;
        if (lifeTime <= 0) {
            this.isLiving = false;
            return;
        }


        // Handle collisions of the zombie (dependng on the type of the object)
        A_GameObjectList collisions = world.getPhysicsSystem().getCollisions(this);
        for (int i = 0; i < collisions.size(); i++) {
            GameObject obj = collisions.get(i);

            int type = obj.type();

            // [Tree]: shot is deleted
            if (type == GlobalConsts.TYPE_TREE) {
                this.isLiving = false;
            }
            // [Zombie]: inform Zombie it is hit
            else if (type == GlobalConsts.TYPE_ZOMBIE && obj.isLiving) {
                Gam20_ZombieAI zombie = (Gam20_ZombieAI) obj;
                zombie.hasBeenShot();
                this.isLiving = false;
            }
        }

        super.move(diffSeconds);
    }

    public final int type() {
        return GlobalConsts.TYPE_SHOT;
    }

    @Override
    public Shape getBounds() {
        int x = (int) (this.x - this.radius - world.worldPartX);
        int y = (int) (this.y - this.radius - world.worldPartY);
        int d = (this.radius * 2);
        return new Ellipse2D.Double(x, y, d, d);
    }


    public void draw(Graphics graphics, A_World world) {

        int x = (int) (this.x - this.radius - world.worldPartX);
        int y = (int) (this.y - this.radius - world.worldPartY);
        int d = (this.radius * 2);


        if (spriteCounter > 10) {
            spriteNumber++;
            if (spriteNumber >= shootingAnimation.size()) {
                spriteNumber = 0;
            }
            spriteCounter = 0;
        }
        spriteCounter++;

        //TODO DELETE WHEN NOT NEEDED
        graphics.setColor(color);
        graphics.fillOval(x, y, d, d);
        graphics.setColor(Color.RED);
        graphics.drawOval(x, y, d, d);
//
        graphics.drawImage(shootingAnimation.get(spriteNumber).getScaledInstance(width, height, Image.SCALE_FAST), (int) x, (int) y, null);
    }

}
