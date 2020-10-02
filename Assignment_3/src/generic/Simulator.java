package generic;

import java.io.*;
import processor.Clock;
import processor.Processor;
import processor.pipeline.IF_EnableLatchType;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	static IF_EnableLatchType IF_EnableLatch;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{

		InputStream ifile = null;
		DataInputStream dfile = null;
	  
		ifile = new FileInputStream(assemblyProgramFile);
		dfile = new DataInputStream(ifile);

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
		
		processor.getRegisterFile().setValue(0, 0);
		processor.getRegisterFile().setValue(1, 65535);
		processor.getRegisterFile().setValue(2, 65535);
	}
	
	public static void simulate()
	{
		while(simulationComplete == false)
		{
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();
		}
		
		// Statistics will be done.
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
