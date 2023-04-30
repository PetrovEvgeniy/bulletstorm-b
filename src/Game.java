import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.Serial;

public class Game extends Canvas implements Runnable {

    @Serial
    private static final long serialVersionUID = 1L;

    private boolean isRunning = false;
    private Thread thread;
    public Game(){
        new Window(1000,580,"Bulletstorm Brigade",this);
        start();
    }

    private void start(){
        isRunning=true;
        thread = new Thread(this);
        thread.start();

    }

    private void stop(){
        isRunning= false;

        try {
            thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // Game loop (this method sets up the game to run at 60 FPS)

        long lastTime = System.nanoTime();

        double amountOfTicks = 60.0;                //Set the FPS = 60
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                // updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                // updates = 0;
            }
        }
        stop();
    }

    public void tick(){
        // Updates everything in the game (60 times a second)

    }

    public void render(){
        // Renders everything in the game (THOUSAND times a second)
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            // Load 3 frames in advance before they are displayed
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        //////////////////////////////////////////

        //Set black background
        g.setColor(Color.BLACK);
        g.fillRect(0,0,1000,580);

        //////////////////////////////////////////
        g.dispose();
        bs.show();

    }

    public static void main(String[] args){
        new Game();
    }
}