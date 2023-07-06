package utils;

import abstracts.A_TextObject;

import java.awt.Color;

public class HelpText extends A_TextObject
{
  private String text = "MOVE:WASD    SHOOT:Mouse left    " +
                        "Fireball:Mouse right   SWORDS:Space Bar    END: Escape";

  public HelpText(int x, int y)
  { super(x,y, new Color(0,120,255,60));
  }
  
  public HelpText(int x, int y, String text)
  { super(x,y, new Color(255,0,0,100));
    this.text = text;
  }

  public void setText(String text){this.text = text;}

  public String toString()
  { 
    return text;
  }
  
}
