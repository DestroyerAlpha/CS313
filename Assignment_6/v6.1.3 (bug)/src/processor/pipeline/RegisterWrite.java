package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite{
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{
			if (MA_RW_Latch.isRW_busy()) {
				return;
			}
			//TODO
			int opcode = MA_RW_Latch.getOpcode();
			int AR = MA_RW_Latch.getALUResult();
			int reg2 = MA_RW_Latch.getreg2();
			int rd = MA_RW_Latch.getrd();
			int ldResult = MA_RW_Latch.getldResult();
			int remainder = MA_RW_Latch.getremainder();			
//			System.out.print("Opcode Result: ");
//			System.out.println(opcode);			
//			System.out.print("ALU Result: ");
//			System.out.println(AR);
//			System.out.print("Load Result: ");
//			System.out.println(ldResult);
			
			if (opcode < 23) {
				if (opcode == 22) {
					containingProcessor.getRegisterFile().setValue(reg2, ldResult);
//					System.out.println("Writing " + ldResult + " to " + reg2);
				}
				else if (opcode % 2 == 1) {
					containingProcessor.getRegisterFile().setValue(reg2, AR);
//					System.out.println("Writing " + AR + " to " + reg2);
					if (((opcode == 7) || (opcode == 5) || (opcode == 1) || (opcode == 17) || (opcode == 19) || (opcode == 21)) && (remainder != 0)) {
//						System.out.print("Remainder2: ");
//						System.out.println(remainder);
						containingProcessor.getRegisterFile().setValue(31, remainder);
					}
				}
				else if (opcode % 2 == 0){
					containingProcessor.getRegisterFile().setValue(rd, AR);
//					System.out.println("Writing " + AR + " to " + rd);
					if (((opcode == 6) || (opcode == 4) || (opcode == 0) || (opcode == 16) || (opcode == 18) || (opcode == 20)) && (remainder != 0)) {
						containingProcessor.getRegisterFile().setValue(31, remainder);
					}
				}
			}
			
			if ((opcode > 23) && (opcode < 29)) {
//				containingProcessor.getIF_Enable().setIF_enable(true);
				System.out.println(opcode);
//				containingProcessor.setControlHazardFlag(false);
			}
			
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			if (opcode == 29) {
				Simulator.setSimulationComplete(true);
//				System.out.println("SIMULATION COMPLETE!");	
//				System.out.println(processor.Clock.getCurrentTime());
			}
			
			if (containingProcessor.getEX_MA().isMA_enable() == false) {
				MA_RW_Latch.setRW_enable(false);
			}
			
//			MA_RW_Latch.setRW_enable(false);
//			IF_EnableLatch.setIF_enable(true);
		}
	}
}
