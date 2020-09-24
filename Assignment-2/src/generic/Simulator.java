package generic;

import java.io.*;
import java.util.*;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


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
		try {
			FileOutputStream fos = new FileOutputStream(objectProgramFile);
		
		
		DataOutputStream dos = new DataOutputStream(fos);
		dos.writeInt(ParsedProgram.symtab.get("main"));
		for(int i=0;i<ParsedProgram.data.size();i++)
		{
			dos.writeInt(ParsedProgram.data.get(i));
		}
		for(int i=ParsedProgram.symtab.get("main");i<ParsedProgram.code.size()+ParsedProgram.symtab.get("main");i++)
		{
			// System.out.println(ParsedProgram.getInstructionAt(i).getProgramCounter());
			String instruction = Integer.toBinaryString(instruction_code(ParsedProgram.getInstructionAt(i).getOperationType()));
			instruction = String.format("%5s", instruction).replaceAll(" ", "0");

			switch(ParsedProgram.getInstructionAt(i).getOperationType())
			{
			//R3I type
			case add : 
			case sub : 
			case mul : 
			case div : 
			case and : 
			case or : 
			case xor : 
			case slt : 
			case sll : 
			case srl : 
			case sra :	{
							String op = Integer.toBinaryString(ParsedProgram.getInstructionAt(i).getSourceOperand1().getValue());
							op = String.format("%5s", op).replaceAll(" ", "0");
							instruction += op;	//rs1 bits
							
							op = Integer.toBinaryString(ParsedProgram.getInstructionAt(i).getSourceOperand2().getValue());
							op = String.format("%5s", op).replaceAll(" ", "0");
							instruction += op;	//rs2 bits

							op = Integer.toBinaryString(ParsedProgram.getInstructionAt(i).getDestinationOperand().getValue());
							op = String.format("%5s", op).replaceAll(" ", "0");
							instruction += op;	//rd bits

							instruction += "000000000000";	//unused bits in machine code is set of all zeros :)
							break;
						} 
			//R2I type
			case addi :
			case subi :
			case muli :
			case divi : 
			case andi : 
			case ori : 
			case xori : 
			case slti : 
			case slli : 
			case srli : 
			case srai :
			case load :
			case store :	{
								String op = Integer.toBinaryString(ParsedProgram.getInstructionAt(i).getSourceOperand1().getValue());
								op = String.format("%5s", op).replaceAll(" ", "0");
								// System.out.println(op);
								instruction += op;	//rs1 bits

								op = Integer.toBinaryString(ParsedProgram.getInstructionAt(i).getDestinationOperand().getValue());
								op = String.format("%5s", op).replaceAll(" ", "0");
								// System.out.println(op);
								instruction += op;	//rd bits

								if(ParsedProgram.getInstructionAt(i).getSourceOperand2().operandType==Operand.OperandType.Immediate)
								{
									// System.out.println("I am in immediate");
									op = Integer.toBinaryString(ParsedProgram.getInstructionAt(i).getSourceOperand2().getValue());
									op = String.format("%17s", op).replaceAll(" ", "0");
									// System.out.println(op);
									instruction += op;	//absolute immediate
								}
								else
								{
									// System.out.println("I am not in immediate");
									op = Integer.toBinaryString(ParsedProgram.symtab.get(ParsedProgram.getInstructionAt(i).getSourceOperand2().getLabelValue()));
									op = String.format("%17s", op).replaceAll(" ", "0");
									// System.out.println(op);
									instruction += op;	//label / symbol
								}

								break;
							} 
			
			case beq : 
			case bne : 
			case blt : 
			case bgt : 	{
							String op = Integer.toBinaryString(ParsedProgram.getInstructionAt(i).getSourceOperand1().getValue());
							op = String.format("%5s", op).replaceAll(" ", "0");
							instruction += op;	//rs1 bits
							// System.out.println(op);

							op = Integer.toBinaryString(ParsedProgram.getInstructionAt(i).getSourceOperand2().getValue());
							op = String.format("%5s", op).replaceAll(" ", "0");
							instruction += op;	//rd bits
							// System.out.println(op);

							if(ParsedProgram.getInstructionAt(i).getDestinationOperand().operandType==Operand.OperandType.Immediate)
							{
								// System.out.println("In immediate");
								op = Integer.toBinaryString(ParsedProgram.getInstructionAt(i).getDestinationOperand().getValue());
								if(op.length()>17)
								{
									op = op.substring(op.length()-17);
								}
								else
									op = String.format("%17s", op).replaceAll(" ", "0");
								instruction += op;	//absolute immediate
								System.out.println(op);
							}
							else
							{
								// System.out.println("Not in immedidate");
								op = Integer.toBinaryString(- ParsedProgram.getInstructionAt(i).getProgramCounter() + ParsedProgram.symtab.get(ParsedProgram.getInstructionAt(i).getDestinationOperand().getLabelValue()));
								if(op.length()>17)
								{
									op = op.substring(op.length()-17);
								}
								else
									op = String.format("%17s", op).replaceAll(" ", "0");
								instruction += op;	//label / symbol
								// System.out.println(op);
							}

							break;
						}
			
			//RI type :
			case jmp :		{
								if(ParsedProgram.getInstructionAt(i).getDestinationOperand().operandType==Operand.OperandType.Register)
								{
									String op = Integer.toBinaryString(ParsedProgram.getInstructionAt(i).getDestinationOperand().getValue());
									op = String.format("%5s", op).replaceAll(" ", "0");
									instruction += op;	//rd bits

									instruction += "0000000000000000000000";
								}
								else if(ParsedProgram.getInstructionAt(i).getDestinationOperand().operandType==Operand.OperandType.Label)
								{
									instruction += "00000";
									// System.out.println("i am in label");
									// String op = Integer.toBinaryString(ParsedProgram.symtab.get(ParsedProgram.getInstructionAt(i).getDestinationOperand().getLabelValue()));
									String op = Integer.toBinaryString(- ParsedProgram.getInstructionAt(i).getProgramCounter() + ParsedProgram.symtab.get(ParsedProgram.getInstructionAt(i).getDestinationOperand().getLabelValue()));
									if(op.length()>22)
									{
										op = op.substring(op.length()-22);
									}
									else
										op = String.format("%22s", op).replaceAll(" ", "0");
									instruction += op;	//imm
								}
								else if(ParsedProgram.getInstructionAt(i).getDestinationOperand().operandType==Operand.OperandType.Immediate)
								{
									instruction += "00000";

									String op = Integer.toBinaryString(ParsedProgram.getInstructionAt(i).getDestinationOperand().getValue());
									if(op.length()>22)
									{
										op = op.substring(op.length()-22);
									}
									else
										op = String.format("%22s", op).replaceAll(" ", "0");
									instruction += op;	//label / symbol
								}

								break;
							}
			
			case end :
						{
							instruction += "000000000000000000000000000";
							break;
						}
			}
			// int flag = 0;
			// System.out.println(instruction);
			// if(instruction.charAt(0) == '1')
			// {
			// 	instruction = '-' + instruction.substring(1);
			// 	flag = 1;
			// }
			// System.out.println();
			// String result = binaryUnicodeToString(instruction);
			// System.out.println(result.trim());
			int foo = new BigInteger(instruction, 2).intValue();
			// if(flag == 0)
			// 	dos.writeInt(Integer.parseInt(instruction, 2));
			// else
			// 	dos.writeInt(Integer.parseInt(instruction, 2)-1);
			dos.writeInt(foo);
		}
		dos.close();
	} catch (Exception e) {
		System.out.println(e);
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

	public static String binaryUnicodeToString(String binary) 
	{
        byte[] array = ByteBuffer.allocate(4).putInt(
                Integer.parseInt(binary, 2)
        ).array();

		String s = new String(array, StandardCharsets.UTF_8);
        return s;
    }
	
}
