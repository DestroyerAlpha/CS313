package processor.pipeline;

import processor.Processor;

public class OperandFetch{
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public static String trim(String s) {
		String t = null;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != '0') {
				t = s.substring(i);
				break;
			}
		}
		System.out.println(t);
		return t;
	}
		
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			if (OF_EX_Latch.isEX_busy()) {
				containingProcessor.getIF_Enable().setIF_enable(false);
//				IF_OF_Latch.setOF_busy(true);
//				IF_OF_Latch.setRdRW(IF_OF_Latch.getRdMA());
//				IF_OF_Latch.setx31RW(IF_OF_Latch.getx31MA());
//				IF_OF_Latch.setRdMA(IF_OF_Latch.getRdEX());
//				IF_OF_Latch.setx31MA(IF_OF_Latch.getx31EX());
//				IF_OF_Latch.setRdEX(-2);
//				IF_OF_Latch.setx31EX(-2);	
				return;
			}
			//TODO
//			System.out.print("RdEX: ");
//			System.out.print(IF_OF_Latch.getRdEX());
//			System.out.print("; RdMA: ");
//			System.out.print(IF_OF_Latch.getRdMA());
//			System.out.print("; RdRW: ");
//			System.out.print(IF_OF_Latch.getRdRW());
//			System.out.print("; x31EX: ");
//			System.out.print(IF_OF_Latch.getx31EX());
//			System.out.print("; x31MA: ");
//			System.out.print(IF_OF_Latch.getx31MA());
//			System.out.print("; x31RW: ");
//			System.out.println(IF_OF_Latch.getx31RW());
			
			int instruction = (IF_OF_Latch.getInstruction());

//			if ((instruction == 0)) {
//				return;
//			}

			String Ins = Integer.toBinaryString(instruction);
			
			while (Ins.length() != 32) {
				Ins = "0" + Ins;
			}

			
//			System.out.print("Instruction: ");
//			System.out.println(Ins);
			
			String opcode = Ins.substring(0, 5);
			String immediate = Ins.substring(15, 32);
			String branchTarget = Ins.substring(10, 32);
			String rs1 = Ins.substring(5, 10);
			String rs2 = Ins.substring(10, 15);
			String rd = Ins.substring(15, 20);
			
			while (branchTarget.length() < 32) {
				branchTarget = branchTarget.charAt(0) + branchTarget;
			}
			
			while (immediate.length() < 32
					) {
				immediate = immediate.charAt(0) + immediate;
			}
			
//			System.out.println(branchTarget);
//			System.out.println(immediate);
//			branchTarget = trim(branchTarget);
			
			int opc = Integer.parseUnsignedInt(opcode, 2);
			//String imme = (immediate);
			int imm = Integer.parseUnsignedInt(immediate, 2);
			long BranchTarget = Long.parseLong(branchTarget, 2);
			int register1 = Integer.parseUnsignedInt(rs1, 2);
			int register2 = Integer.parseUnsignedInt(rs2, 2);
			int registerd = Integer.parseUnsignedInt(rd, 2);

			int r1val = containingProcessor.getRegisterFile().getValue(register1);
			int r2val = containingProcessor.getRegisterFile().getValue(register2);
						
//			System.out.println("Opcode in OF Block: " + opc);

//			System.out.print("opc: ");
//			System.out.println(opc);
//			System.out.print("Target Branch: ");
//			System.out.println(BranchTarget);
//			System.out.print("Destination Register: ");
//			System.out.println(registerd);
//			System.out.print("Immediate: ");
//			System.out.println(imm);
//			System.out.print("Op1: ");
//			System.out.println(r1val);
//			System.out.print("Op2: ");
//			System.out.println(r2val);
//			System.out.print("Register 2: ");
//			System.out.println(register2);

			
			// -1 => Not Applicable
			// -2 => Non Existing
			// xD if you don't get the difference between the two
			int DestinationRegisterFinal = -1;
			int Destinationx31 = -1;
			int SourceRegister1 = -1;
			int SourceRegister2 = -1;
			
			if (opc < 22) {
				if (opc % 2 == 0) {
					DestinationRegisterFinal = registerd;
					SourceRegister1 = register1;
					SourceRegister2 = register2;
				}
				else {
					DestinationRegisterFinal = register2;
					SourceRegister1 = register1;
				}
			}
			else if (opc == 22) {
				DestinationRegisterFinal = register2;
				SourceRegister1 = register1;
			}
			
			else if (opc == 23) {
				SourceRegister1 = register1;
				SourceRegister2 = register2;
			}
			
			else if (opc == 24) {
				SourceRegister1 = register1;
			}
			
			else if (opc >= 25) {
				SourceRegister1 = register1;
				SourceRegister2 = register2;
			}
			
			if ((opc == 0) || (opc == 1) || ((opc <= 7) && (opc >= 4)) || ((opc >= 16) && (opc <= 21))) {
				Destinationx31 = 31;
			}

			
			OF_EX_Latch.setBranchTarget(BranchTarget);
			OF_EX_Latch.setImmediate(imm);
			OF_EX_Latch.setRd(registerd);
			OF_EX_Latch.setOpcode(opc);
			OF_EX_Latch.setop1(r1val);
			OF_EX_Latch.setop2(r2val);
			OF_EX_Latch.setReg2(register2);
			
			// Set if condition such that whenever the rd is being used further down the pipeline(next 3
			// latches), then we have to create a bubble and pass it along -- i.e. set the latch to false 
			// (only IF_EnableLatch)


