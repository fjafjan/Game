/* This class keeps track of the whereabouts of all the characters
 * currently in the game. Also checks for collisions and other
 * interactions between Characters and the environment/Characters */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class CharacterTracker {
    // Hashmap that stores characters in the game
    private HashMap<String, Character> characters;
    // Hashmap that stores character positions with regard to their hashcodes
    private HashMap<String, Position> posOfCharacter;
    // Hashmap that stores positions with characters on them.
    private HashMap<String, String> characterAtPos;
    private Character player;

    public CharacterTracker() {
	characters = new HashMap<String, Character>();
	posOfCharacter = new HashMap<String, Position>();
	characterAtPos = new HashMap<String, String>();
    }

    public void addPlayer(Character c) {
	player = c;
    }

    public Position getPlayerPosition() {
	return player.getPosition();
    }

    public Character getPlayer() {
	return player;
    }

    public void add(Character c) {
	characters.put(c.toString(), c);
	// Got to be careful here because positions are mutable and
	// thus not very suitable as keys in a hashtable. Therefore,
	// copy the position objects rather than using references to
	// the ones owned by characters.
	posOfCharacter.put(c.toString(), c.getPosition().copy());
	characterAtPos.put(c.getPosition().toString(), c.toString());
    }

    public void remove(Character c) {
	characters.remove(c.toString());
	posOfCharacter.remove(c.toString());
	characterAtPos.remove(c.getPosition().toString());
    }

    public void updatePosition(Character c) {
	// get id, use to get current pos. use current pos and id to update to new pos.
	String cId = c.toString();
//	System.out.println("Updating character position of: " + cId);
	Position oldPos = posOfCharacter.get(cId);
//	System.out.println("oldpos is " + oldPos);
	characterAtPos.remove(oldPos.toString());
	characterAtPos.put(c.getPosition().toString(), cId);
	posOfCharacter.remove(cId);
	posOfCharacter.put(cId, c.getPosition().copy());
//	System.out.println("newpos is " + c.getPosition().copy());
    }

    public Collection<Character> getNPClist() {
	return characters.values();
    }

    // Returns a character at position pos. WARNING can return null!!
    public Character getCharacterAtPos(Position pos) {
	System.out.println("Reached getCharacter method");
	System.out.println("Fetching character at: " + pos);
	String characterId = characterAtPos.get(pos.toString());
//	System.out.println("CharacterId is: " + characterId);
	if (characterId != null) {
	    return characters.get(characterId);
	} else {
	    // Throw some exception?
	    return null;
	}
    }

    public boolean isOccupied(Position pos) {
	if (pos.equals(getPlayerPosition())) {
	    return true;
	}
	Iterator<Character> iter = getNPClist().iterator();
	while (iter.hasNext()) {
	    if (pos.equals(iter.next().getPosition())) {
		return true;
	    }
	}
	return false;
    }

    /* This method is potentially quite slow, could be sped up by
    keeping to sorted sets of characters, sorted by their x and y
    coordinate, respectively. Then, only characters within length
    sight of c need to be checked. Atm, however, all characters are
    checked. */
    public ArrayList<Character> getCharactersInRadius(Character c) {
	ArrayList<Character> resultList = new ArrayList<Character>();
	Position cPos = c.getPosition();
	int sight = c.getSightRange();
	Iterator<Character> iter = characters.values().iterator();
	while(iter.hasNext()) {
	    Character e = iter.next();
	    if(cPos.distanceTo(e.getPosition()) <= sight) {
		resultList.add(e);
	    }
	}
	return resultList;
    }
}
