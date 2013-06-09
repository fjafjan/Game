import java.util.Observable;

public class Model extends Observable{
        
    // Constructor
    public Model() {
        XSIZE = 100;
        YSIZE = 100;
        XRES = 30;
        YRES = 30;
        // Create a gameMap with some buffer with zeroes.
        gameMap = new int[XSIZE + XRES][YSIZE + YRES];
        gameState = new int[XRES][YRES];
        initGameMap();
        // Set intial character pos
        currentPosX = (XSIZE + XRES)/2;
        currentPosY = (YSIZE + YRES)/2;
        gameMap[currentPosX][currentPosY] = 2;
        //initGameWallBorder();
    }
    
    // Method that sets border to wall.
    private void initGameWallBorder() {
        // First do long side
        for (int i=0; i<XRES/2; i++) {
            for (int j=0; j<YSIZE+YRES; j++) {
                gameMap[i][j] = gameMap[i+XSIZE][j] = 1;
            }
        }
        // Then do short side. ATM corners are done twice.
        for (int i=0; i<YRES/2; i++) {
            for (int j=0; j<XSIZE; j++) {
                gameMap[j][i] = gameMap[j][i+YSIZE] = 1;
            }
        }
    }

    // Returns current state of the game
    public int[][] getGameState() {
        int x = currentPosX - XRES/2;
        int y = currentPosY - YRES/2;
        for (int i=0; i<XRES; i++) {
            for (int j=0; j<YRES; j++) {
                gameState[i][j] = gameMap[x + i][y + j];
            }
        }
        return gameState;
    }
    
    // Changes the game state. Called by controller.
    public void processInput (String event) {
        switch (event) {
            case "w": System.out.println("upate got to model"); 
            updateCurrentPosition(currentPosX, currentPosY - 1);
            break;
            case "a": updateCurrentPosition(currentPosX - 1, currentPosY);
            break;
            case "s": updateCurrentPosition(currentPosX, currentPosY + 1);
            break;
            case "d": updateCurrentPosition(currentPosX + 1, currentPosY);
            break;
            case "start":  currentPosX = (XSIZE + XRES)/2;
                           currentPosY = (YSIZE + YRES)/2;
                           break;
        }
        // This needs to be called for the notifyObservers to actually
        // notify observers. INTUITIVE! HAHAHA.
        setChanged();                     
        notifyObservers();
    }
     
    
    
    private void initGameMap() {
        for (int i=0; i<XSIZE + XRES; i++) {
            for (int j=0; j<YSIZE + YRES; j++) {
                gameMap[i][j]=1;
            }
        }
    }
    
    // Method to update current position of protagonist
    private void updateCurrentPosition (int newX, int newY) {
        // First delete sprite from old position
        gameMap[currentPosX][currentPosY] = 0;
        // Check if x position is ok
        System.out.println("Got to updatePos");
        if ( (XSIZE - newX) >= XRES/2 && (newX >= XRES/2) ) {
            currentPosX = newX;
        }
        // Check if y position is ok
        if ( (YSIZE - newY) >= YRES/2 && (newY >= YRES/2) ) {
            currentPosY = newY;
        } 
        // Place character in new position
        gameMap[currentPosX][currentPosY] = 2;
    }
    
    // Constants that determine size of playing field in tiles.
    private int XSIZE;
    private int YSIZE;
    
    // Constants that determine VIEWING field in tiles.
    private int XRES;
    private int YRES;
    
    // Variables for storing gamestates
    private int[][] gameMap;
    private int[][] gameState;
    private int currentPosX;
    private int currentPosY;
}
