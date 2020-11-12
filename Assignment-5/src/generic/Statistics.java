package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	public static int numberOfInstructions = 0;
	static int numberOfCycles;
	public static int datahaz=0;
	public static int controlhaz=0;

	

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			// TODO add code here to print statistics in the output file
			// writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("Number of times the OF stage needed to stall because of a datahazard = " + datahaz);
			writer.println("Number of times an instruction on a wrong branch path entered the pipeline = " + controlhaz);
			
			
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	// TODO write functions to update statistics
	public static void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}

	public static  void setNumberOfCycles(int numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}
}
