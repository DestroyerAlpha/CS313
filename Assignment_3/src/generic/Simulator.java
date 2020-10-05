package generic;

import java.io.*;
import processor.Clock;
import processor.Processor;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{

		try
		{
			InputStream infile = new FileInputStream(assemblyProgramFile);
			DataInputStream dfile = new DataInputStream(infile);

			int i=0;
			while(dfile.available()>0) {
				
				int j = dfile.readInt();
				if(i!=0) {
					processor.getMainMemory().setWord(i-1,j);
				
				}
				else {
					processor.getRegisterFile().setProgramCounter(j);
				}
					i++;
			}
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		
		
		processor.getRegisterFile().setValue(0, 0);
		processor.getRegisterFile().setValue(1, 65535);
		processor.getRegisterFile().setValue(2, 65535);
	}
	
	public static void simulate()
	{
		while(simulationComplete == false)
		{

			// System.out.println("IF");
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			// System.out.println("OF");
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			// System.out.println("EX");
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			// System.out.println("MA");
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			// System.out.println("RW");
			processor.getRWUnit().performRW();
			Clock.incrementClock();
			// System.out.println("END");
		}
		
		// TODO
		// set statistics.
		// Statistics is done in Instructionfetch.java .
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
