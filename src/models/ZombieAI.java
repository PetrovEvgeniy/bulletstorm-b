package models;

import abstracts.A_GameObjectList;
import abstracts.A_World;
import abstracts.GameObject;
import utils.CounterEnemies;
import utils.GlobalConsts;

import java.awt.*;
import java.awt.geom.Ellipse2D;


public class ZombieAI extends GameObject {
    private static final int HUNTING = 1;
    private static final int STUCK = 2;
    private static final int CLEARING = 3;

    private int state;
    private double alfaClear;
    private double secondsClear;

    private Image img;



    // Define the life (health) of a zombie
    private double life = 1.0;


    public ZombieAI(double x, double y) {
        super(x, y, 0, 50, "resourses/sprites/zombie/zombie.png");
        this.isMoving = false;

        state = HUNTING;

           //adjust height
        this.height = GlobalConsts.WORLDPART_HEIGHT / 15;
        this.width = GlobalConsts.WORLDPART_WIDTH / 23;
        // Turn LEFT or RIGHT to clear
        alfaClear = Math.PI;
        if (Math.random() < 0.5) alfaClear = -alfaClear;

        img = objectImage.getScaledInstance(width, height, Image.SCALE_FAST);
    }


    public void move(double diffSeconds) {
        // If avatar is too far away: STOP
        double dist = world.getPhysicsSystem()
                .distance(x, y, world.avatar.x, world.avatar.y);

        if (dist > 800) {
            this.isMoving = false;
            return;
        } else {
            this.isMoving = true;
        }


        // state of zombie: [HUNTING]
        //

        if (state == HUNTING) {
            this.setDestination(world.avatar);

            super.move(diffSeconds);

            if(world.avatar.x < this.x){
                isFacingRight = false;
            }else{
                isFacingRight = true;
            }

            // Handle collisions of the zombie
            A_GameObjectList collisions = world.getPhysicsSystem().getCollisions(this);
            for (int i = 0; i < collisions.size(); i++) {
                GameObject obj = collisions.get(i);

                int type = obj.type();

                // If object is [avatar], GAME OVER
                if (type == GlobalConsts.TYPE_AVATAR) {
                    this.moveBack();
                    world.gameOver = true;
                }

                // If object is [zombie], step back
                if (type == GlobalConsts.TYPE_ZOMBIE) {
                    moveBack();
                    state = STUCK;
                    return;
                }

                // if object is a [tree], move back one step
                if (obj.type() == GlobalConsts.TYPE_TREE) {
                    moveBack();
                    state = STUCK;
                    return;
                }
            }
        }

        // state of zombie : [STUCK]
        //

        else if (state == STUCK) {
            // seconds left for clearing
            secondsClear = 1.0 + Math.random() * 0.5;
            // turn and hope to get clear
            alfa += alfaClear * diffSeconds;

            // try to clear
            state = CLEARING;
        }


        // state of zombie : [CLEARING]
        //
        else if (state == CLEARING) {
            // check, if the clearing time has ended
            secondsClear -= diffSeconds;
            if (secondsClear < 0) {
                state = HUNTING;
                return;
            }


            // try step in this direction
            super.move(diffSeconds);

            // check if path was unblocked
            A_GameObjectList collisions = world.getPhysicsSystem().getCollisions(this);
            if (collisions.size() > 0) {
                moveBack();

                // stuck again
                this.state = STUCK;
                return;
            }

        }
    }


    // Inform that a zombie it is hit
    public void hasBeenShot() {
        // every shot decreases life (health)
        life -= 0.21;

        // if zombie is dead, delete it, increase the kills score
        if (life <= 0) {

            world.enemiesKilled++;
            CounterEnemies counter = (CounterEnemies) world.textObjects.get(0);

            //Update the enemies counter
            counter.decrement();
            this.isLiving = false;
            return;
        }
    }


    public int type() {
        return GlobalConsts.TYPE_ZOMBIE;
    }

    @Override
    public Shape getBounds() {
        int x = (int) (this.x - this.radius - world.worldPartX);
        int y = (int) (this.y - this.radius - world.worldPartY);
        int d = (this.radius * 2);
        if(this.isFacingRight)
            return new Ellipse2D.Double(x, y, d, d);
        else
            return new Ellipse2D.Double(x-d, y, d, d);
    }


    public void draw(Graphics graphics, A_World world) {
        int x = (int) (this.x - this.radius - world.worldPartX);
        int y = (int) (this.y - this.radius - world.worldPartY);
        int d = (this.radius * 2);
        // TODO mnogo sum lud
        if ((x >= -100 && y >= -100) && (x <= GlobalConsts.WORLDPART_WIDTH + 100 && y <= GlobalConsts.WORLDPART_HEIGHT + 100)) {


            if (isFacingRight) {

//
//                graphics.setColor(color);
//                graphics.fillOval(x, y, d, d);
//                graphics.setColor(Color.DARK_GRAY);
//                graphics.drawOval(x, y, d, d);

                graphics.drawImage(img, (int) x, (int) y, width, height, null);
            } else {
//                graphics.setColor(color);
//                graphics.fillOval(x-d, y, d, d);
//                graphics.setColor(Color.DARK_GRAY);
//                graphics.drawOval(x-d, y, d, d);
                graphics.drawImage(img, (int) x, (int) y, -width, height, null);
            }
        }
    }


}
