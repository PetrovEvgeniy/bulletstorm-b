package utils;

import abstracts.*;
import models.Background;

import view.B_Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements A_Frame {

    Background menuBg;
    JPanel menuPanel;
    B_Panel gamePanel;
    JButton buttonNewGame;
    JButton buttonExit;
    CardLayout layout;

    JLabel gameName;
    private B_Panel panel;

    public MainMenu() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(GlobalConsts.WORLDPART_WIDTH, GlobalConsts.WORLDPART_HEIGHT);


        menuBg = new Background("resourses/backgrounds/zombiesBG.png");
        buttonNewGame = new JButton();
        buttonExit = new JButton();
        gameName = new JLabel("Bulletstorm Brigade", SwingConstants.CENTER);

        this.setResizable(false);
        panel = new B_Panel();


        gamePanel = new B_Panel();

        panel.setFocusable(true);
        panel.requestFocusInWindow();

        this.setContentPane(panel);

        this.setMenuItems();
        this.setGamePanel();

        layout = new CardLayout();
        setLayout(layout);

        add(menuPanel, "menu");
        add(gamePanel, "game");

        layout.show(this.getContentPane(), "menu");

    }

    private void setGamePanel() {
        gamePanel = new B_Panel();
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

        layout.show(this.getContentPane(), "game");
//        setContentPane(gamePanel); TODO: Probvai s BFrame da napravish toq cardlayout

//        A_World world = new Gam20_World();
//
//        world.setGraphicSystem(this.getGraphicSystem());
//        world.setInputSystem(this.getInputSystem());
//
//        GameObject.setWorld(world);
//        A_TextObject.setWorld(world);
//        this.getGraphicSystem().setWorld(world);
//
//        world.init();
//        world.run();

    }

    public void gameEnd() {
        System.exit(0);
    }

    @Override
    public void displayOnScreen() {
        validate();
        setVisible(true);
    }

    @Override
    public A_GraphicSystem getGraphicSystem() {
        return panel;
    }

    @Override
    public A_InputSystem getInputSystem() {
        return panel.getInputSystem();
    }
}
