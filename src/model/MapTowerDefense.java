package model;

import views.MockGui;
import views.StatGui;



public class MapTowerDefense {

	private int maxTowers;
	private int numNodes;
	private int[][] nodes;
	private MinionFactory MF;
	public TowerFactory TF;
	private MockGui MG;
	private StatGui SG;
	
	public MapTowerDefense(int _maxTowers, int _numNodes, int[][] _nodes, MockGui _mg, StatGui _sg) {
		maxTowers = _maxTowers;
		numNodes = _numNodes;
		nodes = _nodes;
		MF = new MinionFactory(nodes[0][0], nodes[0][1], this);
		TF = new TowerFactory(this, maxTowers);
		MG = _mg;
		SG = _sg;
	}
	
	public MinionFactory getMF() {
		return MF;
	}
	public TowerFactory getTF() {
		return TF;
	}
	public int getNum() {
		return numNodes;
	}
	public int[][] getNodes() {
		return nodes;
	}
	public MockGui getGui() {
		return MG;
	}
	public StatGui getStatGui(){
		return SG;
	}
	public void createMinion(MinionTypes type) {
		MF.createMinion(type);
	}
	
	
	
	
	
	
}