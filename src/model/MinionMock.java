package model;
import java.awt.image.BufferedImage;


public class MinionMock {
	private int x, y, speed, visits, health, attackDamage, maxHealth;
	private MinionTypes type;
	private boolean alive;
	private MapTowerDefense map;
	private BufferedImage sprite;
	private int value;
	private String name;
	
	public MinionMock(MinionTypes type, MapTowerDefense map) {
		this.type = type;
		this.map = map;
		int[] unhashed = map.getGui().hash(map.getNodes()[0][0], map.getNodes()[0][1]);
		x = unhashed[0];
		y = unhashed[1];
		health = type.getHealth();
		maxHealth = health;
		name = type.getName();
		attackDamage = type.getAttackDamage();
		speed = type.getMovementSpeed();
		sprite = type.getSprite();
		value = type.getValue();
		visits = 1;
		alive = true;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public MinionTypes getType() {
		return type;
	}
	
	public BufferedImage getSprite()
	{
		return sprite;
	}
	
	public int findDirection() {
		if (visits == map.getNum()) {
			// CALL TO MAKE NEXUS LOSE HEALTH
			return -1;
		} else {	
			int xGoal = map.getNodes()[visits][0]; 
			int yGoal = map.getNodes()[visits][1];
			int[] Goals = map.getGui().hash(xGoal, yGoal);
			xGoal = Goals[0];
			yGoal = Goals[1];
			if (x == xGoal) {
				if (y == yGoal) {
					visits++;
					return findDirection();
				}
				if (yGoal > y) {
					// MOVE DOWN
					return 2;
				} else {
					return 0;
				}
			} else {
				if (x > xGoal) {
					return 3;
				} else {
					return 1;
				}
			}
		}
	}
	
	public boolean go() {
		int direction = findDirection();
		if (direction == -1) {
			alive = false;
			return map.getTF().getTowerArray()[0].takeDamage(attackDamage);
		}
		if (direction == 0) {
			y-=speed;
		}
		if (direction == 1) {
			x+=speed;
		}
		if (direction == 2) {
			y+=speed;
		}
		if (direction == 3) {
			x-=speed;
		}
		return false;
	}
	
	public boolean isAlive() {
		return alive;
	}
	public int getHealth() {
		return health;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public String getName() {
		return name;
	}
	public int getValue() {
		return value;
	}
	public int getAttackDamage() {
		return attackDamage;
	}
	
	
	
	public void minionTakeHit(int damage) {
            health-=damage;
            if (health <= 0) {
                    if (alive == true) {
                            map.getStatGui().payDay(value);
                            alive = false;
                    }
            }
	}
	
	public void minionTakeDamage() {
            int dam = map.getTF().assignTotalDamage(x, y);
            health-=dam;
            if (health <= 0) {
                    if (alive == true) {
                            map.getStatGui().payDay(value);
                            alive = false;
                    }
            }
		
	}
}
