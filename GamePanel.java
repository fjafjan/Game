import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

public class GamePanel extends JPanel{
    private int[][] gameState;
    private int xPos, yPos;
    private int windowWidth, windowHeight;
    public GamePanel(int[][] gameStateIn, int width, int height){
        super();
        gameState = gameStateIn;
        windowWidth = width;
        windowHeight = height;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        xPos = 0;yPos = 0;
		int xStep = windowWidth/gameState.length;
		int yStep = windowHeight/gameState[0].length;
		for(int i=0;i<gameState.length;i++){
            xPos = i*xStep;
            yPos = 0;
			for(int j=0;j<gameState[0].length;j++){
                yPos = j*yStep;
				int drawType = gameState[i][j];
                RectangularShape drawObj;
                Color chosenColor;
                RectangularShape contour = new Rectangle2D.Double(xPos,yPos,xStep,yStep);
                // Gives us what to draw given the game state
                // I should probably re-write this as a separate method 
                // to improve readability
				switch(drawType){
					case 0:
                        drawObj = new Rectangle2D.Double(xPos,yPos,xStep,yStep);
                        chosenColor = Color.white;
                        break;
                    case 1:
                        drawObj = new Rectangle2D.Double(xPos,yPos,xStep,yStep);
                        chosenColor = Color.gray;
                        break;
                    case 2:
                        drawObj = new Ellipse2D.Double(xPos,yPos,xStep,yStep);
                        chosenColor = Color.blue;
                        break;
                    case 3:
                        drawObj = new Ellipse2D.Double(xPos,yPos,xStep,yStep);
                        chosenColor = Color.red;
                        break;
                    default:
                        drawObj = new Rectangle2D.Double(xPos,yPos,xStep,yStep);
                        chosenColor = Color.white;
                        break;
                }
                // Now we draw the component we have detected
                Graphics2D g2 = (Graphics2D)g;        
                g2.setColor(chosenColor);
                //RectangularShape drawObj = (RectangularShape) drawObj;
                g2.fill(drawObj);
                g2.setColor(Color.black);
                g2.draw(contour);
            }
        }
    }
}
