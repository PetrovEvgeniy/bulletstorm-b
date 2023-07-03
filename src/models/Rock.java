package models;

import utils.GlobalConsts;

public class Rock extends Foliage{
    public Rock(double x_, double y_) {
        super(x_, y_, 0, 0, "resourses/sprites/foliage/rock.png");
    }

    @Override
    public int type() {
        return GlobalConsts.TYPE_TREE;
    }

}
