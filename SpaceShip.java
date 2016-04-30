package Assignment.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class SpaceShip extends Sprite{

	int step = 8;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	@Override
	public void draw(Graphics2D g) {
	
		g.setColor(Color.BLUE);
		g.fillRect(x-5, y+10, 10, 35);
		g.fillRect(x-16, y+20, 33,10);
		g.fillRect(x-20, y+40, 40,7);
		g.setColor(Color.YELLOW);
		g.fillRect(x-20, y+50, 10, 1);
		g.fillRect(x+10, y+50, 10, 1);


	}

	public void move(int directionx,int directiony){
		x += (step * directionx);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
		y += (step * directiony);
		if(y < 0)
			y = 0;
		if(y > 600 - height)
			y = 600 - height;
	}

}
