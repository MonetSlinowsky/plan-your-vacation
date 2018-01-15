package c4_5;


import java.util.LinkedList;

import java.util.List;

/*************************
 * 
 * @author monet
 * Tree node class
 *Functions modified from https://github.com/gt4dev/yet-another-tree-structure/blob/master/java/src/com/tree/TreeNode.java
 *to implement the tree
 */

public class Node {

	//Stores this node's parent
	public Node parent;
	
	//List of this node's children
	public List<Node> children;
	
	//True if it is a question, false if it is an answer (i.e. a question would be 'Hotel_City?' and an answer node would be 'Fresno')
	//If false it can only have one child, which is either a question node or a leaf node
	private boolean questOrAns;
	
	//The attribute this node is associated with
	public Attribute attributeTested;
	
	//Attributes to number the nodes
	private static int count=0;
	private int num;
	
	//The value of the node; either the attribute being tested (ex. Hotel_City) or the answer (ex. Memphis)
	private String nodeVal; 
	
	//For tree traversal
	private List<Node> elementsIndex;
	


	public Node(Attribute attTested, String nodeVal, boolean questOrAns) {
		/*
		 * Constructor 
		 */

		this.attributeTested = attTested;
		this.nodeVal=nodeVal;
		this.setQuestOrAns(questOrAns);

		this.children = new LinkedList<Node>();

		this.elementsIndex = new LinkedList<Node>();

		this.elementsIndex.add(this);
		this.num=count;
		count++;

	}
	
		public String getVal() {
			/*
			 * Returns value of node
			 */
			return nodeVal;
		}

		public boolean isRoot() {
			/*
			 * Returns true if the node is the root of the tree
			 */

			return (parent == null);

		}


		public boolean getQuestOrAns() {
			/*
			 * Returns true if the node is a question node (i.e. the name of an attribute ex. Hotel_City)
			 */
			return this.isQuestOrAns();
		}
		
		
		public boolean isLeaf() {
			/*
			 * Returns true if the node is a leaf
			 */
			
			if(children==null) {
				return true;
			}
			
			return children.size() == 0;

		}

		
		public void addChild(Node childNode, boolean isLeaf) {
			/*
			 * Input: childNode -> node that is not yet on the tree
			 * 	      isLeaf -> true if childNode is to be a leaf
			 * Output: adds childNode to the tree, with this node as the childNode's parent
			 */

			childNode.parent = this;
			
			if(children==null) {
				this.children=new LinkedList<Node>();
			}
			
			this.children.add(childNode);

			this.registerChildForSearch(childNode);
			
			if(isLeaf) {
				childNode.children=null;
			}

		}

		public int getNum() {
			/*
			 * Returns the node's number
			 */
			return this.num;
		}

		public int getLevel() {
			/*
			 * Returns the node's level on the tree (root is level 0)
			 */

			if (this.isRoot())

				return 0;

			else

				return parent.getLevel() + 1;

		}



		private void registerChildForSearch(Node node) {
			/*
			 * Adds node to elementsIndex so it can be part of the tree traversal
			 */

			elementsIndex.add(node);

			if (parent != null)

				parent.registerChildForSearch(node);

		}
	
		public void print() {
			/*
			 * Prints a string representation of the tree
			 */
			
			for (Node element : this.elementsIndex) { 
				
				if(element.isLeaf()) {
					continue;
				}
				
				System.out.println(element.getLevel() + " " + element + " " +element.children);
				System.out.println();
			}

		}

		
		@Override

		public String toString() {
			//Returns a string representation of this node 

			return nodeVal != null ? nodeVal.toString() : "[data null]";

		}

		public boolean isQuestOrAns() {
			return questOrAns;
		}

		public void setQuestOrAns(boolean questOrAns) {
			this.questOrAns = questOrAns;
		}
		
		
	
	
}
