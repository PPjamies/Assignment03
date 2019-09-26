package HW_03;

import java.util.ArrayList;

//Model has access to the data; the recent file names
public class NotePadModel {
	
	private int MAX; //recent 5 files opened
	private int counter; //count the # of files in queue
	private ArrayList<String> recent; //stores the recent 5 file paths
	
	public NotePadModel() {
		this.MAX = 5;
		this.counter = 0;
		this.recent = new ArrayList<>();
		
	}
	
	public void updateRecentDB(String fileName) {
		if(fileName.equals("")) return;
		if(!recent.contains(fileName))
		{
			recent.add(0,fileName);
			System.out.println("Added");
			if(counter < MAX) 
				counter++;	
			else if(counter == MAX) //arraylist is full: insertion then deletion
				recent.remove(counter-1); //remove the oldest "recent" file
		}
		else {
			recent.remove(fileName);
			recent.add(fileName);
			return; //fileName already exists
		}
	}
	
	//return a new list of recent files
	public ArrayList<String> getRecentFiles(){
		return recent;
	}
	
	public String get(int index) {
		return recent.get(index);
	}

}