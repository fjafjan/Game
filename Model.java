import java.util.Observable;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;

public class Model extends Observable{
        
    // Constructor
    public Model(int[] screenResolution) {
	TILESIZE = GameConstants.TILESIZE;
        XSIZE = 2560;
        YSIZE = 2560;
        XRES = screenResolution[0];
        YRES = screenResolution[1];
        // Create a gameMap with some buffer with zeroes.
        gameMap = new GameMap((XSIZE + XRES)/TILESIZE, (YSIZE + YRES)/TILESIZE);
	gameMap.generateRandomMap();
        gameState = new int[XRES/TILESIZE][YRES/TILESIZE];

	// Create a characterTracker
	cTracker = new CharacterTracker();
        // Set intial character pos
	int initX = (XSIZE + XRES)/2;
	int initY = (YSIZE + YRES)/2;
	Position initPos = new Position(initX, initY);
	player = new PlayerCharacter(10, 40, "player", 10, cTracker, initPos, gameMap);
	// Lets add some enemies as well
	new ComputerCharacter(10, 10, "NPC", 1, cTracker, new Position(XRES+3, YRES+3), gameMap);
	new ComputerCharacter(10, 10, "NPC", 1, cTracker, new Position(XSIZE/2 + 2, YSIZE/2 +3), gameMap);
    }
    

    // Returns current state of the game
    public GameState getGameState() {
        System.out.println("Game state requested " + XRES + " " + YRES);
	int currentPosX = player.getPosition().getX();
	int currentPosY = player.getPosition().getY();
        System.out.println("currentPosX: " + currentPosX + " currentPosY: " + currentPosY);
	// x and y are the first tiles to get in x and y axis
	// respectively, in tile coordinates,
        int x = (currentPosX - XRES/2)/TILESIZE;
        int y = (currentPosY - YRES/2)/TILESIZE;
        System.out.println("Start tiles: " + x + " " + y);

	// Matrix to store visible tiles in. +1 is added so that the
	// edge tiles, that are partially visible, are also
	// included. This loop can be sped up by working directly on
	// the arraylist that is created using new and immediately
	// added to the other list.
	ArrayList<ArrayList<Integer>> visibleTiles = new ArrayList<ArrayList<Integer>>(XRES/TILESIZE + 1);
        for (int i=0; i<XRES/TILESIZE +1; i++) {
            visibleTiles.add(new ArrayList<Integer>(YRES/TILESIZE + 1));
            for (int j=0; j<YRES/TILESIZE+1; j++) {
                visibleTiles.get(i).add(gameMap.get(x + i, y + j));
            }
        }
        System.out.println("First tile I got was" + gameMap.get(x, y));
	// Now get visible characters
	Iterator<Character> iter = cTracker.getNPClist().iterator();
	ArrayList<PositionAndSpriteID> visibleCharacters =
	    new ArrayList<PositionAndSpriteID>();

	while (iter.hasNext()) {
	    Character ch = iter.next();
	    if (isVisible(ch.getPosition(), player.getPosition())) {
		Position relativePos = relativePosition(ch.getPosition(),
							player.getPosition());
		visibleCharacters.add(new PositionAndSpriteID(relativePos,
							      ch.getSpriteId()));
	    }
	}
	
	// Add player to visible Characters. He is always at the center of the view.
	visibleCharacters.add(new PositionAndSpriteID(new Position(XRES/2, YRES/2),
                                                      player.getSpriteId()));

	return new GameState(player.getPosition(),
			     visibleTiles,
			     XRES,
			     YRES,
			     visibleCharacters
			     );
    }
    
    /* Takes a position and returns its coordinates in the view
     * defined by XRES, YRES and the center point. This should be
     * moved to the Position class I think. */
    private Position relativePosition(Position p, Position centerPos) {
	int x_new = p.getX() - centerPos.getX() + XRES/2;
	int y_new = p.getY() - centerPos.getY() + YRES/2;
	return new Position(x_new, y_new);
    }

    /* Checks whether a position is seen in current view of game */
    private boolean isVisible(Position pos, Position centerPos) {
	return (Math.abs(pos.getX() - centerPos.getX()) <= XRES/2 &&
                Math.abs(pos.getY() - centerPos.getY()) <= YRES/2);
    }
    
