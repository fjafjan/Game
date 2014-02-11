/* This is a class that contains all the information viewer needs to
 * correctly draw the game world. This includes: currently visible
 * tiles, the position of the player, size (in tiles of the visible
 * world), etc...*/

import java.util.ArrayList;

public class GameState {
    
    GameState() {
	length = 100;
	height = 100;
	visibleTiles = null;
	playerPosition = new Position();
	visibleCharacters = new ArrayList<PositionAndSpriteID>();
    }

    GameState(Position pPos, ArrayList<ArrayList<Integer>> visibleTiles, int length, int height, ArrayList<PositionAndSpriteID> al) {
	playerPosition = pPos;
	this.visibleTiles = visibleTiles;
	this.length = length;
	this.height = height;
	visibleCharacters = al;
    }

    public Position playerPosition;
    public ArrayList<ArrayList<Integer>> visibleTiles;
    public int length; // Length of view in tiles?
    public int height;
    public ArrayList<PositionAndSpriteID> visibleCharacters;
}