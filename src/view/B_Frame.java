package view;

import abstracts.*;
import models.Background;
import utils.Gam20_World;
import utils.GlobalConsts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class B_Frame extends JFrame implements A_Frame {
    // ...ok...
    private static final long serialVersionUID = 2L;

    private B_Panel panel = null;

    Background menuBg;
    JPanel menuPanel;
    JButton buttonNewGame;
    JButton buttonExit;
    JPanel layout;

    JLabel gameName;

    public B_Frame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(GlobalConsts.WORLDPART_WIDTH + 2, GlobalConsts.WORLDPART_HEIGHT + 2);

        menuBg = new Background("resourses/backgrounds/zombiesBG.png");
        buttonNewGame = new JButton();
        buttonExit = new JButton();
        gameName = new JLabel("Bulletstorm Brigade", SwingConstants.CENTER);

        setMenuItems();

//	this.setAlwaysOnTop(true);
//	this.setUndecorated(true);

        this.setResizable(false);
        panel = new B_Panel();

        // Needed for KEYBOARD input !!!
        panel.setFocusable(true);
        panel.requestFocusInWindow();

        layout = new JPanel(new CardLayout());
        layout.add(panel,"game");
        layout.add(menuPanel,"menu");


        this.setContentPane(layout);

//        layout.show(this.getContentPane(),"game");


    }

    public void showMenu(){
        CardLayout temp = (CardLayout) layout.getLayout();
        temp.show(layout, "menu");
    }

    private void setMenuItems() {

        ImageIcon backgroundImage = new ImageIcon(menuBg.getImage().getScaledInstance(GlobalConsts.WORLDPART_WIDTH, GlobalConsts.WORLDPART_HEIGHT, 0));
        menuPanel = new JPanel();
        JLabel backgroundImageLabel = new JLabel(backgroundImage);
        menuPanel.setLayout(new GridLayout(6, 1, 5, 5));


        gameName.setFont(gameName.getFont().deriveFont(Font.BOLD, 72.0f));


        ImageIcon startUnclicked = new ImageIcon("resourses/backgrounds/newGameBlack.png");
        ImageIcon startClicked = new ImageIcon("resourses/backgrounds/newGameRedish.png");

        ImageIcon endUnclicked = new ImageIcon("resourses/backgrounds/exitBlack.png");
        ImageIcon endClicked = new ImageIcon("resourses/backgrounds/exitRed.png");

        buttonNewGame.setIcon(startUnclicked);
        buttonNewGame.setRolloverIcon(startClicked);

        buttonNewGame.setBorderPainted(false);
        buttonNewGame.setFocusPainted(false);
        buttonNewGame.setContentAreaFilled(false);

        buttonNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameStart();
            }
        });

        buttonExit.setIcon(endUnclicked);
        buttonExit.setRolloverIcon(endClicked);

        buttonExit.setBorderPainted(false);
        buttonExit.setFocusPainted(false);
        buttonExit.setContentAreaFilled(false);

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameEnd();
            }
        });


        menuPanel.add(backgroundImageLabel);
        menuPanel.add(gameName);
        menuPanel.add(buttonNewGame);
        menuPanel.add(buttonExit);
    }

    public void gameStart() {

        CardLayout temp = (CardLayout) layout.getLayout();
        temp.show(layout, "game");

        setContentPane(layout);


        A_World world = new Gam20_World();

        world.setGraphicSystem(this.getGraphicSystem());
        world.setInputSystem(this.getInputSystem());

        GameObject.setWorld(world);
        A_TextObject.setWorld(world);
        this.getGraphicSystem().setWorld(world);

        world.init();
        world.run();

    }

    public void gameEnd() {
        System.exit(0);
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
}


