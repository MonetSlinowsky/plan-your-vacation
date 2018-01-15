package c4_5;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		dataStruc myData=new dataStruc();
		System.out.println(myData.getAttributeList().toString());
		myData.getAttributeList().remove(0);
		System.out.println(myData.getAttributeList().toString());
		
	}

}
