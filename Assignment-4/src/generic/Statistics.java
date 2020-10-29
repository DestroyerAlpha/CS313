package generic;

import java.io.PrintWriter;

public class Statistics {
	public static int numberOfInstructions = 0;
	static int numberOfCycles;
	public static int data_hazards=0;
	public static int control_hazards=0;

	

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("Number of times the OF stage needed to stall because of a datahazard = " + data_hazards);
			writer.println("Number of times an instruction on a wrong branch path entered the pipeline = " + control_hazards);
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	public static void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}

	public static  void setNumberOfCycles(int numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}
}
