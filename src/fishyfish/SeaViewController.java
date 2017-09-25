package fishyfish;

//Window Libraries
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;

//Event Libraries
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//Utility Libraries
import java.util.ArrayList;
import java.util.Random;


@SuppressWarnings("serial")
public class SeaViewController extends Canvas {
	// Window Properties
	private int frame_width;
	private int frame_height;
	private BufferStrategy strategy;
	private JFrame container;
	private JPanel panel;
	
	// State Variables
	private String message = "";
	private String second_message = "";
	private String third_message = "";
	private boolean playing = true;
	private boolean key_wait = true;
	
	// Fish Variables
	private ArrayList<Fish> fish_list_right = new ArrayList<Fish>();
	private ArrayList<Fish> fish_list_left = new ArrayList<Fish>();
	private PlayerFish player_fish;
	
	// RNG
	Random rand_gen = new Random();
	
	
	public SeaViewController(int width, int height) {
		// Set Initial Conditions
	    this.frame_width = width;
	    this.frame_height = height;
	
	    // Define Window Vars
	    container = new JFrame("Fishy Fish Fisheries");
	    panel = (JPanel) container.getContentPane();
	    container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	    // Set Window Properties
	    panel.setPreferredSize(new Dimension(frame_width,frame_height));
	    setBounds(0,0,frame_width, frame_height);
	    panel.add(this);
	    setIgnoreRepaint(true);
	
	    // Display Window
	    container.pack();
	    container.setResizable(false);
	    container.setVisible(true);
	    
	    // Key Listener
	    addKeyListener(new KeyInputHandler());
	    requestFocus();
	
	    // Buffer Strategy for 
	    // Accelerated Graphics
	    createBufferStrategy(2);
	    strategy = getBufferStrategy();
	
	    initialFish();
	}
	
	
	// Starts Game */
	private void startGame() {
	 	// Reset Game Fish
		fish_list_right.clear();
		initialFish();
		gameLoop();
	}
	 
	 
	/* Creates Initial Array of Fish */
	private void initialFish() {
	    int random_x;
	    int random_y;
	
	    // Create Player
	    fish_list_right.add( new PlayerFish((frame_width/2), (frame_height/2), frame_height, frame_width) );
	    player_fish = (PlayerFish) fish_list_right.get(0);
	
	    // Quad I Fish
	    random_x = rand_gen.nextInt(frame_width/2-100) + frame_width/2+100;
	    random_y = rand_gen.nextInt(frame_height/2-100);
	    randFish(random_x, random_y);
	
	    // Quad II Fish
	    random_x = rand_gen.nextInt(frame_width/2-100);
	    random_y = rand_gen.nextInt(frame_height/2-100);
	    randFish(random_x, random_y);
	     
	    // Quad III Fish
	    random_x = rand_gen.nextInt(frame_width/2+100);
	    random_y = rand_gen.nextInt(frame_height/2+100) + frame_height/2+100;
	    randFish(random_x, random_y);
	    
	    // Quad IV Fish
	    random_x = rand_gen.nextInt(frame_width/2+100) + frame_width/2+100;
	    random_y = rand_gen.nextInt(frame_height/2+100) + frame_height/2+100;
	    randFish(random_x, random_y);
	    
	    // Wait to Start
	    message = "Use WASD or Arrow Keys to Move";
	    second_message = "Score: " + player_fish.getScore();
	    third_message = "Press any key to continue";
	    key_wait = true;
	}
	
	
	/* Generates a Random Fish Type at Given X,Y */
	private void randFish (int x, int y) {
	    int fish_type = rand_gen.nextInt(3);
	
	    if (fish_type == 0)
	        fish_list_right.add( new SmallFish(x,y) );
	
	    else if (fish_type == 1)
	        fish_list_right.add( new MedFish(x,y) );
	
	    else 
	        fish_list_right.add( new LargeFish(x,y) );
	}

	
	/* Sets Game Over State */
	private void gameOver() {
	    message = "Game Over!";
	    second_message = "Score: " + player_fish.getScore();
	    third_message = "(N)ew Game or (Q)uit";
	    key_wait = true;
	}
	 
	
	/* Sets Game Won State */
	private void gameWin() {
	    message = "You've Won!";
	    second_message = "Score: " + player_fish.getScore();
	    third_message = "(N)ew Game or (Q)uit";
	    key_wait = true;
	}
	
	
	/* Moves Right Fish */
	private void moveFishRight() {
		for (int i=1; i < fish_list_right.size(); i++) {
			fish_list_right.get(i).moveRight();
			 
			if (fish_list_right.get(i).getX() + fish_list_right.get(i).getSize() >= frame_width)
				fish_list_right.remove(i);
		}
	}
	
	
	/* Moves Left Fish */
	private void moveFishLeft() {
		for (int i=0; i < fish_list_left.size(); i++) {
			fish_list_left.get(i).moveLeft();
			 
			if (fish_list_left.get(i).getX() + fish_list_left.get(i).getSize() <= 0)
				fish_list_left.remove(i);
		}
	}
	 
	 
	/* Calculates if Player Eats or is Eaten */
	private void eatOrEaten() {
		Fish npc_fish;
		
		// Right Fish Check
		for (int i=1; i < fish_list_right.size(); i++) {
			npc_fish = fish_list_right.get(i);
			
			switch ( fishCollision(npc_fish) ) {
				case 1:	gameOver();
						break;
						
				case -1:player_fish.eats();	
						fish_list_right.remove(i);
						break;
						
				default:break;
			}
		} 
		
		// Left Fish Check
		for (int i=0; i < fish_list_left.size(); i++) {
			npc_fish = fish_list_left.get(i);
			
			switch ( fishCollision(npc_fish) ) {
				case 1:	gameOver();
						break;
						
				case -1:player_fish.eats();	
						fish_list_left.remove(i);
						break;
						
				default:break;
			}
		} 
	}
	
