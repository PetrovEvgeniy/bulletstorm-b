package utils;

import abstracts.A_TextObject;

import java.awt.Color;

public class CounterEnemies extends A_TextObject
{
  private int number = 1;
	
  public CounterEnemies(int x, int y)
  { super(x,y, new Color(255,255,0,210));
  }
  
  public String toString()
  { String display = "Enemies: ";
    display += number;
    return display;
  }
  
  public int getNumber(){return number;}

  public void increment(){ number++; }

  public void decrement(){ number--; }

  public void setNumber(int n){number=n;}

}
