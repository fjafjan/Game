/* Inherits character and also has method doTurn() which essentially
 * is its AI and decides what the character will do when performed */

public class ComputerCharacter extends Character {

    private AIBrain myBrain;
    
    public ComputerCharacter() {
	moveSpeed = 1;
	hp = 10;
	name = "Benny Banan";
	sightRange = 10;
	myBrain = new CowardsBrain();	
    }

    public ComputerCharacter(int moveSpeed, int hp, string name, int sightRange) {
	this.moveSpeed = moveSpeed;
	this.hp = hp;
	this.name = name;
	this.sightRange = sightRange;
	myBrain = new CowardsBrain();
    }

    public void doTurn() {
	myBrain.doSomething();
    }

    public void interact() {
    }

    public int attack() {
	return 2;
    }
}