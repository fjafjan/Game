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

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.*;


public class RuneDrawer implements MouseListener, MouseMotionListener{    

    private ArrayList<Integer> runeList;
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
            currentRune = new Rune();
            isDrawing   = true;
            System.out.println("Mouse 2 clicked at coordinate : ["+e.getX()+","+e.getY()+"]");
        }
         
    }
     
    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(e.paramString());
        if ((isDrawing) && (e.getButton() == e.BUTTON3)){
            System.out.println("Rune is finished drawing. Our result was");
            isDrawing = false;  // We are done drawing 
            currentRune.printRune();
            
            int runeSize = 20;
            
            int[] corners = currentRune.getCorners();
            System.out.println(""+corners[0]+","+corners[1]+","+corners[2]+","+corners[3]);
            BufferedImage img = currentRune.returnImage();
            BufferedImage goodScale = 
                new BufferedImage(runeSize,runeSize, BufferedImage.TYPE_INT_RGB);
            int h = goodScale.getHeight();
            int w = goodScale.getWidth();
            int white = Color.white.getRGB();
            int[] whites = new int[h*w];
            for(int i=0;i<h*w;i++){
                whites[i] = white;
            }
            // This step could most likely be improved using copyarry
            // as explained vaguelly here:
            // http://stackoverflow.com/questions/5672697/ ...
            //  ... java-filling-a-bufferedimage-with-transparent-pixels
            goodScale.setRGB(0,0,w,h,whites,0,1);
            
            
            
            if(img.getHeight() > img.getWidth()){ // We should add width
                double ratio = (double)img.getWidth()/img.getHeight();
                int offset = (runeSize - (int)(ratio*runeSize))/2;
                System.out.println(""+offset);

                System.out.println(""+ratio);
                System.out.println(""+(int)ratio*runeSize);
                Image scaleImg = img.getScaledInstance((int)(ratio*runeSize),runeSize, Image.SCALE_FAST);
                goodScale.getGraphics().drawImage(scaleImg, offset,0,null);
            }else{
                double ratio = (double)img.getHeight()/img.getWidth();
                int offset = (runeSize - (int)(ratio*runeSize))/2;
                System.out.println(""+ratio);
                System.out.println(""+offset);
                Image scaleImg = img.getScaledInstance(runeSize,(int)(ratio*runeSize), Image.SCALE_FAST);
                goodScale.getGraphics().drawImage(scaleImg, 0,offset,null);
            }
            File f = new File("MyFile.png");
            // Should fix this so that white space is added before shrinking. 
            File f2= new File("Shrunk.png");
            File f3= new File("testRune.png");
            try{
                ImageIO.write(img, "PNG",  f);
                ImageIO.write(goodScale, "PNG",  f2);
            }catch(IOException eee){
                System.out.println("What the fuuu");
            }
            
            w = goodScale.getWidth();
            h = goodScale.getHeight();
            
            
            int[] testing = new int[h*w];
            int[] colorList = goodScale.getRGB(0,0,w,h,testing,0,1);
            
            Rune testRune = new Rune();
            Point p;
            for(int i=0;i<w;i++){
                for(int j=0;j<h;j++){
                    if(goodScale.getRGB(i,j)==-16777216){
                        System.out.println("i="+i+", j="+j);
                        p = new Point(i,j);
                        testRune.addPoint(p);
                    }
                }
            }
             try{
                ImageIO.write(testRune.returnImage(), "PNG",  f3);
            }catch(IOException eee){
                System.out.println("What the fuuu");
            }

            for(int i=0;i<colorList.length;i++){
                //~ if (testing[i]!=0 && testing[i]!=-1){
                    //~ System.out.println("i is "+i);
                    //~ System.out.println("test:  " +testing[i]);
                //~ }else if(colorList[i]!=0){
                    //~ System.out.println("colist:  "+colorList[i]);
                //~ }
            }
            System.out.println("len is "+colorList.length);
            System.out.println("Done!");
        }
    }
     
    @Override
    public void mouseDragged(MouseEvent e) {
        if(e.getModifiersEx() == e.BUTTON3_DOWN_MASK){            
            Point drawPoint = new Point(e.getX(), e.getY());
            currentRune.addPoint(drawPoint);
        }
         
    }
     
    @Override
    public void mouseMoved(MouseEvent e) {
        // Not gonna do anything here. It feels like a waste to still have it...
    }
    
    
}
