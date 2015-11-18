package views;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import model.MapTowerDefense;
import model.MinionFactory;
import model.MinionMock;
import model.TowerFactory;
import model.TowerMock;
import model.TowerTypes;

import java.util.Random;


//draws everything on map
public class MockGui extends JPanel{
	boolean gameOver;
	private int x, y, gridsize, numnodes;
	private int[][] nodes;
	private MapTowerDefense map;//
	private BufferedImage image;
	
	public MockGui(int x, int y, int gridSize, int numNodes, int[][] nodes) {
		this.x = x;
		this.y = y;
		gameOver = false;
		this.gridsize = gridSize;
		this.numnodes = numNodes;
		this.nodes = nodes;
		TowerPlacer TP = new TowerPlacer();
		this.addMouseListener(TP);
		repaint();
	}
	
	public void setMap(MapTowerDefense map) {
		this.map = map;
	}
	//update speed
	public void step() {
		int i = 0;
		while (!gameOver) {
			try {
				Thread.sleep(10);
				repaint();
				i++;
				//
				map.getTF().basicTotalDamage();
				map.getMF().getMinions()[0].minionTakeDamage();
				map.getStatGui().updateHealth();
				map.getStatGui().updateMoney();
				map.getStatGui().updateNumTowers();
				map.getStatGui().updateBasicSellLabel();
				map.getStatGui().updateAOESellLabel();
				map.getStatGui().updateAOEUpgradeLabel();
				map.getStatGui().updateBasicUpgradeLabel();
				map.getMF().assignAllDamage();
			
				//spawn rate speed
				if (i > 63) {
					map.getMF().createRandominion();
					i = 0;
				}
				//
			} catch (InterruptedException e) {
			
			}
		}
	}
	
	public void paint(Graphics g) {
		setBackground(new Color(99, 209, 62));
		drawGrid(x, y, gridsize, g);

		colorPath(g);
		drawAllTowers(map.getTF(), g);
		drawAllMinions(map.getMF(), g);
		drawPlayerHealth(g);
		drawAllBasicAttacks(map.getTF(),g);
		drawAllAOEAttacks(map.getTF(),g);
	
	}
	
