package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	ControlUnit cu = new ControlUnit();
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performOperations()
	{
		IF_EnableLatch.setIF_enable(true);
		cu.opcode="";
		cu.rs1="";
		cu.rs2="";
		cu.rd="";
		cu.Imm = "";
	}
	
	public void setEnableDisable()
	{
		MA_RW_Latch.setRW_enable(false);
		IF_EnableLatch.setIF_enable(true);
	}
	
	public void setEnableDisable1()
	{
		Simulator.setSimulationComplete(true);
		MA_RW_Latch.setRW_enable(false);
		IF_EnableLatch.setIF_enable(false);
	}
	
	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{
			int instruction = MA_RW_Latch.getInstruction();
			cu.setInstruction(instruction);
			if(cu.opcode.equals("11101"))
				setEnableDisable1();
			else 
			{
				int result;
				switch(cu.opcode)
				{
					case "10110":
						result = MA_RW_Latch.getLoadResult();
						break;
					default:
						result = MA_RW_Latch.getFinal_result();
						break;
				}
				int rd = MA_RW_Latch.getrd();
				if(cu.isRegisterWriteBack())
				{
					containingProcessor.getRegisterFile().setValue(rd, result);
				}
				
				setEnableDisable();

			}

		}
		else 
			performOperations();
	}

}
