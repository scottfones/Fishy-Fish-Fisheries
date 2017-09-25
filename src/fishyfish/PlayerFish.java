package fishyfish;

import java.awt.Color;
import java.awt.Graphics;

class PlayerFish extends Fish {
	private int boundary_height;
	private int score;
    
    public PlayerFish(int x, int y, int height) {
        super(x,y);
        species = "player fish";
        color = Color.orange;
        boundary_height = height;
        speed = 12;
        score = 0;
        size = 70;
    }
    
    public void moveUp() {
    	if (this.y - this.size/2 - (this.speed*2/3) >= 0)
    		this.y-=(this.speed*2/3);
    }
    
    public void moveDown() {
    	if (this.y + this.size/2 + (this.speed*2/3) <= this.boundary_height)
    		this.y+=(this.speed*2/3);
    }
    
    public void moveLeft() {
    	if (this.x - this.size/2 - this.speed >= 0)
    		this.x-=this.speed;
    }
    
    public void moveRight() {
    	if (this.x + this.size/2 + this.speed >= 0)
    		this.x+=this.speed;
    }
    
    public void drawFishy(Graphics g) {
        int fish_x = this.x - this.size/2;
        int fish_y = this.y - this.size/2;

        g.setColor(this.color);
        g.fillOval(fish_x, fish_y, this.size, this.size);
        g.setColor(getClassColor());
        g.fillOval(fish_x+this.size/4, fish_y+this.size/4, this.size/2, this.size/2);
    }
    
    private Color getClassColor() {
    	if (size >= 100)
    		return Color.green;
    	else if (size >= 75)
    		return Color.yellow;
    	else
    		return Color.red;
    }
    
    public int getScore() {
    	return this.score;
    }
    
    public void eats() {
    	this.score+=1;
    	this.size+=1;
    }
}