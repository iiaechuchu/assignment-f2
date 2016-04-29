package Assignment.spw;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Bombb extends Sprite{
	public static final int Y_TO_DIE = 60;

	private int step = 20;
	private boolean alive = true;
	
	public Bombb(int x, int y) {
		super(x, y,  5,50);
	}

	@Override
	public void draw(Graphics2D g) {

		/*g.setColor(Color.GRAY);
		g.fillOval(x-12,y+10,width+2,height+5);*/
		g.setColor(Color.RED);
        g.fillRect(x-8,y+12,width,height-15);
        g.setColor(Color.GREEN);
        g.fillRect(x-4,y+12,width-2,height-15);
        g.setColor(Color.GREEN);
        g.fillRect(x-11,y+12,width-2,height-15);
        g.setColor(Color.YELLOW);
        g.fillRect(x-8,y+1,width,height-30);
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
	
	public boolean bombbdie(){
		return alive = false;
	}

}
 
	
