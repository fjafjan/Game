/* This is simply a wrapper for a position and a sprite that is used
   by model when communicating with the view. */

public class PositionAndSpriteID {
    public Position _pos;
    public int _spriteID;

    public PositionAndSpriteID() {
	_pos = new Position();
	_spriteID = 0;
    }
    
    public PositionAndSpriteID(Position pos, int spriteID) {
	_pos = pos;
	_spriteID = spriteID;
    }
}