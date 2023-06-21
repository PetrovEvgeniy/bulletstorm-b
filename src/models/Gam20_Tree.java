package models;

import abstracts.A_World;
import abstracts.GameObject;
import utils.GlobalConsts;

import java.awt.*;

public class Gam20_Tree extends GameObject {
    public Gam20_Tree(double x, double y, int r) {
        super(x, y, 0, 0, r, new Color(64, 160, 64));
        this.isMoving = false;
    }

    public int type() {
        return GlobalConsts.TYPE_TREE;
    }


    public void draw(Graphics graphics, A_World world) {
       // System.out.println("called");
        int x = (int) (this.x - world.worldPartX);
        int y = (int) (this.y - world.worldPartY);
        graphics.setColor(Color.RED);
        graphics.fillRect(x, y, width, height);
        graphics.drawRect(x, y, width, height);

        //Changing argument width here ^ to -width flips character horizontally
    }

    @Override
    public Shape getBounds() {
      int x = (int) (this.x  - world.worldPartX);
      int y = (int) (this.y  - world.worldPartY);
      return new Rectangle((int) x, (int) y, width, height);
    }
}
