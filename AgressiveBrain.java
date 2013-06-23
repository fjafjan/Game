/* This AI always attacks closest player target if visible */

import java.lang.Class;
import java.util.Iterator;
import java.lang.Math;

public class AgressiveBrain extends AIBrain {
    public AgressiveBrain() {
    }

    public AgressiveBrain(ComputerCharacter c) {
	body = c;
    }
    
    // doSomething returns true when successfully done.
    public boolean doSomething() {
	/* Perhaps not ideal that player gets passed around as much as
	 * currently. In the future, consider using location based
	 * attacks. */
	Character player = body.getPlayer();
	Position pPos = body.getPlayerPosition();
	if (pPos.distanceTo(body.getPosition()) <= body.getSightRange()) {
	    body.attack(player);
	    System.out.println("Player was attacked!");
	    // Terminate
	    return true;
	} else {
	    // Do some random walk
	    int dx = (int) ((Math.random() - 0.5) * 4);
	    int dy = (int) ((Math.random() - 0.5) * 4);
	    body.move(dx, dy);
	}
	return true;
    }
}