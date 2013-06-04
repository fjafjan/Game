import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Viewer extends JFrame{
    private static int xpos;
    private static int ypos;
    private static int windowWidth;
    private static int windowHeight;
    private static int gameWidth; // I want these to be final but it won't compile...
    private static int gameHeight;
    
	private static JPanel panel;
	
	private Model gameModel;
	private Controller gameController;
	
	public Viewer(Controller gameController){
		gameModel = gameController.getModel();
		int[][] gameState = gameModel.getGameState();
		gameWidth = gameState.length;
		gameHeight= gameState[0].length;
		initUI();
	}

	public final void initUI(){
	//	int model = fulmodel;
		panel = new JPanel();
		getContentPane().add(panel);
        //~ panel.setLayout(new BorderLayout());
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();


		
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());

		JButton quitButton = new JButton("Quit");
		quitButton.setBounds(50,60,80,30); // What do these numbers mean?
		quitButton.addActionListener(gameController);
		quitButton.setActionCommand("quit");
		c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
		c.weightx = 0.5;
        c.weighty = 0.5;
		menuPanel.add(quitButton, c);

        
		JButton startButton = new JButton("Start game!");
		startButton.setBounds(90,100,120,40);
		startButton.addActionListener(gameController);
		startButton.setActionCommand("start");
		c.gridx = 0;
		menuPanel.add(startButton, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
		c.weightx = 0.5;
        c.weighty = 0.5;
        panel.add(menuPanel, c);
		setTitle("Just trying stuff out");
		windowWidth = 300;
		windowHeight = 400;
		setSize(windowWidth,windowHeight);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//~ public static void main(String args[]){
		//~ SwingUtilities.invokeLater(new Runnable() {
			//~ public void run(){
				//~ Viewer view = new Viewer();
                //~ view.drawGame();
				//~ view.setVisible(true);
			//~ }
		//~ });
	//~ }
	
	public static void startGame(){
		// nothing for now?
	}
	
	public void drawGame(){
		int[][] gameState = gameModel.getGameState();

        GamePanel gamePanel = new GamePanel(gameState, windowWidth, windowHeight);
        //~ gamePanel.setPreferredSize(new Dimension(windowWidth, windowHeight));
        GridBagConstraints c 	 = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        //~ c.gridwidth = 3;
        //~ c.gridheight= 3;
		//~ gamePanel.setLayout(gameLayout);
        //~ gamePanel.setLayout(new GridLayout(gameWidth,gameHeight));
		xpos = 0;ypos = 0;
		int xStep = windowWidth/gameState.length;
		int yStep = windowHeight/gameState[0].length;
		panel.add(gamePanel, c);	
		gamePanel.repaint();
		panel.repaint();
	}

		
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
	

=======
>>>>>>> db6eba4154f2db8a8f7a1f989a52da31f661340e
=======
>>>>>>> db6eba4154f2db8a8f7a1f989a52da31f661340e
=======
>>>>>>> db6eba4154f2db8a8f7a1f989a52da31f661340e
}

