package BaseClasses;

public abstract class A_GameObject {

    //Public so we can access it easier and faster because it is accessed all the time
    public double x,y;
    public double alfa = 0;
    public double speed = 0;

    public A_Shape shape;

    protected boolean isLiving = true;
    protected boolean isMoving = true;

    //Destination the object will move to. Also, old coordinates
    private double destX,destY;
    private boolean hasDestination = false;
    private double xOld,yOld;


    //Objects for calling the physics methods
    protected static A_PhysicsSystem physicsSystem;
    protected static A_World world;

    //Constructor
    public A_GameObject(int x, int y, double alfa, double speed, A_Shape shape){
        this.x = x;
        this.y = y;
        this.alfa = alfa;
        this.speed = speed;
        this.shape = shape;
    }

    /**
     *  Method that's called when the object has to move in direction of alfa
     */
    public void move(double diffSeconds){

        if(!isMoving) return;

        //Stop if destination is reached
        if(hasDestination){
            double diffX = Math.abs(x-destX);
            double diffY = Math.abs(y-destY);
            if(destX < 2 && destY < 2) {
                isMoving = false;
                return;
            }
        }

        // Setting it as the old position so its remembered
        xOld = x;
        yOld = y;

        // Finally, moving one step
        x += Math.cos(alfa)*speed*diffSeconds;
        y += Math.sin(alfa)*speed*diffSeconds;
    }

    /**
     *  Collides with border on screen. In hindsight, we probably won't even need this, so it's just for testing here
     */
    //TODO: Just for testing. I dont think we will need this later on
    protected void collideOnBorder(){
        double radius = this.shape.radius();
        double PI = Math.PI;

        if(x<radius && (alfa>PI/2 && alfa<PI*3/2)){
            alfa = Math.PI-alfa;
        }
        if(y<radius && alfa>PI){
            alfa = PI*2-alfa;
        }
        if(x>A_Const.WIDTH-radius){
            alfa = Math.PI-alfa;
        }
        if(y>A_Const.HEIGHT-radius){
            alfa = PI*2-alfa;
        }
        if(alfa<0) alfa += PI*2;
        if(alfa>PI*2) alfa -= PI*2;

    }

    /**
     * Sets the destination using atan2 method
     */
    public final void setDestination(double dx, double dy){
        isMoving = true;
        hasDestination = true;
        destX = dx;
        destY = dy;

        alfa = Math.atan2(dy-y,dx-x);
    }

    /**
     * Overwritten setDestination method which takes an object as input and calls the other setDestination method with its properties
     */
    public void setDestination(A_GameObject gameObject){
        setDestination(gameObject.x, gameObject.y);
    }

    /**
     * Moves back to old position
     */
    protected void moveBack(){
        x = xOld;
        y = yOld;
    }

    abstract int type();

    static void setPhysicsSystem(A_PhysicsSystem ps){ physicsSystem = ps;}

    static void setWorld(A_World _world){world = _world;}
}
