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
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Bombb> booms = new ArrayList<Bombb>();

	private Timer timer;
	private int count = 0;
	
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
			bornItem();
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
		Iterator<Item> t_iter = items.iterator();
        while(t_iter.hasNext()){
            Item t = t_iter.next();
            t.proceed();
            if(!t.isAlive()){
            	t_iter.remove();
            	gp.sprites.remove(t);
            	score += 100;
            }
        }
        Iterator<Attack> s_iter = shootings.iterator();
        while(s_iter.hasNext()){
            Attack s = s_iter.next();
            s.proceed();
        }
		Iterator<Bombb> b_iter = booms.iterator();
		while(b_iter.hasNext()){
			Bombb b = b_iter.next();
			b.proceed();
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

			for(Item t : items){
				tr = t.getRectangle();
				if(tr.intersects(vr)){
					count+=100;
					gp.sprites.remove(t);
					t.itemdie();	
					return;
				}
		
				for(Attack s : shootings){
					sr = s.getRectangle();
					if(sr.intersects(er)){
						e.changestateenemy();
						return;
					}	
				}

				for(Bombb b : booms){
					br = b.getRectangle();
					if(br.intersects(er)){
						gp.sprites.remove(e);
						e.changestateenemy();
						gp.sprites.remove(b);
						b.bombbdie();
						return;
					}
				}
			}
		}	
	}

	
	public void bornItem(){
    	
    	if(count >=1){
			lbombb();
		}
		Item t = new Item(positionitem,20);
    	gp.sprites.add(t);
    	items.add(t);	
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

    public void lbombb(){
			Bbomb();
			count-=20;		
	} 
  	      
     public int getBombb(){
		return count;
	}

	public void Bbomb(){
            Bombb b = new Bombb(position1,590);
            Bombb b1 = new Bombb(position2,590);            
            gp.sprites.add(b);
            booms.add(b);
            gp.sprites.add(b1);
            booms.add(b1);            
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
