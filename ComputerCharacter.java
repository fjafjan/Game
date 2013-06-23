/* Inherits character and also has method doTurn() which essentially
 * is its AI and decides what the character will do when performed */

import java.util.ArrayList;

public class ComputerCharacter extends Character {

    private AIBrain myBrain;
    private CharacterTracker cTracker;

    public ComputerCharacter() {
	moveSpeed = 1;
	hp = 10;
	name = "Benny Banan";
	sightRange = 10;
	myBrain = new AgressiveBrain(this);
	myId = super.id;
	spriteId = 2;
	super.id++;
    }

    /* Constructor for Computer Character. Each NPC is given a
     * reference to a CharacterTracker, so as to be able to find the
     * location of other Characters and a map in order to navigate. */
    public ComputerCharacter(int moveSpeed, int hp, String name, int sightRange, CharacterTracker ct, Position p, GameMap gm) {
	this.moveSpeed = moveSpeed;
	this.hp = hp;
	this.name = name;
	this.sightRange = sightRange;
	myBrain = new AgressiveBrain(this);
	cTracker = ct;
	pos = p;
	spriteId = 2;
	myId = super.id;
	super.id++;
	cTracker.add(this);
	gameMap = gm;
    }

    public boolean doTurn() {
	System.out.println("myId is: " + myId);
	return myBrain.doSomething();
    }

    public boolean isPC() {
	return false;
    }

    public ArrayList getVisibleCharacters() {
	return cTracker.getCharactersInRadius(this);
    }

    public Position getPlayerPosition() {
	return cTracker.getPlayerPosition();
    }

    public Character getPlayer() {
	return cTracker.getPlayer();
    }

    public boolean checkMove(Position newPos) {
	// Checks for walls and other Characters returns true if move
	// is ok
	return ((gameMap.get(newPos) != 1) && (!cTracker.isOccupied(newPos)));
    }

    public boolean checkMove(int dx, int dy) {
	Position newPos = new Position(getPosition().getX() + dx,
				       getPosition().getY() + dy);
	return checkMove(newPos);
    }

    public void interact() {
    }

    public void attack(Character c) {
	int damage = 2;
	c.getAttacked(damage);
    }
    public void getAttacked(int damage) {
	hp = hp - damage;
    }
}