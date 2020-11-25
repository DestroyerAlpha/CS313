package processor.pipeline;

import configuration.Configuration;
//import configuration.Configuration;
import generic.Element;
import generic.Event;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.Simulator;
import processor.Clock;
//import generic.Simulator;
import processor.Processor;

public class InstructionFetch implements Element{
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performIF()
	{
		if(IF_EnableLatch.isIF_enable())
		{
			if (IF_EnableLatch.isIF_busy()) {
				return;
			}
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			if(containingProcessor.getControlHazardFlag())
				containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			currentPC = containingProcessor.getRegisterFile().getProgramCounter();
//			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
//			IF_OF_Latch.setInstruction(newInstruction);
//			IF_OF_Latch.setOF_enable(true);
//			System.out.println("Add MemoryReadEvent to Queue -- IF");
			Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime() + Configuration.L1i_latency, this, containingProcessor.getL1iCache(), currentPC));
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			IF_EnableLatch.setIF_busy(true);
			IF_OF_Latch.setOF_enable(false);
		}
	}

	@Override
	public void handleEvent(Event e) {
		// TODO Auto-generated method stub
		if (IF_OF_Latch.isOF_busy()) { 
//			System.out.println("Here");
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else {
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			if ((containingProcessor.getControlHazardFlag() == false) || (Configuration.mainMemoryLatency <= 1)) { //
				if (containingProcessor.getControlHazardFlag() == false) {
//					System.out.print("Instruction set to this: ");
//					System.out.println(event.getValue());		
//					if (event.getValue() == -402653184) {
//						System.out.println("-----End Instruction Starts Here----");
//					}
					IF_OF_Latch.setInstruction(event.getValue());
					IF_OF_Latch.setOF_enable(true);
				}
				else {
					System.out.println("hw=ere");
					if (event.getValue() != -402653184) {
//						System.out.println("-----End Instruction Starts Here----");
//						System.out.print("Instruction set to this: ");
//						System.out.println(event.getValue());		
						IF_OF_Latch.setInstruction(event.getValue());
						IF_OF_Latch.setOF_enable(true);
					}
					containingProcessor.setControlHazardFlag(false);
//					int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
//					Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this, containingProcessor.getMainMemory(), currentPC));
//					containingProcessor.getIF_Enable().setIF_enable(false);
				}
			}
			else {
				System.out.print("Instruction set to -1. This is what was: ");
				System.out.println(event.getValue());
				IF_OF_Latch.setInstruction(-1);
				containingProcessor.setControlHazardFlag(false);
			}
			IF_EnableLatch.setIF_busy(false);
		}
	}
}
