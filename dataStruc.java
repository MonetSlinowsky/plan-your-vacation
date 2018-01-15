package c4_5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class dataStruc {
	/****
	 * Contains all data needed for the program
	 * Functions read attributes and case data from file and put it into
	 * the corresponding data structure
	 */
	
	//Consists of 3 structures:
	
	//List of all attributes
	private ArrayList<String> attributeList;
	
	//Map where the key is the attribute name and the value is an object of
	//class Attribute, which stores all possible values of the object
	private HashMap<String, Attribute> attributeMap;
	
	//List of objects of class Case where a case represents one row of data
	//consisting of the values of all attributes for that case
	private ArrayList<Case> dataList;

	public dataStruc(ArrayList<String> attributeList, HashMap<String, Attribute> attributeMap, ArrayList<Case> dataList) {
		/*
		 * Create a datastructure with the given attributes
		 */
		
		//Copy attributeList
		Iterator<String> aIt=attributeList.iterator();
		this.attributeList=new ArrayList<String>();
		while(aIt.hasNext()) {
			this.attributeList.add(aIt.next());
		}
		
		//Copy HashMap
		this.attributeMap=new HashMap<String, Attribute>();
		attributeMap.forEach((k,v) -> this.attributeMap.put(k, v));
		
		//Copy dataList
		this.dataList=new ArrayList<Case>();
		Iterator<Case> dIt=dataList.iterator();
		while(dIt.hasNext()) {
			
			this.dataList.add(new Case(dIt.next()));
		}
	}
	
	public dataStruc(String attFile, String dataFile) {
		/*
		 * Initialize data to create a tree
		 */

				//Initialize attributeMap
				attributeMap=readAttributeFile(attFile);
				
				
				//Initialize attributeList
				attributeList=createAttributeList(attFile);
		

				//Initialize dataList (get data into the program)
				try {
					dataList=initializeCases(dataFile, attributeMap, attributeList);
				} catch (IOException e) {
					System.out.println("Cases could not be initialized");
					e.printStackTrace();
				}

				
	}
	
	
	public dataStruc(dataStruc original) {
		/*
		 * Copy constructor for class
		 */
		
		//Copy attributeList
		this.attributeList=original.copyAttributeList(original.attributeList);
		
		this.dataList=original.copyDataList(original.dataList);
		
		this.attributeMap=original.copyAMap(original.attributeMap);
		
	}
	
	public ArrayList<String> copyAttributeList(ArrayList<String> original) {
		/*
		 * Returns deep copy of the attributeList
		 */
		ArrayList<String> newList=new ArrayList<String>();
		
		Iterator<String> aIt=original.iterator();
		
		while(aIt.hasNext()) {
			newList.add(aIt.next());
		}
		
		return newList;
	}
	
	public ArrayList<Case> copyDataList(ArrayList<Case> original) {
		/*
		 * Returns deep copy of the dataList
		 */
		ArrayList<Case> newList=new ArrayList<Case>();
		
		Iterator<Case> dIt=original.iterator();
		while(dIt.hasNext()) {
			
			newList.add(dIt.next());
		}
		
		return newList;
	}
	
	public HashMap<String, Attribute> copyAMap(HashMap<String, Attribute> original) {
		/*
		 * Returns deep copy of the attributeMap
		 */
		HashMap<String, Attribute> newMap=new HashMap<String, Attribute>();
		
		original.forEach((String k, Attribute v) -> {
			newMap.put(k, (new Attribute(v)));
	});
		return newMap;
		
	}

	
	//GETTERS
	public ArrayList<String> getAttributeList() {
		return copyAttributeList(this.attributeList);
	}
	
	public ArrayList<Case> getDataList() {
		return copyDataList(this.dataList);
	}
	
	public HashMap<String, Attribute> getAttributeMap() {
		return copyAMap(this.attributeMap);
	}
	
	public boolean removeAttributeFromList(Attribute a) {
		/*
		 * Removes the attribute from the attributeList (but NOT the 
		 * attribute map)
		 */
		return (this.attributeList.remove(a.getAName()));
	}
	
	public Attribute getTarget() {
		//Returns the target attribute from attributeList
		
		for(int i=0; i<attributeList.size(); i++) {
			if(attributeMap.get(attributeList.get(i)).isTarget()) {
				return attributeMap.get(attributeList.get(i));
			}
		}
		
		return null; 
	}
	
	//INITIALIZING ATTRIBUTES FUNCTIONS

	public HashMap<String, Attribute> readAttributeFile(String fileName) {
		/*
		 * Initializes the HashMap of attributes and attribute values from the file
		 * Input: a Comma Separated Value file with the form attributeName, value, value
		 * Output: the HashMap attributeMap in which the key is the name of the attribute and the value is the correpsonding Attribute object
		 */
		
		HashMap<String, Attribute> attributeMap=new HashMap<String, Attribute>();
		
		 String line = "";
	     
	     //New read object
	     BufferedReader br = null; 
	     try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
			System.exit(0);
		}
	     try {
	    	 
	    	 String target=br.readLine();
	    	 String[] t=target.split(",", -1);
	    	 target=t[0];
	    	 
	    	 //While there are rows
			while ((line = br.readLine()) != null) {
				// Use comma as separator and put into array hold[]
					String[] hold = line.split(",");
				//Each row is an attribute

				Attribute temp=new Attribute(hold);
				
				if(temp.getAName().equals(target)) {
					temp.setIfTarget(true);
				}
				else {temp.setIfTarget(false); }
				
				attributeMap.put(temp.getAName(), temp);
			 }
		} catch (IOException e) {
			System.out.println("Unreadable line");
			e.printStackTrace();
			System.exit(0);
			
		}//while

	    return attributeMap;
	}
		
	public ArrayList<String> createAttributeList(String fileName) {
		/*
		 * Returns an ArrayList of the names of all attributes
		 */
		ArrayList<String> attList=new ArrayList<String>();
		
		String line = "";
	    
	 
	    BufferedReader br = null; 
	    try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
			System.exit(0);
		}
	    
	    
	    try {
	    	
	    br.readLine();
	  
	    
	   	 //While there are rows
			while ((line = br.readLine()) != null) {
				// Use comma as separator and put into array hold[]
					String[] hold = line.split(",", -1);
					
					//Add the name of the attribute to the attList
					attList.add(hold[0]);
			 }
		} catch (IOException e) {
			System.out.println("Unreadable line");
			e.printStackTrace();
			System.exit(0);
		}//while
			

		
		return attList;
	}
		
	//INITIALIZING DATA FUNCTIONS
		

	public ArrayList<Case> initializeCases(String fileName, HashMap<String, Attribute> attMap, ArrayList<String> attList) throws IOException {
	/*
	 * Function initializeCases(String fileName) takes the data file and returns the dataList
	 * Inputs: fileName -> the name of a csv file containing the data in the form value, value, value, value
	 *                     the first row of the file is assumed to contain the attribute names
	 *         attMap -> attribute map where the key is the attribute name and the value is the object Attribute
	 *         attList -> list of attributes
	 *Output: Each row of the file is put into an instantiation of 'Case' and all Cases are stored in the ArrayList dataList,
	 *        which is returned
	 * 
	 */

		ArrayList<Case> dataList=new ArrayList<Case>();
		
		//Open file and get data
		String line = "";
	     
	     //New read object
	     BufferedReader br = null; 
	     try {
			br = new BufferedReader(new FileReader(fileName));
		
	     } catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
			System.exit(0);
		}
	    
	     	String tempS=br.readLine();
	     	String[] attNames=tempS.split(",");
	     
	    	 //While there are rows
			try {
				while ((line = br.readLine()) != null) {
					// Use comma as separator and put into array hold[]
						String[] hold = line.split(",", -1);
						//Create new case
						Case temp=new Case(attMap, hold, attNames, false);
						
				
						//Add to the data list
						dataList.add(temp);
				
				}
			} catch (IOException e) {
				System.out.println("Unreadable line");
				e.printStackTrace();
				System.exit(0);
			}//while
		
		
		return dataList;
	}

	public boolean allSameClass(String target) {
		//Returns true if all the data in the dataList has the same value 
		
	Iterator<Case> it=dataList.iterator();
	
	//Find the value of the first case for the attribute 'target'
	String fVal=it.next().getValue(target);
	
	while(it.hasNext()) {
		
		Case curCase=it.next();

		//If the value doesn't equal to fVal, this means data is not classified all the same so return false
		if(!(curCase.getValue(target).equals(fVal))) {
			return false;
		}
		
	}
	
	return true;
	
	}
	
}
