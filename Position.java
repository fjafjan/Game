/* A class to represtent a position in the plane */

import java.lang.Math;

public class Position {
    public Position() {
	x = 0;
	y = 0;
    }

    public Position(int x, int y) {
	this.x = x;
	this.y = y;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public boolean equals(Position p) {
	return (p.getX() == x && p.getY() == y);
    }

    public void setX(int newX) {
	x = newX;
    }

    public void setY(int newY) {
	y = newY;
    }

    public int distanceTo(Position p) {
	int dx = p.getX() - x;
	int dy = p.getY() - y;
	return (int) Math.sqrt(dx*dx + dy*dy);
    }
    public void translate(int dx, int dy) {
	x = x + dx;
	y = y + dy;
    }

    /* This method creates a string representation of a position. The
     * representation is unique for each position with unique x,y
     * combination. The string is created simply by looking at the
     * integer part of x and y and padding in front with zeroes. */
    public String toString() {
	String str = "";
	int padZeroes = (int) Math.floor(Math.log10((double) x)) + 1;
	for (int i=0; i<5-padZeroes; i++) {
	    str += "0";
	}
	str += x;
	padZeroes = (int) Math.floor(Math.log10((double) y)) + 1;
	for (int i=0; i<5-padZeroes; i++) {
	    str += "0";
	}
	str += y;
	return str;
    }

    public int hashCode() {
	// Lols this is so wierd.
	return Integer.parseInt(toString());
    }

    public Position copy() {
	return new Position(x,y);
    }

    private int x;
    private int y;
}