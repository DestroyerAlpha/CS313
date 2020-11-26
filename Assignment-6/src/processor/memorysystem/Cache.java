package processor.memorysystem;

import java.util.ArrayList;

import configuration.Configuration;
import generic.Element;
import generic.Event;
import generic.ExecutionCompleteEvent;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;
import generic.Simulator;
import generic.Event.EventType;
import processor.Clock;
import processor.Processor;

public class Cache implements Element{
	Processor containingProcessor;
	int numberOfLines;
	ArrayList<CacheLine> cache0;
	ArrayList<CacheLine> cache1;
	int Associativity;
	ArrayList<Integer> MRU;
	
	public Cache(Processor CP, int lines) {
		containingProcessor = CP;
		numberOfLines = lines;
		Associativity = 2;
		cache0 = new ArrayList<CacheLine>(numberOfLines / 2);
		cache1 = new ArrayList<CacheLine>(numberOfLines / 2);
		MRU = new ArrayList<Integer>(numberOfLines / 2);
		for (int i = 0; i < numberOfLines / 2; i++) {
			CacheLine newline = new CacheLine();
			newline.setTag(-1);
			cache0.add(newline);
		}
		for (int i = 0; i < numberOfLines / 2; i++) {
			CacheLine newline = new CacheLine();
			newline.setTag(-1);
			cache1.add(newline);
		}
		for (int i = 0; i < numberOfLines / 2; i++) {
			MRU.add(1);
		}
	}
		
	public int getnumberOfLines() {
		return numberOfLines;
	}
	
	public void setnumberOfLines(int NOL) {
		numberOfLines = NOL;
	}
		
	public int getTagValue(int Address) {
		int tag = Address / (numberOfLines / 2);
		return tag;
	}
	
	public int getSetValue(int Address) {
		int set = Address % (numberOfLines / 2);
		return set;
	}
	
	public boolean checkTaginCache(int Address) {
		int TagToCheck = getTagValue(Address);
		int set = getSetValue(Address);
		if ((cache0.get(set).getTag() == TagToCheck) || (cache1.get(set).getTag() == TagToCheck)) {
			return true;
		}
		return false;
	}
		
	public int cacheRead(int Address) {
		int set = getSetValue(Address);
		int tag = getTagValue(Address);
		if (checkTaginCache(Address)) { // Precaution
			if (cache0.get(set).getTag() == tag) {
				MRU.set(set, 0);
				return cache0.get(set).getData();
			}
			else if (cache1.get(set).getTag() == tag) {
				MRU.set(set, 1);
				return cache1.get(set).getData();
			}
		}
		return -1;
	}
	
	public void handleCacheMiss(int Address) {
		cacheWrite(Address, containingProcessor.getMainMemory().getWord(Address));
		Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this, containingProcessor.getMainMemory(), Address));
	}
	
	public void cacheWrite (int Address, int value) {
		int set = getSetValue(Address);
		int tag = getTagValue(Address);
		if (MRU.get(set) == 0) {
			CacheLine newLine = new CacheLine();
			newLine.setData(value);
			newLine.setTag(tag);
			MRU.set(set, 1);
			cache1.set(set, newLine);
		}
		else if (MRU.get(set) == 1) {
			CacheLine newLine = new CacheLine();
			newLine.setData(value);
			newLine.setTag(tag);
			MRU.set(set, 0);
			cache0.set(set, newLine);
		}
	}

	@Override
	public void handleEvent(Event e) {
		// TODO Auto-generated method stub
		if (e.getEventType() == EventType.MemoryRead) {
			MemoryReadEvent event = (MemoryReadEvent) e;
			int Address = event.getAddressToReadFrom();
			if (checkTaginCache(Address)) {
				int value = cacheRead(Address);
				Simulator.getEventQueue().addEvent(new MemoryResponseEvent(Clock.getCurrentTime(), this, e.getRequestingElement(), value));
			}
			else {
				handleCacheMiss(Address);
			}
		}
		if (e.getEventType() == EventType.MemoryResponse) {
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			Simulator.getEventQueue().addEvent(new MemoryResponseEvent(Clock.getCurrentTime(), this, containingProcessor.getIFUnit(), event.getValue()));
		}
	}
	
}
