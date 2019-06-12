package HW_03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

//Model has access to the data; the recent file names
public class NotePadModel {
	private int MAX; //recent 5 files opened
	private int counter; //count the # of files in queue
	private LinkedHashMap<String,String> recent; //stores the recent 5 file paths
	
	public NotePadModel() {
		this.MAX = 5;
		this.counter = 0;
		this.recent = new LinkedHashMap<>();
	}
	
	public void updateRecentDB(String fileName, String filePath) {
		if(fileName.equals("")) return;
		if(recent.isEmpty() || !recent.containsKey(fileName))
		{
			recent.put(fileName,filePath);
			System.out.println("Added");
			if(counter < MAX) 
				counter++;	
			else if(counter == MAX) //arraylist is full: insertion then deletion
				recent.remove(counter-1); //remove the oldest "recent" file
		}
		else {
			return; //fileName already exists
		}
	}
	
	//return a new list of recent files
	public HashMap<String,String> getRecentFiles(){
		return recent;
	}
}
