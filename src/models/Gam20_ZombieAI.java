package models;

import abstracts.A_GameObjectList;
import abstracts.GameObject;
import utils.GlobalConsts;

import java.awt.Color;


public class Gam20_ZombieAI extends GameObject {
    private static final int HUNTING = 1;
    private static final int STUCK = 2;
    private static final int CLEARING = 3;

    private int state;
    private double alfaClear;
    private double secondsClear;

    // Define the life (health) of a zombie
    private double life = 1.0;


    public Gam20_ZombieAI(double x, double y) {
        super(x, y, 0, 60, 15, new Color(160, 80, 40));
        this.isMoving = false;

        state = HUNTING;

        // Turn LEFT or RIGHT to clear
        alfaClear = Math.PI;
        if (Math.random() < 0.5) alfaClear = -alfaClear;

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

		// if zombie is dead, delete it
        if (life <= 0) {
            this.isLiving = false;
            return;
        }
    }


    public int type() {
        return GlobalConsts.TYPE_ZOMBIE;
    }
}
