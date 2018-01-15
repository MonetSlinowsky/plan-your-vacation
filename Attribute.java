package c4_5;


import java.util.HashSet;
import java.util.Iterator;

public class Attribute {
	
	/***
	 * Represents an attribute; a column heading in the data table and all its possible values 
	 */

private String aName;

//True if the attribute is the target, false if it is just a normal attribute
private boolean target; 
public HashSet<String> possibleValues;


public Attribute(String[] row) {
/*
 * Initializes a new attribute
 * Input is a row from the csv file that details the attributes 
 * that has been parsed into a string array
 * Format is Atrribute_name, value, value
 */
possibleValues=new HashSet<String>();
aName=row[0];
for(int i=1; i<row.length; i++) {
	possibleValues.add(row[i]);
}//for

	
}//constructor

public Attribute(Attribute original) {
	/*
	 * Copy constructor 
	 */
	this.aName=original.aName;
	this.target=original.target;
	this.possibleValues=new HashSet<String>();
	
	original.possibleValues.forEach((String s)-> {
		this.possibleValues.add(s);
	});
}

public String getAName() {
	/*
	 * Returns the name of the attribute
	 */
	return aName;
}

public boolean isTarget() {
	/*
	 * Returns true if this attribute is the target attribute
	 */
	return target;
}

public void setIfTarget(boolean istarget) {
	this.target=istarget;
}

public HashSet<String> getPossibleValues() {
	/*
	 * Returns a deep copy of the HashSet possibleValues 
	 */
	HashSet<String> sentVals=new HashSet<String>();
	possibleValues.forEach((s) -> sentVals.add(s));
	return sentVals;
	
}
	
public boolean containsVal(String val) {
	/*
	 * Returns true if the val is one of the discrete values in this attribute's possibleValues
	 */
	if (possibleValues.contains(val)) {
		return true;
	}
	else
		return false;
}

public String toString() {
	/*
	 * Returns a string representation of the attribute
	 */
	String rslt=aName + ": ";
	
	Iterator<String> it=possibleValues.iterator();
	
	while(it.hasNext()) {
		rslt=rslt+it.next() + ", ";
	}
	
	rslt=rslt+ "\n";
	
	return rslt;
	
}

@Override
public boolean equals(Object otherObject) {
	/*
	 * Returns true if this attribute equals the other attribute passed in 
	 */
	
	if(getClass()!= otherObject.getClass()) {
		return false;
	}
	
	Attribute other=(Attribute) otherObject;
	
	if(aName.equals(other.getAName())) {
		return true;
	}
	
	return false;
}

}//class