//			boolean Flag = true;
			if ((SourceRegister1 == IF_OF_Latch.getRdEX()) || (SourceRegister1 == IF_OF_Latch.getRdMA()) || (SourceRegister1 == IF_OF_Latch.getRdRW()) || (SourceRegister2 == IF_OF_Latch.getRdEX()) || (SourceRegister2 == IF_OF_Latch.getRdMA()) || (SourceRegister2 == IF_OF_Latch.getRdRW()) || ((SourceRegister1 == IF_OF_Latch.getx31EX()) && (IF_OF_Latch.getx31EX() != -1)) || ((SourceRegister1 == IF_OF_Latch.getx31MA()) && (IF_OF_Latch.getx31MA() != -1)) || ((SourceRegister1 == IF_OF_Latch.getx31RW()) && (IF_OF_Latch.getx31RW() != -1)) || ((SourceRegister2 == IF_OF_Latch.getx31EX()) && (IF_OF_Latch.getx31EX() != -1)) || ((SourceRegister2 == IF_OF_Latch.getx31MA()) && (IF_OF_Latch.getx31MA() != -1)) || ((SourceRegister2 == IF_OF_Latch.getx31RW()) && (IF_OF_Latch.getx31RW() != -1))) {
				System.out.println("Clash. Bubble formed.");
				containingProcessor.getIF_Enable().setIF_enable(false);
				containingProcessor.getOF_EX().setEX_enable(false);
				if (containingProcessor.getEX_MA().isMA_busy()) {
					IF_OF_Latch.setRdRW(-2);
					IF_OF_Latch.setx31RW(-2);
//					System.out.println("MA Busy");
					IF_OF_Latch.setOF_busy(true);
					containingProcessor.setControlHazardFlag2(true);
//					containingProcessor.getIF_Enable().setIF_enable(false);
//					containingProcessor.getOF_EX().setEX_enable(false);
				}
				else {
//					System.out.println("MA Not Busy");
	//				containingProcessor.getEX_MA().setMA_enable(false);
	//				containingProcessor.getOF_EX().setEX_enable(false);
	//				containingProcessor.getMA_RW().setRW_enable(false);
	//				Flag = false;
					containingProcessor.setDataHazards(containingProcessor.getDataHazards() + 1);
					if(!containingProcessor.getMA_RW().isRW_enable() && !containingProcessor.getEX_MA().isMA_enable())
					{
						IF_OF_Latch.setRdRW(IF_OF_Latch.getRdEX());
						IF_OF_Latch.setx31RW(IF_OF_Latch.getx31EX());
						IF_OF_Latch.setRdMA(-2);
						IF_OF_Latch.setx31MA(-2);
						IF_OF_Latch.setRdEX(-2);
						IF_OF_Latch.setx31EX(-2);
					}
					else
					{
						IF_OF_Latch.setRdRW(IF_OF_Latch.getRdMA());
						IF_OF_Latch.setx31RW(IF_OF_Latch.getx31MA());
						IF_OF_Latch.setRdMA(IF_OF_Latch.getRdEX());
						IF_OF_Latch.setx31MA(IF_OF_Latch.getx31EX());
						IF_OF_Latch.setRdEX(-2);
						IF_OF_Latch.setx31EX(-2);
					}
//					if(IF_OF_Latch.getRdEX() == -2 && IF_OF_Latch.getRdMA() == -2 && IF_OF_Latch.getRdRW() == -2 && IF_OF_Latch.getx31EX() == -2 && IF_OF_Latch.getx31MA() == -2 && IF_OF_Latch.getx31RW() == -2)
//					{
//						if ((DestinationRegisterFinal > 0)) {// && (Flag == false)) {
//							IF_OF_Latch.setx31EX(Destinationx31);
//							IF_OF_Latch.setRdEX(DestinationRegisterFinal);		
//						}
//						else {
//							IF_OF_Latch.setx31EX(-2);
//							IF_OF_Latch.setRdEX(-2);				
//						}
//					OF_EX_Latch.setEX_enable(true);
//					}
				}
			}
			else {
//				IF_OF_Latch.setOF_enable(false);
//				System.out.println("NoCLASH");
				if(!containingProcessor.getIF_Enable().isIF_busy())
				{
					containingProcessor.getIF_Enable().setIF_enable(true);
					OF_EX_Latch.setEX_enable(true);
				}
				if (containingProcessor.getEX_MA().isMA_busy()) {
					if (instruction != 0) {
						if ((DestinationRegisterFinal > 0)) {// && (Flag == false)) {
							IF_OF_Latch.setx31EX(Destinationx31);
							IF_OF_Latch.setRdEX(DestinationRegisterFinal);		
						}
						else {
							IF_OF_Latch.setx31EX(-2);
							IF_OF_Latch.setRdEX(-2);				
						}
					}
					IF_OF_Latch.setRdRW(-2);
					IF_OF_Latch.setx31RW(-2);	
//					System.out.println("MA Busy");
				}
				else if (containingProcessor.getIF_Enable().isIF_busy() == false) {
//					System.out.println("IF not busy");
//					containingProcessor.getIF_Enable().setIF_enable(true);
//					OF_EX_Latch.setEX_enable(true);
					IF_OF_Latch.setRdRW(IF_OF_Latch.getRdMA());
					IF_OF_Latch.setx31RW(IF_OF_Latch.getx31MA());
					IF_OF_Latch.setRdMA(IF_OF_Latch.getRdEX());
					IF_OF_Latch.setx31MA(IF_OF_Latch.getx31EX());
					if ((DestinationRegisterFinal > 0)) {// && (Flag == false)) {
						IF_OF_Latch.setx31EX(Destinationx31);
						IF_OF_Latch.setRdEX(DestinationRegisterFinal);		
					}
					else {
						IF_OF_Latch.setx31EX(-2);
						IF_OF_Latch.setRdEX(-2);				
					}
				}
				else {
//					System.out.println("MA Not Busy and IF busy");
//					containingProcessor.getIF_Enable().setIF_enable(true);
//					OF_EX_Latch.setEX_enable(true);
					IF_OF_Latch.setRdRW(IF_OF_Latch.getRdMA());
					IF_OF_Latch.setx31RW(IF_OF_Latch.getx31MA());
					IF_OF_Latch.setRdMA(IF_OF_Latch.getRdEX());
					IF_OF_Latch.setx31MA(IF_OF_Latch.getx31EX());
					IF_OF_Latch.setRdEX(-2);
					IF_OF_Latch.setx31EX(-2);	
					if(IF_OF_Latch.getRdEX() == -2 && IF_OF_Latch.getRdMA() == -2 && IF_OF_Latch.getRdRW() == -2 && IF_OF_Latch.getx31EX() == -2 && IF_OF_Latch.getx31MA() == -2 && IF_OF_Latch.getx31RW() == -2)
					{
						if ((DestinationRegisterFinal > 0)) {// && (Flag == false)) {
							IF_OF_Latch.setx31EX(Destinationx31);
							IF_OF_Latch.setRdEX(DestinationRegisterFinal);		
						}
						else {
							IF_OF_Latch.setx31EX(-2);
							IF_OF_Latch.setRdEX(-2);				
						}
					}
					
				}
				if(IF_OF_Latch.getInstruction() != 0)
				{
					OF_EX_Latch.setEX_enable(true);
//					
				}
				IF_OF_Latch.setInstruction(0);
			}			
