package models;

import utils.GlobalConsts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background {
    BufferedImage background;
    File pathToBG;

    public Background(String pathToBG){
        this.pathToBG = new File(pathToBG);
        try {
            background = ImageIO.read(this.pathToBG);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void draw(Graphics graphics){
        graphics.drawImage(this.background, 0, 0, null);
    }


}