	//cambios para el grid
	public void drawGrid(int x, int y, int gridSize, Graphics g) {
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, 32*20, 32*20);
		g.setColor(Color.WHITE);
		int i = 0;
		while (i < x-gridSize) {
			i+= gridSize;
			g.drawLine(i, gridSize, i, y-gridSize);
		}
		i = 0;
		while (i < y-gridSize) {
			i+= gridSize;
			g.drawLine(gridSize, i, x-gridSize, i);
		}
	}
	public int[] hash (int x, int y) {
		// Convert x y on grid to pixel
		int[] result = new int[2];
		result[0] = x*gridsize;
		result[1] = y*gridsize;
		return result;
	}
	public int[] unhash(int x, int y) {
		int[] result = new int[2];
		result[0] = x/gridsize;
		result[1] = y/gridsize;
		return result;
	}
	
	public void colorBlock(int x, int y, Graphics g) {
		int[] coords = hash(x,y);
		g.fillRect(coords[0], coords[1], gridsize, gridsize);
	}
	public void colorBlocks(int x1, int y1, int x2, int y2, Graphics g) {
		if (x1 == x2) {
			// Vertically
			if (y1 < y2) {
				while (y1 <= y2) {
					colorBlock(x1, y1, g);
					y1+=1;
				}
			} else {
				while (y2 <= y1) {
					colorBlock(x1, y2, g);
					y2+=1;
				}
			}
		} else {
			// Horizontally
			if (x1 < x2) {
				while (x1 <= x2) {
					colorBlock(x1, y1, g);
					x1+=1;
				}
			} else {
				while (x2 <= x1) {
					colorBlock(x2, y1, g);
					x2+=1;
				}
			}
		}
	}
	public void colorPath(Graphics g) {
		int i = 1;
		g.setColor(new Color(213,196,161));
		while (i < numnodes) {
			colorBlocks(nodes[i-1][0], nodes[i-1][1], nodes[i][0], nodes[i][1], g);
			i++;
		}
	}
	
	public void drawPlayerHealth(Graphics g) {
		int health = map.getTF().getTowerArray()[0].getHealth();
		g.setColor(new Color(0,255,0));
		colorBlocks(19, 0, 19, health, g);
		g.setColor(new Color(255,0,0));
		colorBlocks(19, 20, 19, health, g);
	}
	
	public void drawAllMinions(MinionFactory MF, Graphics g) {
		int i = 0;
		while (i < MF.getNum()) {
			if (MF.getMinions()[i].isAlive()) {
				drawMinion(MF.getMinions()[i], g);
				if (MF.getMinions()[i].go()) 
				{
					gameOver = true;
					
				}
			}
			i++;
		}
	}
	//TODO REMOVE
	public void drawMinion(int x, int y, int health, Graphics g) {
		g.drawImage(image, x,y, null);
	}
	
	
	//change what draws on minion
	public void drawMinion(MinionMock m, Graphics g)
	{
		g.drawImage(m.getSprite(), m.getX(), m.getY(), null);
		//String h = String.format("[%s/%s]", m.getHealth(), m.getMaxHealth());
		g.setColor(Color.GREEN);
		//g.drawString(h, m.getX()-15, m.getY()-10);
		g.drawString(m.getName(), m.getX()-15, m.getY()+45);
		//g.drawString(("V: " + m.getValue() + "\nD: " + m.getAttackDamage()), m.getX(), m.getY()+46);
	}
	
	public void drawAllTowers(TowerFactory TF, Graphics g) {
		int i = 0;
		while (i < TF.getNum()) {
			int[] local = hash(TF.getTowerArray()[i].getTowerXlocation(), TF.getTowerArray()[i].getTowerYlocation());
				drawBasicTower(TF.getTowerArray()[i], local[0], local[1], g);	
			i++;
		}
	}
	
	public void drawBasicTower(TowerMock tm, int x, int y, Graphics g) {
		
		g.drawImage(tm.getSprite(),x , y, null);
		
		
	}
	//draws the basic attack
	public void drawBasicAttack(int x, int y, int level, Graphics g){
		if(level == 1){
			g.setColor(Color.RED);
		}
		else if(level == 2){
			g.setColor(Color.ORANGE);
		}
		else if(level ==3){
			g.setColor(Color.BLUE);
		}
		
		g.fillRect(x, y, 5, 5);
	}
	
	//if Basic tower is attacking, draw dots to where minion is
	public void drawAllBasicAttacks(TowerFactory TF, Graphics g){
		for(int i = 1; i < TF.getNum(); i++){
			if(TF.getTowerArray()[i].basicDealDamage() && TF.getTowerArray()[i].type == TowerTypes.BASIC){
				int towerDotX = (TF.getTowerArray()[i]._xlocation * 32) + 15; //+ 15 for it can be centerd on tower
				int towerDotY = (TF.getTowerArray()[i]._ylocation *32) + 15;
				int halfDotX = (((TF.getTowerArray()[i]._xlocation * 32) + 15)+ (TF.getTowerArray()[i].currentTarget.getX() + 15))/2;
				int halfDotY = (((TF.getTowerArray()[i]._ylocation * 32) + 15)+ (TF.getTowerArray()[i].currentTarget.getY() + 15))/2;
				int minionDotX = TF.getTowerArray()[i].currentTarget.getX() + 15;
				int minionDotY = TF.getTowerArray()[i].currentTarget.getY()+15;
				drawBasicAttack(towerDotX,towerDotY, TF.getTowerArray()[i].getLevel(), g);
				drawBasicAttack((towerDotX + halfDotX)/2, (towerDotY + halfDotY)/2, TF.getTowerArray()[i].getLevel(), g);
				drawBasicAttack(halfDotX, halfDotY, TF.getTowerArray()[i].getLevel(), g);
				drawBasicAttack((halfDotX + minionDotX)/2, (halfDotY + minionDotY)/2, TF.getTowerArray()[i].getLevel(), g);
				drawBasicAttack(minionDotX, minionDotY, TF.getTowerArray()[i].getLevel(), g);
			}
		}
		
	}
	//configure aoe attack visual
	public void drawAOEAttack(int x, int y, int range, int level, Graphics g){
		if(level == 1){
			g.setColor(Color.RED);
		}
		else if(level == 2){
			g.setColor(Color.MAGENTA);
		}
		else if(level ==3){
			g.setColor(Color.CYAN);
		}
		g.drawOval((int)(x - ( range*1.8)/2),(int)( y - ( range*1.8)/2),(int)( range*1.8), (int)( range*1.8));
		//g.drawOval((int)(x - ( range*1.6)/2),(int)( y - ( range*1.6)/2),(int)( range*1.6), (int)( range*1.6));
		//g.drawOval((int)(x - ( range*1.4)/2),(int)( y - ( range*1.4)/2),(int)( range*1.4), (int)( range*1.4));
	}
	
	public void drawAllAOEAttacks(TowerFactory TF, Graphics g){
		for(int i = 1; i < TF.getNum(); i++){
			if( TF.getTowerArray()[i].basicDealDamage() && TF.getTowerArray()[i].type == TowerTypes.AOE){
			drawAOEAttack((TF.getTowerArray()[i]._xlocation * 32) + 15, (TF.getTowerArray()[i]._ylocation *32) + 15,TF.getTowerArray()[i].getRange(), TF.getTowerArray()[i].getLevel(), g);
		
			}
		}
	}
	
	
	private class TowerPlacer extends MouseAdapter{

		@Override
		public void mousePressed(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			System.out.println("x; " +  x + "  y: " + y);
			int[] local = unhash(x,y);
			
			if(map.getStatGui().gettowerPlacerVar() == 2){
			map.getTF().createBasicTower(local[0], local[1], TowerTypes.AOE);
			}
			else if(map.getStatGui().gettowerPlacerVar() == 1){
				map.getTF().createBasicTower(local[0], local[1], TowerTypes.BASIC);
			}
			//sell tower click check
			if(map.getStatGui().gettowerPlacerVar() == 3){
				for(int i=1; i<map.getTF().quantity; i++){
				if(	(map.getTF().getTowerArray()[i].getTowerXlocation() == local[0]) && (map.getTF().getTowerArray()[i].getTowerYlocation() == local[1])){
					//TODO //how to destroy tower?
					map.getTF().getTowerArray()[i].moveOffToSell();
				map.getStatGui().payDay(map.getTF().getTowerArray()[i].getSellValue());
				}
				}
				}
			//upgrade tower click check
			if(map.getStatGui().gettowerPlacerVar() == 4){
				
				for(int i=1; i<map.getTF().quantity; i++){
					if(map.getStatGui().canAfford(map.getTF().getTowerArray()[i]._upgradecost)){
				if(	(map.getTF().getTowerArray()[i].getTowerXlocation() == local[0]) && (map.getTF().getTowerArray()[i].getTowerYlocation() == local[1])){
					map.getTF().getTowerArray()[i].upgrade();
				map.getStatGui().spendMoney(map.getTF().getTowerArray()[i].getUpgradeCost());
				}
				}
				}
				}
		}
			
		
	}
	
	
}
