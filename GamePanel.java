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
import java.util.Iterator;

public class GamePanel extends JPanel{
    private GameState gameState;
    private int xPos, yPos;
    private int windowWidth, windowHeight;
    public GamePanel(GameState gameStateIn, int width, int height){
        super();
        gameState = gameStateIn;
        windowWidth = width;
        windowHeight = height;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        xPos = 0; yPos = 0;
        int xStep = GameConstants.TILESIZE;//windowWidth/gameState.length;
        int yStep = GameConstants.TILESIZE;//windowHeight/gameState[0].length;
        System.out.println("TILESIZE: " + xStep);
        /* Calculate how many pixels of the */
        int xOffset = gameState.playerPosition.getX() % xStep;
        int yOffset = gameState.playerPosition.getY() % yStep;
        
        System.out.println("PlayerPos is: " + gameState.playerPosition.getX() + ", " + gameState.playerPosition.getY() + "Offsets = " + xOffset + ", " + yOffset);

        xPos = -xOffset;
        for(int i=0;i<gameState.visibleTiles.size();i++){
            yPos = -yOffset;
            for(int j=0;j<gameState.visibleTiles.get(0).size();j++){
                int drawType = gameState.visibleTiles.get(i).get(j);
                drawStuff(drawType, xPos, yPos, xStep, yStep, g);
                yPos = yPos + yStep;
            }
            xPos = xPos + xStep;
        }

        System.out.println("First tile drawn " + gameState.visibleTiles.get(0).get(0));
        // Now lets draw the characters on top of the tiles!
        Iterator<PositionAndSpriteID> iter = gameState.visibleCharacters.iterator();
        while (iter.hasNext()) {
            PositionAndSpriteID pid = iter.next();
            drawStuff(pid._spriteID, pid._pos.getX(), pid._pos.getY(), xStep, yStep, g);
        }
        
    }
    
    private void drawStuff(int drawType, int xPos, int yPos, int xStep, int yStep, Graphics g) {
        /*        System.out.println("Redrawing something " + drawType + " at pos " +
                           xPos + ", " +  yPos + " width " +
                           xStep + " height " +  yStep);*/
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
