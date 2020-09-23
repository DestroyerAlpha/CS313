package generic;
import java.util.ArrayList;
import java.io.*;
import java.io.FileInputStream;


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
		int stack_top = 65535;
		System.out.println(ParsedProgram.symtab.get("main"));
		for(int i=0;i<ParsedProgram.data.size();i++)
		{
			System.out.println(ParsedProgram.data.get(i));
		}
		for(int i=ParsedProgram.symtab.get("main");i<ParsedProgram.code.size()+ParsedProgram.symtab.get("main");i++)
		{
			// System.out.println(ParsedProgram.getInstructionAt(i).getProgramCounter());
			String instruction = Integer.toBinaryString(instruction_code(ParsedProgram.getInstructionAt(i).getOperationType()));
			instruction = String.format("%5s", instruction).replaceAll(" ", "0");
			if(ParsedProgram.getInstructionAt(i).getSourceOperand1()!=null)
			{
				if(ParsedProgram.getInstructionAt(i).getSourceOperand1().operandType==Operand.OperandType.Register)
				{
					String op = Integer.toBinaryString(ParsedProgram.getInstructionAt(i).getSourceOperand1().getValue());
					op = String.format("%5s", op).replaceAll(" ", "0");
					instruction += op;
				}
				else if(ParsedProgram.getInstructionAt(i).getSourceOperand1().operandType==Operand.OperandType.Label)
				{
					int addr = stack_top - ParsedProgram.symtab.get(ParsedProgram.getInstructionAt(i).getSourceOperand1().getLabelValue());

				}
				
			}
			else
			{
				instruction += "00000";
			}
			if(ParsedProgram.getInstructionAt(i).getSourceOperand2()!=null)
			{
				String op = Integer.toBinaryString(ParsedProgram.getInstructionAt(i).getSourceOperand2().getValue());
				op = String.format("%5s", op).replaceAll(" ", "0");
				instruction += op;
			}
			else
			{
				instruction += "00000";
			}
			System.out.println(ParsedProgram.getInstructionAt(i).getDestinationOperand().getLabelValue());
		}
		// System.out.println(ParsedProgram.symtab.get("loop"));
		
		
		//TODO your assembler code
		//1. open the objectProgramFile in binary mode
		//2. write the firstCodeAddress to the file
		//3. write the data to the file
		//4. assemble one instruction at a time, and write to the file
		//5. close the file
	}

	public static int instruction_code(Instruction.OperationType in)
	{
		ArrayList<Instruction.OperationType> ins = new ArrayList<Instruction.OperationType>();
		for(Instruction.OperationType o: Instruction.OperationType.values())
		{
			ins.add(o);
		}
		for(int i=0;i<ins.size();i++)
		{
			if(ins.get(i)==in)
			{
				return i;
			}
		}
		return -1;
	}
	public static byte[] toBinary5bit(int number) { 
		byte[] binary = new byte[5]; 
		int index = 0; 
		int copyOfInput = number; 
		while (copyOfInput > 0) { 
			binary[index++] = (byte) (copyOfInput % 2); 
			copyOfInput = copyOfInput / 2; 
		}
		return binary; 
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
