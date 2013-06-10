// This is a class that holds the data for a Rune and does various
// manipulation one might want to use with a rune such as returning
// an image of the rune (in whatever format), converting between 
// different formats and comparing with different runes. 

import java.util.ArrayList;
import java.awt.Point;

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
}
    
        
