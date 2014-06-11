package com.Ford;

import java.util.ArrayList;
import java.util.List;

public class Check 
{
	public static void main(String[] args)
	{
      ScenarioVO sc=new ScenarioVO();
      sc.scenarioName="Login";
      List some;
      List address = new ArrayList();
      
      for(int i=0;i<=2;i++)
      {
    	  some=new ArrayList<>();
    	  some.add(i);
    	  address.add(some);
    	  System.out.println(some);
    	  System.out.println("*********************");
    	  System.out.println(Integer.toHexString(address.toString().hashCode()));
    	  String s=Integer.toHexString(address.toString().hashCode());
    	  List ss=(List)(Object)Integer.valueOf(s);
      }
	}
	
	

}
