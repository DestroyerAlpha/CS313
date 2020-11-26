package processor;

import configuration.Configuration;
import processor.memorysystem.CacheLd;
import processor.memorysystem.CacheLi;
import processor.memorysystem.MainMemory;
import processor.pipeline.EX_IF_LatchType;
import processor.pipeline.EX_MA_LatchType;
import processor.pipeline.Execute;
import processor.pipeline.IF_EnableLatchType;
import processor.pipeline.IF_OF_LatchType;
import processor.pipeline.InstructionFetch;
import processor.pipeline.MA_RW_LatchType;
import processor.pipeline.MemoryAccess;
import processor.pipeline.OF_EX_LatchType;
import processor.pipeline.OperandFetch;
import processor.pipeline.RegisterFile;
import processor.pipeline.RegisterWrite;

public class Processor {
	
	RegisterFile registerFile;
	MainMemory mainMemory;
	
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	InstructionFetch IFUnit;
	OperandFetch OFUnit;
	Execute EXUnit;
	MemoryAccess MAUnit;
	RegisterWrite RWUnit;
	
	CacheLi L1iCache;
	CacheLd L1dCache;
	
	int DataHazards;
	int ControlHazards;
	boolean IF_Flag;
	boolean HazardLock;
	boolean ControlHazardFlag;
	boolean ControlHazardFlag2;
	
	public Processor()
	{
		registerFile = new RegisterFile();
		mainMemory = new MainMemory();
		
		IF_EnableLatch = new IF_EnableLatchType();
		IF_OF_Latch = new IF_OF_LatchType();
		OF_EX_Latch = new OF_EX_LatchType();
		EX_MA_Latch = new EX_MA_LatchType();
		EX_IF_Latch = new EX_IF_LatchType();
		MA_RW_Latch = new MA_RW_LatchType();
		
		IFUnit = new InstructionFetch(this, IF_EnableLatch, IF_OF_Latch, EX_IF_Latch);
		OFUnit = new OperandFetch(this, IF_OF_Latch, OF_EX_Latch);
		EXUnit = new Execute(this, OF_EX_Latch, EX_MA_Latch, EX_IF_Latch);
		MAUnit = new MemoryAccess(this, EX_MA_Latch, MA_RW_Latch);
		RWUnit = new RegisterWrite(this, MA_RW_Latch, IF_EnableLatch);
		
		L1iCache = new CacheLi(this, Configuration.L1i_numberOfLines);
		L1dCache = new CacheLd(this, Configuration.L1d_numberOfLines);
		
		DataHazards = 0;
		ControlHazards = 0;
		HazardLock = false;
		ControlHazardFlag = false;
		ControlHazardFlag2 = false;
		
	}
	
	public void printState(int memoryStartingAddress, int memoryEndingAddress)
	{
		System.out.println(registerFile.getContentsAsString());
		
		System.out.println(mainMemory.getContentsAsString(memoryStartingAddress, memoryEndingAddress));		
	}

	public RegisterFile getRegisterFile() {
		return registerFile;
	}

	public void setRegisterFile(RegisterFile registerFile) {
		this.registerFile = registerFile;
	}

	public MainMemory getMainMemory() {
		return mainMemory;
	}

	public void setMainMemory(MainMemory mainMemory) {
		this.mainMemory = mainMemory;
	}

	public InstructionFetch getIFUnit() {
		return IFUnit;
	}

	public OperandFetch getOFUnit() {
		return OFUnit;
	}

	public Execute getEXUnit() {
		return EXUnit;
	}

	public MemoryAccess getMAUnit() {
		return MAUnit;
	}

	public RegisterWrite getRWUnit() {
		return RWUnit;
	}

	public IF_EnableLatchType getIF_Enable() {
		return IF_EnableLatch;
	}
	
	public IF_OF_LatchType getIF_OF() {
		return IF_OF_Latch;
	}

	public OF_EX_LatchType getOF_EX() {
		return OF_EX_Latch;
	}
	
	public EX_MA_LatchType getEX_MA() {
		return EX_MA_Latch;
	}
	
	public MA_RW_LatchType getMA_RW() {
		return MA_RW_Latch;
	}
	
	public CacheLi getL1iCache() {
		return L1iCache;
	}
	
	public CacheLd getL1dCache() {
		return L1dCache;
	}

	public int getDataHazards() {
		return DataHazards;
	}
	
	public void setDataHazards(int DataHaz) {
		if (HazardLock == false) {
			this.DataHazards = DataHaz;
		}
	}
	
	public int getControlHazards() {
		return ControlHazards;
	}
	
	public void setControlHazards(int ContHaz) {
		System.out.println(ControlHazards);
		if (HazardLock == false) {
			this.ControlHazards = ContHaz;
		}
		System.out.println(ControlHazards);
	}
	
	public boolean getHazardLock() {
		return HazardLock;
	}
	
	public void setHazardLock(boolean HazLock) {
		this.HazardLock = HazLock;
	}
	
	public boolean getIF_Flag() {
		return IF_Flag;
	}
	
	public void setIF_Flag(boolean IFF_Flag) {
		this.IF_Flag = IFF_Flag;
	}
	
	public boolean getControlHazardFlag() {
		return ControlHazardFlag;
	}
	
	public void setControlHazardFlag(boolean CF_Flag) {
		System.out.println(ControlHazardFlag);
		this.ControlHazardFlag = CF_Flag;
		System.out.println(ControlHazardFlag);
	}

	public boolean getControlHazardFlag2() {
		return ControlHazardFlag2;
	}
	
	public void setControlHazardFlag2(boolean CF_Flag2) {
		this.ControlHazardFlag2 = CF_Flag2;
	}

}
