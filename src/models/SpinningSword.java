package models;

import abstracts.A_GameObjectList;
import abstracts.A_World;
import abstracts.GameObject;
import utils.GlobalConsts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SpinningSword extends GameObject {
    private double lifeTime = 20;
    private int spriteCounter = 0;
    private int spriteNumber = 0;
    private int counter;

    private static int ROTATION_INCREMENT = 20;
    private BufferedImage img;
    ArrayList<BufferedImage> shootingAnimation;
    GameObject avatar;



    public SpinningSword(String pathToImage, GameObject avatar) {
        super(avatar.x - A_World.worldPartX + avatar.width/2 + 120,avatar.y - A_World.worldPartY + avatar.height/2 + 120, 0, 200, pathToImage);
        this.isMoving = true;
        this.isLiving = true;
        shootingAnimation = new ArrayList<>();
        width = 22;
        height = 109;
        counter = 0;
        this.avatar = avatar;
        this.radius = (int) Math.sqrt(width*width + height*height);
        try {
            img = ImageIO.read(new File(pathToImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    public SpinningSword (double x, double y,String path); {
//        super(x, y,0,200, path);   //Color in case there's issue with loading the animation
//        this.isMoving = true;
//        shootingAnimation = new ArrayList<>();
//        width = 79;
//        height = 72;
//        this.avatar = avatar;
//        this.radius = (int) Math.sqrt(width*width + height*height);
//        try {
//            img = ImageIO.read(new File("resourses/sprites/Sword/SwordH"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        //loadAnimations();
//    }

    private void loadAnimations() {
        String path = "resourses/sprites/Sword";
        for (int i = 1; i < 5; i++) {
            String fileName = "Sword" + i + ".png";
            try {
                shootingAnimation.add(ImageIO.read(new File(path + "/" + fileName)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void draw(Graphics graphics, A_World world) {
        if(counter >= 360) {
//            this.isLiving = false;
//            this.isMoving = false;
        }
//        Graphics2D g2d = (Graphics2D) graphics;
        double pointX = avatar.x + avatar.width/2;
        double pointY = avatar.y + avatar.height/2;

//        AffineTransform originalTransform = g2d.getTransform();
//        g2d.translate(pointX, pointY);
//
//        // Apply rotation transformation
//        g2d.rotate(counter*ROTATION_INCREMENT);
        counter++;



        // Translate back to the original position

    //    g2d.setTransform(originalTransform);

        int x = (int) (this.x  - world.worldPartX);
        int y = (int) (this.y  - world.worldPartY);





////
        graphics.drawImage(img, (int) x, (int) y, null);
        this.x =  (avatar.x + 120 * Math.cos(counter*ROTATION_INCREMENT));
        this.y =  (avatar.y + 120 * Math.sin(counter*ROTATION_INCREMENT));
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
                //this.isLiving = false;
            }
            // [Zombie]: inform Zombie it is hit
            else if (type == GlobalConsts.TYPE_ZOMBIE && obj.isLiving) {
                ZombieAI zombie = (ZombieAI) obj;
                zombie.hasBeenShot();
                this.isLiving = false;
            }
        }

        super.move(diffSeconds);
    }
    @Override
    public int type() {
        return GlobalConsts.TYPE_SHOT;
    }

    @Override
    public Shape getBounds() {
        int x = (int) (this.x - world.worldPartX);
        int y = (int) (this.y - world.worldPartY);
        return new Rectangle((int) x, (int) y, width, height);
    }
}
