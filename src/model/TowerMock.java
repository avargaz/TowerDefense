package model;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TowerMock{
	public int _xlocation, _ylocation, _sellCost, _buyCost, _range, _power, _level, _upgradecost, _towerid;
	private BufferedImage _sprite;
	public TowerTypes type;
	public int health; // (FOR NEXUS ONLY)
	private MapTowerDefense _map;
	public boolean _isTower; //false is Nexus
	public String Description = "Tower lol."; 
	
	public MinionMock currentTarget;
	
	
	public TowerMock(int id, int xloc, int yloc, boolean isTower, TowerTypes type, MapTowerDefense  map){
		_towerid = id;
		_map = map;
		_xlocation = xloc;
		_ylocation = yloc;
		_isTower = isTower;
		if (!isTower) {
			health = 20;
			_power = 0;
		}
		
		this.type=type;
		this._buyCost=type.getBuycost();
		this._sellCost= type.getSellcost();
		this._power = type.getPower();
		this._range = type.getRange();
		this._level = type.getLevel();
		this._sprite=type.getSprite();
		
	}
	
	public BufferedImage getSprite() {
		
		return _sprite;
		
	}
	
	public void updateSpriteLevel(){
		System.out.println(_level + " " + type.getTowerType());
		if(_level ==2 && type.getTowerType()==TowerTypes.BASIC){
			try {
				_sprite = ImageIO.read(new File("src/resources/images/Basic Tower LVL2.png"));
			} catch (IOException e) {
				
			}
		}
		if(_level ==3 && type.getTowerType()==TowerTypes.BASIC){
			try {
				_sprite = ImageIO.read(new File("src/resources/images/Basic Tower LVL3.png"));
			} catch (IOException e) {
				
			}
		}
		if(_level ==2 && type.getTowerType()==TowerTypes.AOE){
			try {
				_sprite = ImageIO.read(new File("src/resources/images/AOE Tower LVL2.png"));
			} catch (IOException e) {
				
			};
		}
		if(_level ==3 && type.getTowerType()==TowerTypes.AOE){
			try {
				_sprite = ImageIO.read(new File("src/resources/images/AOE Tower LVL3.png"));
			} catch (IOException e) {
				
			};
		}
		
	}
	
	public int basicAttack() {
		if (findMinion()) {
			
		}
		return 0;
	}
	
	
	
	public boolean basicDealDamage() {
		if (findMinion()) {
			currentTarget.minionTakeHit(_power);
			return true;
		} else {
			return false;
		}
		
		
	}
	
	
	
	public boolean findMinion() {
		int i = 0;
		while (i < 100) {
			MinionMock m = _map.getMF().getMinions()[i];
			if (m == null) {
				return false;
			}
			if (m.isAlive()) {
				if (1 == inRange(m.getX(), m.getY())) {
					currentTarget = m;
					return true;
				}
			}
			i++;
		}
		return false;
	}
	
	
	
	public int inRange(int x, int y) {
		int deltax = (32*_xlocation - x);
		int deltay = (32*_ylocation - y);
		int dist2 = deltax*deltax + deltay*deltay;
		if (dist2 > _range*_range) {
			return 0;
		} else {
			return 1;
		}
		
	}
	
	
	public boolean takeDamage(int damage) {
		if (this.health <= damage)
		{
			health = 0;
			return true; //gameover
		}
		else
		{
			health -= damage;
			System.out.println(health);
			return false;
		}
	}
	
	public boolean takeDamage() {
		return takeDamage(1);
	}
	public int getHealth() {
		return health;
	}

	public int getTowerXlocation() {
		return _xlocation;
	}

	public int getTowerYlocation() {
		return _ylocation;
	}


	public int getPower() {
		return _power;
	}

	public int getSellValue() {
		return _sellCost * _level;
	}

	public void moveOffToSell() {
		_xlocation = -1000;
		_range = 0;
		_map.getStatGui().notTowers++;
	}
	
	public int getBuyCost() {
			return _buyCost;
	}
	
	public int getRange(){
		return _range;
	}

	public int getUpgradeCost() {
		_upgradecost = _level * _buyCost;
		return _upgradecost;
	}
	
	public void upgrade(){
		if(_level < 3){
		this._power++;
		this._range += 30;
		_level++;
		this.updateSpriteLevel();
		}
		else{
		System.out.println("Sorry LVL3 is max");
		}
	}
		

	public int getLevel() {
		return _level;
	}

	public boolean isActuallyTower() {
		return _isTower;
	}

	public String getDescription() {
		return Description;
	}

	public void setTarget(MinionMock aminion){
		currentTarget = aminion;
	}
	
	public int getID() {
		return _towerid;
	}

	

}
