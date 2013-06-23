/* This class keeps track of the whereabouts of all the characters
 * currently in the game. Also checks for collisions and other
 * interactions between Characters and the environment/Characters */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class CharacterTracker {
    private HashMap<String, Character> characters;
    private Character player;

    public CharacterTracker() {
	characters = new HashMap<String, Character>();
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
    }

    public void remove(Character c) {
	characters.remove(c.toString());
    }

    public Collection<Character> getNPClist() {
	return characters.values();
    }

    /* This method is potentially quite slow, could be speeded up by
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