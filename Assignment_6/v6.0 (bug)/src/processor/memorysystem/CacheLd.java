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

public class CacheLd implements Element{
	Processor containingProcessor;
	int numberOfLines;
	ArrayList<CacheLine> cache0;
	ArrayList<CacheLine> cache1;
	int Associativity;
	ArrayList<Integer> MRU;
	
	public CacheLd(Processor CP, int lines) {
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
//		else {
//			handleCacheMiss(Address);
//			return -1; 				
//		}
		return -1;
	}
	
	public void handleCacheMiss(int Address) {
		cacheWrite(Address, containingProcessor.getMainMemory().getWord(Address));
		Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this, containingProcessor.getMainMemory(), Address));
//		Simulator.getEventQueue().addEvent(new Memory);
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
	
	public void printCaches() {
		System.out.print("Cache 0:");
		System.out.println("Size: " + cache0.size());
		for (int i = 0; i < cache0.size(); i++) {
			System.out.print(" At i: " + i + " Tag: " + cache0.get(i).getTag() + " Data: " + cache0.get(i).getData() + " MRU: " + MRU.get(i));
		}
		System.out.println();
		System.out.print("Cache 1:");
		System.out.println("Size: " + cache1.size());
		for (int i = 0; i < cache1.size(); i++) {
			System.out.print(" At i: " + i + " Tag: " + cache1.get(i).getTag() + " Data: " + cache1.get(i).getData());
		}
		System.out.println();
	}
	
//	public void WriteIntoCache(int data, int tag, int cacheIndex) {
//		CacheLine newLine = new CacheLine();
//		newLine.setTag(tag);
//		newLine.setData(data);
//		cache.set(cacheIndex, newLine);
//	}

	@Override
	public void handleEvent(Event e) {
		// TODO Auto-generated method stub
		if (e.getEventType() == EventType.MemoryRead) {
			MemoryReadEvent event = (MemoryReadEvent) e;
			int Address = event.getAddressToReadFrom();
			System.out.println("Address Given to CacheLd: " + Address);
			if (checkTaginCache(Address)) {
				System.out.println("TagINLD");
				int value = cacheRead(Address);
				Simulator.getEventQueue().addEvent(new MemoryResponseEvent(Clock.getCurrentTime(), this, e.getRequestingElement(), value));
			}
			else {
				System.out.println("TagOUTLD");
				handleCacheMiss(Address);
			}
//			Simulator.getEventQueue().addEvent(new MemoryResponseEvent(Clock.getCurrentTime(), this, e.getRequestingElement(), getWord(event.getAddressToReadFrom())));
		}
		else if (e.getEventType() == EventType.MemoryResponse) {
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			System.out.println("Returning Value from CLD: " + event.getValue());
			Simulator.getEventQueue().addEvent(new MemoryResponseEvent(Clock.getCurrentTime(), this, containingProcessor.getMAUnit(), event.getValue()));
			printCaches();
		}
		else if (e.getEventType() == EventType.MemoryWrite) {
			MemoryWriteEvent event = (MemoryWriteEvent) e;
			int Address = event.getAddressToWriteTo();
			if (checkTaginCache(Address)) {
				cacheWrite(Address, event.getValue());
				Simulator.getEventQueue().addEvent(new MemoryWriteEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this, containingProcessor.getMainMemory(), event.getAddressToWriteTo(), event.getValue()));
			}
			else {
				Simulator.getEventQueue().addEvent(new MemoryWriteEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this, containingProcessor.getMainMemory(), event.getAddressToWriteTo(), event.getValue()));				
			}
		}
		else if (e.getEventType() == EventType.ExecutionComplete) {
			System.out.println("Execution Complete Forwarding");
			Simulator.getEventQueue().addEvent(new ExecutionCompleteEvent(Clock.getCurrentTime(), this, containingProcessor.getMAUnit()));
		}
	}
	
}
