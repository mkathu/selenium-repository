package com.Ford;
import java.net.InetAddress;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import com.ibm.icu.util.Calendar;
import com.mongodb.*;

import defFiles.Common;
//import defFiles.MailOut;
import Lib.Controller.Driver;

public class DBConnection {
	
	public Humongous mongo = new Humongous();
	private final String selectMethod = "cursor"; 
    
    /**
     * This function will construct the connection string, to connect to the Sql Server
     * @author krishna krishna g
     * @exception Exception
     * @return	ReturnConString	This will return the connection string
     */
    private String getConnectionUrl(){
    	String ReturnConString = "";
    	try{
	    	String HostName = InetAddress.getLocalHost().getHostName();
	    	if (HostName.toLowerCase().contains("clb") || !Driver.Init.GetDatabaseIntegratedSecurity()){
		    	if(Driver.Init.GetIsInstance()){
		    		ReturnConString = "jdbc:sqlserver://" + Driver.Init.GetDBServer() + ";instanceName=" + Driver.Init.GetDBInstance() + ";databaseName=" + Driver.Init.GetDBName() +";selectMethod="+selectMethod+";"; //instanceName=" + instanceName + ";";
		    	}
		    	else{
		    		ReturnConString = "jdbc:sqlserver://" + Driver.Init.GetDBServer() + ";databaseName=" + Driver.Init.GetDBName() +";selectMethod="+selectMethod+";"; //instanceName=" + instanceName + "; 
		    	}
	    	}
	    	else{
	    		if(Driver.Init.GetIsInstance()){
		    		ReturnConString = "jdbc:sqlserver://" + Driver.Init.GetDBServer() + ";instanceName=" + Driver.Init.GetDBInstance() + ";databaseName=" + Driver.Init.GetDBName() +";integratedSecurity=true;selectMethod="+selectMethod+";"; //instanceName=" + instanceName + ";";
		    	}
		    	else{
		    		ReturnConString = "jdbc:sqlserver://" + Driver.Init.GetDBServer() + ";databaseName=" + Driver.Init.GetDBName() +";integratedSecurity=true;selectMethod="+selectMethod+";"; //instanceName=" + instanceName + "; 
		    	}
	    	}
    	}
    	catch(Exception ex){
    	
    	}
    	
    	return ReturnConString;
    }
    
    /**
     * This function will construct the connection URL from the parameters passed
     * this is a over loaded function. the other overloaded function does not take any arguments 
     * and build the connection URL from the details available in the Environment file.
     * <strong>if there is no DB Instance then need to pass "" as the value to SqlDBInstance argument</strong> 
     * @author Krishna Kishore G
     * @param SQLDBServer The SQL Server Name
     * @param SqlDBInstance The Sql Server Instance
     * @param SqlDBName The Database name to which the code has to connect to
     * @return	this function will return the connection URI
     */
    private String getConnectionUrl(String SQLDBServer, String SqlDBInstance, String SqlDBName){
    	String ReturnConString = "";
    	try{
	    	String HostName = InetAddress.getLocalHost().getHostName();
	    	if (HostName.toLowerCase().contains("clb") || !Driver.Init.GetDatabaseIntegratedSecurity()){
	    		if (SqlDBInstance.trim().equals("")){
	    			ReturnConString = "jdbc:sqlserver://" + SQLDBServer + ";databaseName=" + SqlDBName +";selectMethod="+selectMethod+";"; //instanceName=" + instanceName + ";
	    		}
	    		else{
		    		ReturnConString = "jdbc:sqlserver://" + SQLDBServer + ";instanceName=" + SqlDBInstance + ";databaseName=" + SqlDBName +";selectMethod="+selectMethod+";"; //instanceName=" + instanceName + ";";
	    		}
	    	}
	    	else{
	    		if (SqlDBInstance.trim().equals("")){
	    			ReturnConString = "jdbc:sqlserver://" + SQLDBServer + ";databaseName=" + SqlDBName +";selectMethod="+selectMethod+";"; //instanceName=" + instanceName + ";
	    			if( Driver.Init.GetDatabaseIntegratedSecurity() )
	    				ReturnConString += ";integratedSecurity=true;";
	    		}
	    		else{
		    		ReturnConString = "jdbc:sqlserver://" + SQLDBServer + ";instanceName=" + SqlDBInstance + ";databaseName=" + SqlDBName +";integratedSecurity=true;selectMethod="+selectMethod+";"; //instanceName=" + instanceName + ";";
	    		}
	    	}
    	}
    	catch(Exception ex){
    	
    	}
    	
    	return ReturnConString;
    }
    
