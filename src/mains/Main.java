package mains;
import java.awt.*;

import javax.swing.*;

import views.MockGui;
import views.StatGui;
import model.MapTowerDefense;
import model.MinionTypes;

public class Main extends JFrame{
	private static int[][] nodes;
	private static final int NUM = 6;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("TOWER DEFENSE: PRISON BREAK");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setupDefaultNodes();				
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout(10,10));
		MockGui mg = new MockGui(640,640,32, NUM, nodes);
		StatGui sg= new StatGui(0,0);
	
		container.add(mg, BorderLayout.CENTER);
		container.add(sg, BorderLayout.LINE_END);
		sg.setPreferredSize(new Dimension(210,600));
		
		MapTowerDefense map = new MapTowerDefense(100, NUM, nodes, mg, sg);
		mg.setMap(map);
		sg.setMap(map);
		sg.setup();
		
		frame.add(container);
		frame.pack();
		frame.setSize(860,670);
		frame.setVisible(true);
		
		map.createMinion(MinionTypes.CRIMINAL);
		mg.step();
		
	}
	//dibujar mapa con nodos
	private static void setupDefaultNodes()
	{
		nodes = new int[NUM][2];
                nodes[0][0] = 1; 
		nodes[0][1] = 3;
		nodes[1][0] = 4;
		nodes[1][1] = 3;
		nodes[2][0] = 4;
		nodes[2][1] = 9;
		nodes[3][0] = 7;
		nodes[3][1] = 9;
		nodes[4][0] = 7;
		nodes[4][1] = 15;
		nodes[5][0] = 18;
		nodes[5][1] = 15;
                /*
		nodes[0][0] = 1; 
		nodes[0][1] = 3;
		nodes[1][0] = 16;
		nodes[1][1] = 3;
		nodes[2][0] = 16;
		nodes[2][1] = 9;
		nodes[3][0] = 4;
		nodes[3][1] = 9;
		nodes[4][0] = 4;
		nodes[4][1] = 15;
		nodes[5][0] = 18;
		nodes[5][1] = 15;*/
		/*nodes[6][0] = 18;
		nodes[6][1] = 13;
		nodes[7][0] = 11;
		nodes[7][1] = 13;
		nodes[8][0] = 11;
		nodes[8][1] = 1;*/

	}
}
