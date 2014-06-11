package com.Ford;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
class Scenario
{
	String scenarioName;
	List<TestCaseVO>lisOftestCases;
}
public class CollectionsToXml
{

	 static List <TestCaseVO>listTestCaseObj=new ArrayList<TestCaseVO>();
	static List<TestStepVo>listTestSteps=new ArrayList<TestStepVo>();;
	static List<Scenario> listScenario=new ArrayList();	

	public void prepareSteps(TestStepVo obj)
	{
		listTestSteps.add(obj);
	}
	public void prepareTestCase(TestCaseVO obj)
	{
		listTestCaseObj.add(obj);
	}
	public void prepareScenario(Scenario scenario)
	{
		listScenario.add(scenario);
	}
	public void prepareXml(List<Scenario>scenarios) throws ParserConfigurationException, TransformerException
	{
		try 
		{
			for(int scenario=0;scenario<scenarios.size();scenario++)
			{
				Scenario scenarioObject=scenarios.get(scenario);
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
					for(int testStep=0;testStep<=lisOfTestSteps.size()-1;testStep++)
					{
						
						TestStepVo testStepObejct=lisOfTestSteps.get(testStep);
						//if(testStepObejct!=null)
							String keyword=testStepObejct.KeyWord;
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
	
	public static void main(String[] args) throws ParserConfigurationException, TransformerException 
	{
		TestCaseVO obj=new TestCaseVO();
		
		TestStepVo objs=new TestStepVo();
		TestStepVo obj1=new TestStepVo();
		TestStepVo obj2=new TestStepVo();
		TestStepVo obj3=new TestStepVo();
        TestStepVo obj4=new TestStepVo();
        CollectionsToXml object=new CollectionsToXml();
        
       
        objs.KeyWord="LAUNCH";
        objs.Step_Description="open application";
        objs.TestData="http://www.gmail.com";
		
        obj1.KeyWord="VERIFY TITLE";
        obj1.Step_Description="Verify if the title of the page is Google";
        obj1.TestData="Google";
        
        obj2.KeyWord="VERIFY TEXTFIELD";
        obj2.Step_Description="Verify if username field is present";
        obj2.XpathLocator="//input[1]";
        
        obj3.KeyWord="VERIFY PASSWORD";
        obj3.Step_Description="Verify if password field is present";
        obj3.XpathLocator="//input[2]";
        
        
        obj3.KeyWord="CLICK";
        obj3.Step_Description="Verify if password field is present";
        obj3.XpathLocator="//input[3]";
        
        object.prepareSteps(objs);
        object.prepareSteps(obj1);
        object.prepareSteps(obj2);
        object.prepareSteps(obj3);
        
        obj.lisOfTestSteps=listTestSteps;
        obj.TestCase_ID="TC_01";
        obj.TestCase_Name="Login_Validate_User_Logout";
        obj.TestCase_Summary="this test case is to validate login functionality";
        obj.TestScenario="Login";
        obj.Type="Smoke";
        
        object.prepareTestCase(obj);
        
        Scenario sc=new Scenario();
        sc.scenarioName=obj.TestScenario;
        sc.lisOftestCases=listTestCaseObj;
        
        object.prepareScenario(sc);
        
        System.out.println("Scenario: "+listScenario.get(0).scenarioName);
         
        object.prepareXml(listScenario);
	}

}
