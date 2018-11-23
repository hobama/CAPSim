package cap.sim.generator.helper;

import java.util.Hashtable;
import java.util.Map;

public class ArduinoHelper {
			
	public String readAnalog(String pinNum,String val) {

		return val+" = analogRead("+pinNum+");\n";
	}
	
	public String digitalWrite(int pinNum,DigitalStatus status)
	{
		return "digitalWrite("+pinNum+", "+status+");\n";
	}
	
	public String spi_transfer(String value)
	{
		return "SPI.transfer("+value+");\n";
	}
	
	public String delay(int value)
	{
		return "delay("+value+");\n";
	}
	public String defineVars(Hashtable<String,Integer> vars)
	{
		String varStr="";
		for (Map.Entry<String, Integer> var : vars.entrySet()) {
			varStr+="int "+ var.getKey()+ " = "+var.getValue()+";\n";
		}
		return varStr;
	}
	
}
