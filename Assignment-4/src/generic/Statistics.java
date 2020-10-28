package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	public static int numberOfInstructions = 0;
	public static int numberOfCycles = 0;
	public static int data_hazard=0;
	public static int control_hazard=0;

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			
			// writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("The number of times the OF stage needed to stall because of a data_hazard = " + data_hazard);
			writer.println("The number of times an instruction on a wrong branch path entered the pipeline = " + control_hazard);
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	public void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}

	public static void setNumberOfCycles(int numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}

	public int getNumberOfInstructions() {
		return Statistics.numberOfInstructions;
	}
}
