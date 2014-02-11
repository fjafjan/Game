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
    private static int gameWidth; // In tiles
    private static int gameHeight; // In tiles
    private int xPos, yPos;
    private int xStep, yStep;

    /* This constant determines the number of pixels in a tile
       side. Since tiles are square they are of dimension TILESIZE *
       TILESIZE */
    private int TILESIZE = 16;


    private JLabel oldFrame;
	private static JPanel panel;
	
	private Model gameModel;
	private Controller gameController;
	
    public Viewer(Controller gameController, int[] resolution){
	gameModel = gameController.getModel();
	GameState gameState = gameModel.getGameState();
	TILESIZE = GameConstants.TILESIZE;
	gameWidth = resolution[0]/TILESIZE;
	gameHeight= resolution[1]/TILESIZE;
	initUI(resolution);
    }

    public final void initUI(int[] resolution){
	//	int model = fulmodel;
	panel = new JPanel();
	getContentPane().add(panel);
        //~ panel.setLayout(new BorderLayout());
	panel.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
		
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());

	JButton quitButton = new JButton("Quit");

	quitButton.setActionCommand("quit");
	quitButton.addActionListener(gameController);

	c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
	c.weightx = 0.1;
        c.weighty = 0.1;
	menuPanel.add(quitButton, c);

        
	JButton startButton = new JButton("Start game!");
	//~ startButton.setBounds(90,100,120,80);
	startButton.setActionCommand("start");
	startButton.addActionListener(gameController);
	c.gridx = 0;
	menuPanel.add(startButton, c);
        
        //~ c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        //~ c.gridy = 0;
	//~ c.weightx = 0.1;
        //~ c.weighty = 0.1;

//        panel.add(menuPanel, c);
	setTitle("Just trying stuff out");
	int TITLEBARHEIGHT = 50;
	windowWidth = resolution[0];
	windowHeight = resolution[1];
	setSize(windowWidth+10,windowHeight+30);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
		
    public static void startGame(){
	    // nothing for now?
    }
	
    public void update(Observable obs, Object o){
        drawGame();
    }
    
    
    public void drawGame(){        
	GameState gameState = gameModel.getGameState();
        //BufferedImage bImage = new BufferedImage(windowWidth, windowHeight,
	//						 BufferedImage.TYPE_INT_RGB);
	//Graphics2D drawer = bImage.createGraphics();
        
        xPos = 0;yPos = 0;
        xStep = TILESIZE;
	yStep = TILESIZE;

        GamePanel gamePanel = new GamePanel(gameState, windowWidth, windowHeight);
        //~ gamePanel.setPreferredSize(new Dimension(windowWidth, windowHeight));
        GridBagConstraints c 	 = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        RuneDrawer runeDraw = new RuneDrawer();
        gamePanel.addMouseListener(runeDraw);
        gamePanel.addMouseMotionListener(runeDraw);
        panel.add(gamePanel,c);
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