    /**
     * This function will build the Connection object and returns the same to the user
     * this is a overloaded method where this function does not take any argument,
     * insted this takes the required details from the environment file
     * @author krishna kishore g
     * @exception SqlException
     * @return	Con	This function returns the connection object.
     */
    private java.sql.Connection getConnection(){
    	java.sql.Connection  con = null;
         try{
              //Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
        	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        	  String[] SqlServer = Driver.Init.GetDBServer().split("~");
        	  String[] SqlInstance = Driver.Init.GetDBInstance().split("~");
        	  String[] SqlDBName = Driver.Init.GetDBName().split("~");
        	  String[] DBUser = Driver.Init.GetDBUserName().split("~");
        	  String[] DBPwd = Driver.Init.GetDBPassword().split("~");
        	  String HostName = InetAddress.getLocalHost().getHostName();
        	  String ConURI = "";
        	  for(int LoopA=0;LoopA<SqlServer.length;LoopA++){
            	  //String ConURI = getConnectionUrl();
        		  ConURI = getConnectionUrl(SqlServer[LoopA], SqlInstance[LoopA], SqlDBName[LoopA]);
            	  if (HostName.toLowerCase().contains("clb") || !Driver.Init.GetDatabaseIntegratedSecurity()){
      	    		  con = java.sql.DriverManager.getConnection(ConURI,DBUser[LoopA],DBPwd[LoopA]);
      	    	  }
      	    	  else{
      	    		  con = java.sql.DriverManager.getConnection(ConURI);
      	    	  }
                  if(con!=null)
                  {
                	  System.out.println("Connection Successful!");
                	  break;
                  }
        	  }
        	  
         }catch(Exception e){
              e.printStackTrace();
              System.out.println("Error Trace in getConnection() : " + e.getMessage());
        }
         return con;
     }

    /**
     * This Function will build the connection object, this is a overloaded function
     * where in all the value required are passed as argument to the function. 
     * <strong>if there is no DB Instance then need to pass "" as the value to SqlDBInstance argument</strong> 
     * @author Krishna Kishore G
     * @param SQLDBServer The SQL Server Name
     * @param SqlDBInstance The Sql Server Instance
     * @param SqlDBName The Database name to which the code has to connect to
     * @return	this function will return the connection URI
     */
    private java.sql.Connection getConnection(String SQLDBServer, String SqlDBInstance, String SqlDBName, String UserName, String Password){
    	java.sql.Connection  con = null;
    	try{
             //Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
       	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//       	  String[] SqlServer = Driver.Init.GetDBServer().split("~");
//       	  String[] SqlInstance = Driver.Init.GetDBInstance().split("~");
//       	  String[] SqlDBName = Driver.Init.GetDBName().split("~");
//       	  String[] DBUser = Driver.Init.GetDBUserName().split("~");
//       	  String[] DBPwd = Driver.Init.GetDBPassword().split("~");
       	  String HostName = InetAddress.getLocalHost().getHostName();
       	  String ConURI = "";
//       	  for(int LoopA=0;LoopA<SqlServer.length;LoopA++){
//           	  //String ConURI = getConnectionUrl();
       		  	 ConURI = getConnectionUrl(SQLDBServer, SqlDBInstance, SqlDBName);
           	  	 con = java.sql.DriverManager.getConnection(ConURI,UserName,Password);
     	    	 if(con!=null)
                 {
               	  System.out.println("Connection Successful!");
                 }
//       	  }
       	  
        }catch(Exception e){
             e.printStackTrace();
             System.out.println("Error Trace in getConnection() : " + e.getMessage());
       }
        return con;
    }

