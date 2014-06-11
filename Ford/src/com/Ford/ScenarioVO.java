package com.Ford;

import java.util.List;

public class ScenarioVO 
{
	String scenarioName;
	List<TestCaseVO>lisOftestCases;
	public String getScenarioName() {
		return scenarioName;
	}
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	public void setLisOftestCases(List<TestCaseVO> lisOftestCases) {
		this.lisOftestCases = lisOftestCases;
	}
	public List<TestCaseVO> getLisOftestCases() {
		return lisOftestCases;
	}
}
