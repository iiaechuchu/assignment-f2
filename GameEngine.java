package Assignment.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private SpaceShip v;
	private ArrayList<Attack> shootings = new ArrayList<Attack>();	
	
	private Timer timer;
	
	private int positionitem;
	private int position1;
	private int position2;

	private long score = 0;
	private double difficulty = 0.1;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	
	private void process(){

		if(Math.random() < difficulty){
			position1=(int)(Math.random()*380);
			generateEnemy();

			position2=(int)(Math.random()*380);
			
			positionitem=(int)(Math.random()*6000);
		}

		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
			}
				
		}
		
		
        Iterator<Attack> s_iter = shootings.iterator();
        while(s_iter.hasNext()){
            Attack s = s_iter.next();
            s.proceed();
        }

		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double sr;
		Rectangle2D.Double br;
		Rectangle2D.Double tr;



		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				e.changestateenemy();
				score -= 1000;
				if(score==0||score<0){
						die();
						return;
				}
				
			}
			for(Attack s : shootings){
				sr = s.getRectangle();
				if(sr.intersects(er)){
					e.changestateenemy();
					return;
				}	
			}
		}
	}	
	
	
	public void die(){
		timer.stop();
		gp.updateGameUI();
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move(-1,0);
			break;
		case KeyEvent.VK_RIGHT:
			v.move(1,0);
			break;
		case KeyEvent.VK_UP:
			v.move(0,-1);
			break;
		case KeyEvent.VK_DOWN:
			v.move(0,1);
			break;
		case KeyEvent.VK_E:
			difficulty += 0.1;
			break;
		case KeyEvent.VK_B:
			fire();
			break;
		}
	}


	public void fire(){
            Attack s = new Attack((v.x) + 8 , v.y);
            gp.sprites.add(s);
            shootings.add(s);
    }


	public long getScore(){
		return score;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
