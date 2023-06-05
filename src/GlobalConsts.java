

/**
 * Class that holds all constants for the game
 */
final class GlobalConsts
{
  // Define size of the world
  static final int WORLD_WIDTH      = 5000;
  static final int WORLD_HEIGHT     = 4000;

  // Define size of the displayed part of the world
  static final int WORLDPART_WIDTH  = 1200;
  static final int WORLDPART_HEIGHT = 800;

  // Define Border (when to scroll)
  static final int SCROLL_BOUNDS    =  300;
	  

  // Define constants for spawn intervals
  static final double SPAWN_INTERVAL = 0.2;
  static final double SPAWN_GRENADE  = 10.0;
  static final double LIFE_GRENADE   = 15.0;
  

  // Define constants for object types
  static final int TYPE_AVATAR  = 1;
  static final int TYPE_TEXT    = 2;
  static final int TYPE_TREE    = 3;
  static final int TYPE_ZOMBIE  = 4;
  static final int TYPE_SHOT    = 5;
  static final int TYPE_GRENADE = 6;
}
