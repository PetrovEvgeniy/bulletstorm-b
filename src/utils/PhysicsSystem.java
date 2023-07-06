package utils;

import abstracts.A_GameObjectList;
import abstracts.A_PhysicsSystem;
import abstracts.A_World;
import abstracts.GameObject;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class PhysicsSystem extends A_PhysicsSystem {

    public PhysicsSystem(A_World w) {
        super(w);
    }


    //
    // Collisions for CIRCLES AND RECTANGLES
    //
    public A_GameObjectList getCollisions(GameObject object) {
        A_GameObjectList result = new A_GameObjectList();

        int len = world.gameObjects.size();
        for (int i = 0; i < len; i++) {
            GameObject obj2 = world.gameObjects.get(i);

            // Make sure that an object doesn't collide with itself
            if (obj2 == object) continue;

            // Check if they touch each other

            // 2 CIRCLES
            if (obj2.getBounds() instanceof Ellipse2D && object.getBounds() instanceof Ellipse2D) {
                List<Point> points = getCirclePoints(object);
                for(int j = 0; j < points.size(); j++){
                    //now it is much easier to check the collision of points
                    if((obj2.getBounds()).contains(points.get(j))){
                        result.add(obj2);
                        break;
                    }
                }

            // A CIRCLE AND A RECTANGLE
            }else if (object.getBounds() instanceof Ellipse2D && obj2.getBounds() instanceof Rectangle){
                // CONVERT THE CIRCLE TO 50 POINTS
                List<Point> points = getCirclePoints(object);
                for(int j = 0; j < points.size(); j++){
                    //now it is much easier to check the collision of points
                    if(((Rectangle) obj2.getBounds()).contains(points.get(j))){
                        result.add(obj2);
                        break;
                    }
                }
                // TWO RECTANGLES
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
    //converts the circle into n number of points
    private List<Point> getCirclePoints(GameObject object){
        List<Point> points = new ArrayList<>();
        int numPoints = 10;
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