package generic;

import processor.Clock;
import processor.Processor;

import java.io.*;
import java.util.*;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	static EventQueue eventQueue;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		simulationComplete = false;
		eventQueue = new EventQueue();
	}
	
	public static EventQueue getEventQueue() {
		return eventQueue;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{
		ArrayList <String> list = new ArrayList<String>();
		try (
			InputStream inputStream = new FileInputStream(assemblyProgramFile);
		)
		{
			int i, data;
			data = 0;
			String istring = "";
			while ((i = inputStream.read()) != -1) {
				String s = Integer.toHexString(i);
				if (s.length() == 2) {
					istring = istring + s;
				}
				else if (s.length() == 1) {
					istring = istring + "0" + s;
				}
				if (data == 3) {
					data = -1;
					list.add(istring);
					istring = "";
				}
				data = data + 1;
			}
		}
		catch (FileNotFoundException exc) {
			System.out.println(exc);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// System.out.println(list);
		
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
		
		// Step 1: Loading into Main Memory
		for (int i = 0; i < list.size() - 1; i++) {
			int l = Integer.parseUnsignedInt(list.get(i + 1), 16);
			processor.getMainMemory().setWord(i, l);
		}
		
		// Step 2: Setting PC
		processor.getRegisterFile().setProgramCounter(Integer.parseUnsignedInt(list.get(0), 16));
		
		// Step 3: Setting x0, x1, x2
		processor.getRegisterFile().setValue(0, 0);
		processor.getRegisterFile().setValue(1, 65535);
		processor.getRegisterFile().setValue(2, 65535);

//		processor.printState(0, 30);	
	}
	
	public static void simulate()
	{
		int count = 30;
		long Instructions = 0;
		boolean flag = true;
		while(simulationComplete == false)
		{	
			eventQueue.processEvents();
			processor.getRWUnit().performRW();
			processor.getMAUnit().performMA();
			processor.getEXUnit().performEX();
			processor.getOFUnit().performOF();
			processor.getIFUnit().performIF();
			if (processor.getIF_Flag()) {
				processor.getIF_Enable().setIF_enable(true);
			}
			if ((processor.getMA_RW().isRW_enable()) && (flag == true)) {
				Instructions = Instructions + 1;
			}
			if (processor.getMA_RW().getOpcode() == 29) {
				flag = false;
			}
			count -= 1;
			Clock.incrementClock();
		}
		
		// TODO
		// set statistics
		long Cycles = Clock.getCurrentTime();
		Statistics.setNumberOfCycles(Cycles);
		Statistics.setNumberOfInstructions(Instructions); 
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
