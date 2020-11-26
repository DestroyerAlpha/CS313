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
					//System.out.println(istring);
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
		
		System.out.println(list);
		
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
		boolean Jhanda = true;
		while(simulationComplete == false)
		{	
//			System.out.print("Size of Queue: ");
//			System.out.println(getEventQueue().getSize());
//			processor.printState(0, 10);
			eventQueue.processEvents();
//			System.out.println("Events Processed. ");
//			System.out.println("Status: ");
//			System.out.print("RWE: ");
//			System.out.print(processor.getMA_RW().isRW_enable());
//			System.out.print("; MAE: ");
//			System.out.print(processor.getEX_MA().isMA_enable());
//			System.out.print("; EXE: ");
//			System.out.print(processor.getOF_EX().isEX_enable());
//			System.out.print("; OFE: ");
//			System.out.print(processor.getIF_OF().isOF_enable());
//			System.out.print("; IFE: ");
//			System.out.print(processor.getIF_Enable().isIF_enable());
//			System.out.print("; RWB: ");
//			System.out.print(processor.getMA_RW().isRW_busy());
//			System.out.print("; MAB: ");
//			System.out.print(processor.getEX_MA().isMA_busy());
//			System.out.print("; EXB: ");
//			System.out.print(processor.getOF_EX().isEX_busy());
//			System.out.print("; OFB: ");
//			System.out.print(processor.getIF_OF().isOF_busy());
//			System.out.print("; IFB: ");
//			System.out.print(processor.getIF_Enable().isIF_busy());
//			System.out.print("; ControlHazardFlag: ");
//			System.out.print(processor.getControlHazardFlag());
//			System.out.print("; PC: ");
//			System.out.println(processor.getRegisterFile().getProgramCounter());
			processor.getRWUnit().performRW();
//			System.out.println("RW Complete: ");
//			System.out.println("Status: ");
//			System.out.print("RWE: ");
//			System.out.print(processor.getMA_RW().isRW_enable());
//			System.out.print("; MAE: ");
//			System.out.print(processor.getEX_MA().isMA_enable());
//			System.out.print("; EXE: ");
//			System.out.print(processor.getOF_EX().isEX_enable());
//			System.out.print("; OFE: ");
//			System.out.print(processor.getIF_OF().isOF_enable());
//			System.out.print("; IFE: ");
//			System.out.print(processor.getIF_Enable().isIF_enable());
//			System.out.print("; RWB: ");
//			System.out.print(processor.getMA_RW().isRW_busy());
//			System.out.print("; MAB: ");
//			System.out.print(processor.getEX_MA().isMA_busy());
//			System.out.print("; EXB: ");
//			System.out.print(processor.getOF_EX().isEX_busy());
//			System.out.print("; OFB: ");
//			System.out.print(processor.getIF_OF().isOF_busy());
//			System.out.print("; IFB: ");
//			System.out.print(processor.getIF_Enable().isIF_busy());
//			System.out.print("; ControlHazardFlag: ");
//			System.out.print(processor.getControlHazardFlag());
//			System.out.print("; PC: ");
//			System.out.println(processor.getRegisterFile().getProgramCounter());
			processor.getMAUnit().performMA();
//			System.out.println("MA Complete: ");
//			System.out.println("Status: ");
//			System.out.print("RWE: ");
//			System.out.print(processor.getMA_RW().isRW_enable());
//			System.out.print("; MAE: ");
//			System.out.print(processor.getEX_MA().isMA_enable());
//			System.out.print("; EXE: ");
//			System.out.print(processor.getOF_EX().isEX_enable());
//			System.out.print("; OFE: ");
//			System.out.print(processor.getIF_OF().isOF_enable());
//			System.out.print("; IFE: ");
//			System.out.print(processor.getIF_Enable().isIF_enable());
//			System.out.print("; RWB: ");
//			System.out.print(processor.getMA_RW().isRW_busy());
//			System.out.print("; MAB: ");
//			System.out.print(processor.getEX_MA().isMA_busy());
//			System.out.print("; EXB: ");
//			System.out.print(processor.getOF_EX().isEX_busy());
//			System.out.print("; OFB: ");
//			System.out.print(processor.getIF_OF().isOF_busy());
//			System.out.print("; IFB: ");
//			System.out.print(processor.getIF_Enable().isIF_busy());
//			System.out.print("; ControlHazardFlag: ");
//			System.out.print(processor.getControlHazardFlag());
//			System.out.print("; PC: ");
//			System.out.println(processor.getRegisterFile().getProgramCounter());
//			System.out.println(Clock.getCurrentTime());
			processor.getEXUnit().performEX();
//			System.out.println("EX Complete: ");
//			System.out.println("Status: ");			
//			System.out.print("RWE: ");
//			System.out.print(processor.getMA_RW().isRW_enable());
//			System.out.print("; MAE: ");
//			System.out.print(processor.getEX_MA().isMA_enable());
//			System.out.print("; EXE: ");
//			System.out.print(processor.getOF_EX().isEX_enable());
//			System.out.print("; OFE: ");
//			System.out.print(processor.getIF_OF().isOF_enable());
//			System.out.print("; IFE: ");
//			System.out.print(processor.getIF_Enable().isIF_enable());
//			System.out.print("; RWB: ");
//			System.out.print(processor.getMA_RW().isRW_busy());
//			System.out.print("; MAB: ");
//			System.out.print(processor.getEX_MA().isMA_busy());
//			System.out.print("; EXB: ");
//			System.out.print(processor.getOF_EX().isEX_busy());
//			System.out.print("; OFB: ");
//			System.out.print(processor.getIF_OF().isOF_busy());
//			System.out.print("; IFB: ");
//			System.out.print(processor.getIF_Enable().isIF_busy());
//			System.out.print("; ControlHazardFlag: ");
//			System.out.print(processor.getControlHazardFlag());
//			System.out.print("; PC: ");
//			System.out.println(processor.getRegisterFile().getProgramCounter());
			processor.getOFUnit().performOF();
//			processor.printState(0, 10);
//			System.out.println("OF Complete: ");
//			System.out.println("Status: ");
//			System.out.print("RWE: ");
//			System.out.print(processor.getMA_RW().isRW_enable());
//			System.out.print("; MAE: ");
//			System.out.print(processor.getEX_MA().isMA_enable());
//			System.out.print("; EXE: ");
//			System.out.print(processor.getOF_EX().isEX_enable());
//			System.out.print("; OFE: ");
//			System.out.print(processor.getIF_OF().isOF_enable());
//			System.out.print("; IFE: ");
//			System.out.print(processor.getIF_Enable().isIF_enable());
//			System.out.print("; RWB: ");
//			System.out.print(processor.getMA_RW().isRW_busy());
//			System.out.print("; MAB: ");
//			System.out.print(processor.getEX_MA().isMA_busy());
//			System.out.print("; EXB: ");
//			System.out.print(processor.getOF_EX().isEX_busy());
//			System.out.print("; OFB: ");
//			System.out.print(processor.getIF_OF().isOF_busy());
//			System.out.print("; IFB: ");
//			System.out.print(processor.getIF_Enable().isIF_busy());
//			System.out.print("; ControlHazardFlag: ");
//			System.out.print(processor.getControlHazardFlag());
//			System.out.print("; PC: ");
//			System.out.println(processor.getRegisterFile().getProgramCounter());
			processor.getIFUnit().performIF();
//			System.out.println("IF Complete: ");
//			System.out.print("RWE: ");
//			System.out.print(processor.getMA_RW().isRW_enable());
//			System.out.print("; MAE: ");
//			System.out.print(processor.getEX_MA().isMA_enable());
//			System.out.print("; EXE: ");
//			System.out.print(processor.getOF_EX().isEX_enable());
//			System.out.print("; OFE: ");
//			System.out.print(processor.getIF_OF().isOF_enable());
//			System.out.print("; IFE: ");
//			System.out.print(processor.getIF_Enable().isIF_enable());
//			System.out.print("; RWB: ");
//			System.out.print(processor.getMA_RW().isRW_busy());
//			System.out.print("; MAB: ");
//			System.out.print(processor.getEX_MA().isMA_busy());
//			System.out.print("; EXB: ");
//			System.out.print(processor.getOF_EX().isEX_busy());
//			System.out.print("; OFB: ");
//			System.out.print(processor.getIF_OF().isOF_busy());
//			System.out.print("; IFB: ");
//			System.out.print(processor.getIF_Enable().isIF_busy());
//			System.out.print("; ControlHazardFlag: ");
//			System.out.print(processor.getControlHazardFlag());
//			System.out.print("; PC: ");
//			System.out.println(processor.getRegisterFile().getProgramCounter());
//			System.out.print("Size of Queue: ");
//			System.out.println(getEventQueue().getSize());
//			processor.printState(0, 10); // ((0, 0) refers to the range of main memory addresses we wish to print. this is an empty set.
// 			System.out.println(Clock.getCurrentTime());
//			System.out.println("---------------------------------Cycle complete---------------------------------");
			if (processor.getIF_Flag()) {
				processor.getIF_Enable().setIF_enable(true);
			}
			if ((processor.getMA_RW().isRW_enable()) && (Jhanda == true)) {
				Instructions = Instructions + 1;
//				System.out.print("Instruction Increased at: ");
//				System.out.println(Clock.getCurrentTime());
//				System.out.print("Opcode: ");
//				System.out.println(processor.getMA_RW().getOpcode());				
			}
			if (processor.getMA_RW().getOpcode() == 29) {
				Jhanda = false;
			}
			count -= 1;
			Clock.incrementClock();
//			System.out.print("Simulation Complete? ");
//			System.out.println(simulationComplete);
//			System.out.print("Cycle: ");
//			System.out.println(Clock.getCurrentTime());
//			if (count == 0) {
//				break;
//			}
		}
		
		// TODO
		// set statistics
		long Cycles = Clock.getCurrentTime();
//		System.out.println(Cycles);
		Statistics.setNumberOfCycles(Cycles);
		Statistics.setNumberOfInstructions(Instructions); 
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
