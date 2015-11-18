package model;
import java.util.Deque;

/**
 * Represents a game of tower defense, encapsulating player data and directs actions of in-game units.
 *
 */
public class TowerDefenseGame {
	
	private Deque minionSchedule;
	private boolean gameOver;
	private MapTowerDefense map;
	
	public TowerDefenseGame(Deque minionSchedule, MapTowerDefense map)
	{
		gameOver = false;
		this.map = map;
	}
	
	public void mainLoop()
	{
		//Need to decide on the best way to implement updates in this cycle. Could do it in a tick-based manner, where you sleep x amount of time and then update all your shit.
		//I'd prefer to update the model without delay, but that requires timing units and game interactions in another way so that it doesn't run at super-speed.
		int i = 0;
		while (!gameOver)
		{
			map.getMF().assignAllDamage();
			
			if (i > 63) {
				map.createMinion(MinionTypes.CRIMINAL);
				i = 0;
			}
		}
	}
}
