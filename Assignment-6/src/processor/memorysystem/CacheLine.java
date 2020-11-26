package processor.memorysystem;

import java.util.ArrayList;

import configuration.Configuration;
import generic.Simulator;
import processor.Processor;

public class CacheLine {
	int data;
	int tag;
	
	public CacheLine() {
	}
	
	public int getData() {
		return data;
	}

	public void setData(int value) {
		data = value;
	}
	
	public int getTag() {
		return tag;
	}
	
	public void setTag(int newTag) {
		tag = newTag;
	}
	
	
}
