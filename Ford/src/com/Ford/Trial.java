package com.Ford;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Trial {

    public Map getHeaders()
    {
           Map lisOfHeader=new LinkedHashMap();
           FileInputStream file = null;
           try {
                  try {
                        file = new FileInputStream(new File("ExcelToXml.xlsx"));
                  } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                  }

                  XSSFWorkbook workbook = null;
                  try {
                        workbook = new XSSFWorkbook(file);
                  } catch (IOException e) {
                        // TODO Auto-generated catch block
                         e.printStackTrace();
                  }
                  XSSFSheet sheet = workbook.getSheetAt(0);
                  Iterator<Row> rowIterator = sheet.iterator();
           
                  int count = 0;
                  int colCount=0;
                  Row row =  null;
                  while(rowIterator.hasNext())
                  {      
                        if(count>0)
                        {
                               break;
                        }
                        row = rowIterator.next();
                        Iterator<Cell>celIterator=row.cellIterator();
                        while(celIterator.hasNext())
                        {
                               Cell cell = celIterator.next();
                               
                               if(cell != null)
                                      if(cell.getCellType() != Cell.CELL_TYPE_BLANK)
                                      {
                                             if(cell.getCellType() == Cell.CELL_TYPE_STRING)
                                             {
                                                    lisOfHeader.put(colCount,cell.getStringCellValue());
                                             }             
                                      }
                               colCount++;
                        }
                        count++;
                        row = rowIterator.next();
                  
                  }
           }
                  catch(Exception e)
                  {
                        e.printStackTrace();
                  }
              return lisOfHeader;
    }
    
    public void prepareXml(List<ScenarioVO>scenarios) throws ParserConfigurationException, TransformerException
	{
		try 
		{
			for(int scenario=0;scenario<scenarios.size();scenario++)
			{
				ScenarioVO scenarioObject=scenarios.get(scenario);
				String scenarioName=scenarioObject.scenarioName;
				System.out.println("scenarioName: "+scenarioName);
			
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("testscenario");
				doc.appendChild(rootElement);
			
				Element scenarioname = doc.createElement("testscenarioname");
				scenarioname.appendChild(doc.createTextNode(scenarioName));
				rootElement.appendChild(scenarioname);
			
				List<TestCaseVO>lsitOfTestCase=scenarioObject.lisOftestCases;
				for(int testCase=0;testCase< lsitOfTestCase.size();testCase++)
				{
				
					TestCaseVO testCaseObject=lsitOfTestCase.get(testCase);
					//if(testCaseObject!=null)
					
					Element testcase = doc.createElement("testcase");
					rootElement.appendChild(testcase);
					
					String testCaseName=testCaseObject.TestCase_Name;
					String testCaseId=testCaseObject.TestCase_ID;
					String testCaseSummary=testCaseObject.TestCase_Summary;
					String testCaseType=testCaseObject.Type;
					System.out.println("testCaseName: "+testCaseName);
					System.out.println("testCaseId: "+testCaseId);
					System.out.println("testCaseSummary: "+testCaseSummary);
					System.out.println("testCaseType: "+testCaseType);
					
					Element testcasename = doc.createElement("testcasename");
					testcasename.appendChild(doc.createTextNode(testCaseName));
					testcase.appendChild(testcasename);
					
					Element testcaseid = doc.createElement("testcaseid");
					testcaseid.appendChild(doc.createTextNode(testCaseId));
					testcase.appendChild(testcaseid);
					
					Element testcasesummary = doc.createElement("testcasesummary");
					testcasesummary.appendChild(doc.createTextNode(testCaseSummary));
					testcase.appendChild(testcasesummary);
					
					Element testcasetype = doc.createElement("testcasetype");
					testcasetype.appendChild(doc.createTextNode(testCaseType));
					testcase.appendChild(testcasetype);
					
					List<TestStepVo>lisOfTestSteps=testCaseObject.lisOfTestSteps;
					for(int testStep=0;testStep<lisOfTestSteps.size();testStep++)
					{
						
						TestStepVo testStepObejct=lisOfTestSteps.get(testStep);
						//if(testStepObejct!=null)
							String keyword=testStepObejct.getKeyWord();
							String descr=testStepObejct.Step_Description;
							String data=testStepObejct.TestData;
							String locator=testStepObejct.XpathLocator;
						
							System.out.println("keyword: "+keyword);
							System.out.println("descr: "+descr);
							System.out.println("data: "+data);
							System.out.println("locator: "+locator);
							System.out.println("***********************************************");
						
							Element teststep = doc.createElement("teststep");
							testcase.appendChild(teststep);
						
							Element testmethod = doc.createElement("testmethod");
							testmethod.appendChild(doc.createTextNode(keyword));
							teststep.appendChild(testmethod);
							if(data!=null)
							{
								Element testvalue = doc.createElement("testvalue");
								testvalue.appendChild(doc.createTextNode(data));
								teststep.appendChild(testvalue);
							}
							if(locator!=null)
							{
								Element elementlocator = doc.createElement("locator");
								elementlocator.appendChild(doc.createTextNode(locator));
								teststep.appendChild(elementlocator);
							}
					}
				}
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("C:\\Users\\mkarthik\\Documents\\file.xml"));
				transformer.transform(source, result);
			}
		}
			
						
							/*TransformerFactory transformerFactory = TransformerFactory.newInstance();
							Transformer transformer = transformerFactory.newTransformer();
							DOMSource source = new DOMSource(doc);
							StreamResult result = new StreamResult(new File("C:\\Users\\mkarthik\\Documents\\file.xml"));
							transformer.transform(source, result);*/
						
						// write the content into xml file
		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  }

	}
    public static void main(String[] args) throws ParserConfigurationException, TransformerException {
           // TODO Auto-generated method stub
           FileInputStream file = null;
           TestCaseVO testCaseObject = null;
           TestStepVo testStepObject=null;
           ScenarioVO scenarioObject=null;
          
           List<TestStepVo>listOfTestSteps = null;
           List<TestCaseVO>lisOfTestCase=null;
           List<ScenarioVO>listOfScenario=null;
           
           Trial trialObj=new Trial();
           try {
                  try {
                        file = new FileInputStream(new File("ExcelToXml.xlsx"));
                  } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                  }

                  XSSFWorkbook workbook = null;
                  try {
                        workbook = new XSSFWorkbook(file);
                  } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                  }
                  XSSFSheet sheet = workbook.getSheetAt(0);
                  Iterator<Row> rowIterator = sheet.iterator();
                  Map headers=trialObj.getHeaders();
               
                  int count = 0;
                  Row row =  null;
                  Cell cell=null;
                  
                  //boolean listCreateStep=false;
                  boolean listCreateTestCase=false;
           	      boolean listCreateScenario=false;
           	      //List<TestCaseVO>dummy=null;
                  while(rowIterator.hasNext())
                  {
                        if(count == 0)
                        {
                             row = rowIterator.next();
                             
                        }
                        else{
                            row = rowIterator.next();

                     	   int colCount=0;
                     	   Iterator<Cell> celIterator = null;
                     	   celIterator=row.cellIterator();
                     	   
                     	   boolean colZeroContainsData=true;
                     	   boolean colOneContainsData=true;
                     	  
                     	   boolean listCreateStep=false;
                           //boolean listCreateTestCase=false;
                           
                     	   while(/*celIterator.hasNext()*/colCount < 10)
                     	   {
                     		   cell=null;
                     		  int num= row.getRowNum();
                               cell =row.getCell(colCount);
                               
                               if(cell != null)
                               {
                                      if(cell.getCellType() != Cell.CELL_TYPE_BLANK)
                                      {
                                             if(cell.getCellType() == Cell.CELL_TYPE_STRING)
                                             {
                                            	 if(colCount==0)
                                            	 {
                                            		 
                                            		 if((colZeroContainsData==true)&&(colOneContainsData==true))
                                                	  {
                                                		 if((testStepObject!=null)&&(listOfTestSteps!=null))
                                              		   	 {
                                                			 if((testCaseObject!=null)&&(listOfTestSteps!=null))
                                                			 {
                                                				if((testCaseObject!=null)&&(scenarioObject.lisOftestCases!=null))
                                                				{
                                                			      if((scenarioObject!=null)&&(scenarioObject.lisOftestCases!=null))
                                                			      {
                                                			    	 if((scenarioObject!=null)&&(listOfScenario!=null))
                                                			    	 {
                                                			    		 if(listOfTestSteps.contains(testStepObject))
                                                			    		 {
                                                			    			 System.out.println("list of steps already contains: "+testStepObject);
                                                			    		 }
                                                			    		 else
                                                			    		 {
                                                			    			 listOfTestSteps.add(testStepObject);
                                                			    		 }
                                                			    		 testCaseObject.setLisOfTestSteps(listOfTestSteps);
                                                			    		 if(scenarioObject.lisOftestCases.contains(testCaseObject))
                                                			    		 {
                                                			    			 System.out.println("list of testcase already contains: "+testCaseObject);
                                                			    		 }
                                                			    		 else
                                                			    		 {
                                                			    			 scenarioObject.lisOftestCases.add(testCaseObject);
                                                			    			 System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                                               			  	 System.out.println("the address of listOfTestCases: "+scenarioObject.lisOftestCases);
                                                               			  	 System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                                			    		 }
                                                			    		 //scenarioObject.setLisOftestCases(lisOfTestCase);
                                                			    		 //listCreateTestCase=false;
                                                			    		 if(listOfScenario.contains(scenarioObject))
                                                			    		 {
                                                			    			 System.out.println("list of scenario already contains: "+scenarioObject);
                                                			    		 }
                                                			    		 else
                                                			    		 {
                                                			    			 listOfScenario.add(scenarioObject);
                                                			    		 }
                                                			    		 //listCreateStep=false;
                                                			    		 //listCreateTestCase=false;
                                                			    	 }
                                                			      }
                                                					
                                                				}
                                                				 
                                                			 }
                                              			  
                                              		     }	                    		 
                                                	  }
                                            		 if(listCreateScenario==false)
                                              		 {
                                              			listOfScenario=new ArrayList<ScenarioVO>();
                                              			listCreateScenario=true;
                                              		 }
                                            		 String header=(String) headers.get(colCount);
                                                	  if(header.equalsIgnoreCase("Test_Scenario"))
                                                	  {
                                                		  scenarioObject=new ScenarioVO();
                                                		  String test_Scenario=cell.getStringCellValue();
                                                		  scenarioObject.setScenarioName(test_Scenario);
                                                		  if(listCreateTestCase==false)
                                                		  {
                                                			  scenarioObject.lisOftestCases=new ArrayList<TestCaseVO>();
                                                			 listCreateTestCase=true;
                                                     		 listCreateStep=false;
                                                		  }
                                                	  }
                                                	  
                                            	 }
                                            	 if(colCount==1)
                                            	 {
                                            		 /*if(listCreateTestCase==false)
                                                	 {
                                                		 lisOfTestCase=new ArrayList<TestCaseVO>();
                                                		 listCreateTestCase=true;
                                                		 listCreateStep=false;
                                                	 }*/
                                            		 testCaseObject=new TestCaseVO();
                                           		  	 System.out.println("new testCaseObject: "+testCaseObject);
                                           		  	 //String test_Scenario=scenarioObject.scenarioName;
                                           		  	 //testCaseObject.setTestScenario(test_Scenario);
                                           		  	 String header=(String) headers.get(colCount);
                                           		  	 if(header.equalsIgnoreCase("TestCase_ID"))
                                           		  	 {
                                           		  		 String testCaseID=cell.getStringCellValue();
                                           		  		 testCaseObject.setTestCase_ID(testCaseID);
                                           		  	 }
                                            	 }
                                            	 if(colCount==2)
                                            	 {
                                            		 String header=(String) headers.get(colCount);
                                                	 if(header.equalsIgnoreCase("TestCase_Name"))
                                                	 {
                                                	    String testCaseName=cell.getStringCellValue();
                                                		testCaseObject.setTestCase_Name(testCaseName);
                                                	 }
                                            	 }
                                            	 if(colCount==3)
                                                 {
                                            		 String header=(String) headers.get(colCount);
                                                	 if(header.equalsIgnoreCase("Type"))
                                                	 {
                                                		 String type=cell.getStringCellValue();
                                                		 testCaseObject.setType(type);
                                                	 }
                                                 }
                                            	 if(colCount==4)
                                            	 {
                                            		 String header=(String) headers.get(colCount);
                                                	 if(header.equalsIgnoreCase("TestCase_Summary"))
                                                	 {
                                                		 String testCaseSummary=cell.getStringCellValue();
                                                		 testCaseObject.setTestCase_Summary(testCaseSummary);
                                                	 }
                                            	 }
                                            	 if(colCount==5)
                                            	 {
                                            		 if(listCreateStep==false)
                                            		 {
                                            		 	listOfTestSteps=new ArrayList<TestStepVo>();
                                            			listCreateStep=true;
                                            		 	testStepObject=null;
                                            		 }
                                            		 String header=(String) headers.get(colCount);
                                                	 if(header.equalsIgnoreCase("Step_Description"))
                                                	 {
                                                		  testStepObject=new TestStepVo(); 
                                                		  String stepDescription=cell.getStringCellValue();
                                                		  testStepObject.setStep_Desceription(stepDescription);
                                                	 }
                                            	 }
                                            	 if(colCount==7)
                                            	 {
                                            		 String header=(String) headers.get(colCount);
                                                	 if(header.equalsIgnoreCase("Key_Word"))
                                                	 {
                                                		 String keyWord=cell.getStringCellValue();
                                                		 testStepObject.setKeyWord(keyWord);
                                                	 } 
                                            	 }
                                            	 if(colCount==8)
                                            	 {
                                            		 String header=(String) headers.get(colCount);
                                                	 if(header.equalsIgnoreCase("Test_Data"))
                                                	 {
                                                		String testData=cell.getStringCellValue();
                                                		testStepObject.setTestData(testData);
                                                	 }
                                            	 }
                                            	 if(colCount==9)
                                            	 {
                                            		 String header=(String) headers.get(colCount);
                                                	 if(header.equalsIgnoreCase("Xpath_Locator"))
                                                	 {
                                                		 String xpathLocator=cell.getStringCellValue();
                                                		 testStepObject.setXpathLocator(xpathLocator);
                                                	 } 
                                            	 }
                                             }
                                             	             
                                      }
                                      if((colCount==0)&&(cell.getCellType()== Cell.CELL_TYPE_BLANK))
                                      {
                                    	  colZeroContainsData=false;
                                      }
                                      if((colCount==1)&&(cell.getCellType()== Cell.CELL_TYPE_BLANK))
                                      {
                                    	  colOneContainsData=false;
                                      }
                                     
                               //colCount++;
                     	   }
                               colCount++;  
                     	   }
                     	   if((colZeroContainsData==false)&&(colOneContainsData==false))
                     	   {
                     		   if((testStepObject!=null)&&(listOfTestSteps!=null))
                     		   {
                     			   if(listOfTestSteps.contains(testStepObject))
                     			   {
                     				   System.out.println("list of steps already contains: "+testStepObject);
                     			   }
                     			   else
                     			   {
                     				  listOfTestSteps.add(testStepObject);
                     			   }
                     		   }	
                     	   }
                     	  if((colZeroContainsData==false)&&(colOneContainsData==true))
                    	   {
                    		   if((testStepObject!=null)&&(listOfTestSteps!=null)) 
                    		   {
                    			   if((testCaseObject!=null)&&(listOfTestSteps!=null))
                    			   {
                    				   if((testCaseObject!=null)&&(scenarioObject.lisOftestCases!=null))
                    				   {
                    					   if(listOfTestSteps.contains(testStepObject))
                    					   {
                    						   System.out.println("list of steps already contains: "+testStepObject);
                    					   }
                    					   else
                    					   {
                    						   listOfTestSteps.add(testStepObject);
                    					   }
                    					   testCaseObject.setLisOfTestSteps(listOfTestSteps);
                    					   if(scenarioObject.lisOftestCases.contains(testCaseObject))
                    					   {
                    						   System.out.println("list of test case already contains: "+testCaseObject);
                    					   }
                    					   else
                    					   {
                    						   scenarioObject.lisOftestCases.add(testCaseObject);
                    					   }                  					   
                    				   }
                    			   }
                    		   }	
                    	   }
                       }
                        count++;
                        
                       //row = rowIterator.next();
                        //celIterator = null;
                        if((testCaseObject!=null)&&(testStepObject!=null))
                        {
                     	     //System.out.println(testCaseObject.TestScenario+" "+testCaseObject.TestCase_ID+" "+testCaseObject.TestCase_Name+" "+testCaseObject.TestCase_Summary+" "+testCaseObject.TestScenario+" "+testCaseObject.Type+" "+testCaseObject.lisOfTestSteps);
                     	  // System.out.println(testStepObject.KeyWord+" "+testStepObject.Step_Description+" "+testStepObject.TestData+" "+testStepObject.XpathLocator);
                        }
                  }
            		 if((testStepObject!=null)&&(listOfTestSteps!=null))
          		   	 {
            			 if((testCaseObject!=null)&&(listOfTestSteps!=null))
            			 {
            				if((testCaseObject!=null)&&(scenarioObject.lisOftestCases!=null))
            				{
            			      if((scenarioObject!=null)&&(scenarioObject.lisOftestCases!=null))
            			      {
            			    	 if((scenarioObject!=null)&&(listOfScenario!=null))
            			    	 {
            			    		 if(listOfTestSteps.contains(testStepObject))
            			    		 {
            			    			 System.out.println("list of steps already contains: "+testStepObject);
            			    		 }
            			    		 else
            			    		 {
            			    			 listOfTestSteps.add(testStepObject);
            			    		 }
            			    		 testCaseObject.setLisOfTestSteps(listOfTestSteps);
            			    		 if(scenarioObject.lisOftestCases.contains(testCaseObject))
            			    		 {
            			    			 System.out.println("list of testcase already contains: "+testCaseObject);
            			    		 }
            			    		 else
            			    		 {
            			    			 scenarioObject.lisOftestCases.add(testCaseObject);
            			    			 System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                           			  	 System.out.println("the address of listOfTestCases: "+scenarioObject.lisOftestCases);
                           			  	 System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            			    		 }
            			    		 //scenarioObject.setLisOftestCases(lisOfTestCase);
            			    		 if(listOfScenario.contains(scenarioObject))
            			    		 {
            			    			 System.out.println("list of scenario already contains: "+scenarioObject);
            			    		 }
            			    		 else
            			    		 {
            			    			 listOfScenario.add(scenarioObject);
            			    		 }
            			    	 }
            			      }
            					
            				}
            				 
            			 }
          			  
          		     }
           }
                  catch(Exception e)
                  {
                        e.printStackTrace();
                  }
           
           System.out.println("no. of objects in scenario list: "+listOfScenario.size());
           for(int i=0;i<listOfScenario.size();i++)
           {
        	  ScenarioVO obj= listOfScenario.get(i);
        	  System.out.println("scenario name: "+obj.getScenarioName());
        	  for(int j=0;j<scenarioObject.lisOftestCases.size();j++)
        	  {
        		  System.out.println("the lisOfTestCases is pointing to: "+scenarioObject.lisOftestCases);
        		  TestCaseVO test=scenarioObject.lisOftestCases.get(j);
        		  System.out.println("test case name: "+test.TestCase_Name);
        		  for(int k=0;k<test.lisOfTestSteps.size();k++)
        		  {
        			  TestStepVo step=test.lisOfTestSteps.get(k);
        			  System.out.println("keyword: "+step.KeyWord);
        			  System.out.println("************************************************************");
        		  }
        	  }
           }
          // ExcelToXml obj=new ExcelToXml();
          //trialObj.prepareXml(listOfScenario);
           }
}