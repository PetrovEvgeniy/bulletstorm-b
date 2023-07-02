package models;

import utils.GlobalConsts;

public class Bush extends Foliage{
    public Bush(double x_, double y_) {
        super(x_, y_, 0, 0, "resourses/sprites/foliage/bush.png");
    }

    @Override
    public int type() {
        return GlobalConsts.TYPE_BUSH;
    }
}
