package processor.memorysystem;

import generic.Element;
import generic.Event;
import generic.Event.EventType;
import generic.ExecutionCompleteEvent;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;
import generic.Simulator;
import processor.Clock;

public class MainMemory implements Element {
	int[] memory;
	boolean isMemoryBusy;
	
	public MainMemory()
	{
		memory = new int[65536];
		isMemoryBusy = false;
	}
	
	public int getWord(int address)
	{
		return memory[address];
	}
	
	public void setWord(int address, int value)
	{
		memory[address] = value;
	}
	
	public String getContentsAsString(int startingAddress, int endingAddress)
	{
		if(startingAddress == endingAddress)
			return "";
		
		StringBuilder sb = new StringBuilder();
		sb.append("\nMain Memory Contents:\n\n");
		for(int i = startingAddress; i <= endingAddress; i++)
		{
			sb.append(i + "\t\t: " + memory[i] + "\n");
		}
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public void handleEvent(Event e) {
		// TODO Auto-generated method stub
		System.out.println("C0");
		if (e.getEventType()  == EventType.MemoryRead) {
			System.out.println("C1a");
			MemoryReadEvent event = (MemoryReadEvent) e;
			System.out.println("From Main Memory: " + getWord(event.getAddressToReadFrom()));
			Simulator.getEventQueue().addEvent(new MemoryResponseEvent(Clock.getCurrentTime(), this, e.getRequestingElement(), getWord(event.getAddressToReadFrom())));
		}
		else if (e.getEventType() == EventType.MemoryWrite) {
			System.out.println("C1b");
			MemoryWriteEvent event = (MemoryWriteEvent) e;
			memory[event.getAddressToWriteTo()] = event.getValue();
			Simulator.getEventQueue().addEvent(new ExecutionCompleteEvent(Clock.getCurrentTime(), this, e.getRequestingElement()));
		}
		
	}
}
