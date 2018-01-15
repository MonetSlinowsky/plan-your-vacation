package c4_5;

import java.util.ArrayList;

import java.util.Iterator;


public class c4_5_main {

	/****
	 * This implements the C4.5 algorithm
	 * @param myData: a data structure consisting of a list of attributes, a map of attributes and their values,
	 *                and a list of data cases containing the value of that case for every attribute
	 * @param target: the attribute that data is being classified into; every leaf of the tree will be one of
	 *                the possible values of this attribute 
	 * @return: the root of the decision tree
	 */
	public static int print1=0;
	
	public static Node c4_5_call(dataStruc myData, Attribute target) {
		
		
		//Call c4_5
		myData.removeAttributeFromList(target);
		Node root=c4_5(myData, null, target);
			
		//root.print();
		
		return root;
	}
	
	public static Node c4_5(dataStruc dataSet, Node subtreeRoot, Attribute target) {
		print1++;
		
		if(subtreeRoot!=null) {
			//System.out.println(subtreeRoot.getNum());
		}
		
		//Check if set all belongs to same class -> make class a leaf
		
		if(dataSet.allSameClass(target.getAName())) {
			
			if(subtreeRoot==null) {
			//All the data is of the same type and subtree root is null. This would only happen if this
			//is the first time the function has been called. Thus, all the data is of the same type. 
			//Tree consists of one node of the attribute target with the value all attributes have and it is 
			//an answ node
				Node newOne= new Node(target, dataSet.getDataList().get(0).getValue(target.getAName()), false);
				return newOne;
			}//null subtree
			
			else {
			//Make a leaf on the existing tree
				Node childNode = new Node(target, dataSet.getDataList().get(0).getValue(target.getAName()), false);
				subtreeRoot.addChild(childNode, true);
				return childNode;
			}
			
		}//case where data is all of same class
		
		
		//If not met
		
		//Find entropy for all attributes 
		int max=0;
		
		if(dataSet.getAttributeList().size()<2) {

			max=0;
		}
		
		else {
		Iterator<String> attIt=dataSet.getAttributeList().iterator();
		double maxGainRatio=Integer.MIN_VALUE;
		 max=0;
		
		while(attIt.hasNext()) {
		
			//Calculate the gain ratio
			String thisAtt=attIt.next();
	
			Double gainRatio=gainRatioCalc.getGainRatio(target, dataSet.getAttributeMap().get(thisAtt), dataSet.getDataList(), print1);
			//System.out.println("Gain ratio is: " +gainRatio + " for " + thisAtt);
			if(gainRatio > maxGainRatio) {
				max=dataSet.getAttributeList().indexOf(thisAtt);
				maxGainRatio=gainRatio;
			}
		}
		
		}
		
		if(print1==1) {
			System.out.println("The attribute with maximum gain ratio is " + dataSet.getAttributeMap().get(dataSet.getAttributeList().get(max)).getAName()) ;
			System.out.println("It is now the root of the tree.");
			System.out.println(" ");
		}
		
		//The one with the highest gain ratio is chosen as node
		Attribute best=dataSet.getAttributeMap().get(dataSet.getAttributeList().get(max));
		
		//Create a new n/ode, it is a question node so questOrAns is true
		Node rootNode=new Node(best, best.getAName(), true);
		
		if(subtreeRoot!=null) {
			subtreeRoot.addChild(rootNode, false);
		}
		
		//Information is updated to be sent to next call (attribute deleted from the list)
		ArrayList<String> updatedAttList=dataSet.getAttributeList();
		updatedAttList.remove(max);

		if(print1==1) {
			System.out.println(best.getAName() + " is now removed from the list");
			System.out.println("New attribute list is now: " +updatedAttList);
			System.out.println(" ");
			System.out.println("");
		}
		
		//Deal with the possible values of the attribute; where s is an outcome
		best.getPossibleValues().forEach((s) -> {
				
			
				//Make the value an "answer" child
				Node thisNode=new Node(best, s, false);
				rootNode.addChild(thisNode, updatedAttList.isEmpty());
				
				if (print1==1) {
					System.out.println("The node " + thisNode + " is now added to the tree as " + rootNode +"'s child.");
				}
				
				//Create an updated dataList that only contains cases with this value
				ArrayList<Case> subsetWithOutcome=new ArrayList<Case>();
				
				//Find the subset of data with outcome s for the attribute (not neccessary if this node is a leaf)
			
				
				if(!(dataSet.getDataList().isEmpty())) {
						
					dataSet.getDataList().forEach((c) -> { 
					if(c.getValue(best.getAName()).equals(s)) {
								subsetWithOutcome.add(c);
							}}); //for each
						}//isEmpty()
					
				if(!(subsetWithOutcome.isEmpty())) {
						//Call c4_5 with the updated data
					
						if(updatedAttList.isEmpty()) {
							//If there are no attributes left, make a leaf; this is the end of this subtree. 
							//There are no other attributes to test.
							//Figure out which value is most common and make an answer node with that value
							double countYes=0;
							double countNo=0;
							countYes=gainRatioCalc.Count(subsetWithOutcome, target, "yes");
							countNo=gainRatioCalc.Count(subsetWithOutcome, target, "no");
							
							String ans;
							if(countYes > countNo) {
								ans="yes";
							}
							
							else {
								//Default value is no (if number of yes and no is equal)
								ans="no";
							}
							
							Node childNode = new Node(target, ans, false);
							//Add to the tree, it is a leaf node
							thisNode.addChild(childNode, true);
						}
						
						
						else {
							
							if(print1==1) {
								System.out.println("Now calling c4_5 again for subest with " +thisNode);
								System.out.println("This continues for every value of " +best.getAName());
								System.out.println("");
								System.out.println("------------");
							}
							c4_5(new dataStruc(updatedAttList, dataSet.getAttributeMap(), subsetWithOutcome), thisNode, target);
						}
						
					}
				
				else {
				//The subset is empty. There is no data with this outcome. Set it to the default of 'no' 
					
					Node childNode=new Node(target, "no", false);

					thisNode.addChild(childNode, true);
				}
			
			
		});

		
		return rootNode;
		
	}
}
