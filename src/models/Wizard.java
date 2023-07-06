package models;

import abstracts.A_GameObjectList;
import abstracts.A_World;
import abstracts.GameObject;
import utils.Main_World;
import utils.GlobalConsts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Wizard extends GameObject {

    ArrayList<BufferedImage> idleAnimation;
    ArrayList<BufferedImage> runningAnimation;
    ArrayList<BufferedImage> shootingAnimation;
    ArrayList<BufferedImage> currentAnimation;
    int spriteCounter = 0;
    int spriteNumber = 0;
    private static final int shiftRight = 15;
    public Wizard(double x_, double y_, String pathToImage) {
        super(x_, y_, 0, 250, pathToImage);
        this.isMoving = false;
        idleAnimation = new ArrayList<>();
        runningAnimation = new ArrayList<>();
        shootingAnimation = new ArrayList<>();
        currentAnimation = new ArrayList<>();
        loadAllImages("resourses/sprites/fire_wizard");
    }


    @Override
    public void draw(Graphics graphics, A_World world) {
        if(isShooting){
            currentAnimation = shootingAnimation;
        }else {
            if (!isMoving) {
                currentAnimation = idleAnimation;
            } else {
                currentAnimation = runningAnimation;
            }
        }
        int x = (int) (this.x - world.worldPartX);
        int y = (int) (this.y - world.worldPartY);

        if (spriteCounter > 10) {
            spriteNumber++;
            if (spriteNumber >= currentAnimation.size()) {
                spriteNumber = 0;
            }
            spriteCounter = 0;
        }
        spriteCounter++;

        if (!isFacingRight) {
            //I put try catch blocks here because some sprite lists are larger so to dodge IndexOutOfBoundsException
            try {
                graphics.drawImage(currentAnimation.get(spriteNumber).getScaledInstance(width, height, Image.SCALE_FAST), (int) x + width, (int) y, -width, height, null);
            } catch (Exception e) {

            }
        } else {
            try {
                graphics.drawImage(currentAnimation.get(spriteNumber).getScaledInstance(width, height, Image.SCALE_FAST), (int) x, (int) y, width, height, null);
            } catch (Exception e) {

            }                                                                                      //Changing argument width here ^ to -width flips character horizontally
        }
//        if(this.isFacingRight == true)
//            graphics.drawRect(x+shiftRight, y, width-shiftRight-5, height);
//        else
//            graphics.drawRect(x, y, width-shiftRight-5, height);
        isMoving = false;
    }

    /**
     * Loads all pics of the sprite animation into the arrayList
     */
    public void loadAllImages(String pathToFolder) {
        //First we load all idle animation images
        for (int i = 1; i < 8; i++) {
            String fileName = "idle/wizard_" + i + ".png";
            try {
                idleAnimation.add(ImageIO.read(new File(pathToFolder + "/" + fileName)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        //Then loading the attacking animation images
        for (int i = 1; i < 6; i++) {
            String fileName = "attacking/shooting_" + i + ".png";
            try {
                shootingAnimation.add(ImageIO.read(new File(pathToFolder + "/" + fileName)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        //Lastly, loading all running animation images
        for (int i = 1; i < 9; i++) {
            String fileName = "running/running_" + i + ".png";
            try {
                runningAnimation.add(ImageIO.read(new File(pathToFolder + "/" + fileName)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

    }

    public void move(double x, double y) {
        //If moving right sets it to true

        // Move Avatar one step forward

        super.move(x, y);


        // Calculate all collisions with other Objects
        A_GameObjectList collisions = world.getPhysicsSystem().getCollisions(this);
        for (int i = 0; i < collisions.size(); i++) {
            GameObject obj = collisions.get(i);


            // If Object is a tree, move back one step
            if (obj.type() == GlobalConsts.TYPE_TREE || obj.type() == GlobalConsts.TYPE_ROCK) {
                this.moveBack();
            }

            // TODO: Do the same for other obstacles...


            // Pick up Fireball
            else if (obj.type() == GlobalConsts.TYPE_FIREBALL) {

                //Play pickup sound 
               ((Main_World) world).mkSoundSystem.playSound("pickup");

                //Add Fireball to Inventory
                ((Main_World) world).addFireball();


                // Remove Fireball from World
                obj.isLiving = false;
            }
        }

    }


    public Shape getBounds() {
        int x = (int) (this.x - world.worldPartX);
        int y = (int) (this.y - world.worldPartY);
        if (this.isFacingRight == true)
        return new Rectangle((int) x+shiftRight, (int) y, width-shiftRight-5, height);
        else
            return new Rectangle((int) x, (int) y, width-shiftRight-5, height);
    }


    public int type() {
        return GlobalConsts.TYPE_AVATAR;
    }
}
