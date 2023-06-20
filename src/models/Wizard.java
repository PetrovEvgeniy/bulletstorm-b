package models;

import abstracts.A_GameObjectList;
import abstracts.A_World;
import abstracts.GameObject;
import utils.Gam20_World;
import utils.GlobalConsts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Wizard extends GameObject {

    ArrayList<BufferedImage> spriteAnimation;
    int spriteCounter = 0;
    int spriteNumber = 0;

    public Wizard(double x_, double y_, String pathToImage) {
        super(x_, y_, 0, 200, pathToImage);
        this.isMoving = false;
        spriteAnimation = new ArrayList<>();
        loadAllImages("resourses/sprites/fire_wizard/idle");
    }


    @Override
    public void draw(Graphics graphics, A_World world) {
        int x = (int) (this.x  - world.worldPartX);
        int y = (int) (this.y  - world.worldPartY);

        if(spriteCounter > 10) {
            spriteNumber++;
            if(spriteNumber >= spriteAnimation.size()) {
                spriteNumber = 0;
            }
            spriteCounter = 0;
            }
        spriteCounter++;
        graphics.drawRect(x, y, width, height);
        graphics.drawImage(spriteAnimation.get(spriteNumber).getScaledInstance(width, height, Image.SCALE_FAST), (int) x, (int) y,width,height, null);
                                                                                                    //Changing argument width here ^ to -width flips character horizontally
    }

    /**
     * Loads all pics of the sprite animation into the arrayList
     */
    public void loadAllImages(String pathToFolder){
        for(int i = 1; i < 8;i++){
            String fileName = "wizard_" + i + ".png";
            try {
                spriteAnimation.add(ImageIO.read(new File(pathToFolder + "/" + fileName)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }

        }
    }
    public void move(double x, double y ) {
        // Move Avatar one step forward
        super.move(x, y);

        // Calculate all collisions with other Objects
        A_GameObjectList collisions = world.getPhysicsSystem().getCollisions(this);
        for (int i = 0; i < collisions.size(); i++) {
            GameObject obj = collisions.get(i);

            // If Object is a tree, move back one step
            if (obj.type() == GlobalConsts.TYPE_TREE) {
                this.moveBack();
            }

            // TODO: Do the same for other obstacles...


            // Pick up Grenades
            else if (obj.type() == GlobalConsts.TYPE_GRENADE) {
                ((Gam20_World) world).addGrenade();
                obj.isLiving = false;
            }
        }
    }


    public Shape getBounds(){
        int x = (int) (this.x  - world.worldPartX);
        int y = (int) (this.y  - world.worldPartY);
        return new Rectangle((int) x, (int) y, width, height);
    }



    public int type() {
        return GlobalConsts.TYPE_AVATAR;
    }
}
