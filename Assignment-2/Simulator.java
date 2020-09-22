package generic;

import java.io.*;
import java.io.FileInputStream;
import generic.Operand.OperandType;


public class Simulator {
		
	static FileInputStream inputcodeStream = null;
	
	public static void setupSimulation(String assemblyProgramFile)
	{	
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}
	
	public static void assemble(String objectProgramFile)
	{
		System.out.println(ParsedProgram.symtab.get("palindrome"));
		System.out.println(ParsedProgram.getInstructionAt(3));
		//String s = Integer.toBinaryString(21);
		System.out.println(Simulator.toBinary(21));

		
		
		//TODO your assembler code
		//1. open the objectProgramFile in binary mode
		//2. write the firstCodeAddress to the file
		//3. write the data to the file
		//4. assemble one instruction at a time, and write to the file
		//5. close the file
	}

	public static byte[] toBinary(int number) { 
		byte[] binary = new byte[32]; 
		int index = 0; 
		int copyOfInput = number; 
		while (copyOfInput > 0) { 
			binary[index++] = (byte) (copyOfInput % 2); 
			copyOfInput = copyOfInput / 2; 
		}
		return binary; 
	}
	
}
