import java.awt.event.ActionListener;
import java.awt.event.*;

public class Controller implements ActionListener, KeyListener{
    public Controller(Model m) {
	model = m;
    }
    public void actionPerformed(ActionEvent e){
	String action = e.getActionCommand();
        System.out.println(action);
	/* 
	switch(action){
	case("quit"): System.exit(0);
	    break;
	case("start"): model.processInput("start");// Start the game!
	    break;
	}
	*/
	// Another java 6 work around remove as soon as you see and
	// revert to switch? add other cases here as needed? We might
	// need to "implement" some kind of "time" listener too I
	// would assume.
	if (action.equals("quit")) {
	    System.out.println("User pressed quit I do believe.");
	    System.exit(0);
	} else if (action.equals("start")) {
	    model.processInput("start");
	}
    }
	
    public void keyPressed(KeyEvent e){
	String keyPressed = e.getKeyText(e.getKeyCode());
//	System.out.println(keyPressed);
	/*if(keyPressed.equals("W") || keyPressed.equals("Up")){
	    // tell game to move up
	    System.out.println("w pressed");
	    model.processInput("w");
	}else if(keyPressed.equals("S") || keyPressed.equals("Down")){
	    // tell game to move down
	    model.processInput("s");
	}else if(keyPressed.equals("A") || keyPressed.equals("Left")){
	    // Tell the game to shake it to the beat
	    model.processInput("a");
	}else if(keyPressed.equals("D") || keyPressed.equals("Right")){
	    // whatever
	    model.processInput("d");
	    }*/
	// A slight simplification although you can no longer use arrow keys..
	model.processInput(keyPressed.toLowerCase());
    }
	
    public void keyReleased(KeyEvent e){
	// Let's just do nothing atm!
    } 
	
    public void keyTyped(KeyEvent e){
	// Whateverrrrr 
    }
    public Model getModel(){
	return model;
    }
    //~ system.out.println("Im a big time code writer");
    private Model model;
}
