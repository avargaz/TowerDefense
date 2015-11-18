package views;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.MapTowerDefense;
import model.TowerTypes;




public class StatGui extends JPanel implements ActionListener{
	private int X, Y;
	private MapTowerDefense map;
	private int maxHealth = 20;
	private int playermoney = 1000;
	JLabel hpLabel, moneyLabel, towersnumLabel, sellLabelBasic, sellLabelAOE, upgradeLabelBasic, upgradeLabelAOE;
	JButton buttonAOETower, buttonBasicTower, buttonSellTower, buttonUpgradeTower;
	public int towerPlacerVar = 1; //1:Basic, 2:AOE, 3:Sell, 4:Upgrade
	public int notTowers = 1;
	
	public StatGui (int x, int y) {
		X = x;
		Y = y;
		 
	}
	public int getMoney() {
		return playermoney;
	}
	public boolean canAfford(int cost) {
		if (playermoney >= cost) {
			return true;
		} else {
			return false; 
		}
	}
	
	public boolean spendMoney(int cost) {
		if (canAfford(cost)) {
			playermoney -= cost;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean payDay(int value) {
		playermoney += value;
		return true;
	}
	
	public void setMap(MapTowerDefense map){
		this.map=map;
	}
	
	public void setup(){
		
		ImageIcon iconBasicTower = new ImageIcon("src/resources/images/Basic borrar estoTower LVL1.png", "Basic tower sprite");
		buttonBasicTower = new JButton("Torre Metralleta $" + TowerTypes.BASIC.getBuycost(), iconBasicTower);
		buttonBasicTower.setActionCommand("select basic tower");
		buttonBasicTower.setEnabled(true);
		buttonBasicTower.addActionListener(this);
		this.add(buttonBasicTower);
		
		ImageIcon iconAOE = new ImageIcon("src/resources/images/AOE borrar estoTower LVL1.png", "AOE tower sprite");
		buttonAOETower = new JButton("Torre Electrochoques $" + TowerTypes.AOE.getBuycost(), iconAOE);
		buttonAOETower.setActionCommand("select aoe tower");
		buttonAOETower.setEnabled(true);
		buttonAOETower.addActionListener(this);
		this.add(buttonAOETower);
		
		sellLabelBasic = new JLabel();
		sellLabelBasic.setFont(new Font("Serif", Font.BOLD, 12));
		sellLabelBasic.setForeground(Color.BLACK);
		this.add(sellLabelBasic);
		
		sellLabelAOE = new JLabel();
		sellLabelAOE.setFont(new Font("Serif", Font.BOLD, 12));
		sellLabelAOE.setForeground(Color.BLACK);
		this.add(sellLabelAOE);
		
		ImageIcon iconSellTower = new ImageIcon("src/resources/images/Sell borrar estoButton.png", "Sell tower sprite");
		buttonSellTower = new JButton("Vender" , iconSellTower);
		buttonSellTower.setActionCommand("select sell");
		buttonSellTower.setEnabled(true);
		buttonSellTower.addActionListener(this);
		this.add(buttonSellTower);
		
		upgradeLabelBasic = new JLabel();
		upgradeLabelBasic.setFont(new Font("Serif", Font.BOLD, 12));
		upgradeLabelBasic.setForeground(Color.BLACK);
		this.add(upgradeLabelBasic);
		
		upgradeLabelAOE = new JLabel();
		upgradeLabelAOE.setFont(new Font("Serif", Font.BOLD, 12));
		upgradeLabelAOE.setForeground(Color.BLACK);
		this.add(upgradeLabelAOE);
		
		ImageIcon iconUpgradeTower = new ImageIcon("src/resources/images/Upgrade borrar estoButton.png", "Sell tower sprite");
		buttonUpgradeTower = new JButton("Actualizar" , iconUpgradeTower);
		buttonUpgradeTower.setActionCommand("select upgrade");
		buttonUpgradeTower.setEnabled(true);
		buttonUpgradeTower.addActionListener(this);
		this.add(buttonUpgradeTower);
		
		hpLabel = new JLabel();
		hpLabel.setFont(new Font("Serif", Font.BOLD, 20));
		hpLabel.setForeground(Color.BLUE);
		//this.add(hpLabel);
		
		moneyLabel = new JLabel();
		moneyLabel.setFont(new Font("Serif", Font.BOLD, 20));
		moneyLabel.setForeground(Color.BLUE);
		this.add(moneyLabel);
		
		towersnumLabel = new JLabel();
		towersnumLabel.setFont(new Font("Serif", Font.BOLD, 20));
		towersnumLabel.setForeground(Color.BLUE);
		this.add(towersnumLabel);
		
		
	}
	
	public void actionPerformed(ActionEvent e){
		if("select aoe tower".equals(e.getActionCommand())){
			buttonAOETower.setEnabled(true);
			towerPlacerVar = 2;
			System.out.println("AOE tower selected!");
		}
		else if("select basic tower".equals(e.getActionCommand())){
			buttonBasicTower.setEnabled(true);
			towerPlacerVar = 1;
			System.out.println("Basic tower selected!");
		}
		else if("select sell".equals(e.getActionCommand())){
			buttonSellTower.setEnabled(true);
			towerPlacerVar = 3;
			System.out.println("Sell selected!");
		}
		else if("select upgrade".equals(e.getActionCommand())){
			buttonUpgradeTower.setEnabled(true);
			towerPlacerVar = 4;
			System.out.println("Upgrade selected!");
		}
		
	}
	
	public int gettowerPlacerVar(){
		return towerPlacerVar;
	}
	
	public void updateHealth(){
		hpLabel.setText("[Health]: " + map.getTF().getTowerArray()[0].getHealth() + "/" + maxHealth);
	}
	
	public void updateMoney(){
		moneyLabel.setText("Dinero: $ " + playermoney);
	}
	
	public void updateNumTowers(){
		towersnumLabel.setText("Torres: " + (map.TF.quantity - notTowers));
	}
	
	public void updateBasicSellLabel(){
		sellLabelBasic.setText("Torre metralleta $" + TowerTypes.BASIC.getSellcost());
	}
	
	public void updateAOESellLabel(){
		sellLabelAOE.setText("Torre de electrochoques $" + TowerTypes.AOE.getSellcost());
	}
	
	public void updateBasicUpgradeLabel(){
		upgradeLabelBasic.setText("Actualizar Torre metralleta $" + TowerTypes.BASIC.getBuycost());
	}
	
	public void updateAOEUpgradeLabel(){
		upgradeLabelAOE.setText("Actualizar Torre Electrochoques $" + TowerTypes.AOE.getBuycost());
	}
	
	
}
