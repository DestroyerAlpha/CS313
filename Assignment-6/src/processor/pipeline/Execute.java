package processor.pipeline;

import configuration.Configuration;
import generic.Simulator;
import processor.Processor;

public class Execute{
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performEX()
	{
		//TODO
		if (OF_EX_Latch.isEX_enable()) 
		{
			if (EX_MA_Latch.isMA_busy()) {
				OF_EX_Latch.setEX_busy(true);
				return;
			}
			if (containingProcessor.getControlHazardFlag2()) {
				containingProcessor.setControlHazardFlag2(false);
				return;
			}

			int opcode = OF_EX_Latch.getOpcode();
			int op1 = OF_EX_Latch.getop1();
			int op2 = OF_EX_Latch.getop2();
			int imm = OF_EX_Latch.getImmediate();
			int rd = OF_EX_Latch.getrd();
			long branchTarget = OF_EX_Latch.getBranchTarget();
			int reg2 = OF_EX_Latch.getreg2();
			int aluresult = -1;
			long AR = 0;
			int remainder = -1;
			
//			System.out.println("Opcode in Execute Block: " + opcode);
			
			//add
			if (opcode == 0){
				AR = (long) op1 + (long) op2;
				String s = Long.toBinaryString(AR);
//				System.out.println(s);
				while (s.length() < 63) {
					s = "0" + s;
				}
				String t = s.substring(0, 32);
				String u = s.substring(32, 63);
				remainder = Integer.parseUnsignedInt(t, 2);
				aluresult = Integer.parseUnsignedInt(u, 2);
			}
			//addi
			else if (opcode == 1){
				AR = (long) op1 + (long) imm;
				String s = Long.toBinaryString(AR);
//				System.out.println(s);
				while (s.length() < 63) {
					s = "0" + s;
				}
				String t = s.substring(0, 32);
				String u = s.substring(32, 63);
				remainder = Integer.parseUnsignedInt(t, 2);
				aluresult = Integer.parseUnsignedInt(u, 2);
			}
			//sub
			else if (opcode == 2){
				aluresult = op1 - op2;			
			}
			//subi
			else if (opcode == 3){
				aluresult = op1 - imm;			
			}
			//mul
			else if (opcode == 4){
				//aluresult = op1 * op2;
				AR = (long) op1 * (long) op2;
				String s = Long.toBinaryString(AR);
//				System.out.println(s);
				while (s.length() < 63) {
					s = "0" + s;
				}
				String t = s.substring(0, 32);
				String u = s.substring(32, 63);
				remainder = Integer.parseUnsignedInt(t, 2);
				aluresult = Integer.parseUnsignedInt(u, 2);
			}
			//muli
			else if (opcode == 5){
				AR = (long) op1 * (long) imm;
				String s = Long.toBinaryString(AR);
//				System.out.println(s);
				while (s.length() < 63) {
					s = "0" + s;
				}
				String t = s.substring(0, 32);
				String u = s.substring(32, 63);
				remainder = Integer.parseUnsignedInt(t, 2);
				aluresult = Integer.parseUnsignedInt(u, 2);
			}
			//div
			else if (opcode == 6){
				aluresult = op1 / op2;	
				remainder = op1 % op2;
			}
			//divi
			else if (opcode == 7){
				System.out.println("DIVI");
				aluresult = op1 / imm;
				remainder = op1 % imm;
			}
			//and
			else if (opcode == 8){
				aluresult = op1 & op2;			
			}
			//andi
			else if (opcode == 9){
				aluresult = op1 & imm;			
			}
			//or
			else if (opcode == 10){
				aluresult = op1 | op2;			
			}
			//ori
			else if (opcode == 11){
				aluresult = op1 | imm;			
			}
			//xor
			else if (opcode == 12){
				aluresult = op1 ^ op2;			
			}
			//xori
			else if (opcode == 13){
				aluresult = op1 ^ imm;			
			}
			//slt
			else if (opcode == 14){
				if (op1<op2) {
				    aluresult = 1;
				}
				else {
					aluresult = 0;
				}
			}
			//slti
			else if (opcode == 15){
				if (op1<imm) {
				    aluresult = 1;
				}
				else {
					aluresult = 0;
				}			
			}
			//sll
			else if (opcode == 16){
				String s = Integer.toBinaryString(op1);
				while (s.length() < 32) {
					s = "0" + s;
				}
				remainder = Integer.parseUnsignedInt(s.substring(0, op2));
				aluresult = op1<<op2;
			}
			//slli
			else if (opcode == 17){
				String s = Integer.toBinaryString(op1);
				while (s.length() < 32) {
					s = "0" + s;
				}
				remainder = Integer.parseUnsignedInt(s.substring(0, imm));
				aluresult = op1<<imm;			
			}
			//srl
			else if (opcode == 18){
				String s = Integer.toBinaryString(op1);
				while (s.length() < 32) {
					s = "0" + s;
				}
//				System.out.println(s.substring(s.length() - op2));
				remainder = Integer.parseUnsignedInt(s.substring(s.length() - op2));
				aluresult = op1>>op2;			
			}
			//srli
			else if (opcode == 19){
				String s = Integer.toBinaryString(op1);
				while (s.length() < 32) {
					s = "0" + s;
				}
				remainder = Integer.parseUnsignedInt(s.substring(s.length() - imm));
				aluresult = op1>>imm;			
			}
			//sra
			else if (opcode == 20){
				String s = Integer.toBinaryString(op1);
				while (s.length() < 32) {
					s = "0" + s;
				}
//				System.out.println(s.substring(s.length() - op2));
				remainder = Integer.parseUnsignedInt(s.substring(s.length() - op2));
				aluresult = op1>>>op2;			
			}
			//srai
			else if (opcode == 21){
				String s = Integer.toBinaryString(op1);
				while (s.length() < 32) {
					s = "0" + s;
				}
				remainder = Integer.parseUnsignedInt(s.substring(s.length() - imm));
				aluresult = op1>>>imm;			
			}
			//load
			else if (opcode == 22){
				aluresult = op1 + imm;			
			}
			//store
			else if (opcode == 23){
//				System.out.println("op2: " + op2 + "; imm: " + imm);
				aluresult = op2 + imm;			
			}
			//jump
			else if (opcode == 24){
				int PC = containingProcessor.getRegisterFile().getProgramCounter();
//				System.out.println((int) branchTarget);
				if(containingProcessor.getIF_Enable().isIF_busy())
				{
					containingProcessor.getRegisterFile().setProgramCounter((int) (PC + branchTarget - 2)); //update PC	
				}
				else
				{
					containingProcessor.getRegisterFile().setProgramCounter((int) (PC + branchTarget - 3)); //update PC	
				}
//				System.out.println("PC: " + ((int) PC + branchTarget - 2));
				containingProcessor.getIF_OF().setOF_enable(false);
				containingProcessor.getIF_OF().setRdRW(containingProcessor.getIF_OF().getRdEX());
				containingProcessor.getIF_OF().setx31RW(containingProcessor.getIF_OF().getx31EX());
				containingProcessor.getIF_OF().setRdMA(-2);
				containingProcessor.getIF_OF().setx31MA(-2);
				containingProcessor.getIF_OF().setRdEX(-2);
				containingProcessor.getIF_OF().setx31EX(-2);
				containingProcessor.getOF_EX().setEX_enable(false);
//				containingProcessor.getIF_Enable().setIF_enable(false);
				containingProcessor.setIF_Flag(true);				
				containingProcessor.setControlHazardFlag(true);
				containingProcessor.setControlHazards(containingProcessor.getControlHazards() + 1);
//				System.out.println("Control Hazard Here. ");
			}
			//beq
			else if (opcode == 25){
				if (op1==op2) {
					int PC = containingProcessor.getRegisterFile().getProgramCounter(); //update PC			
					PC = PC + imm - 2;
//					System.out.println("PC: " + PC);
					if(containingProcessor.getIF_Enable().isIF_busy())
					{
						containingProcessor.getRegisterFile().setProgramCounter(PC); //update PC			
					}
					else
					{
						containingProcessor.getRegisterFile().setProgramCounter(PC - 1); //update PC			
					}
//					containingProcessor.getRegisterFile().setProgramCounter(PC); //update PC			
					containingProcessor.getIF_OF().setOF_enable(false);
					containingProcessor.getIF_OF().setRdRW(containingProcessor.getIF_OF().getRdEX());
					containingProcessor.getIF_OF().setx31RW(containingProcessor.getIF_OF().getx31EX());
					containingProcessor.getIF_OF().setRdMA(-2);
					containingProcessor.getIF_OF().setx31MA(-2);
					containingProcessor.getIF_OF().setRdEX(-2);
					containingProcessor.getIF_OF().setx31EX(-2);
					containingProcessor.getOF_EX().setEX_enable(false);
//					containingProcessor.getIF_Enable().setIF_enable(false);
					containingProcessor.setIF_Flag(true);
					containingProcessor.setControlHazardFlag(true);
					containingProcessor.setControlHazards(containingProcessor.getControlHazards() + 1);
//					System.out.println("Control Hazard Here. ");
				}
			}
			//bne
			else if (opcode == 26){
				if (op1!=op2) {
					int PC = containingProcessor.getRegisterFile().getProgramCounter(); //update PC			
					PC = PC + imm - 2;
//					System.out.println("PC: " + PC);
					if(containingProcessor.getIF_Enable().isIF_busy())
					{
						containingProcessor.getRegisterFile().setProgramCounter(PC); //update PC			
					}
					else
					{
						containingProcessor.getRegisterFile().setProgramCounter(PC - 1); //update PC			
					}
//					containingProcessor.getRegisterFile().setProgramCounter(PC); //update PC			
					containingProcessor.getIF_OF().setOF_enable(false);
					containingProcessor.getIF_OF().setRdRW(containingProcessor.getIF_OF().getRdEX());
					containingProcessor.getIF_OF().setx31RW(containingProcessor.getIF_OF().getx31EX());
					containingProcessor.getIF_OF().setRdMA(-2);
					containingProcessor.getIF_OF().setx31MA(-2);
					containingProcessor.getIF_OF().setRdEX(-2);
					containingProcessor.getIF_OF().setx31EX(-2);
					containingProcessor.getOF_EX().setEX_enable(false);
//					containingProcessor.getIF_Enable().setIF_enable(false);
					containingProcessor.setIF_Flag(true);
					containingProcessor.setControlHazardFlag(true);
					containingProcessor.setControlHazards(containingProcessor.getControlHazards() + 1);
//					System.out.println("Control Hazard Here. ");
				}			
			}
			//blt
			else if (opcode == 27){
				if (op1<op2) {
					System.out.println("BLT");
					int PC = containingProcessor.getRegisterFile().getProgramCounter(); //update PC			
					PC = PC + imm - 2;
					System.out.println("PC (What?): " + PC);
					System.out.println("Immediate: " + imm);
					if(containingProcessor.getIF_Enable().isIF_busy()) //  || Configuration.mainMemoryLatency == 1
					{
						containingProcessor.getRegisterFile().setProgramCounter(PC); //update PC			
					}
					else
					{
						containingProcessor.getRegisterFile().setProgramCounter(PC - 1); //update PC			
					}
					containingProcessor.getIF_OF().setOF_enable(false);
					containingProcessor.getIF_OF().setRdRW(containingProcessor.getIF_OF().getRdEX());
					containingProcessor.getIF_OF().setx31RW(containingProcessor.getIF_OF().getx31EX());
					containingProcessor.getIF_OF().setRdMA(-2);
					containingProcessor.getIF_OF().setx31MA(-2);
					containingProcessor.getIF_OF().setRdEX(-2);
					containingProcessor.getIF_OF().setx31EX(-2);
					containingProcessor.getOF_EX().setEX_enable(false);
//					containingProcessor.getIF_Enable().setIF_enable(false);
					containingProcessor.setIF_Flag(true);
					containingProcessor.setControlHazardFlag(true);
					containingProcessor.setControlHazards(containingProcessor.getControlHazards() + 1);
//					System.out.println("Control Hazard Here. ");
				}	
			}
			//bgt
			else if (opcode == 28){
				if (op1>op2) {
					System.out.println("BGT");
					int PC = containingProcessor.getRegisterFile().getProgramCounter(); //update PC			
					PC = PC + imm - 2;
					System.out.println("PC (What?): " + PC);
					System.out.println("Immediate: " + imm);
					System.out.println("IF Busy???: " + containingProcessor.getIF_Enable().isIF_busy());
					if(containingProcessor.getIF_Enable().isIF_busy()|| Configuration.mainMemoryLatency > 3) //  
					{
						containingProcessor.getRegisterFile().setProgramCounter(PC); //update PC			
					}
					else
					{
						containingProcessor.getRegisterFile().setProgramCounter(PC - 1); //update PC			
					}
//					containingProcessor.getRegisterFile().setProgramCounter(PC); //update PC			
					containingProcessor.getIF_OF().setOF_enable(false);
					containingProcessor.getIF_OF().setRdRW(containingProcessor.getIF_OF().getRdEX());
					containingProcessor.getIF_OF().setx31RW(containingProcessor.getIF_OF().getx31EX());
					containingProcessor.getIF_OF().setRdMA(-2);
					containingProcessor.getIF_OF().setx31MA(-2);
					containingProcessor.getIF_OF().setRdEX(-2);
					containingProcessor.getIF_OF().setx31EX(-2);
					containingProcessor.getOF_EX().setEX_enable(false);
//					containingProcessor.getIF_Enable().setIF_enable(false);
					containingProcessor.setIF_Flag(true);
					containingProcessor.setControlHazardFlag(true);
					containingProcessor.setControlHazards(containingProcessor.getControlHazards() + 1);
//					System.out.println("Control Hazard Here. ");
				}	
			}
			else if (opcode == 29) {
//				Simulator.setSimulationComplete(true);
				System.out.println("End");
				containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getRegisterFile().getProgramCounter() - 1);
				containingProcessor.getRegisterFile().setLock(true);
				containingProcessor.setControlHazards(containingProcessor.getControlHazards() * 2);
				containingProcessor.setHazardLock(true);
//				System.out.println("Data Hazards: " + containingProcessor.getDataHazards());
//				System.out.println("Control Hazards: " + containingProcessor.getControlHazards());				
			}
			EX_MA_Latch.setALUResult(aluresult);
			EX_MA_Latch.setop1(op1);
			EX_MA_Latch.setOpcode(opcode);
			EX_MA_Latch.setrd(rd);
			EX_MA_Latch.setreg2(reg2);
			EX_MA_Latch.setremainder(remainder);

//			System.out.println(EX_MA_Latch.getALUResult());
//			System.out.println(EX_MA_Latch.getremainder());
			
			// In case of a jump / branch (control hazard) instruction, we have to create a reset function 
			// which either resets the values of the latches, or stops OF and EX from working and let IF work
			// again so that it overwrites the values of IF_OF_Latch and then enable OF also so that it
			// overwrites the values of OF_EX_Latch i.e. control hazard avoided.
			
			// If the instruction does branch, then the opening 3 phases are set to false with the setting
			// such that the IF_Latch becomes true immediately after that cycle is over (because it happens
			// simultaneously in real life, but not here).
			
			OF_EX_Latch.setEX_enable(false);
			EX_MA_Latch.setMA_enable(true);
		}
	}
}
