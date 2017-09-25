package fishyfish;

import java.awt.Color;
import java.awt.Graphics;

class PlayerFish extends Fish {
	private int boundary_height;
	private int boundary_width;
	private int score;
    
    public PlayerFish(int x, int y, int height, int width) {
        super(x,y);
        this.species = "player fish";
        this.color = Color.orange;
        this.boundary_height = height;
        this.boundary_width = width;
        this.speed = 12;
        this.score = 0;
        this.size = 70;
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
    	if (this.x + this.size/2 + this.speed <= boundary_width)
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