	/**
	 * This function will display the Sql Db properties that is connected
	 * @author krishnakg
	 *  
	 */
    public void displayDbProperties(){
    	java.sql.Connection  con = null;
         java.sql.DatabaseMetaData dm = null;
         java.sql.ResultSet rs = null;
         try{
              con= this.getConnection();
              if(con!=null){
                   dm = con.getMetaData();
                   System.out.println("Driver Information");
                   System.out.println("\tDriver Name: "+ dm.getDriverName());
                   System.out.println("\tDriver Version: "+ dm.getDriverVersion ());
                   System.out.println("\nDatabase Information ");
                   System.out.println("\tDatabase Name: "+ dm.getDatabaseProductName());
                   System.out.println("\tDatabase Version: "+ dm.getDatabaseProductVersion());
                   System.out.println("Avalilable Catalogs ");
                   rs = dm.getCatalogs();
                   while(rs.next()){
                        System.out.println("\tcatalog: "+ rs.getString(1));
                   } 
                   rs.close();
                   rs = null;
                   closeConnection(con);
              }else System.out.println("Error: No active Connection");
         }catch(Exception e){
              e.printStackTrace();
         }
         dm=null;
    }     
    
    /**
     * This function executes the query in the DB and returns the value in a double dimensional array
     * this function uses
     * java.sql.Connection getConnection
     * String getConnectionUrl
     * the above private functions to execute the query.
     * this is a overloaded function where it takes all the required value from the environment file
     * @param Query	The Sql Query that needs to be executed
     * @return	values as String[][] 
     */
    public String[][] ExecuteQuery(String Query){
    	java.sql.Connection  con = null;
        java.sql.DatabaseMetaData dm = null;
        java.sql.ResultSet rs = null;
        int ColCount = 0;
        String DBRValue = "";
        String[][] RowCol = null;
        try{
             con= this.getConnection();
             if(con!=null){
                  Statement sqlstmt = con.createStatement();
                  sqlstmt.setQueryTimeout(500000); // added by kk on 03/26/2014 -  setting the max query time out to 8 min                  
                  rs = sqlstmt.executeQuery(Query);
                  ColCount = rs.getMetaData().getColumnCount();
                  while(rs.next()){
                       System.out.println("\tcatalog: "+ rs.getString(1));
                       DBRValue += rs.getRow();
                       for (int LoopA = 1; LoopA <= ColCount; LoopA++){
                    	   DBRValue += "`" + rs.getString(LoopA);
                       }
                       DBRValue += "~";
                  }
                  String[] Rows = DBRValue.split("~");
                  String[] Col1 = Rows[0].split("`");
                  int RLen = Rows.length;
                  int CLen = Col1.length;
                  RowCol = new String[RLen][CLen];
                  for(int LoopB = 0; LoopB < RLen; LoopB++){
                	  Col1 = Rows[LoopB].split("`");
                	  for(int LoopC = 0; LoopC < CLen; LoopC++){
                		  System.out.println(Col1[LoopC]);
                		  RowCol[LoopB][LoopC] = Col1[LoopC];
                	  }
                  }
                  rs.close();
                  rs = null;
                  closeConnection(con);
             }else System.out.println("Error: No active Connection");
        }catch(Exception e){
        	System.out.println("Error @ ExecuteQuery(Non-OverLoaded) " + e.getMessage());
             e.printStackTrace();
        }
        dm=null;
        return RowCol;
   }

