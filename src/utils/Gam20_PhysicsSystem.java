package utils;

import abstracts.A_GameObjectList;
import abstracts.A_PhysicsSystem;
import abstracts.A_World;
import abstracts.GameObject;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

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
            if (obj2.getBounds() instanceof Ellipse2D && object.getBounds() instanceof Ellipse2D) {
                double dist = object.radius + obj2.radius;
                double dx = object.x - obj2.x;
                double dy = object.y - obj2.y;

                if (dx * dx + dy * dy < dist * dist) {
                    result.add(obj2);
                }
            }else if (object.getBounds() instanceof Ellipse2D && obj2.getBounds() instanceof Rectangle){
                List<Point> points = getCirclePoints(object);
                for(int j = 0; j < points.size(); j++){
                    if(((Rectangle) obj2.getBounds()).contains(points.get(j))){
                        result.add(obj2);
                        break;
                    }
                }
            }else if(object.getBounds() instanceof Rectangle && obj2.getBounds() instanceof Rectangle){
                if(((Rectangle) object.getBounds()).intersects((Rectangle) obj2.getBounds())){
                    result.add(obj2);
                }
            }else{ // if object is rectangle and obj 2 is eclipse
                List<Point> points = getCirclePoints(obj2);
                for(int j = 0; j < points.size(); j++){
                    if(((Rectangle) object.getBounds()).contains(points.get(j))){
                        result.add(obj2);
                        break;
                    }
                }
            }
        }
        return result;
    }

    private List<Point> getCirclePoints(GameObject object){
        List<Point> points = new ArrayList<>();
        int numPoints = 50;
        double angleIncrement = 2 * Math.PI / numPoints;
        for (int j = 0; j < numPoints; j++) {
            double angle = j * angleIncrement;
            int realX = (int) (object.x  - world.worldPartX);
            int realY = (int) (object.y  - world.worldPartY);
            int x = (int) (realX + object.radius * Math.cos(angle));
            int y = (int) (realY + object.radius * Math.sin(angle));
            points.add(new Point(x, y));
        }
        return points;
    }

}