package utils;

/**
 * Class that holds all constants for the game
 */
public final class GlobalConsts
{
  // Define size of the world
  public static final int WORLD_WIDTH      = 3200;
  public static final int WORLD_HEIGHT     = 3200;

  // Define size of the displayed part of the world
  public static final int WORLDPART_WIDTH  = 1200;
  public static final int WORLDPART_HEIGHT = 800;

  // Define Border (when to scroll)
  public static final int SCROLL_BOUNDS    =  300;
	  

  // Define constants for spawn intervals
  public static final double SPAWN_INTERVAL = 0.15; // increase this if you want to make zombies spawning faster


  public static final double SPAWN_FIREBALL = 10.0;
  public static final double LIFE_FIREBALL = 15.0;


  // Define constants for object types
  public static final int TYPE_AVATAR  = 1;
  public static final int TYPE_TEXT    = 2;
  public static final int TYPE_TREE    = 3;
  public static final int TYPE_ZOMBIE  = 4;
  public static final int TYPE_SHOT    = 5;
  public static final int TYPE_FIREBALL = 6;
  public static final int TYPE_ROCK    = 7;
  public static final int TYPE_BUSH  = 8;


  // Time to wait before leveling up
  public static final double LEVEL_UP_TIME   = 10.0;

 // Time to wait before next multikill notification (in seconds)
  public static final double MULTI_KILL_DELAY  = 0.1;

  // Number of spawned zombies per level
  public static final double[] SPAWN_ZOMBIE_INTERVAL_PER_LEVEL = {0.3, 0.25, 0.2, 0.1, 0.05, 0.025, 0.055}; 

}