    /**
     * This function executes the query in the DB and returns the value in a double dimensional array
     * this function uses
     * java.sql.Connection getConnection
     * String getConnectionUrl
     * the above private functions to execute the query.
     * this is a overloaded function where we need to pass all the required values to the function
     * <strong>if there is no DB Instance then need to pass "" as the value to SqlDBInstance argument</strong> 
     * @param Query	The Sql Query that needs to be executed
     * @return	values as String[][] 
     */
    public String[][] ExecuteQuery(String Query, String SQLDBServer, String SqlDBInstance, String SqlDBName, String UserName, String Password){
    	java.sql.Connection  con = null;
    	java.sql.DatabaseMetaData dm = null;
        java.sql.ResultSet rs = null;
        int ColCount = 0;
        String DBRValue = "";
        String[][] RowCol = null;
        String FUnction_Name = "ExecuteQuery";
        boolean IsDBReturnedRow=false;
        try{
        	//Message,functionname,status=> Done,Pass,Fail,Warning,N/A Mar 26, 2014 8:09:26 PM
			Driver.CUtil.WriteResults("Query = " + Query, FUnction_Name, "done");
			Driver.CUtil.WriteResults("Connection Parameters; "
					+ "SQLDBServer = " + SQLDBServer +
					"SqlDBInstance = " + SqlDBInstance +
					"SqlDBName = " + SqlDBName +
					"UserName = " + UserName +
					"Password = " + Password, FUnction_Name, "done");
             con= this.getConnection(SQLDBServer,SqlDBInstance,SqlDBName,UserName,Password);
             if(con!=null){
                  Statement sqlstmt = con.createStatement();
                  sqlstmt.setQueryTimeout(500000); // added by kk on 03/26/2014 -  setting the max query time out to 8 min
                  rs = sqlstmt.executeQuery(Query);
                  //Message,functionname,status=> Done,Pass,Fail,Warning,N/A Mar 26, 2014 8:13:39 PM
                  Driver.CUtil.WriteResults("Query executed successfully", FUnction_Name, "done");
                  ColCount = rs.getMetaData().getColumnCount();
                  String Data = "";
                  while(rs.next()){
                       DBRValue += rs.getRow();
                       for (int LoopA = 1; LoopA <= ColCount; LoopA++){
                    	   Data += "`" + rs.getString(LoopA);
                    	   DBRValue += "`" + rs.getString(LoopA);
                       }
                       DBRValue += "~";
                       //Message,functionname,status=> Done,Pass,Fail,Warning,N/A Mar 26, 2014 8:14:45 PM
					Driver.CUtil.WriteResults("Row Data = " + Data, FUnction_Name, "done");
					Data = "";
					IsDBReturnedRow=true;
                  }
                  //Message,functionname,status=> Done,Pass,Fail,Warning,N/A Mar 26, 2014 8:15:05 PM
				 if(IsDBReturnedRow){//If condition added:Madhu:25/04/2014
                  Driver.CUtil.WriteResults(":: All Rows printed ::", FUnction_Name, "done");
				 }
				 else
				 {
					 Driver.CUtil.WriteResults(":: No Rows printed ::", FUnction_Name, "Warning"); 
				 }
                  String[] Rows = DBRValue.split("~");
                  String[] Col1 = Rows[0].split("`");
                  int RLen = Rows.length;
                  int CLen = Col1.length;
                  RowCol = new String[RLen][CLen];
                  for(int LoopB = 0; LoopB < RLen; LoopB++){
                	  Col1 = Rows[LoopB].split("`");
                	  for(int LoopC = 0; LoopC < CLen; LoopC++){
                		  RowCol[LoopB][LoopC] = Col1[LoopC];
                	  }
                  }
                  rs.close();
                  rs = null;
                  closeConnection(con);
             }else {
            	 //Message,functionname,status=> Done,Pass,Fail,Warning,N/A Mar 26, 2014 8:15:32 PM
            	 Driver.CUtil.WriteResults("Error: No active Connection", FUnction_Name, "done");
            	 System.out.println("Error: No active Connection");
             }
        }catch(Exception e){
        	System.out.println("Error @ ExecuteQuery(OverLoaded) " + e.getMessage());
        	//Message,functionname,status=> Done,Pass,Fail,Warning,N/A Mar 26, 2014 8:15:54 PM
			Driver.CUtil.WriteResults("Error @ ExecuteQuery(OverLoaded) " + e.getMessage(), FUnction_Name, "fail");
            //System.out.println(e.printStackTrace());
        }
        dm=null;
        return RowCol;
   }

    
    /**
     * This optimized function executes the query in the DB and returns the value in a double dimensional array
     * this function uses
     * java.sql.Connection getConnection
     * String getConnectionUrl
     * the above private functions to execute the query.
     * this is a overloaded function where we need to pass all the required values to the function
     * <strong>if there is no DB Instance then need to pass "" as the value to SqlDBInstance argument</strong>
     * @author Harry Reid III <harry.reid@move.com> 
     * @param Query	The Sql Query that needs to be executed
     * @return	values as String[][] 
     */
    public String[][] ExecuteQueryOptimized (
    		String Query, 
    		String SQLDBServer, 
    		String SqlDBInstance, 
    		String SqlDBName, 
    		String UserName, 
    		String Password )
    {
    	java.sql.Connection  con = null;
        java.sql.ResultSet rs = null;
        int ColCount = 0;
        String[][] RowCol = null;
        try{
             con= this.getConnection( SQLDBServer, SqlDBInstance, SqlDBName, UserName, Password );
             if( con != null ){
                  Statement sqlstmt = con.createStatement();
                  rs = sqlstmt.executeQuery(Query);
                  ColCount = rs.getMetaData().getColumnCount();
                  ArrayList<String[]> rowsList = new ArrayList<String[]>();
                  String [] rowData;
                  while( rs.next() ){
                	  rowData = new String[ ColCount + 1 ];
                	  rowData[ 0 ] = ( ( Integer )rs.getRow() ).toString();
                      for (int LoopA = 1; LoopA <= ColCount; LoopA++){
                    	  rowData[ LoopA ] = rs.getString( LoopA );
                      }
                      rowsList.add( rowData );
                  }
                  rs.close();
                  rs = null;
                  closeConnection(con);
                  RowCol = rowsList.toArray( new String[ rowsList.size() ][ ColCount + 1 ] );
             }
             else System.out.println( "Error: No active Connection" );
        }
        catch( Exception e )
        { 
        	System.out.println("Error @ ExecuteQuery(OverLoaded) " + e.getMessage());
        }

        return RowCol;
    }
	
