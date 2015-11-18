package model;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum MinionTypes {
	//NAME (Health, Attack, Speed, goldValue, sprite file name)

	INOCENTE	    (100,	1, 	1, 5,	"black dot.png"), 
	CRIMINAL		(255,	1, 	1, 7,	"black dot.png"),
	LADRON 		(100,	1, 	4, 10,	"black dot.png"),
	CHAPO	(255,	1, 	4, 5,	"black dot.png"),
	SICARIO      (1000,	1, 	1, 20,	"black dot.png"),
//	UNKILLABLE	(10000,	1,	1, 100,	"Unkillable Minion.png"),
	ASESINO	(255,	2,	1, 25,	"black dot.png");
//	SUPERKILLER	(10,	20,	8, 100,	"Superkiller Minion.png"),
//	HEALTH		(2000,  -1, 1, -50, "Basic Minion.png"),
//	MONEYENEMY	(350,	1,	2, 50,	"Moneybags Minion.png");


	
	private int health, attackDamage, movementSpeed, value;
	private BufferedImage sprite;
	private static final String SPRITE_FILE_DIR = "src/resources/images/";	//Make sure that the directory name ends in a forward slash

	MinionTypes(int health, int attackDamage, int movementSpeed, int value, String spriteFileName) {
		this.health = health;
		this.attackDamage = attackDamage;
		this.movementSpeed = movementSpeed;
		this.value = value;
		try {                
	          this.sprite = ImageIO.read(new File(SPRITE_FILE_DIR.concat(spriteFileName)));
	       } catch (IOException ex) {
	            System.out.println("image not found");
	       }
	}
	
	public int getHealth() {
		return health;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public String getName() {
		return this.toString();
	}
	
	public int getMovementSpeed() {
		return movementSpeed;
	}
	public int getValue() {
		return value;
	}
	public BufferedImage getSprite()
	{
		return sprite;
	}
}
