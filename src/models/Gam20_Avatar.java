package models;

import abstracts.A_GameObjectList;
import abstracts.GameObject;
import utils.Gam20_World;
import utils.GlobalConsts;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Gam20_Avatar extends GameObject {



    public Gam20_Avatar(double x, double y) {
        super(x, y, 0, 200, 15, new Color(96, 96, 255));
        this.isMoving = false;
    }

    public Gam20_Avatar(double x, double y, String pathToImage) {
        super(x, y, 0, 200, pathToImage);
        this.isMoving = false;
    }

    public void move(double diffSeconds) {
        // Move Avatar one step forward
        super.move(diffSeconds);

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


    public int type() {
        return GlobalConsts.TYPE_AVATAR;
    }
}
