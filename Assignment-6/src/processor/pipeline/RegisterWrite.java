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
			
			if (opcode < 23) {
				if (opcode == 22) {
					containingProcessor.getRegisterFile().setValue(reg2, ldResult);
				}
				else if (opcode % 2 == 1) {
					containingProcessor.getRegisterFile().setValue(reg2, AR);
					if (((opcode == 7) || (opcode == 5) || (opcode == 1) || (opcode == 17) || (opcode == 19) || (opcode == 21)) && (remainder != 0)) {
						containingProcessor.getRegisterFile().setValue(31, remainder);
					}
				}
				else if (opcode % 2 == 0){
					containingProcessor.getRegisterFile().setValue(rd, AR);
					if (((opcode == 6) || (opcode == 4) || (opcode == 0) || (opcode == 16) || (opcode == 18) || (opcode == 20)) && (remainder != 0)) {
						containingProcessor.getRegisterFile().setValue(31, remainder);
					}
				}
			}
			if (opcode == 29) {
				Simulator.setSimulationComplete(true);
			}
			
			if (containingProcessor.getEX_MA().isMA_enable() == false) {
				MA_RW_Latch.setRW_enable(false);
			}
		}
	}
}