    /**
     * This function will query the CukeResulkts table and returns the data in a double dimensional array
     * @author krishna kishore g - krishnakishore.g@move.com
     * @param Query
     * @return DB Return value
     */
    public String[][] QueryCukeResultDB(String Query){
    	String[][] rv = null;
    	try {
			rv = ExecuteQuery(Query, "Q53VSQL04","VSQL04","MoveAutomation","qaautomation","Welcome12!");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error @ InsertCuckResult " + e.getMessage());
			rv = null;
		}
    	return rv;
    }
    
    /**
     * THis function will write the result data to the DB, which is used to compute the  
     * @author krishna kishore g - krishnakishore.g@move.com
     * @param RunID
     * @param ProjectName
     * @param Environment
     * @param ProcessLogFIlePath
     * @param FeatureFilePath
     * @param LogFIlePath
     * @param Passcounter
     * @param FailCounter
     */
    public void InsertCuckResult(String RunID, String ProjectName, String Environment, String ProcessLogFIlePath, String FeatureFilePath, String LogFIlePath, String Passcounter, String FailCounter, String ExitCode, String FeatureFiles, String Tags, String Browser){
    	String DateTimes[] = null;
    	Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
	    DateTimes = ft.format(dNow).split("/");
    	java.sql.Connection  con = null;
    	java.sql.DatabaseMetaData dm = null;
        java.sql.ResultSet rs = null;
        try{
             con= this.getConnection("Q53VSQL04","VSQL04","MoveAutomation","qaautomation","Welcome12!");
             if(con!=null){
                  dm = con.getMetaData();
                  rs = dm.getCatalogs();
                  Statement sqlstmt = con.createStatement();
                  int opRCode = sqlstmt.executeUpdate("Insert into CukeResults (runid,project,environment,processedlog,foverview,logfile,passcounter,failcounter,exitcode,cukefeature,cuketag,dateday,datemonth,dateyear,browser) values ('" + RunID + "','" + ProjectName + "','" + Environment + "','" + ProcessLogFIlePath + "','" + FeatureFilePath + "','" + LogFIlePath + "','" + Passcounter +" ','" + FailCounter +" ','" + ExitCode + " ','" + FeatureFiles + " ','" + Tags + " ','" + DateTimes[0] + " ','" + DateTimes[1] + " ','" + DateTimes[2] +"','" + Browser + "');");
                  System.out.println(opRCode);
                  closeConnection(con);
             }else System.out.println("Error: No active Connection");
        }catch(Exception e){
        	System.out.println("Error @ InsertCuckResult " + e.getMessage());
            //System.out.println(e.printStackTrace());
        }
        dm=null;
   }
    
