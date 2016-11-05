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
	 * 
	 * To-Do:
	 * 1. Create a getItemloc();
	 * 2. Create a runable, testable code
	 */
	class Belt{
		float beltSpeed = 1;
		boolean verbose = false;
		boolean isPaused = false;
		float time = 0;
		Bin temp1 = null;
		Box temp2 = null;
		ArrayList<loglist> Log = new ArrayList<loglist>();
		Queue<Item> newitem = new LinkedList<Item>();

		
		public Belt(float speed, boolean verbose, boolean isPaused){
			this.beltSpeed = speed;
			this.verbose = verbose;
			this.isPaused = isPaused;
			
		}
		
		public int getBinLoc(String itemNum){		

			for(int i = 0; i < toPacker.size(); i++){
				temp1 = toPacker.get(i);
				if(Bin.getbinID(temp1) == itemNum){
					if(verbose == true){
						System.out.println("The item is in belt 1, spot " + i);
					}
					return 0;
				}					
			}			
			for(int j = 0; j < toDock.size(); j++){
				temp2 = toDock.get(j);
				if(Box.getboxID(temp2) == itemNum){
					if(verbose == true){
						System.out.println("The item is in belt 2, spot " + j);
					}
					return 1;
				}				    
			}			
			if(verbose == true){
				System.out.println("Your order is not in processing");
			}
			return -1;
			
		}
		public void updatelog(String ID){
			int temp = getBinLoc(ID);
			
			for(int i = 0; i < Log.size(); i++){
				if(Log.get(i).ID == ID){
					loglist temploglist = new loglist(ID, temp, -1);
					Log.set(i, temploglist);
				}
				if(verbose == true){
					System.out.println("Update log: ID " + ID + " is now a bin in the log");
				}
			}
		}
		public void addItem(Item temp){
			newitem.add(temp);
			if(verbose == true){
				System.out.println("NewItem: new item inside the newitem queue");
			}
		}
		public void updatefinal(String ID){			
			for(int i = 0; i < Log.size(); i++){
				if(Log.get(i).ID == ID){
					loglist temploglist = new loglist(ID, 3, time);
					Log.set(i, temploglist);
					
				}
			}
			
		}
		public void addBin(Bin temp){
			toPacker.add(temp);
			loglist temp1 = new loglist(temp.ID, 1, -1);
			Log.add(temp1);
		}
		public void addBox(Box temp){
			toDock.add(temp);
			updatelog(temp.ID);
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
		public void getTime(float pushtime){
			this.time = pushtime;
		}
		public void move(float pushtime){
			ArrayList<Bin> temparray = new ArrayList<Bin>();
			
			if(pushtime == (this.time + beltSpeed)){
				if(toPacker.size() < 10){
					if(!(newitem.isEmpty())){
						Bin tempitem = new Bin(newitem.poll().name);
						addBin(tempitem);
					}
					
				}

				else if(toPacker.size() == 10){
					if(toDock.size() <= 10){
						if(!(Bin.getbinID(toPacker.get(9)) == "null")){	
							Bin tempitem1 = toPacker.get(9);
							Box tempbox = new Box(Bin.getbinID(tempitem1));
							addBox(tempbox);
							if(verbose == true){
								System.out.println("toPacker/toDock: Order # " + tempbox.ID + " has been packaged and in transit");
							}
						}
					}
					else{
						if(verbose == true){
							System.out.println("toPacker: The belt is full and it is stopped");
						}					
						stop();
					}
				}
				
				if(toDock.size() == 10){
					Box finalbox = toDock.get(9);
					toDock.remove(9);
					updatefinal(finalbox.ID);
					if(verbose == true){
						System.out.println("toDock: Order # " + finalbox.ID + " has been loaded onto the dock");
					}
				}
				Bin nullbin = new Bin("null");
				temparray.clear();
				temparray.add(0,nullbin);
				for(int i = 0; i < toPacker.size()-1; i++){
					Bin temp1 = toPacker.get(i);					
					temparray.add(i+1, temp1);
				}
				toPacker.clear();
				for(int j = 0; j < toPacker.size(); j++)
					toPacker.add(temparray.get(j));
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
	class loglist{
		String ID;
		int whichbelt;
		float whattime;
		public loglist(String ID, int whichbelt, float whattime){
			this.ID = ID;
			this.whichbelt = whichbelt;
			this.whattime = whattime;
		}
	}
	class Run{
		
	}
	public static void main(String [] args){
		
	}
}

