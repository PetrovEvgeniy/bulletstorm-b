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

public class FireBallShoot extends GameObject {
    ArrayList<Image> animationList;
    public double lifetime = 1.5;

    private int spriteCounter = 0;
    private int spriteNumber = 0;

    public FireBallShoot(double x, double y){
        super(x,y,0,0,75,Color.red);
        animationList = new ArrayList<>();
        loadAnimations();
    }

    private void loadAnimations() {
        String path = "resourses/sprites/fire_wizard/fireball";


        for (int i = 1; i < 11; i++) {
            String fileName = "Explosion_" + i + ".png";
            try {
                animationList.add(ImageIO.read(new File(path + "/" + fileName)).getScaledInstance(radius*4,radius*4,Image.SCALE_FAST));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void draw(Graphics graphics, A_World world) {

        int x = (int) (this.x - this.radius - world.worldPartX);
        int y = (int) (this.y - this.radius - world.worldPartY) + 10;
        int d = (this.radius * 2);


        if (spriteCounter > 10) {
            spriteNumber++;
            if (spriteNumber >= animationList.size()) {
                spriteNumber = 0;
            }
            spriteCounter = 0;
        }
        spriteCounter++;

        graphics.drawImage(animationList.get(spriteNumber), (int) x-75, (int) y-80, null);
    }

    @Override
    public int type() {
        return GlobalConsts.TYPE_FIREBALL;
    }

    @Override
    public Shape getBounds() {
        int x = (int) (this.x - this.radius - A_World.worldPartX) + this.radius;
        int y = (int) (this.y - this.radius - A_World.worldPartY) + this.radius;
        int d = (this.radius * 2);
        return new Ellipse2D.Double(x, y, d, d);
    }

    public void move(double diffSeconds) {
        lifetime -= diffSeconds;
        if (lifetime <= 0) {
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
//                this.isLiving = false;
            }
            // [Zombie]: inform Zombie it is hit
            else if (type == GlobalConsts.TYPE_ZOMBIE && obj.isLiving) {
                Gam20_ZombieAI zombie = (Gam20_ZombieAI) obj;
                zombie.hasBeenShot();
//                this.isLiving = false;
            }
        }

        super.move(diffSeconds);
    }
}
