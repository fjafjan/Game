/* This is a representation of a game map which can be used for the
 * main background map as well as sprite maps etc. It stores the map
 * as a grid of 16x16 tiles. It should be turned into a singleton, as
 * only one instance can exist. Thus we dont have to bother with
 * passing it around between objects. */

import java.util.Collection;
import java.util.Iterator;
import java.lang.Math;

public class GameMap {
    public GameMap() {
	_xSize = 100;
	_ySize = 100;
	_map = new int[_xSize][_ySize];
	generateEmptyMap();
	TILESIZE = GameConstants.TILESIZE;
    }

    public GameMap(int xSize, int ySize) {
	_xSize = xSize;
	_ySize = ySize;
	_map = new int[xSize][ySize];
	TILESIZE = GameConstants.TILESIZE;
	generateEmptyMap();
    }

    public GameMap addMaps(GameMap gm) {
	// Combines two maps of equal size
	GameMap res = new GameMap(_xSize, _ySize);
	for (int i=0; i<_xSize; i++) {
	    for (int j=0; j<_ySize; j++) {
		if (gm.get(i, j) != -1) {
		    res.set(i, j, gm.get(i, j));
		} else {
		    res.set(i, j, _map[i][j]);
		}
	    }
	}
	return res;
    }

    public GameMap addMaps(Collection<Character> chs) {
	// Adds a vector of character and their positions to this map,
	// and returns the result;
	GameMap res = getCopy();
	Iterator<Character> iter = chs.iterator();
	while (iter.hasNext()) {
	    Character ch = iter.next();
	    res.set(ch.getPosition(), ch.getSpriteId());
	}
	return res;
    }

    public GameMap getCopy() {
	GameMap result = new GameMap(_xSize, _ySize);
	for (int i=0; i<_xSize; i++) {
	    for (int j=0; j<_ySize; j++) {
		result.set(i, j, _map[i][j]);
	    }
	}
	return result;
    }

    private void generateEmptyMap() {
	for (int i=0; i<_xSize; i++) {
	    for (int j=0; j<_ySize; j++) {
		_map[i][j] = -1;
	    }
	}
    }

    public void generateWallMap() {
	for (int i=0; i<_xSize; i++) {
	    for (int j=0; j<_ySize; j++) {
		_map[i][j] = 1;
	    }
	}
    }

    public void generateRandomMap() {
	for (int i=0; i<_xSize; i++) {
	    for (int j=0; j<_ySize; j++) {
		// Think the floor is superfluous.
		_map[i][j] = (int) Math.floor(Math.random() + 0.5);
	    }
	}	
    }

    public void eraseMap() {
	for (int i=0; i<_xSize; i++) {
	    for (int j=0; j<_ySize; j++) {
		_map[i][j] = -1;
	    }
	}
    }

    public void set(Position pos, int tileId) {
	_map[pos.getX()][pos.getY()] = tileId;
    }

    public void set(int x, int y, int tileId) {
	_map[x][y] = tileId;
    }

    public void set(Collection<Character> cList) {
	Iterator<Character> iter = cList.iterator();
	while (iter.hasNext()) {
	    Character ch = iter.next();
	    set(ch.getPosition(), ch.getSpriteId());
	}
    }

    public int get(Position p) {
	return _map[p.getX()][p.getY()];
    }

    public int get(int x, int y) {
	return _map[x][y];
    }

    public int getTileFromPixel(int pX, int pY) {
	System.out.println("Map returned: " + _map[pX/TILESIZE][pY/TILESIZE] + "and pX/TILESIZE is" + pX/TILESIZE);
	return _map[pX/TILESIZE][pY/TILESIZE];
    }
    
    public int getTileFromPixel(Position p) {
	return getTileFromPixel(p.getX(), p.getY());
    }

    // Returns size in tiles.
    public int[] getSize() {
	return new int[]{_xSize, _ySize};
    }

    public int[] getSizeInPixels() {
	return new int[]{_xSize*TILESIZE, _ySize*TILESIZE};
    }

    private int TILESIZE;
    private int _xSize;
    private int _ySize;
    private int[][] _map;
}
