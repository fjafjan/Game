/* This is a class representing the player */

import java.util.ArrayList;

public class PlayerCharacter extends Character {

    public PlayerCharacter (int moveSpeed, int hp, String name, int sightRange, CharacterTracker ct, Position p, GameMap gm) {
	this.moveSpeed = moveSpeed;
	this.hp = hp;
	this.name = name;
	this.sightRange = sightRange;
	cTracker = ct;
	pos = p;
	myId = super.id;
	super.id++;
	cTracker.addPlayer(this);
	spriteId = 2;
	gameMap = gm;
    }

    public void attack(Character c) {
	int damage = 2;
	System.out.println("Attacking character.");
	c.getAttacked(damage);
    }

    public void attack(Position pos) {
	attack(cTracker.getCharacterAtPos(pos));
    }

    public boolean isPC() {
	return true;
    }

    public boolean checkMove(Position newPos) {
	// Checks for walls and other Characters returns true if move
	// is ok. If the destination is occupied by an enemy. Attack!!
	if (cTracker.isOccupied(newPos)) {
	    System.out.println("Player trying to move to occupied square!!!");
	    attack(newPos);
	    return false;
	} else if (gameMap.getTileFromPixel(newPos) == 0){
	    return true;
	} else {
	    System.out.println("square is occupied lols");
	    return false;
	}
    }

    public void interact() {
	// Do nothing
    }
    
    public ArrayList<Character> getVisibleCharacters() {
	return cTracker.getCharactersInRadius(this);
    }
    
    public void getAttacked(int damage) {
	hp = hp - damage;
	System.out.println("You were attacked and have " + hp + " hp left.");
	// Check if dead
	if (hp <= 0) {
	    die();
	}
    }

    public void die() {
	super.die();
	System.out.println("AUUUUU you died.");
	System.exit(0);
    }
}