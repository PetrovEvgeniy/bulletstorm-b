package view;

import abstracts.A_Frame;
import abstracts.A_TextObject;
import abstracts.A_World;
import abstracts.GameObject;
import utils.Gam20_World;
import utils.MainMenu;

/**
 * Main class where the game is launched from
 */
public final class A_Main {
    private A_World world = null;

    public A_Main() {
        world = new Gam20_World();
        B_Frame frame = new B_Frame();

        frame.displayOnScreen();

        frame.showMenu();

//        world.setGraphicSystem(frame.getGraphicSystem());
//        world.setInputSystem(frame.getInputSystem());
//
//        GameObject.setWorld(world);
//        A_TextObject.setWorld(world);
//        frame.getGraphicSystem().setWorld(world);
//
//        world.init();
//        world.run();
    }

    public void switchToGame(){

    }


    public static void main(String[] args) {
        new A_Main();
    }
}
