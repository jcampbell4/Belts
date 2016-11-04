import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public interface Amazon_Belts {

	ArrayList<Bin> toPacker = new ArrayList<Bin>();
	ArrayList<Box> toDock = new ArrayList<Box>();
	/*********************************************************
	 * 
	 * @author Jared Campbell
	 *
	 * This class simulates the belt itself. 
	 * 
	 * assumptions:
	 * 1. The belt itself has 20 spaces
	 * 2. Each space can take one bin
	 * 3. The belt consists of two parts, to the packer, and to the dock, split exactly down the middle
	 */
	class Belt{
		float beltSpeed = 0;
		boolean verbose = false;
		boolean isPaused = false;
		double time = 0.0;
		Bin temp1 = null;
		Box temp2 = null;

		
		public Belt(float speed, boolean verbose, boolean isPaused){
			this.beltSpeed = speed;
			this.verbose = verbose;
			this.isPaused = isPaused;
			
		}
		
		public int getLoc(String BinNum, int whichbelt){		
			if(whichbelt == 1){
				for(int i = 0; i < toPacker.size(); i++){
					temp1 = toPacker.get(i);
					if(Bin.getbinID(temp1) == BinNum){
						if(verbose == true){
							System.out.println("The bin is in belt 1, spot " + i);
						}
						return i;
					}					
				}
			}
			else if(whichbelt == 2){
				for(int j = 0; j < toDock.size(); j++){
					temp2 = toDock.get(j);
					if(Box.getboxID(temp2) == BinNum){
						if(verbose == true){
							System.out.println("The box is in belt 2, spot " + j);
						}
						return j;
					}				    
				}

			}
			if(verbose == true){
				System.out.println("Your order is not in processing");
			}
			return -1;
			
		}
		public void binList(){
			for(int i = 0; i <= toPacker.size(); i++){
				System.out.println((toPacker).get(i));
			}
		}
		public void addBin(Bin temp){
			toPacker.add(temp);
		}
		public void addBox(Box temp){
			toDock.add(temp);
		}
		public void start(){
			isPaused = false;
			if(verbose == true){
				System.out.println("The belt has started");
			}
		}
		public void stop(){
			isPaused = true;
			if(verbose == true){
				System.out.println("The belt has stopped");
			}
		}
		public void getTime(double pushtime){
			this.time = pushtime;
		}
		public void move(double pushtime, Bin pick){
			
			if(pushtime == (this.time + beltSpeed)){
				if(toPacker.size() <= 10){
					addBin(pick);
				}
				else{
					if(verbose == true){
						System.out.println("toPacker: The belt is full");
					}					
					stop();
				}
				if(toPacker.size() == 10){
					if(toDock.size() <= 10){
						if(!(Bin.getbinID(toPacker.get(9)) == "-1")){					
							Box tempbox = new Box(Bin.getbinID(pick));
							addBox(tempbox);
							if(verbose == true){
								System.out.println("toPacker/toDock: Order # " + tempbox.ID + " has been packaged and in transit");
							}
						}
					}
				}
				if(toDock.size() == 10){
					Box finalbox = toDock.get(9);
					if(verbose == true){
						System.out.println("toDock: Order # " + finalbox.ID + " has been loaded onto the dock");
					}
				}
			}
		}
	}
	
	class Bin{
		String ID = null;
		public Bin(String ID){
			this.ID = ID;
		}
		public void addItem(Item temp){
			
		}
		public static String getbinID(Bin temp){
			return temp.ID;
		}
		
		
	}
	class Item{
		String name = null;
		int quantity = 0;
		public Item(String name, int quantity){
			this.name = name;
			this.quantity = quantity;
		}
	}
	class Box{
		String ID = null;
		public Box(String ID){
			this.ID = ID;
		}
		public static String getboxID(Box temp){
			return temp.ID;
		}
	}
	class Run{
		
	}
}
