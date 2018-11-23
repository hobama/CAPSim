package cap.testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cap.model.entity.SoftwareArchitecture;
import cap.model.handler.XMLParser;
import cap.sim.analyzer.ModelAnalyzer;
import cap.sim.generator.ConfigurationGenerator;
import cap.sim.generator.ScriptController;

public class TestReadingFile {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		XMLParser handler=new XMLParser("finalcasestudy.xml");
		handler.loadDataFromXML();		
		SoftwareArchitecture sa=SoftwareArchitecture.getSoftwareArchitechure();
		
		
		assertNotEquals("Elements are empty", 0, SoftwareArchitecture.getSoftwareArchitechure().getElements().size());
		
		ModelAnalyzer.ComponentId=0;
		ModelAnalyzer.SensorId=0;
//		// Analyzing Model >>> 
		ModelAnalyzer.analyzeModel(sa);	
//		// Create SenScript code files
		new ScriptController().createScript("");
//		// create Sensor Radios && Nodes(sensors) Files
		ConfigurationGenerator.creatConfigration();
		
		System.out.println("END");
	}

}
