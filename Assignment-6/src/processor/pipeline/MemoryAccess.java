package processor.pipeline;

import processor.Clock;
import processor.Processor;
import configuration.Configuration;
import generic.Element;
import generic.Event;
import generic.Event.EventType;
import generic.ExecutionCompleteEvent;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;
import generic.Simulator;

public class MemoryAccess implements Element{
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performMA()
	{
		//TODO
		if (EX_MA_Latch.isMA_enable()) {
			if (EX_MA_Latch.isMA_busy()) {
				return;
			}
			int opcode = EX_MA_Latch.getOpcode();
			int rd = EX_MA_Latch.getrd();
			int ALUResult = EX_MA_Latch.getALUResult();
			int op1 = EX_MA_Latch.getop1();
			int reg2 = EX_MA_Latch.getreg2();
			int ldResult = -1;
			int remainder = EX_MA_Latch.getremainder();
			
//			System.out.println("Opcode in MA Block: " + opcode);

			// Load:
			if (opcode == 22) {
				// Address = ALUResult
				// Register to be written into = op2
				Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this, containingProcessor.getMainMemory(), ALUResult));
//				ldResult = containingProcessor.getMainMemory().getWord(ALUResult);
//				System.out.println("Add MemoryReadEvent to Queue -- MA");
				EX_MA_Latch.setMA_busy(true);
				//containingProcessor.getRegisterFile().setValue(reg2, data);
			}
			// Store:
			else if (opcode == 23) {
				// Address = ALUResult
				// Value to be written = op1
				Simulator.getEventQueue().addEvent(new MemoryWriteEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this, containingProcessor.getMainMemory(), ALUResult, op1));
				EX_MA_Latch.setMA_busy(true);
//				System.out.println("Add MemoryWriteEvent to Queue -- MA");
//				containingProcessor.getMainMemory().setWord(ALUResult, op1);
//				System.out.println("Storing " + op1 + " at " + ALUResult);
			}
			
			MA_RW_Latch.setALUResult(ALUResult);
//			MA_RW_Latch.setldResult(ldResult);
			MA_RW_Latch.setOpcode(opcode);
			MA_RW_Latch.setreg2(reg2);
			MA_RW_Latch.setrd(rd);
			MA_RW_Latch.setremainder(remainder);
			
			if (!EX_MA_Latch.isMA_busy()) {
				EX_MA_Latch.setMA_enable(false);
				MA_RW_Latch.setRW_enable(true);
			}
			
//			EX_MA_Latch.setMA_enable(false);
//			MA_RW_Latch.setRW_enable(true);
		}
	}

	@Override
	public void handleEvent(Event e) {
		// TODO Auto-generated method stub
		if (MA_RW_Latch.isRW_busy()) {
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else if (e.getEventType() == EventType.MemoryResponse){
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			MA_RW_Latch.setldResult(event.getValue());
			MA_RW_Latch.setRW_enable(true);
			EX_MA_Latch.setMA_busy(false);
			EX_MA_Latch.setMA_enable(false);
			containingProcessor.getIF_OF().setOF_busy(false);
			containingProcessor.getIF_OF().setOF_enable(true);
			containingProcessor.getIF_OF().setRdRW(containingProcessor.getIF_OF().getRdMA());
			containingProcessor.getIF_OF().setx31RW(containingProcessor.getIF_OF().getx31MA());
			if(!containingProcessor.getOF_EX().isEX_busy())
			{
				containingProcessor.getIF_OF().setRdMA(containingProcessor.getIF_OF().getRdEX());
				containingProcessor.getIF_OF().setx31MA(containingProcessor.getIF_OF().getx31EX());
				containingProcessor.getIF_OF().setRdEX(-2);
				containingProcessor.getIF_OF().setx31EX(-2);
			}
			else
			{
				containingProcessor.getIF_OF().setRdMA(-2);
				containingProcessor.getIF_OF().setx31MA(-2);
			}
			containingProcessor.getOF_EX().setEX_busy(false);
			containingProcessor.getOF_EX().setEX_enable(true);
//			MA_RW_Latch.setInstruction(event.getValue());
		}
		else if (e.getEventType() == EventType.ExecutionComplete){
			ExecutionCompleteEvent event = (ExecutionCompleteEvent) e;
//			MA_RW_Latch.setldResult(event.getValue());
			MA_RW_Latch.setRW_enable(true);
			EX_MA_Latch.setMA_busy(false);
			EX_MA_Latch.setMA_enable(false);
			containingProcessor.getOF_EX().setEX_busy(false);
			containingProcessor.getIF_OF().setOF_busy(false);
			containingProcessor.getIF_OF().setOF_enable(true);
			containingProcessor.getOF_EX().setEX_enable(true);
//			MA_RW_Latch.setInstruction(event.getValue());
		}
		
	}

}
