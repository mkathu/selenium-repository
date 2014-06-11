package com.Ford;

import java.util.List;

public class TestCaseVO 
{

	String TestScenario;
	String TestCase_ID;
	String TestCase_Name;
	String Type;
	String TestCase_Summary;
	List<TestStepVo>lisOfTestSteps;
	
	public void setLisOfTestSteps(List<TestStepVo> lisOfTestSteps) {
		this.lisOfTestSteps = lisOfTestSteps;
	}
	public List<TestStepVo> getLisOfTestSteps() {
		return lisOfTestSteps;
	}
	public String getTestScenario() {
		return TestScenario;
	}
	public String getTestCase_ID() {
		return TestCase_ID;
	}
	public String getTestCase_Name() {
		return TestCase_Name;
	}
	public String getType() {
		return Type;
	}
	public String getTestCase_Summary() {
		return TestCase_Summary;
	}
	public void setTestScenario(String testScenario) {
		TestScenario = testScenario;
	}
	public void setTestCase_ID(String testCase_ID) {
		TestCase_ID = testCase_ID;
	}
	public void setTestCase_Name(String testCase_Name) {
		TestCase_Name = testCase_Name;
	}
	public void setType(String type) {
		Type = type;
	}
	public void setTestCase_Summary(String testCase_Summary) {
		TestCase_Summary = testCase_Summary;
	}
}
