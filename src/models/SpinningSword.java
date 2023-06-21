package models;

import abstracts.A_World;
import abstracts.GameObject;

import java.awt.*;

public class SpinningSword extends GameObject {

    public SpinningSword(double x_, double y_, double a_, double s_, String pathToImage) {
        super(x_, y_, a_, s_, pathToImage);
    }

    @Override
    public void draw(Graphics graphics, A_World world) {

    }

    @Override
    public int type() {
        return 0;
    }

    @Override
    public Shape getBounds() {
        return null;
    }
}
