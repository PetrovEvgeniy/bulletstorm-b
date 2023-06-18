
package utils;

import abstracts.A_TextObject;

import java.awt.Color;

public class Gam20_CounterLevel  extends A_TextObject{
     private int number = 1;
	
  public Gam20_CounterLevel(int x, int y)
  { super(x,y, new Color(255,255,0,210));
  }
  
  public String toString()
  { String display = "Level: ";
    display += number;
    return display;
  }
  
  public void increment(){ number++; }
}


