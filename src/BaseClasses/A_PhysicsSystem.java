package BaseClasses;

public abstract class A_PhysicsSystem {

    protected A_World world;

    public A_PhysicsSystem(A_World world){
        this.world = world;
    }

    /**
     * Abstract method so that any class extending this has to define it
     */
    protected abstract A_GameObjectList getCollisions(A_GameObject obj);

    /**
     * Returns distance between two objects
     */
    protected double distance(double x1, double y1, double x2, double y2){
        double xd = x1 - x2;
        double yd = y1 - y2;
        return Math.sqrt(xd*xd + yd*yd);
    }

    /**
     * Moves objects if it collides and gets stuck to object
     */
    public void moveBackToUncollide(A_GameObject obj){
        double dx = Math.cos(obj.alfa);
        double dy = Math.sin(obj.alfa);

        while(true){
            obj.x -= dx;
            obj.y -= dy;

            A_GameObjectList collisions = getCollisions(obj);
            if(collisions.size()==0){
                break;
            }
        }
    }

}