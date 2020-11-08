package processor.pipeline;
import generic.Statistics;
import processor.Processor;

public class InstructionFetch {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	boolean conflict = false;
	boolean isEND = false;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void setEnableDisable()
	{
		IF_EnableLatch.setIF_enable(false);
		IF_OF_Latch.setOF_enable(true);
	}
	
	public void performIF()
	{
		if(IF_EnableLatch.isIF_enable() && !isEND)
		{
			if(EX_IF_Latch.IF_enable){
				if(EX_IF_Latch.isBranchTaken){
					containingProcessor.getRegisterFile().setProgramCounter(EX_IF_Latch.branchTarget);
				}
				EX_IF_Latch.IF_enable=false;
			}
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
			String instructionString = Integer.toBinaryString(newInstruction);
			String padding = "";
			for (int i=0;i<32-instructionString.length();i++){
				padding = padding + "0" ;
			}
			instructionString = padding + instructionString;
			if(!conflict)
			{
				if(instructionString.substring(0,5).equals("11101")) {
					isEND = true;
				}
				Statistics.setNumberOfInstructions(Statistics.numberOfInstructions+1);
				IF_OF_Latch.setInstruction(newInstruction);
				containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			}
			setEnableDisable();
		}
	}
}
