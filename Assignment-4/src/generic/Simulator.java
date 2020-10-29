package generic;
import java.io.*;

import processor.Clock;
import processor.Processor;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	static int cycle=0;
	
	 
	
	public static void setupSimulation(String assemblyProgramFile, Processor p) throws IOException
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile) throws IOException
	{
		try{
			int i=0;
			DataInputStream din = new DataInputStream(new FileInputStream(assemblyProgramFile)) ;
			processor.getRegisterFile().setProgramCounter(din.readInt());
			while (din.available() > 0) {				
				processor.getMainMemory().setWord(i,din.readInt());
				i++ ;	
			}
			processor.getMainMemory().getContentsAsString(0, 10);
			processor.getRegisterFile().setValue(0,0);
			processor.getRegisterFile().setValue(1,65535);
			processor.getRegisterFile().setValue(2,65535);
			din.close();
			
		}
    	catch(FileNotFoundException e){
    		System.out.println("Cannot Open the Input File");
    		return;
    	}
		
		
	}
	
	public static void simulate()
	{
		while(simulationComplete == false)
		{
			processor.getRWUnit().performRW();
			processor.getMAUnit().performMA();
			processor.getEXUnit().performEX();
			processor.getOFUnit().performOF();
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			cycle++;
		}
		Statistics.setNumberOfCycles(cycle);
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
