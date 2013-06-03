import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.geom.Rectangle2D;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Main{
    public static void main(String[] args ){
		Model gameModel = new Model();
		Controller gameController = new Controller(gameModel);
		Viewer gameViewer = new Viewer(gameController);
		gameViewer.drawGame();
		gameViewer.setVisible(true);
        System.out.println("Hejsan");
        System.out.println(Color.blue);
        System.out.println(Color.gray);
    }
}
