/* This is a class representing the player */

import java.util.ArrayList;

public class PlayerCharacter extends Character {
    private CharacterTracker cTracker;

    public PlayerCharacter (int moveSpeed, int hp, String name, int sightRange, CharacterTracker ct, Position p) {
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
    }

    public void attack(Character c) {
	int damage = 2;
	c.getAttacked(damage);
    }

    public boolean isPC() {
	return true;
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