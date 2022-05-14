package com.java;

import java.util.Scanner;

public class ATMMain {

	public static void main(String[] args) {
	  //Menu for User
		
		Scanner sc=new Scanner(System.in);
		while(true)
		{
			System.out.println(" WELCOME TO SBI ATM ..");
			System.out.println("**********Enter your Choice***********");
			System.out.println("1.Login for Transaction");
			System.out.println("2.Set Pin or Change Pin");
			System.out.println("**************************************");
			int ch=sc.nextInt();
			
			switch(ch)
			{
			case 1:
				ATMOperation.Login_Account();
				break;
			case 2:
				
				System.out.println("Enter Your Option:");
				System.out.println("1.Set Pin");
				System.out.println("2.Change Pin");
				int c=sc.nextInt();
				 if(c==1)
				 {
					 ATMOperation.Set_Pin();
				 }
				 else if(c==2)
				 {
					 ATMOperation.Change_Pin();
				 }
				 else
				 {
					 System.out.println("Please Enter the Correct Option.");
				 }
				break;
			}
			System.out.println("Do you Want to Continue Transaction :");
			System.out.println("Yes/No");
			String a=sc.next();
			if(a.equalsIgnoreCase("No"))
			{  
				System.out.println("Thanks For Coming");
				System.out.println("Please Visit Again..");
				break;
			}
		}
	}

}
