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

    private int x;
    private int y;
}