    /**
     * This is the function that will execute the stored procedure and returns the value in a double dimensional array
     * <strong>this function is not yet dev complete</strong>
     * @param StroedProc
     * @return
     * @deprecated
     */
    public String[][] ExecuteStroedProc(String StroedProc){
    	java.sql.Connection  con = null;
        java.sql.DatabaseMetaData dm = null;
        java.sql.ResultSet rs = null;
        int ColCount = 0;
        String DBRValue = "";
        String[][] RowCol = null;
        StroedProc = "exec " + StroedProc;
        try{
             con= this.getConnection();
             if(con!=null){
                  dm = con.getMetaData();
                  System.out.println("Driver Information");
                  System.out.println("\tDriver Name: "+ dm.getDriverName());
                  System.out.println("\tDriver Version: "+ dm.getDriverVersion ());
                  System.out.println("\nDatabase Information ");
                  System.out.println("\tDatabase Name: "+ dm.getDatabaseProductName());
                  System.out.println("\tDatabase Version: "+ dm.getDatabaseProductVersion());
                  System.out.println("Avalilable Catalogs ");
                  CallableStatement CStat = con.prepareCall(StroedProc);
                  CStat.setQueryTimeout(180);
                  CStat.setEscapeProcessing(true);
                  rs = CStat.executeQuery();
                  ColCount = rs.getMetaData().getColumnCount();
                  while(rs.next()){
                       System.out.println("\tcatalog: "+ rs.getString(1));
                       DBRValue += rs.getRow();
                       for (int LoopA = 1; LoopA <= ColCount; LoopA++){
                    	   DBRValue += "&" + rs.getString(LoopA);
                       }
                       DBRValue += "~";
                  }
                  String[] Rows = DBRValue.split("~");
                  String[] Col1 = Rows[0].split("&"); 
                  RowCol = new String[Rows.length][Col1.length];
                  for(int LoopB = 0; LoopB < Rows.length; LoopB++){
                	  Col1 = Rows[LoopB].split("&");
                	  for(int LoopC = 0; LoopC<Col1.length; LoopC++){
                		  System.out.println(Col1[LoopC]);
                		  RowCol[LoopB][LoopC] = Col1[LoopC];
                	  }
                  }
                  rs.close();
                  rs = null;
                  closeConnection(con);
             }else System.out.println("Error: No active Connection");
        }catch(Exception e){
             e.printStackTrace();
        }
        dm=null;
        return RowCol;
   }
    
    /**
     * this function will clode the DB connection
     */
    private void closeConnection(java.sql.Connection  con){
         try{
              if(con!=null)
                   con.close();
              con=null;
         }catch(Exception e){
              e.printStackTrace();
         }
    }
    
    
//    public static void main(String[] args) throws Exception
//      {
//    	DBConnection myDbTest = new DBConnection();
//         //myDbTest.displayDbProperties();
//         String Query = "select top 5 * from Lead.lead";
//         Query = "WITH a AS (SELECT top 1000 s.mpr_id,s.property_status,l.address_line,l.city,l.country from Property.dataagg.listings l (nolock) join SE_Loading.dbo.property_repository s (nolock) on l.property_id = s.property_id and l.state_code = s.state_code join Property.mls.listings_extended_info e (nolock)  on e.listing_id = l.listing_id where e.fulfillmentgroup_bitmask = '64' and l.agreement_id = 1 and l.listing_status_id =4 and s.property_status = 'Recently sold' and l.source_alias_id is not NULL) SELECT TOP 1 a.* FROM a Order by NEWID ()";
//         myDbTest.ExecuteQuery(Query);
//         //myDbTest.ExecuteStroedProc("dbo.GetRanLat_Long_QAAuto");   
//      }

    public class Humongous {

    	// Downoad Mongo jar from https://github.com/mongodb/mongo-java-driver/downloads