//			if (containingProcessor.getMA_RW().isRW_enable()) {
//				IF_OF_Latch.setRdRW(IF_OF_Latch.getRdMA());
//			}
//			else {
//				IF_OF_Latch.setRdRW(0);				
//			}
//			if (containingProcessor.getEX_MA().isMA_enable()) {
//				IF_OF_Latch.setRdMA(IF_OF_Latch.getRdEX());
//			}
//			else {
//				IF_OF_Latch.setRdMA(0);				
//			}
//			if (containingProcessor.getOF_EX().isEX_enable()) {
//				IF_OF_Latch.setRdEX(DestinationRegisterFinal);
//			}
//			else {
//				IF_OF_Latch.setRdEX(0);				
//			}
			
//			System.out.print("Destination Register Final: ");
//			System.out.print(DestinationRegisterFinal);
//			System.out.print("; Source Register 1: ");
//			System.out.print(SourceRegister1);
//			System.out.print("; Source Register 2: ");
//			System.out.print(SourceRegister2);
//			System.out.print("; x31 Destination: ");
//			System.out.print(Destinationx31);
//			System.out.print("; RdEX: ");
//			System.out.print(IF_OF_Latch.getRdEX());
//			System.out.print("; RdMA: ");
//			System.out.print(IF_OF_Latch.getRdMA());
//			System.out.print("; RdRW: ");
//			System.out.print(IF_OF_Latch.getRdRW());
//			System.out.print("; x31EX: ");
//			System.out.print(IF_OF_Latch.getx31EX());
//			System.out.print("; x31MA: ");
//			System.out.print(IF_OF_Latch.getx31MA());
//			System.out.print("; x31RW: ");
//			System.out.println(IF_OF_Latch.getx31RW());
		}
		else {
			if (!containingProcessor.getEX_MA().isMA_busy()) {
//				System.out.println("OF Not enabled, MA not Busy");
				IF_OF_Latch.setRdRW(IF_OF_Latch.getRdMA());
				IF_OF_Latch.setx31RW(IF_OF_Latch.getx31MA());
				IF_OF_Latch.setRdMA(IF_OF_Latch.getRdEX());
				IF_OF_Latch.setx31MA(IF_OF_Latch.getx31EX());
				IF_OF_Latch.setRdEX(-2);
				IF_OF_Latch.setx31EX(-2);	
			}
		}
	}
}
