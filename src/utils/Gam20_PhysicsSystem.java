package utils;

import abstracts.A_GameObjectList;
import abstracts.A_PhysicsSystem;
import abstracts.A_World;
import abstracts.GameObject;

public class Gam20_PhysicsSystem extends A_PhysicsSystem {

    public Gam20_PhysicsSystem(A_World w) {
        super(w);
    }


    //
    // Collisions for CIRCLE objects only...
    //
    public A_GameObjectList getCollisions(GameObject object) {
        A_GameObjectList result = new A_GameObjectList();

        int len = world.gameObjects.size();
        for (int i = 0; i < len; i++) {
            GameObject obj2 = world.gameObjects.get(i);

            // Make sure that an object doesn't collide with itself
            if (obj2 == object) continue;

            // Check if they touch each other
            double dist = object.radius + obj2.radius;
            double dx = object.x - obj2.x;
            double dy = object.y - obj2.y;

            if (dx * dx + dy * dy < dist * dist) {
                result.add(obj2);
            }
        }

        return result;
    }


}