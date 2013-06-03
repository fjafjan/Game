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
        panel.setLayout(new BorderLayout());
		
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);

		JButton quitButton = new JButton("Quit");
		quitButton.setBounds(50,60,80,30); // What do these numbers mean?
		quitButton.addActionListener(gameController);
		quitButton.setActionCommand("quit");
		menuPanel.add(quitButton);

		JButton startButton = new JButton("Start game!");
		startButton.setBounds(90,100,120,40);
		startButton.addActionListener(gameController);
		startButton.setActionCommand("start");
		menuPanel.add(startButton);
        
        panel.add(menuPanel, BorderLayout.LINE_START);
		
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
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(gameWidth,gameHeight));
		int[][] gameState = gameModel.getGameState();
		xpos = 0;ypos = 0;
		int xStep = windowWidth/gameState.length;
		int yStep = windowHeight/gameState[0].length;
		
		for(int i=0;i<gameState.length;i++){
			for(int j=0;j<gameState[0].length;j++){
				int drawType = gameState[i][j];
                final RectangularShape drawObj;
                final Color chosenColor;
                final RectangularShape contour = new Rectangle2D.Double(0,0,xStep,yStep);
				switch(drawType){
					case 0:
                        drawObj = new Rectangle2D.Double(0,0,xStep,yStep);
                        chosenColor = Color.white;
                        break;
                    case 1:
                        drawObj = new Rectangle2D.Double(0,0,xStep,yStep);
                        chosenColor = Color.gray;
                        break;
                    case 2:
                        drawObj = new Ellipse2D.Double(0,0,xStep,yStep);
                        chosenColor = Color.blue;
                        break;
                    case 3:
                        drawObj = new Ellipse2D.Double(0,0,xStep,yStep);
                        chosenColor = Color.red;
                        break;
                    default:
                        drawObj = new Rectangle2D.Double(0,0,xStep,yStep);
                        chosenColor = Color.white;
                        break;

				}
                gamePanel.add(new JPanel(){
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D)g;
                        
                        g2.setColor(chosenColor);
                        //RectangularShape drawObj = (RectangularShape) drawObj;
                        g2.fill(drawObj);
                        g2.setColor(Color.black);
                        g2.draw(contour);
                    }
                });

			}
            panel.add(gamePanel, BorderLayout.CENTER);	
            //~ panel.pack()
		}
	}
						
		// gameState = model.getGameState();
		
	
	public static int[][] fulskapa(){
		int[][] ful = new int[10][10];
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				if(i==0 || j == 0 || j==9 || i == 9){
					ful[i][j] = 1;
				}else{
					ful[i][j] = 0;
				}
			}
		}
		ful[5][4] = 2;
		ful[5][6] = 3;
		return ful;
	}
}

