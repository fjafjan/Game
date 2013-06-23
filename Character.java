/* This is an abstract class containing some methods common to all
 * NPCs. Classes inheriting this class must implement interact(), */ 

public abstract class Character {

    abstract void interact();

    public String getName() {
	return name;
    }

    public int getSightRange() {
	return sightRange;
    }

    public abstract void attack(Character c);

    public Position getPosition() {
	return pos;
    }

    public String toString() {
	return "" + myId;
    }
    
    public abstract boolean isPC();

    public abstract boolean checkMove(Position newPos);

    public void move(Position newPos) {
	pos = newPos;
    }

    public void move(int dx, int dy) {
	pos.translate(dx, dy);
    }

    public abstract void getAttacked(int damage);
    public int getSpriteId() {
	return spriteId;
    }
	
    protected static int id = 0;
    protected Position pos;
    protected int spriteId;
    protected int moveSpeed;
    protected int hp;
    protected String name;
    protected int sightRange;
    protected int myId;
    protected GameMap gameMap;
}