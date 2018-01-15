package c4_5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Classifier {


	public static void main(String[] args) {
		
		//Files containing testing data
		//String attFile="/Users/monet/Documents/algs/attr_defn_small.csv";
		String attFile="/Users/monet/Documents/algs/attr_defn.csv";
		//String attFile="/Users/monet/Documents/algs/attr_defn_with_hotel.csv";
				
		//Data filename
		//String dataFile="/Users/monet/Documents/algs/trip_data.csv";
		String dataFile="/Users/monet/Documents/algs/training_data.csv";
		//String dataFile="/Users/monet/Documents/algs/training_data_WITH_HOTEL.csv";
		
		//String testCaseFile="/Users/monet/Documents/algs/test_data.csv";
		String testCaseFile="/Users/monet/Documents/algs/full_test_data.csv";
		//String testCaseFile="/Users/monet/Documents/algs/full_test_data WITH hotel.csv";
		
		classifyMe(attFile, dataFile, testCaseFile);
		
		
	}
	
	
	public static double classifyMe(String attFile, String dataFile, String testCaseFile) {
		/****
		 * Main function 
		 * Calls c4_5_main.c4_5_call to build the decision tree
		 * Then classifies new data cases and compares the predicted class to the actual class
		 * Returns the ratio of correctly classified cases to incorrectly classified cases
		 */
		
		//Ratio to be returned 
		double ratio=0;
		
		
		System.out.println("WELCOME TO C4.5");
		System.out.println("");
		
		
		//Create the data structure used to build the tree
		dataStruc myData=new dataStruc(attFile, dataFile);
		
		System.out.println("DATA STRUCTURES INITIALIZED");
		System.out.println("");

		

		//Create data structure to hold test cases
		ArrayList<Case> myTestCases=createTestCasesList(testCaseFile, myData.getAttributeList(), myData.getAttributeMap());
		System.out.println("Test cases now initialized");
		System.out.println(" ");
		
		//Get target attribute
		Attribute target=myData.getTarget();
		System.out.println("Data cases are being classified by the attribute: " +target);
		System.out.println(" ");
		
		if(target==null) {
			System.out.println("No target attribute");
			System.exit(0);
		}
		
		//Create the decision tree
		System.out.println("CALLING C4.5 TO BUILD THE DECISION TREE");
		System.out.println(" ");
		
		Node treeRoot=c4_5_main.c4_5_call(myData, target);

		System.out.println("The root of the tree is: " +treeRoot);
		System.out.println("Its children are: " +treeRoot.children);
		
		
		//Initialize count for data that is correctly classified (countMatch) 
		//and data that is not correctly classified (countNotMatch)
		double countMatch=0;
		double countNotMatch=0;
		
		System.out.println("");
		System.out.println("--------------------------------------");
		System.out.println("NOW ENTERING THE CLASSIFICATION STAGE");
		System.out.println(" ");
		System.out.println("The following is a sample classification: ");
		
		//Classification procedure begins
		//Note: print statements are called only for the first case (i=0) so as to show how classification works,
		//but not to print an overwhelming amount of output 
		
		//For each test case
		for (int i=0; i<myTestCases.size();i++) {
		
			if(i==0) {
				System.out.println("");
				System.out.println("The test case is " +myTestCases.get(i));
				System.out.println("");
			}
			
			//Call traverseTree, which returns the predicted class of the test case as a string
			String rslt=traverseTree(myTestCases.get(i), treeRoot,i);

			//Compare the predicted class (rslt) to the case's actual class 
			
			if (rslt.equals(myTestCases.get(i).getValue(target.getAName()))) {
				//If equal, increment countMatch
				countMatch++;
				if(i==0) {
					System.out.println("MATCH!");
					System.out.println("Algorithm classified case as: " + rslt + " and test case " + myTestCases.get(i).getValue("User_ID") + " was a " +myTestCases.get(i).getValue(target.getAName()));
					System.out.println(" ");
				}
			}
			
			else {
				//If not equal, increment countNotMatch
				countNotMatch++;
				if(i==0) {
					System.out.println("NO MATCH!");
					System.out.println("Algorithm classified case as: " + rslt + " and test case " + myTestCases.get(i).getValue("User_ID") + " was a " +myTestCases.get(i).getValue(target.getAName()));
					System.out.println(" ");
				}
			}
			
		}//for all test cases
		
		if(countNotMatch==0) {
			//Ensure we don't divide by 0. 
			countNotMatch=1;
		}
		
		//All done! Find ratio
		ratio=countMatch/countNotMatch;
		
		//Display output 
		System.out.println("The number of matches is: " +countMatch);
		System.out.println("The number of non-matches is: " +countNotMatch);
		System.out.println("");
		System.out.println("---------------------------");
		System.out.println("RATIO IS " + ratio);
		
		
		return ratio;	
		
	}
	
	
	public static String traverseTree(Case myCase, Node root, int m) {
		/***
		 * Input: myCase-> a single test case (row in the data table)
		 *        root-> root of the decision tree
		 *        m -> the index of the test case, if it is 0, output will be printed to console
		 *Output: The predicted class of myCase (as a string)
		 */
		
		if(m==0) {
			
			System.out.println("");
			System.out.println("---------------------------------");
			System.out.println("Level " + root.getLevel()+ " Current node is " + root + " and it-is-a-question-node = " +root.isQuestOrAns());
			System.out.println("");
		}
		
		if(root.isLeaf()) {
			//If the node is a leaf, we've reached the end. 
			//Return its value; this is the case's predicted class
			
			if(m==0) {
			System.out.println("We have a classification. This case is predicted to be " +root.getVal());
			System.out.println("");
			}
			
			return root.getVal();
		}
		
		else if(root.isQuestOrAns()) {
			//If root is a question node, find the next node to traverse by 'answering' the question
			//(I.e. identifying which child has the same value for root's attribute as myCase)

			for(int i=0; i<root.children.size(); i++) {
				//For every child, check if myCases's value of the attribute equals the child's value 
				
				String nodeVal=root.children.get(i).getVal();
				String caseVal=myCase.getValue(root.attributeTested.getAName());
				
				if(nodeVal.equals(caseVal)) {
					//Values match, traverse the child node
					return traverseTree(myCase, root.children.get(i), m);
				}

				
			}
			
			//myCase's value for this attribute is not valid or is not on the decision tree 
			System.out.println("Error: Did not match any value on the tree");
			System.out.println("");
			System.out.println("----------------");
			return "no match";
			
		}//is a question
		
		
		else {
			//If it is an answer node, it will only have one child (the next question) so traverse this child
				return traverseTree(myCase, root.children.get(0),m);
		}

	}
	
	public static ArrayList<Case> createTestCasesList(String fileName, ArrayList<String> attList, HashMap<String, Attribute> attMap) {
		/*
		 * Initializes an ArrayList of test cases from the file given in fileName
		 * Input: fileName -> the name of a local file with test data
		 *        attList -> list of attributes
		 *        attMap -> key is the name of the attribute, values are the possible values of the attribute
		 *Output: an ArrayList of data test cases (each entry is a row from file fileName) 
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
			    
			     String[] aS = null;
			     //Skip over the first line with the attribute names 
			     try {
						
			    	 String tmp=line=br.readLine();
			    	 aS=tmp.split(",");
			
					} catch (IOException e1) {
						System.out.println("Can't read line");
						e1.printStackTrace();
					}
			     
			    	 //While there are rows
					try {
						while ((line = br.readLine()) != null) {
							// Use comma as separator and put into array hold[]
								String[] hold = line.split(",");
								//Create new case
								Case temp=new Case(attMap, hold, aS, true);
								
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
	
	

	
	
	
}
