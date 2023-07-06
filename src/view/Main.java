package view;

import abstracts.A_Frame;
import abstracts.A_TextObject;
import abstracts.A_World;
import abstracts.GameObject;
import utils.Main_World;

/**
 * Main class where the game is launched from
 */
public final class Main {
    private A_World world = null;

    public Main() {
        A_Frame frame = new Main_Frame();
        frame.displayOnScreen();
        world = new Main_World();

        world.setGraphicSystem(frame.getGraphicSystem());
        world.setInputSystem(frame.getInputSystem());

        GameObject.setWorld(world);
        A_TextObject.setWorld(world);
        frame.getGraphicSystem().setWorld(world);

        world.init();
        world.run();
    }

    public static void main(String[] args) {
        new Main();
    }
}