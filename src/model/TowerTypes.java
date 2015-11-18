package model;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum TowerTypes {
	//NAME (Cost to buy, Cost to sell, "sprite dir")
	NEXUS (1, 1, 0, 0, 1, "Nexus Tower.png"),
	BASIC	(50, 25, 1, 96, 1,	"Basic Tower LVL1.png"), 
	AOE 		(100, 50, 1, 96, 1, "AOE Tower LVL1.png");
	
	private int _buycost, _sellcost, _power, _range,_level;
	private BufferedImage sprite;
	private static final String SPRITE_FILE_DIR = "src/resources/images/";	

	TowerTypes(int buycost, int sellcost, int power, int range, int level, String spriteFileName) {
		this._buycost = buycost;
		this._sellcost = sellcost;
		this._power = power;
		this._range = range;
		this._level = level;
		
		try {                
			
	          this.sprite = ImageIO.read(new File(SPRITE_FILE_DIR.concat(spriteFileName)));
	          
	       } catch (IOException ex) {
	            System.out.println("image not found");
	       }
	}
	
	
	public int getBuycost() {
		return _buycost;
	}

	public int getSellcost() {
		return _sellcost;
	}
	
	public int getPower(){
		return _power;
	}
	
	public int getRange(){
		return _range;
	}
	
	public int getLevel(){
		return _level;
	}
	
	public void setLevel(int newLevel){
		_level = newLevel;
	}
	
	public String getName() {
		return this.toString();
	}
	
	public TowerTypes getTowerType(){
		return this;
	}
	
	public BufferedImage getSprite()
	{
		return sprite;
	}
}