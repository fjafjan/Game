// Most of this code is stolen from 
// http://mrbool.com/creating-a-simple-mouse-analyzer-with-java-swing/24507

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.awt.Point;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.*;


public class RuneDrawer implements MouseListener, MouseMotionListener{    

    private ArrayList<Point> runeList;
    private Rune currentRune;
    private boolean isDrawing;


    public RuneDrawer(){
        isDrawing = false;
        // For now we do nothing right?
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == e.BUTTON1){
            System.out.println("Mouse 1 clicked at coordinate : ["+e.getX()+","+e.getY()+"]");
        }else if(e.getButton() == e.BUTTON3){
            System.out.println("Mouse 2 clicked at coordinate : ["+e.getX()+","+e.getY()+"]");
        }
    }
 
    @Override
    public void mouseEntered(MouseEvent e) {
       System.out.println("Entered Current mouse Coordinates : ["+e.getX()+","+e.getY()+"]");
    }
    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse outside access window");
         
    }
     
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed at coordinates : ["+e.getX()+","+e.getY()+"]");
        if(e.getButton() == e.BUTTON3){
            runeList = new ArrayList<Point>();
            Point drawPoint = new Point(e.getX(), e.getY());
            runeList.add(drawPoint);
            isDrawing   = true;
            System.out.println("Mouse 2 clicked at coordinate : ["+e.getX()+","+e.getY()+"]");
        }
         
    }
     
    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(e.paramString());
        currentRune = new Rune(runeList);
        if ((isDrawing) && (e.getButton() == e.BUTTON3)){
            //~ System.out.println("Rune is finished drawing. Our result was");
            //~ currentRune.printRune();
            
            isDrawing = false;  // We are done drawing 
            
            int runeSize = currentRune.runeSize;
            
            int[] corners = currentRune.getCorners();
            BufferedImage img = currentRune.returnImage();
            File f = new File("MyFile.png");
            File f2= new File("CircleRune.png");
            BufferedImage circleIm = null;
            try{
                ImageIO.write(img, "PNG",  f);
                circleIm = ImageIO.read(f2);
            }catch(IOException eee){
                System.out.println("What the fuuu");
            }
            
            Rune idealRune = new Rune(getListFromIm(circleIm));
            
            boolean yuss = currentRune.similarTo(idealRune);
            
            
            
            
            System.out.println("Done!");
        }
    }
     
    @Override
    public void mouseDragged(MouseEvent e) {
        if(e.getModifiersEx() == e.BUTTON3_DOWN_MASK){            
            Point drawPoint = new Point(e.getX(), e.getY());
            runeList.add(drawPoint);
        }
         
    }
     
    @Override
    public void mouseMoved(MouseEvent e) {
        // Not gonna do anything here. It feels like a waste to still have it...
    }
    private ArrayList<Point> getListFromIm(BufferedImage im){
        ArrayList<Point> testList = new ArrayList<Point>();
        Point p;
        for(int i=0;i<im.getWidth();i++){
            for(int j=0;j<im.getHeight();j++){
                if(im.getRGB(i,j)==-16777216){
                    System.out.println("i="+i+", j="+j);
                    p = new Point(i,j);
                    testList.add(p);
                }
            }
        }
        return testList;
    }
    
    
}
