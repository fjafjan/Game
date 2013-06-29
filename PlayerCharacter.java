/* This is a class representing the player */

import java.util.ArrayList;

public class PlayerCharacter extends Character {
    private CharacterTracker cTracker;

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
	c.getAttacked(damage);
    }

    public boolean isPC() {
	return true;
    }

    public boolean checkMove(Position newPos) {
	// Checks for walls and other Characters returns true if move
	// is ok
	return (gameMap.get(newPos) != 1) && (!cTracker.isOccupied(newPos));
    }

    public void interact() {
	// Do nothing
    }
    public ArrayList<Character> getVisibleCharacters() {
	return cTracker.getCharactersInRadius(this);
    }
    public void getAttacked(int damage) {
	hp = hp - damage;
    }
}