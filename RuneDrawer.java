// Most of this code is stolen from 
// http://mrbool.com/creating-a-simple-mouse-analyzer-with-java-swing/24507

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.util.ArrayList;





public class RuneDrawer implements MouseListener, MouseMotionListener{    

    private ArrayList<Integer> runeList;
    //~ private 

    public RuneDrawer(){
        // For now we do nothing right?
    }
    //~ @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == e.BUTTON1){
            System.out.println("Mouse 1 clicked at coordinate : ["+e.getX()+","+e.getY()+"]");
        }else if(e.getButton() == e.BUTTON2){
            System.out.println("Mouse 2 clicked at coordinate : ["+e.getX()+","+e.getY()+"]");
        }
    }
 
    //~ @Override
    public void mouseEntered(MouseEvent e) {
       System.out.println("Current mouse Coordinates : ["+e.getX()+","+e.getY()+"]");
    }
@Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse outside access window");
         
    }
     
    //~ @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed at coordinates : ["+e.getX()+","+e.getY()+"]");
         
    }
     
    //~ @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Current mouse coordinates : ["+e.getX()+","+e.getY()+"]");
         
    }
     
    //~ @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse dragged at coordinates : ["+e.getX()+","+e.getY()+"]");
         
    }
     
    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("Mouse moved to coordinates : ["+e.getX()+","+e.getY()+"]");
    }
}
