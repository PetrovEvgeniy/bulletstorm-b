package utils;

import abstracts.A_TextObject;

import java.awt.Color;

public class Gam20_CounterGrenades extends A_TextObject
{
  private int number = 1;
	
  public Gam20_CounterGrenades(int x, int y)
  { super(x,y, new Color(255,255,0,210));
  }
  
  public String toString()
  { String display = "Grenades: ";
    display += number;
    return display;
  }
  
  public void setNumber(int n){number=n;}
}
