package models;

import abstracts.A_World;
import utils.GlobalConsts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

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
        BufferedImage sub = this.background.getSubimage((int) A_World.worldPartX, (int) A_World.worldPartY, GlobalConsts.WORLDPART_WIDTH, GlobalConsts.WORLDPART_HEIGHT);
        graphics.drawImage(sub, 0, 0, null);
    }


}
