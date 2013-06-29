import java.util.Observable;
import java.util.Collection;
import java.util.Iterator;

public class Model extends Observable{
        
    // Constructor
    public Model() {
        XSIZE = 100;
        YSIZE = 100;
        XRES = 30;
        YRES = 30;
        // Create a gameMap with some buffer with zeroes.
        gameMap = new GameMap(XSIZE + XRES, YSIZE + YRES);
	gameMap.generateRandomMap();
        gameState = new int[XRES][YRES];

	// Create a characterTracker
	cTracker = new CharacterTracker();
        // Set intial character pos
	int initX = (XSIZE + XRES)/2;
	int initY = (YSIZE + YRES)/2;
	Position initPos = new Position(initX, initY);
	player = new PlayerCharacter(10, 10, "player", 10, cTracker, initPos, gameMap);
	// Lets add some enemies as well
	new ComputerCharacter(10, 10, "NPC", 10, cTracker, new Position(XRES+3, YRES+3), gameMap);
	new ComputerCharacter(10, 10, "NPC", 10, cTracker, new Position(XSIZE/2 + 2, YSIZE/2 +3), gameMap);
        //initGameWallBorder();
    }
    

    // Returns current state of the game
    public int[][] getGameState() {
	int currentPosX = player.getPosition().getX();
	int currentPosY = player.getPosition().getY();
        int x = currentPosX - XRES/2;
        int y = currentPosY - YRES/2;
	GameMap augmentedMap = gameMap.addMaps(cTracker.getNPClist());
        for (int i=0; i<XRES; i++) {
            for (int j=0; j<YRES; j++) {
                gameState[i][j] = augmentedMap.get(x + i, y + j);
            }
        }
	gameState[XRES/2][YRES/2] = 2;
	return gameState;
    }
    
    private void updateGameState(Position p, int spriteId) {
	gameState[p.getX()][p.getY()] = spriteId;
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
	} else if (event.equals("s")) {
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
	}  else if (event.equals("x")) {
	    updateCurrentPosition(1, 1);
	    runAIandNotify();
	}
    }
     
    private void runAIandNotify() {
	runAI();
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
            currentPosX = newX;
        }
        // Check if y position is ok with regard to map edges
        if ( (YSIZE - newY) >= YRES/2 && (newY >= YRES/2) ) {
            currentPosY = newY;
        } 
        // Place character in new position
	if (player.checkMove(new Position(currentPosX, currentPosY)) ) {
	    player.move(new Position(currentPosX, currentPosY));
	}
    }
    
    private void runAI() {
	// First let AI do its moves
	Iterator<Character> enemies = cTracker.getNPClist().iterator();
	while(enemies.hasNext()) {
	    ((ComputerCharacter) enemies.next()).doTurn();
	}
    }

    // Constants that determine size of playing field in tiles.
    private int XSIZE;
    private int YSIZE;
    
    // Constants that determine VIEWING field in tiles.
    private int XRES;
    private int YRES;
    
    // Variables for storing gamestates
    private GameMap gameMap;
    private int[][] gameState;
    private PlayerCharacter player;
    private CharacterTracker cTracker;
}
