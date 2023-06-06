

class Gam20_World extends A_World {
    private double timePassed = 0;
    private double timeSinceLastShot = 0;
    private final double charSpeed = 2.0;
    // For grenades
    private int grenades = 5;
    private Gam20_CounterGrenades counterG;
    private Gam20_Counter counterZ;
    private Gam20_HelpText helpText;
    private double spawnGrenade = 0;

    private double lifeHelpText = 10.0;

    protected void init() {
        // Add the Avatar
        avatar = new Gam20_Avatar(2500, 2000);
        gameObjects.add(avatar);

        // Set WorldPart position
        worldPartX = 1500;
        worldPartY = 1500;

        // [REMOVED] Add a little forest

         for (int x = 0; x < 5000; x += 1000) {

             for (int y = 0; y < 4000; y += 800) {
                 gameObjects.add(new Gam20_Tree(x + 300, y + 200, 80));
                 gameObjects.add(new Gam20_Tree(x + 600, y + 370, 50));
                 gameObjects.add(new Gam20_Tree(x + 200, y + 600, 50));
                 gameObjects.add(new Gam20_Tree(x + 500, y + 800, 40));
                 gameObjects.add(new Gam20_Tree(x + 900, y + 500, 100));
                 gameObjects.add(new Gam20_Tree(x + 760, y + 160, 40));
             }
         }


        // Add one single zombie
        gameObjects.add(new Gam20_ZombieAI(100, 100));


        counterZ = new Gam20_Counter(20, 40);
        counterG = new Gam20_CounterGrenades(770, 40);
        helpText = new Gam20_HelpText(100, 400);

        counterG.setNumber(grenades);
        textObjects.add(counterZ);
        textObjects.add(counterG);
        textObjects.add(helpText);
    }

    protected void processUserInput(A_UserInput userInput, double diffSeconds) {
        // Distinguish if Avatar shall move or shoots
        int button = userInput.mouseButton;

        //
        // Mouse events
        //
        // MOUSE EVENT NOT NEEDED FOR NOW
        //	if(userInput.isMouseEvent)
        //	{
        //	  // move
        //	  if(button==1) { avatar.setDestination(userInput.mousePressedX+worldPartX,
        //                              userInput.mousePressedY+worldPartY);
        //	  }
        //	}

        //
        // Mouse still pressed?
        //
        //	if(userInput.isMousePressed && button==3) {
        //	  // only 1 shot every ... seconds:
        //      timeSinceLastShot += diffSeconds;
        //      if(timeSinceLastShot > 0.2)
        //      {
        //    	timeSinceLastShot = 0;
        //
        //        Gam20_Shot shot = new Gam20_Shot(
        //          avatar.x,avatar.y,userInput.mouseMovedX+worldPartX,userInput.mouseMovedY+worldPartY);
        //        this.gameObjects.add(shot);
        //      }
        //	}

        //
        // Keyboard events
        //
        //TODO MAYBE FIX COMBINATIONS

        int denom = 2;
        if (userInput.isKeyEvent) {
            // this way is not perfect because you should implement all possible combinations
            // for now it is ok
            if (userInput.keyPressed.contains('w') && userInput.keyPressed.contains('a')) {
                moveChar(-charSpeed/denom, -charSpeed/denom);
            }
            else if (userInput.keyPressed.contains('s') && userInput.keyPressed.contains('a')) {

                moveChar(-charSpeed/denom, charSpeed/denom);
            }
            else if (userInput.keyPressed.contains('w') && userInput.keyPressed.contains('d')) {
                moveChar(charSpeed/denom, -charSpeed/denom);
            }
            else if (userInput.keyPressed.contains('s') && userInput.keyPressed.contains('d')) {
                moveChar(charSpeed/denom, charSpeed/denom);
            }
            else if (userInput.keyPressed.contains('w')) {
                System.out.println("w");
                moveChar(0, -charSpeed);
            } else if(userInput.keyPressed.contains('a')){
                moveChar(-charSpeed, 0);
            }
            else if (userInput.keyPressed.contains('s')) {
                moveChar(0, charSpeed);
            }else if(userInput.keyPressed.contains('d')){
                moveChar(charSpeed, 0);
            }
            // TODO
            else if (userInput.keyPressed.contains(' ')) {
                throwGrenade(userInput.mouseMovedX + worldPartX, userInput.mouseMovedY + worldPartY);
            }

//
//            } else if (userInput.keyPressed == (char) 27) {
//                //TODO MAKE PAUSE
//                //System.exit(0);
//            }else if(userInput.keyPressed == 'w' && userInput.keyPressed == 'a'){
//
//            }
        }
    }


