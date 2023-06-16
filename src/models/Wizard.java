package models;

import abstracts.A_World;
import abstracts.GameObject;
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

        int x = (int) (this.x - this.radius - world.worldPartX);
        int y = (int) (this.y - this.radius - world.worldPartY);

        if(spriteCounter > 10) {
            spriteNumber++;
            if(spriteNumber >= spriteAnimation.size()) {
                spriteNumber = 0;
            }
            spriteCounter = 0;
            }
        spriteCounter++;
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

    public int type() {
        return GlobalConsts.TYPE_AVATAR;
    }
}
