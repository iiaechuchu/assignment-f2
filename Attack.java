package Assignment.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Attack extends Sprite{
	public static final int Y_TO_DIE = 60;
	
	private int step = 50; // SpeedOfShooting
	private boolean alive = true;
	
	public Attack(int x, int y) {
		super(x, y,  2,50);      
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		
                g.fillOval(x-10,y+10,width+2,height-15);
                
	}

	

	public void proceed(){
		y -= step;
		if(y < Y_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
	

}
 
