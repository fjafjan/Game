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
import javax.swing.*;

import java.util.*;
import java.awt.image.BufferedImage;


public class Viewer extends JFrame implements Observer{
    private static int windowWidth;
    private static int windowHeight;
    private static int gameWidth; // I want these to be final but it won't compile...
    private static int gameHeight;
    private int xPos, yPos;
    private int xStep, yStep;

    private JLabel oldFrame;
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
		//~ quitButton.setBounds(50,60,80,30); // What do these numbers mean?
		quitButton.addActionListener(gameController);
		quitButton.setActionCommand("quit");
		c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
		c.weightx = 0.1;
        c.weighty = 0.1;
		menuPanel.add(quitButton, c);

        
		JButton startButton = new JButton("Start game!");
		//~ startButton.setBounds(90,100,120,80);
		startButton.addActionListener(gameController);
		startButton.setActionCommand("start");
		c.gridx = 0;
		menuPanel.add(startButton, c);
        
        //~ c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        //~ c.gridy = 0;
		//~ c.weightx = 0.1;
        //~ c.weighty = 0.1;
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
	
    public void update(Observable obs, Object o){
        drawGame();
    }
    
    
	public void drawGame(){
        // clean out the old frame
        try{
            panel.remove(oldFrame);
        }catch(Exception e){
            //whatever
        }
		int[][] gameState = gameModel.getGameState();
        BufferedImage bImage = new BufferedImage(windowWidth, windowHeight,
        BufferedImage.TYPE_INT_RGB);
        Graphics2D drawer = bImage.createGraphics();
        
        xPos = 0;yPos = 0;
        xStep = windowWidth/gameState.length;
		yStep = windowHeight/gameState[0].length;

        //~ System.out.println(xStep + " and" + yStep);
        //~ for(int i=0;i<gameState.length;i++){
            //~ System.out.println("");
            //~ for(int j=0;j<gameState[0].length;j++){
                //~ System.out.print(gameState[i][j]);
            //~ }
        //~ }

        for(int i=0;i<gameState.length;i++){
            xPos = i*xStep;
			for(int j=0;j<gameState[0].length;j++){
                yPos = j*yStep;
				int drawType = gameState[i][j];
                
                // Gives us what to draw given the game state in this pos
                RectangularShape drawObj = getDrawObj(drawType);
                Color chosenColor = getDrawColor(drawType);


                // Now we draw the component we have detected      
                drawer.setColor(chosenColor);
                drawer.fill(drawObj);

                // And we draw a contour around it
                RectangularShape contour = new Rectangle2D.Double(xPos,yPos,xStep,yStep);
                drawer.setColor(Color.black);
                drawer.draw(contour);
            }
        }
        oldFrame = new JLabel(new ImageIcon(bImage));
        

        GamePanel gamePanel = new GamePanel(gameState, windowWidth, windowHeight);
        //~ gamePanel.setPreferredSize(new Dimension(windowWidth, windowHeight));
        GridBagConstraints c 	 = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 1;
		//~ gamePanel.setLayout(gameLayout);
        //~ gamePanel.setLayout(new GridLayout(gameWidth,gameHeight));
		//~ int xStep = windowWidth/gameState.length;
		//~ int yStep = windowHeight/gameState[0].length;
        //~ JPanel gameHolder = new JPanel();
        //~ gameHolder.setLayout(new BoxLayout(gameHolder,  BoxLayout.PAGE_AXIS));
        //~ gameHolder.add(gamePanel);
		//~ panel.add(gameHolder, c);
        RuneDrawer runeDraw = new RuneDrawer();
        gamePanel.addMouseMotionListener(runeDraw);
        panel.add(gamePanel,c);	
		//~ gamePanel.repaint();
        //~ panel.add(oldFrame, c);
		panel.repaint();
	}
    
    private Graphics2D drawGrid(Graphics2D g){
        return g;
    }
    
    // For now just returns a shape corresponding to the game state
    // at that position. In the future it should probably return an image
    private RectangularShape getDrawObj(int drawType){
        switch(drawType){
            case 0: return new Rectangle2D.Double(xPos,yPos,xStep,yStep);
            case 1: return new Rectangle2D.Double(xPos,yPos,xStep,yStep);
            case 2: return new Ellipse2D.Double(xPos,yPos,xStep,yStep);
            case 3: return new Ellipse2D.Double(xPos,yPos,xStep,yStep);
            default: return new Rectangle2D.Double(xPos,yPos,xStep,yStep);
        }
    }
    
    // Returns the color of a given object. Will be removed once images
    // are implemented
    private Color getDrawColor(int drawType){
            switch(drawType){
            case 0: return Color.white;
            case 1: return Color.gray;
            case 2: return Color.blue;
            case 3: return Color.red;
            default: return Color.white;
        }
    }
  
        
}

