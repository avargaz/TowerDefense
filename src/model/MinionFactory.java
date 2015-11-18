package model;
import java.util.Random;

public class MinionFactory {
	private int X, Y;
	private MapTowerDefense map;
	private MinionMock[] minions;
	private int Max;
	private int id;
	private Random rand;
	private MinionTypes[] types;
	
	public MinionFactory(int x, int y, MapTowerDefense _map) {
		X = x;
		Y = y;
		map = _map;
		Max = 100;
		id = 0;
		minions = new MinionMock[Max];
		rand = new Random();
		types = MinionTypes.values();
	}
	
	public void createMinion(MinionTypes type) {
		MinionMock m = new MinionMock(type, map);
		if (id < Max) {
			minions[id] = m;
			id++;
		}
	}
	public void createRandominion()
	{
		this.createMinion(types[rand.nextInt(types.length)]);
	}
	
	public MinionMock[] getMinions() {
		return minions;
	}
	public int getNum() {
		return id;
	}
	
	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}
	public int getMax() {
		return Max;
	}
	
	public void assignAllDamage() {
		int i = 0;
		while (i < id) {
			if (minions[i].isAlive()) {
				minions[i].minionTakeDamage();
			}
			i++;
		}
	}
	
}
