package models;

import abstracts.A_GameObjectList;
import abstracts.A_World;
import abstracts.GameObject;
import utils.Gam20_World;
import utils.GlobalConsts;

import java.awt.*;

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


            // Pick up Fireball
            else if (obj.type() == GlobalConsts.TYPE_FIREBALL) {
                //Add fireball to inventory
                ((Gam20_World) world).addFireball();
                
                //Play pickup sound 
               ((Gam20_World) world).mkSoundSystem.playSound("pickup");


                // Remove Fireball from the world
                obj.isLiving = false;
            }
        }
    }


    public int type() {
        return GlobalConsts.TYPE_AVATAR;
    }

    @Override
    public Shape getBounds() {
        return null;
    }
    public void draw(Graphics graphics, A_World world) {

        int x = (int) (this.x - this.radius - world.worldPartX);
        int y = (int) (this.y - this.radius - world.worldPartY);
        int d = (this.radius * 2);

        if (objectImage == null) {
            graphics.setColor(color);
            graphics.fillOval(x, y, d, d);
            graphics.setColor(Color.DARK_GRAY);
            graphics.drawOval(x, y, d, d);
        } else {
            graphics.drawImage(objectImage.getScaledInstance(width, height, Image.SCALE_FAST), (int) x, (int) y, null);
        }

    }
}
