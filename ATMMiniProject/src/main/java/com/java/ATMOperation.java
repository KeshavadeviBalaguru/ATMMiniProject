package com.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class ATMOperation {
	static Connection scon=null;
	static Statement st=null;
	static ResultSet rs=null;
	static Scanner sc=new Scanner(System.in);
	static int customer_pin;
	static String customer_name;
	static String customer_password;
	static Float Balance_Amount;
	static Map<Float, String> ministmt=new HashMap<Float, String>();
	// static int wcount=0,dcount=0;
	
	public static void Login_Account()
	{
		scon=DbConnect.getConnection();
		try {
			st=scon.createStatement();
			System.out.println("Please Enter your Pin Number");
			 int Pin=sc.nextInt();
			 String sql="select * from customer where customer_pin="+Pin+"";
			 rs=st.executeQuery(sql);
			 if(rs.next())
			 {
				 System.out.println("Hello\n"+rs.getString(2));
				 DisplayMenu();
			 }
			 else
			 {
				 System.out.println("Invalid Pin!!!\n Please Enter Correct Pin Number");
			 }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	

	private static void DisplayMenu()
	{
		System.out.println("-----------------------------------------");
		System.out.println("Please Enter your Option");
		System.out.println("1.Balance Enquiry");
		System.out.println("2.Deposite Amount");
		System.out.println("3.Withdraw Amount");
		System.out.println("4.View Statement");
		System.out.println("5.Exit");
		System.out.println("-----------------------------------------");
		int ch=sc.nextInt();
		switch(ch)
		{
		case 1:
			Balance_Enquiry();
			break;
		case 2:
			Deposite();
			break;
		case 3:
			
			Withdraw();
			
			break;
		case 4:
			view_Statement();
			break;
		case 5:
			System.out.println("Your Transaction is End");
			break;
		default:
			System.out.println("Invalid Choice");
			
		}
		
		
	}






	private static void Withdraw() 
	{
		scon=DbConnect.getConnection();
		try {
			st=scon.createStatement();
			System.out.println("Enter your Account Number:");
			int Account_Number=sc.nextInt();
			String sql="select * from Account where cus_Account_Number="+Account_Number+"";
			rs=st.executeQuery(sql);
			
			
			if(rs.next())
			{
				
				System.out.println("Enter The Amount for Withdraw: ");
				float withdraw_amount=sc.nextFloat();
				
			
				if(withdraw_amount<500)
				{
					System.out.println(" Sorry!!! \nATM Machine have only 500 Rupees Note.");
				}
				else if(withdraw_amount<rs.getFloat(2)) 
				{  
					
						System.out.println("Collect Your Cash.");
						
						float Amount=rs.getFloat(2)-withdraw_amount;
						String upd="update Account set Balance_Amount ="+Amount+"where cus_Account_Number="+Account_Number+"";
						int a=st.executeUpdate(upd);
								if(a>0)
								{
							
								LocalDateTime current=LocalDateTime.now();
								System.out.println(" "+current+"\n");
					    System.out.println(withdraw_amount + "Amount Withdraw Successfully..");
					    //wcount++;
					    ministmt.put(withdraw_amount," Amount Withdraw  @"+current);
						System.out.println("Available Balance is ="+Amount);
								}
				}	
			
				else 
					{
						 System.out.println("InSufficient Balance.");
					}
				
				
				
			}
			else
			{
				System.out.println("Your Account Number is Incorrect\nPlease Check your Account Number.");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}



	private static void Deposite()
	{
		scon=DbConnect.getConnection();
		try {
			st=scon.createStatement();
			System.out.println("Enter Your Account Number:");
			int Account_Number=sc.nextInt();
			String sql="select * from Account where cus_Account_Number="+Account_Number+"";
			rs=st.executeQuery(sql);
			if(rs.next())
			{
				
				System.out.println("Enter The Amount to Deposite:");
				float Deposite_Amount=sc.nextInt();
				 float Amount=rs.getFloat(2)+Deposite_Amount;
				String upd="update Account set Balance_Amount ="+Amount+" where cus_Account_Number="+Account_Number+"";
				int a=st.executeUpdate(upd);
						if(a>0)
						{
					
							LocalDateTime current=LocalDateTime.now();
							System.out.println(" "+current+"\n");
			    System.out.println( Deposite_Amount+ "Amount Deposited Successfully..");
			  //  dcount++;
			
			  // ministmt.put(Deposite_Amount," Amount Deposited @"+current);
			    ministmt.put(Deposite_Amount," Amount  Deposited @"+current);
			    System.out.println("Available Balance is "+Amount);
				         }
				
				
				
			}
			else
			{
				System.out.println("Your Account Number is Incorrect\n Please check Your Number.");
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}



	private static void Balance_Enquiry() 
	{
		scon=DbConnect.getConnection();
		try {
			st=scon.createStatement();
			System.out.println("Enter Your Account Number:");
			int Account_Number=sc.nextInt();
			String sql="select * from Account where cus_Account_Number="+Account_Number+"";
			rs=st.executeQuery(sql);
			if(rs.next())
			{
				System.out.println("Your Available Balance is :"+rs.getFloat(2));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		

		
		
	}
	
	private static void view_Statement() {
		scon=DbConnect.getConnection();
		for(Map.Entry<Float, String> m:ministmt.entrySet())
		{
			System.out.println(m.getKey()+" "+m.getValue());
		}
		//System.out.println(" Number of Time Deposited : "+dcount);
	//	System.out.println(" Number of Time Withdraw : "+wcount);
		
		
	}
	



	public static void Set_Pin()
	{
		scon=DbConnect.getConnection();
		try {
			
			st=scon.createStatement();
		    System.out.println("Enter  Your Account Number To Set Your Pin");
			int Account_Number=sc.nextInt();
			//Check id exists
			String sql="select * from customer where cus_Account_Number="+Account_Number;
			rs=st.executeQuery(sql);
			
			if(rs.next()) 
			{
				System.out.println("Enter your Pin to set");
				int customer_pin=sc.nextInt();
		String upd="update customer set customer_pin="+customer_pin+" where cus_Account_Number="+Account_Number;
				int  i =st.executeUpdate(upd);
				if(i>0)
				{
					System.out.println("Pin Setted Successfully..");
				}
		
		
			}
			
			  
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		
		
		
	}
	

	public static void Change_Pin() {
		scon=DbConnect.getConnection();
		try {
			
			st=scon.createStatement();
			System.out.println("Enter your Account Number:");
			int Account_Number=sc.nextInt();
		   
			//Check Account Number exists
					String sql="select * from customer where cus_Account_Number="+Account_Number;
			
			rs=st.executeQuery(sql);
			
			if(rs.next()) 
			{
				 System.out.println("Enter  Your Old Pin");
					int Pin=sc.nextInt();
					String sql1="select * from customer where customer_pin="+Pin;
					rs=st.executeQuery(sql1);
					if(rs.next())
					{
				System.out.println("Enter your New Pin to set");
				int customer_pin=sc.nextInt();
		String upd="update customer set customer_pin="+customer_pin+" where cus_Account_Number="+Account_Number;
				int  i =st.executeUpdate(upd);
				if(i>0)
				{
					System.out.println("Pin Changed Successfully..");
				}
					}
					else
					{
						System.out.println("Your Pin Number is InCorrect \n Please Check Your Pin Number");
					}
		
			}
			else
			{
				System.out.println("Your Account Number is Incorrect\n Please check Your Number.");
			}
			
			
			  
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		
	}
}
	
		
	
	
	

	
		
