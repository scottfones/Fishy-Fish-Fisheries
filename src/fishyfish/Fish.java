package fishyfish;

import java.awt.Graphics;
import java.awt.Color;

public abstract class Fish implements Comparable<Fish> {
    protected String species;
    protected Color color;
    protected int speed;
    protected int size;
    protected int x;
    protected int y;


    public Fish(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int compareTo(Fish other_fish) {
        if ( this.size > other_fish.getSize() )
            return 1;
        
        else if ( this.size == other_fish.getSize() ) 
            return 0;
        
        else
            return -1;
    }

    public void drawFishy(Graphics g) {
        int fish_x = this.x - this.size/2;
        int fish_y = this.y - this.size/2;

        g.setColor(this.color);
        g.fillOval(fish_x, fish_y, this.size, this.size);
    }
    
    public void moveLeft() {
    	this.x-=this.speed;
    }
    
    public void moveRight() {
    	this.x+=this.speed;
    }

    public String getSpecies() {
        return this.species;
    }

    public Color getColor() {
        return this.color;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getSize() {
        return this.size;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
