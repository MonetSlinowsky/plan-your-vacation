package c4_5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Case implements Cloneable{
	/****
	 * Represents a data case; a tuple (or row) in the data table
	 */

	
	//Holds the data for each case. The key is the name of the attribute
	//The value is this case's value for that attribute
	private HashMap<String, String> data;
	
public Case(HashMap<String, Attribute> attributeMap, String[] dataList, String[] attributeNames, boolean toClassify) {
/*
 * Initializes a new case (constructor)
 * Input: attributeMap -> Key is the attribute name, value is a hashset of all possible values
 *        dataList -> A row of file input, represented as an array of Strings
 *                    (every comma separated value is in a different index)
 *        attributeNames -> a list of all the attributes            
 *                    Assumption: attributes in dataList are in same order as those in attributeNames
 *                    (i.e. dataList[i] is the value of the attribute attributeName[i]
 */
	
	data=new HashMap<String, String>();
	
	//Note: the attribute at dataList[0] is the user_ID, which is not relevant to building the decision tree 
	
	if(toClassify) {
		
		data.put(attributeNames[0], dataList[0]);
		
	}//if classifying
	

	for (int i=1; i<dataList.length; i++) {
		
		if(attributeMap.get(attributeNames[i]).containsVal(dataList[i])) {
			data.put(attributeNames[i], dataList[i]);
		
		}//inner if;
		
		else {
			System.out.println(dataList[i]);
			System.out.println(attributeMap.get(attributeNames[i]).getAName());
			//System.out.println(attributeMap.get(attributeNames.get(i)).getAName());
			System.out.println("--------------");
			System.out.println("");
		}

	}//for
	
}//constructor

public Case(Case original) {
	/*
	 * Copy constructor 
	 */
	data=new HashMap<String, String>();
	original.data.forEach((k,v) -> this.data.put(k, v) );
}

public String getValue(String key) {
	/*
	 * Input: key-> a string representation of an attribute
	 * Output: this case's value of that attribute 
	 */
	//if(data.containsKey(key)) {
		return data.get(key);
}


public boolean attributesEqual(Object otherObject, ArrayList<String> attributeNames) {
	if(getClass()!=otherObject.getClass()){
		return false;
	}
	
	Case other=(Case) otherObject;
	
	for(int i=0; i<data.size(); i++) {
		if(!(data.get(attributeNames.get(i)).equals(other.data.get(attributeNames.get(i))))) {
			return false;
		}
	}
	
	//all values are not equal (except for the target)
	return true;
}

@Override
public String toString(){
	/*
	 * Returns a string representation of the case
	 */
	
	String rep="";
	Iterator<String> keyIt=data.keySet().iterator();
	while(keyIt.hasNext()){
		String a=keyIt.next();
		rep=rep+a + ": " + data.get(a) + ", ";

	}
	rep=rep+"\n";
	return rep;
	
}
	

}//class
