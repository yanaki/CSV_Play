package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

 

public class Main {
    public static void main(String[] args) throws IOException { 
//    	String testLine1 = " 123213  	 123 123	321";
//    	String testLine2 = "012321s3  	 123 123	321";
//    	String testLine3 = " 123213  	 0123 123	321";

    	String testLine1 = "111 222 33";
    	String testLine2 = "222 33 444";
    	String testLine3 = "33 44 555";
    	
    	
    	CSV_Data_Manipulator testData = new CSV_Data_Manipulator();
    	testData.addLine(testLine1);
    	testData.addLine(testLine2);
    	DataValidator validator = new DataValidator(testData);
    	System.out.print(validator.getErrors());
    	System.out.println("---------------------------------------------");
    	testData.addLine(testLine3);
    	System.out.println(validator.getErrors().length());
    	//testData.switchLines(0, 1);
    	testData.switchNumber(0, 0, 2, 2);
    	testData.createValue(5, 5, "12312132");
    	System.out.println(testData);
    	System.out.print(validator.getErrors());
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        System.out.print("Enter String");
//        String s = br.readLine();
//        System.out.print("Enter Integer:");
//        try{
//            int i = Integer.parseInt(br.readLine());
//        }catch(NumberFormatException nfe){
//            System.err.println("Invalid Format!");
//        }
    }
}
