package view;

import abstracts.A_Frame;
import abstracts.A_GraphicSystem;
import abstracts.A_InputSystem;
import utils.GlobalConsts;

import javax.swing.*;

class B_Frame extends JFrame implements A_Frame
{
  // ...ok...
  private static final long serialVersionUID = 2L;

  private B_Panel panel = null;	

  public B_Frame()
  { this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setSize(GlobalConsts.WORLDPART_WIDTH+2, GlobalConsts.WORLDPART_HEIGHT+2);
	
//	this.setAlwaysOnTop(true);
//	this.setUndecorated(true);
	
	this.setResizable(false);
	
	panel = new B_Panel();
	
	// Needed for KEYBOARD input !!!
	panel.setFocusable(true);
	panel.requestFocusInWindow();
	
	this.setContentPane(panel);
  }
  
  public void displayOnScreen() { validate(); setVisible(true); }
  
  public A_GraphicSystem getGraphicSystem() { return panel; }
  public A_InputSystem getInputSystem()   { return panel.getInputSystem(); }
}
