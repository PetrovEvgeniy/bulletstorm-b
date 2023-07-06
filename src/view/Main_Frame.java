package view;

import abstracts.A_Frame;
import abstracts.A_GraphicSystem;
import abstracts.A_InputSystem;
import utils.GlobalConsts;

import javax.swing.*;
import java.awt.*;

class Main_Frame extends JFrame implements A_Frame {
    // ...ok...
    private static final long serialVersionUID = 2L;

    private Main_Panel panel = null;

    public Main_Frame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(GlobalConsts.WORLDPART_WIDTH + 2, GlobalConsts.WORLDPART_HEIGHT + 2);

//	this.setAlwaysOnTop(true);
//	this.setUndecorated(true);

        this.setResizable(false);

        panel = new Main_Panel();

        // Needed for KEYBOARD input !!!
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        Toolkit.getDefaultToolkit();
        this.setContentPane(panel);

    }

    public void displayOnScreen() {
        validate();
        setVisible(true);
    }

    public A_GraphicSystem getGraphicSystem() {
        return panel;
    }

    public A_InputSystem getInputSystem() {
        return panel.getInputSystem();
    }

    @Override
    public void setBufferedStragety() {
        this.createBufferStrategy(3);
    }
}

