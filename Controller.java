import java.awt.event.ActionListener;
import java.awt.event.*;

public class Controller implements ActionListener{
//~ Controller.java:4: error: Controller is not abstract and does not override
 //~ abstract method actionPerformed(ActionEvent) in ActionListener
	public Controller(Model m) {
		model = m;
	}
	public void actionPerformed(ActionEvent e){
		String action = e.getActionCommand();
		switch(action){
		case("quit"): System.exit(0);
			break;
		case("start"): // Start the game!
			break;
		// add other cases here as needed? We might need to "implement" some kind of 
		// "time" listener too I would assume.
	}
	
	public Model getModel(){
		return model;
	}
	//~ system.out.println("Im a big time code writer");
	private Model model;
}