    	public AggregationOutput ExecuteQuery(BasicDBObject Match, BasicDBObject Projection, BasicDBObject Group, String DBName, String Collection) {
    		//BasicDBObject Match, Projection, Group = null;
    		DBCollection table = null;
    		DB db = null;
    		MongoClient mongo = null;
    		try{
    			/**** Connect to MongoDB ****/
    			// Since 2.10.0, uses MongoClient
    			mongo = GetMongoClient();
    			/**** Get database****/
    			db = mongo.getDB(DBName);
    			/**** Get collection****/
    			table = db.getCollection(Collection);
    			/**** Compose Query using BasicDBObject & Collect the output in AggregationOutput  ****/
//    			Match = new BasicDBObject("$match",new BasicDBObject("OfficeID", "100045").append("MSTDateDimKey", new BasicDBObject("$gte",20130501).append("$lte", 20130731)).append("EventSummaryCategory", new BasicDBObject("$in",new String[]{"Generic Page Views"})));
//    			Group = new BasicDBObject("$group", new BasicDBObject().append("_id", new BasicDBObject("OfficeID", "$OfficeID").append("EventSummary", "$EventSummaryCategory")).append("EventCount", new BasicDBObject("$sum","$EventCount")));
//    			Projection = new BasicDBObject("$project",new BasicDBObject().append("_id",0).append("'OfficeId'", "$_id.OfficeID").append("'EventSummary'", "$_id.EventSummary").append("EventCount", 1));
    			AggregationOutput output = table.aggregate(Match, Projection, Group);
    			//System.out.println("Query Output : " + output);
    			//System.out.println("End of Execution");
    			return output;
    		}
    		catch(Exception ex){
    			System.out.println(ex.getMessage());
    			System.out.println(ex.getStackTrace());
    			return null;
    		}
    		finally{
    			
    		}
    	}

    	
    	public AggregationOutput ExecuteQuery(BasicDBObject Match, String DBName, String Collection) {
    		DBCollection table = null;
    		DB db = null;
    		MongoClient mongo = null;
    		try{
    			/**** Connect to MongoDB ****/
    			// Since 2.10.0, uses MongoClient
    			mongo = GetMongoClient();
    			/**** Get database****/
    			db = mongo.getDB(DBName);
    			/**** Get collection****/
    			table = db.getCollection(Collection);
    			/**** Compose Query using BasicDBObject & Collect the output in AggregationOutput  ****/
//    			Match = new BasicDBObject("$match",new BasicDBObject("OfficeID", "100045").append("MSTDateDimKey", new BasicDBObject("$gte",20130501).append("$lte", 20130731)).append("EventSummaryCategory", new BasicDBObject("$in",new String[]{"Generic Page Views"})));
    			AggregationOutput output = table.aggregate( Match );
    			//System.out.println("Query Output : " + output);
    			//System.out.println("End of Execution");
    			return output;
    		}
    		catch(Exception ex){
    			System.out.println(ex.getMessage());
    			System.out.println(ex.getStackTrace());
    			return null;
    		}
    		finally{
    			
    		}
    	}
    	
    	
    	/**
    	 * Initialize the mongo driver using the environment file.
    	 * @author Harry Reid III
    	 * @return connection to mongo database
    	 */
    	public MongoClient GetMongoClient(){
    		MongoClient mongo = null;
    		try {
    			/**** Retrieve mongo replica set servers from Environment and parse ****/
    			String[] replicaSet = Driver.Init.GetMongoDatabase().split( "," );
    			ArrayList< ServerAddress > list = new ArrayList<ServerAddress>();
    			for( int i = 0; i < replicaSet.length; i++ ) {
    				String name = replicaSet[ i ];
    				int port = 27017;
    				if( name.contains( ":" ) ) {
    					String s[] = replicaSet[ i ].split( ":" );
    					name = s[ 0 ];
    					port = Integer.parseInt( s[ 1 ] );
    				}
    				list.add( new ServerAddress( name, port ) );
    			}
    			/**** initialize the MongoClient ****/
    			mongo = new MongoClient( list );
    		}
    		catch( Exception ex ){
    			System.out.println( "Error @ GetMongoCleint" );
    			System.out.println( "Unable to connect to Mongo DB with the exception " + ex.getMessage() );
    			System.out.println( "Stact Trace : " + ex.getStackTrace().toString() );
    			mongo = null;
    		}
    		return mongo;
    	}

    	
    	/**
    	 * Query the data to find the latest dates and determine the semi-month or month end date
    	 * along with the start date.  We can use these dates later in our testing by retrieving them from
    	 * <em>Driver.Gprops.GetAggregationDateStart()</em> and <em>Driver.Gprops.GetAggregationDateEnd()</em>.
    	 * @author Harry Reid III
    	 * @param database Mongo database name
    	 * @param collection Mongo collection name
    	 */
    	public void getLatestAggregationDatesFromMongoDatabase(
    			String database, 
    			String collection)
    	{
    		Date aggregationDateStart = null, aggregationDateEnd = null;
    		DBCollection table = null;
    		DB db = null;
    		MongoClient mongo = null;
    		try{
    			/**** Connect to MongoDB ****/
    			PrintLine( "Connect to MongoDB" );
    			mongo = Driver.DBCon.mongo.GetMongoClient();
    			
    			/**** Get database ****/
    			PrintLine( "Get database" );
    			db = mongo.getDB( database );
    			
    			/**** Get collection ****/
    			PrintLine( "Get collection" );
    			table = db.getCollection( collection );
    			
    			/**** Group data by date in mongo and return the dates in reverse order ****/
    			PrintLine( "Group data by date in mongo and return the dates in reverse order" );
    			BasicDBObject Group = new BasicDBObject( "$group", new BasicDBObject()
    				.append( "_id", "$MSTDateDimKey" )
    				.append( "count", new BasicDBObject( "$sum", 1 ) ) );
    			BasicDBObject Sort = new BasicDBObject( "$sort", new BasicDBObject( "_id", -1 ) );
    			AggregationOutput output = table.aggregate( Group, Sort );
    		
    			/**** Find the latest start and end aggregation dates *****/
    			PrintLine( "Find the latest start and end aggregation dates" );
    			String MSTDateDimKey;
    			SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
    			Calendar calendar = Calendar.getInstance();
    			Iterator< DBObject > iterator = output.results().iterator();
    			while( iterator.hasNext() ) {
    				MSTDateDimKey = ( ( Integer )iterator.next().toMap().get( "_id") ).toString();
    				calendar.setTime( simpleDateFormat.parse(
    						MSTDateDimKey.substring(0, 4) + "-" 
    						+ MSTDateDimKey.substring(4, 6) + "-"
    						+ MSTDateDimKey.substring(6, 8)));
    				/**** This is where we determine the aggregation end date ****/
        			PrintLine( "This is where we determine the aggregation end date" );
    				if( calendar.get( Calendar.DAY_OF_MONTH ) == 15 
    						|| calendar.get( Calendar.DAY_OF_MONTH ) == calendar.getActualMaximum( Calendar.DAY_OF_MONTH ) ) {
    					aggregationDateEnd = calendar.getTime();
    					calendar.set( Calendar.DAY_OF_MONTH, 1);
    					aggregationDateStart = calendar.getTime();
    					break;
    				}
    			}
    			/**** Expose the aggregation dates to the rest of the application ****/
    			PrintLine( "Expose the aggregation dates to the rest of the application" );
    			Driver.Gprops.SetStartDate( aggregationDateStart );
    			Driver.Gprops.SetEndDate( aggregationDateEnd );	
    		}
    		catch( Exception ex ) {
    			System.out.println( "Error @ GetMongoCleint" );
    			System.out.println( "Unable to connect to Mongo DB with the exception " + ex.getMessage() );
    			System.out.println( "Stact Trace : " + ex.getStackTrace().toString() );
    			mongo = null;
    		}
    		finally {
    			
    		}		
    	}    	
    }
    
	
	public static void PrintLine( String message )
	{
		System.out.println( 
				new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).format( Calendar.getInstance().getTime() ) 
				+ "  \t" + message );
	}
}