	/* Calculates if  */
	private int fishCollision(Fish npc_fish) {
		double x_squared = Math.pow( (Math.max(npc_fish.getX(),player_fish.getX()) - Math.min(npc_fish.getX(),player_fish.getX()) ), 2);
		double y_squared = Math.pow( (Math.max(npc_fish.getY(),player_fish.getY()) - Math.min(npc_fish.getY(),player_fish.getY()) ), 2);
		double max_radius = Math.pow( (npc_fish.getSize()/2 + player_fish.getSize()/2), 2);
	
		if ( x_squared + y_squared <= max_radius ) {			
			if (npc_fish.getSize() > player_fish.getSize())
				return 1;
			else
				return -1;
		
		} else { return 0; }
	}
	 
	 
	/* Main Game Loop */
	private void gameLoop() {
		long start_time = System.currentTimeMillis();
		long time_diff;
		 
	    while(playing) {
	    	time_diff = System.currentTimeMillis() - start_time;
	    	 
	        // Initialize Window
	        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
	        g.setColor(Color.blue);
	        g.fillRect(0, 0, frame_width, frame_height);
	
	        // Move Fishies
	        moveFishRight();
	        moveFishLeft();
	         
	        // Look for Collisions
	        eatOrEaten();
	        
	        // Draw Fishies
	        for (Fish f : fish_list_right) 
	            f.drawFishy(g);
	        
	        for (Fish f : fish_list_left) 
	            f.drawFishy(g);
	        
	
	        // Potentially Draw Message
	        if (key_wait) {
	        	g.setColor(Color.white);
				g.setFont(new Font("Sans Serif", Font.BOLD, 42));
				g.drawString(message,(frame_width-g.getFontMetrics().stringWidth(message))/2,frame_height/2);
				g.drawString(second_message,(frame_width-g.getFontMetrics().stringWidth(second_message))/2,frame_height/2+56);
				g.drawString(third_message,(frame_width-g.getFontMetrics().stringWidth(third_message))/2,frame_height/2+112);
	        }
	         
	        // Update Screen
	        g.dispose();
	        strategy.show();
	        
	        /* Right Fish Add */
	        // Add Small Fish
	        if (time_diff % 200 == 0)
	        	fish_list_right.add(new SmallFish(0, rand_gen.nextInt(frame_height)));       
	        // Add Medium Fish
	        if (time_diff % 300 == 0)
	        	fish_list_right.add(new MedFish(0, rand_gen.nextInt(frame_height)));	         
	        // Add Large Fish
	        if (time_diff % 400 == 0) 
	        	fish_list_right.add(new LargeFish(0, rand_gen.nextInt(frame_height)));
	        
	        /* Left Fish Add */
	        // Add Small Fish
	        if (time_diff % 300 == 0)
	        	fish_list_left.add(new SmallFish(frame_width, rand_gen.nextInt(frame_height)));       
	        // Add Medium Fish
	        if (time_diff % 400 == 0)
	        	fish_list_left.add(new MedFish(frame_width, rand_gen.nextInt(frame_height)));	         
	        // Add Large Fish
	        if (time_diff % 500 == 0) 
	        	fish_list_left.add(new LargeFish(frame_width, rand_gen.nextInt(frame_height)));
	        
	        // Reset Timer
	        if (time_diff >= 1000)
	        	start_time = System.currentTimeMillis();
	        
	        // Pause for Key Press
	        while (key_wait) 
	        	try { Thread.sleep(10); } catch (Exception e) {}
	         
	        // Loop Delay
	        try { Thread.sleep(10); } catch (Exception e) {}
	    }
	}
	 
	
	private class KeyInputHandler extends KeyAdapter {
		// Key Pressed
		public void keyPressed(KeyEvent e) {
			if ( (e.getExtendedKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_A) ) 
				player_fish.moveLeft();
						
			if (e.getExtendedKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) 
				player_fish.moveRight();
						
			if (e.getExtendedKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) 
				player_fish.moveUp();
			
			if (e.getExtendedKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) 
				player_fish.moveDown();
			
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) 
				System.exit(0);
		
			if (e.getKeyCode() == KeyEvent.VK_N && key_wait) {
				fish_list_right.clear();
				initialFish();
				key_wait = false;
			
			} else if (e.getKeyCode() == KeyEvent.VK_Q && key_wait) {
				System.exit(0);
			
			} else {
				key_wait = false;
			}
		}	
	}
	
	// Game Entry Point - Main
	public static void main(String[] args) {
		SeaViewController game = new SeaViewController(1200,800);
	
		game.startGame();
	}
}