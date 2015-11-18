package model;

public class TowerFactory {
	
	int xloc, yloc;
	MapTowerDefense _map;
	TowerMock[] towerarray;
	public int quantity; //FML
	int _max;
	int cost;
	
	public TowerFactory(MapTowerDefense amap, int max){
		_map = amap;
		_max = max;
		towerarray = new TowerMock[_max];
		createNexus();
		quantity = 1;
		cost = 100;
	}
	
	
	public void basicTotalDamage() {
		int i = 1;
		while (i < quantity) {
			if (0 == (towerarray[i].type.compareTo(TowerTypes.BASIC))) {
				towerarray[i].basicDealDamage();
			}
			i++;
		}
	}
	
	public int assignTotalDamage(int x, int y) {
		int i = 1;
		int total = 0;
		while (i < quantity) {
			if (0 == (towerarray[i].type.compareTo(TowerTypes.AOE))) {
				total += towerarray[i].inRange(x, y);
			}
			i++;
		}
		return total;
	}
	
	
	public TowerMock[] getTowerArray() {
		return towerarray;
	}
	public int getNum() {
		return quantity;
	}
	
	
	
	public void createNexus(){
		int[] local = _map.getNodes()[_map.getNum()-1];
		int x = local[0];
		int y = local[1];
		towerarray[0] = new TowerMock(0, x, y, false, TowerTypes.NEXUS, _map);
	}
	
	public void createBasicTower(int x, int y, TowerTypes type){
		System.out.println("x is: "+ x + " , y is: "+ y);
		//Checks if there's already a tower here
		//TODO make this better
		if (!_map.getStatGui().canAfford(type.getBuycost())) { //check to see if player can afford
			return;
		}
                
		if (quantity < _max) {
			int i = 0;
			//prevents tower from being placed on existing towers or on path
			while (i < quantity) {
				if ((towerarray[i].getTowerXlocation() == x) && (towerarray[i].getTowerYlocation() == y)) {
					System.out.println("CONSEQUENCES WILL NEVER BE THE SAME"); //informal logging
					return;
				}
				i++;
			}
			//Check if new torret is on path
                        
                        int [][] path=_map.getNodes();
                        for(int j= 0 ; j < path.length - 1 ; j++){//[fila][columna]
                            if(path[j][0] == path[j+1][0]){//going vertically 
                                System.out.println("vertical");
                                System.out.println("Y1:"+path[j][1]+" Y2: "+path[j+1][1]+ " X:"+path[j][0]);
                                if((y >= path[j][1] && y <= path[j+1][1]) && x == path[j][0]){//going down
                                    System.out.println("CONSEQUENCES WILL NEVER BE THE SAME");
                                    return;
                                }else if((y <= path[j][1] && y >= path[j+1][1]) && x == path[j][0]){//going down
                                    System.out.println("CONSEQUENCES WILL NEVER BE THE SAME");
                                    return;
                                }
                            }else if(path[j][1] == path[j+1][1]){//going horizontally right
                                System.out.println("horizontal");
                                System.out.println("X1:"+path[j][0]+" X2: "+path[j+1][0]+ " Y: "+path[j][1]);
                                if((x >= path[j][0] && x<= path[j+1][0]) && y==path[j][1]){//going right
                                    System.out.println("CONSEQUENCES WILL NEVER BE THE SAME");
                                    return;
                                }else if((x <= path[j][0] && x>= path[j+1][0]) && y==path[j][1]){//going left
                                    System.out.println("CONSEQUENCES WILL NEVER BE THE SAME");
                                    return;
                                }
                            }
                        }
                        
			_map.getStatGui().spendMoney(type.getBuycost());	//actually spend money, prevent double spending
			towerarray[quantity] = new TowerMock(quantity, x, y, true, type, _map);
			quantity++;
		}
	}
}
