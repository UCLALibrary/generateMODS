package edu.ucla.library.dep.generateMods;

public class TestSplit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = new String("arm|russ");
	       System.out.println("split(String regex):");
	       String array1[]= str.split("|");
	       for (String temp: array1){
	          System.out.println(temp);
	       }
	      

	}

}