    private void moveChar(double x, double y) {
        avatar.x += x;
        avatar.y += y;
    }

    private void throwGrenade(double x, double y) {
        if (grenades <= 0) return;

        // Throw a grenade
        for (int i = 0; i < 2000; i++) {
            double alfa = Math.random() * Math.PI * 2;
            double speed = 50 + Math.random() * 200;
            double time = 0.2 + Math.random() * 0.4;
            Gam20_Shot shot = new Gam20_Shot(x, y, alfa, speed, time);
            this.gameObjects.add(shot);
        }

        // Adjust grenade counter
        grenades--;
        counterG.setNumber(grenades);
    }


    protected void createNewObjects(double diffSeconds) {
        createZombie(diffSeconds);
        createGrenade(diffSeconds);

        // delete HelpText after ... seconds
        if (helpText != null) {
            lifeHelpText -= diffSeconds;
            if (lifeHelpText < 0) {
                textObjects.remove(helpText);
                helpText = null;
            }
        }
    }


    private void createGrenade(double diffSeconds) {
        final double INTERVAL = GlobalConsts.SPAWN_GRENADE;

        spawnGrenade += diffSeconds;
        if (spawnGrenade > INTERVAL) {
            spawnGrenade -= INTERVAL;

            // create new Grenade
            double x = worldPartX + Math.random() * GlobalConsts.WORLDPART_WIDTH;
            double y = worldPartY + Math.random() * GlobalConsts.WORLDPART_HEIGHT;

            // if too close to Avatar, cancel
            double dx = x - avatar.x;
            double dy = y - avatar.y;
            if (dx * dx + dy * dy < 200 * 200) {
                spawnGrenade = INTERVAL;
                return;
            }


            // if collisions occur, cancel
            Gam20_Grenade grenade = new Gam20_Grenade(x, y);
            A_GameObjectList list = getPhysicsSystem().getCollisions(grenade);
            if (list.size() != 0) {
                spawnGrenade = INTERVAL;
                return;
            }

            // else add zombie to world
            this.gameObjects.add(grenade);
            counterG.setNumber(grenades);
        }

    }


    private void createZombie(double diffSeconds) {
        final double INTERVAL = GlobalConsts.SPAWN_INTERVAL;

        timePassed += diffSeconds;
        if (timePassed > INTERVAL) {
            timePassed -= INTERVAL;

            // create new Zombie; preference to current screen
            double x, y;
            if (Math.random() < 0.7) {
                x = Math.random() * GlobalConsts.WORLD_WIDTH;
                y = Math.random() * GlobalConsts.WORLD_HEIGHT;
            } else {
                x = worldPartX + Math.random() * GlobalConsts.WORLDPART_WIDTH;
                y = worldPartY + Math.random() * GlobalConsts.WORLDPART_HEIGHT;
            }


            // if too close to Avatar, cancel
            double dx = x - avatar.x;
            double dy = y - avatar.y;
            if (dx * dx + dy * dy < 400 * 400) {
                timePassed = INTERVAL;
                return;
            }

            // if collisions occur, cancel
            Gam20_ZombieAI zombie = new Gam20_ZombieAI(x, y);
            A_GameObjectList list = getPhysicsSystem().getCollisions(zombie);
            if (list.size() != 0) {
                timePassed = INTERVAL;
                return;
            }

            // else add zombie to world
            this.gameObjects.add(zombie);
            zombie.setDestination(avatar);
            Gam20_Counter counter = (Gam20_Counter) textObjects.get(0);
            counter.increment();
        }

    }


    public void addGrenade() {
        if (grenades < 999) {
            grenades++;
        }
        counterG.setNumber(grenades);
    }

}
