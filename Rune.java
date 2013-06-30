// This is a class that holds the data for a Rune and does various
// manipulation one might want to use with a rune such as returning
// an image of the rune (in whatever format), converting between 
// different formats and comparing with different runes. 

import java.util.ArrayList;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.Color;

import java.lang.*;
class Rune{
    
    
    
    private ArrayList<Point> runeList;
    private int inputWidth;
    private int inputHeight;
    private int runeWidth;
    private int runeHeight;
    private int minX;
    private int minY;
    
    //Predefined constant. Would it be nicer if this was somewhere else?
    public final int runeSize = 50;
    
    // Creates a rune object from an arraylist. The rune will have a 
    // uniform size "runeSize" which one can check if curious what it is.
    public Rune(ArrayList<Point> inlist){
        // Check at some point that this is actually a new ohject
        // since I am fairly certain this is actually created by reference
        runeList = inlist;
        int[] corners = getCorners();
        inputWidth  = corners[2] - corners[0];
        inputHeight = corners[3] - corners[1];
        minX = corners[0];
        minY = corners[1];
        
        // Moves the rune so that it starts at (0,0)
        normalize(); 
        
        
        // Resizes the rune so that it has the uniform runeSize
        double ratio = ((double)runeSize-1)/Math.max(inputWidth, inputHeight);
        rescale(ratio);
        
        corners = getCorners();
        runeWidth  = corners[2] - corners[0];
        runeHeight = corners[3] - corners[1];
    } 
 
    //~   Implement this image constructor in a nice way
    //~ public Rune(BufferedImage img){
        //~ this(getListFromIm(img));
    //~ }
    
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
        // Current problem: is no movement is made we get error.
        //~ System.out.println("Runelist size is "+ runeList.size());
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

        System.out.println("The width is " + runeWidth + " and the height is " + runeHeight);
        
        BufferedImage runeImage = new BufferedImage(runeSize, runeSize, BufferedImage.TYPE_INT_RGB);
        
        // Use javas "pre made" RGB colors
        int black = Color.black.getRGB();
        int white = Color.white.getRGB();
        
        // This step could most likely be improved using copyarry
        // as explained vaguelly here:
        // http://stackoverflow.com/questions/5672697/ ...
        //  ... java-filling-a-bufferedimage-with-transparent-pixels
        int[] whites = new int[(runeSize)*(runeSize)];
        for(int i=0;i<whites.length;i++){
            whites[i] = white;
        }
        runeImage.setRGB(0,0,runeSize,runeSize,whites,0,1);
        
        // Color the relevant pixels black
        int xOffset = (runeSize-runeWidth)/2;
        int yOffset = (runeSize-runeHeight)/2;
        System.out.println("Xoffset is " + xOffset + " and yOffset is " + yOffset);
        for(int i=0;i<runeList.size();i++){
            Point pixel = runeList.get(i);
            int x = (int) pixel.getX()+xOffset;
            int y = (int) pixel.getY()+yOffset;
            System.out.println("x="+x+", y="+y);
            runeImage.setRGB(x, y, black);
        }
        
        return runeImage;
        // ferdigh

        
        
    }
    
    // moves the rune into the right place
    public void normalize(){
        for(int i=0;i<runeList.size();i++){
            Point pixel = runeList.get(i);
            double x = pixel.getX()-minX; // Both of these numbers
            double y = pixel.getY()-minY; // should be integers
            pixel.setLocation(x,y);
            runeList.set(i, pixel);
        }
        
    }
    
    // Rescales the rune with a factor 'ratio'
    public void rescale(double ratio){
        System.out.println("ratio="+ratio);
        for(int i=0;i<runeList.size();i++){
            Point pixel = runeList.get(i);
            // Should be careful how things are rounded here but let's
            // try this first
            double x = Math.floor(pixel.getX()*ratio); 
            double y = Math.floor(pixel.getY()*ratio);
            pixel.setLocation(x,y);
            runeList.set(i, pixel);
        }
    }
    
    public boolean similarTo(Rune otherRune){
        double[] dist = new double[runeList.size()];
        Point p;
        double[][] minDist = otherRune.getMinDistance();
        for(int i=0;i<runeList.size();i++){
            p = runeList.get(i);
            dist[i] = minDist[(int)p.getX()][(int)p.getY()];
        }
        double leastSquareSum = 0;
        for(int i=0;i<runeList.size();i++){
            leastSquareSum += dist[i];
        }
        leastSquareSum = leastSquareSum/runeList.size();
        System.out.println("The (not actually...) least square sum was "+leastSquareSum);
        if(leastSquareSum>100){
            return false;
        }else{
            return true;
        }
    }
    
    // For each pixel in the image computes the minimum distance to the rune
    // currently uses a stupid brute force algorithm because let's just try
    Point p;
    public double[][] getMinDistance(){
        double[][] minDist = new double[runeSize][runeSize];
        for(int i=0;i<runeSize;i++){
            for(int j=0;j<runeSize;j++){
                double currMin = 123456789;
                for(int k=0;k<runeList.size();k++){
                    p = runeList.get(k);
                    double xDiff = p.getX()-i;
                    double yDiff = p.getY()-j; 
                    double dist =  Math.sqrt((xDiff*xDiff)+(yDiff*yDiff));
                    if(dist < currMin){
                        minDist[i][j] = dist;
                        currMin = dist;
                    }
                }
            }
        }
        return minDist;
    }
    


}
    
        
