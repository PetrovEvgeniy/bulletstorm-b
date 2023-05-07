

import BaseClasses.A_GameObject;
import BaseClasses.A_GameObjectList;
import BaseClasses.A_PhysicsSystem;
import BaseClasses.A_World;

public class PhysicsEngine extends A_PhysicsSystem {

    public PhysicsEngine(A_World world){
        super(world);
    }

    /**
     * Returns list of objects that collide with each other.
     */
    @Override
    protected A_GameObjectList getCollisions(A_GameObject obj) {
        A_GameObjectList objList = new A_GameObjectList();

        int numberOfObjects = world.gameObjects.size();

        for(int i = 0; i < numberOfObjects; i++){
            A_GameObject temp = world.gameObjects.get(i);

            // Check an object doesn't collide with itself
            if(temp == obj) continue;


            // Calculate the distance to the other CIRCLE object
            double dist = obj.shape.radius() + temp.shape.radius();
            double dx = Math.abs(obj.x - temp.x);
            double dy = Math.abs(obj.y - temp.y);

            // Checks if the object touches the other CIRCLE object
            if(dx < dist && dy < dist){
                if(dx*dx + dy*dy < dist*dist){
                    objList.add(temp);
                }
            }
        }
        return objList;
    }
}