    private void spawnMonster() {
	// This method can of course be changed so as to spawn
	// different types of monsters randomly. 
	int x1 = (int) Math.floor(Math.random() * XSIZE) + XRES/2;
	int y1 = (int) Math.floor(Math.random() * YSIZE) + YRES/2;
	// Check that position is not already occupied
	int loopCount = 0;
	int maxAttempts = 5;
	Position p = new Position(x1, y1);
	while ((cTracker.isOccupied(p) || gameMap.getTileFromPixel(p) == 1) 
               && (loopCount < maxAttempts)) {
	    x1 = (int) Math.floor(Math.random() * XSIZE) + XRES/2;
	    y1 = (int) Math.floor(Math.random() * YSIZE) + YRES/2;
	    loopCount++;
	}
	if(loopCount<maxAttempts){
	    new ComputerCharacter(10, 10, "NPC", 1, cTracker, new Position(x1, y1), gameMap);
	} else {
	    System.out.println("We failed to spawn a monster!");
	}
    }

    // Changes the game state. Called by controller.
    public void processInput (String event) {
	// Incompatible with java version < 7.
	/*
          switch (event) {
          case "w": updateCurrentPosition(0, -1);
          break;
          case "a": updateCurrentPosition(-1, 0);
          break;
          case "s": updateCurrentPosition(0, 1);
          break;
          case "d": updateCurrentPosition(1, 0);
          break;
          case "start":  break;
          }*/
        // This needs to be called for the notifyObservers to actually
        // notify observers. INTUITIVE! HAHAHA.  Java 6 compatible
        // work around for Torbjorns laptop currently without internet
        // and thus unable to obtain java 7.
	if (event.equals("w")) {
            updateCurrentPosition(0, -1);
	    runAIandNotify();
	} else if (event.equals("a")) {
	    updateCurrentPosition(-1, 0);
	    runAIandNotify();
	} else if (event.equals("s") || event.equals("x")) {
	    updateCurrentPosition(0, 1);
	    runAIandNotify();
	} else if (event.equals("d")) {
	    updateCurrentPosition(1, 0);
	    runAIandNotify();
	} else if (event.equals("q")) {
	    updateCurrentPosition(-1, -1);
	    runAIandNotify();
	} else if (event.equals("e")) {
	    updateCurrentPosition(1, -1);
	    runAIandNotify();
	} else if (event.equals("z")) {
	    updateCurrentPosition(-1, 1);
	    runAIandNotify();
	}  else if (event.equals("c")) {
	    updateCurrentPosition(1, 1);
	    runAIandNotify();
	}
    }
     
    private void runAIandNotify() {
	runAI();
	if (Math.random() > 0.9) {
	    spawnMonster();
	}
	setChanged();                     
	notifyObservers();
    }

    // Method to update current position of protagonist
    private void updateCurrentPosition (int dX, int dY) {
	int currentPosX = player.getPosition().getX();
	int currentPosY = player.getPosition().getY();

        // Check if x position is ok with regard to map edges
	int newX = currentPosX + dX;
	int newY = currentPosY + dY;
        if ( (XSIZE - newX) >= XRES/2 && (newX >= XRES/2) ) {
            System.out.println("Old posx: " + currentPosX + " new posx" + newX);
            currentPosX = newX;
        }
        // Check if y position is ok with regard to map edges
        if ( (YSIZE - newY) >= YRES/2 && (newY >= YRES/2) ) {
            System.out.println("Old posy: " + currentPosY + " new posy" + newY);
            currentPosY = newY;
        } 
        // Place character in new position
	if (player.checkMove(new Position(currentPosX, currentPosY)) ) {
            System.out.println("Succeded in moving");
	    player.move(dX, dY);
	}
    }
    
    private void runAI() {
	// First let AI do its moves
	Iterator<Character> enemies = cTracker.getNPClist().iterator();
	while(enemies.hasNext()) {
	    ((ComputerCharacter) enemies.next()).doTurn();
	}
    }

    // Length of the side of a square tile.
    private int TILESIZE = 16;

    // Constants that determine the resolution of the map in pixels
    private int XSIZE;
    private int YSIZE;
    
    // Constants that determine VIEWING field in pixels
    private int XRES;
    private int YRES;
    
    // Variables for storing gamestates
    private GameMap gameMap;
    private int[][] gameState;
    private PlayerCharacter player;
    private CharacterTracker cTracker;
}
