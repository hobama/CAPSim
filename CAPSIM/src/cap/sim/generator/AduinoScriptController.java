package cap.sim.generator;

import cap.sim.analyzer.ArduinoModelAnalyzer;
import cap.sim.analyzer.ModelAnalyzer;
import cap.sim.entity.CupcarbonElement;
import cap.sim.entity.Component;
import cap.sim.entity.NaturalEvent;
import cap.sim.entity.Sensor;
import cap.sim.entity.SensorType;
import cap.sim.gui.CAPSIMMainUI;
import cap.sim.utility.FileUtility;
import cap.sim.utility.FolderUtil;

public class AduinoScriptController {
	public boolean createCode(String path) 
			throws NumberFormatException, Exception {		
		ArduinoGenerator generator=new ArduinoGenerator();
		String codeFolder=CAPSIMMainUI.cubPath+"\\Arduino";
		FolderUtil.checkPath(codeFolder);		
		for (ArduinoBoard board : ArduinoModelAnalyzer.Boards) {
			String code = "";
			code = generator.generateArduinoCode(board);
			String codefile = "ArduinoCode" + board.getBoardID() + ".ino";
			FileUtility.createAndWriteToFile(codeFolder + "\\" + codefile, code);			
		}				
		return true;
	}
}
