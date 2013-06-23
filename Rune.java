// This is a class that holds the data for a Rune and does various
// manipulation one might want to use with a rune such as returning
// an image of the rune (in whatever format), converting between 
// different formats and comparing with different runes. 

import java.util.ArrayList;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.Color;

class Rune{
    
    private ArrayList<Point> runeList;

    public Rune(){
        runeList = new ArrayList<Point>();
    }
    
    public void addPoint(Point p){
        runeList.add(p);
    }
    
    public void printRune(){
        for(int i=0;i<runeList.size();i++){
            System.out.println(runeList.get(i).toString());
        }
    }
    
    // returns the Arraylist representation of the rune
    public ArrayList<Point> toArrayList(){
        // Sadly I think we need to make a deep copy. At least for the moment... 
        ArrayList<Point> copyList = new ArrayList<Point>();
        for(int i=0;i<runeList.size();i++){
            copyList.add(new Point(runeList.get(i)));
        }
        return copyList;
    }
    
    public int[] getCorners(){
        int minX = 123456789;
        int minY = 123456789;
        int maxX = 0;
        int maxY = 0;
        int iminX, iminY, imaxX, imaxY;
        for(int i=0;i<runeList.size();i++){
            int currX = (int) runeList.get(i).getX();
            int currY = (int) runeList.get(i).getY();
            if (currX > maxX){
                maxX = currX;
                imaxX= i; // Not sure this is actually needed but can easily take it out
            }
            if(currX < minX){
                minX = currX;
                iminX= i; // Not sure this is actually needed but can easily take it out                
            }
            if(currY > maxY){
                maxY = currY;
                imaxY= i;// Not sure this is actually needed but can easily take it out                
            }
            if(currY < minY){
                minY = currY;
                iminY= i;// Not sure this is actually needed but can easily take it out                
            }
        }
        int corners[] = new int[] {minX, minY, maxX, maxY};
        return corners;
    }
    

    public BufferedImage returnImage(){
        // upon creation the rune should note its own sie using the above method
        int[] corners = getCorners();
        int runeWidth  = corners[2] - corners[0];
        int runeHeight = corners[3] - corners[1];
        int minX = corners[0];
        int minY = corners[1];
        System.out.println("The width is " + runeWidth + " and the height is " + runeHeight);
        
        BufferedImage runeImage = new BufferedImage(runeWidth+1, runeHeight+1, BufferedImage.TYPE_INT_RGB);
        
        // Use javas "pre made" RGB colors
        int black = Color.black.getRGB();
        int white = Color.white.getRGB();

        // Make the whole image black
        for(int i=0;i<runeWidth;i++){
            for(int j=0;j<runeHeight;j++){
                runeImage.setRGB(i,j,white);
            }
        }
        
        // Color the relevant pixels
        for(int i=0;i<runeList.size();i++){
            Point pixel = runeList.get(i);
            int x = (int) pixel.getX()-minX;
            int y = (int) pixel.getY()-minY;
            runeImage.setRGB(x, y, black);
        }
        // ferdigh
        return runeImage;
    }
}
    
        
