package processor.pipeline;

public class IF_OF_LatchType {
	
	boolean OF_enable;
	boolean OF_busy;
	int instruction;
	int RdEX = -2;
	int RdMA = -2;
	int RdRW = -2;
	int x31EX = -2;
	int x31MA = -2;
	int x31RW = -2;
	
	public IF_OF_LatchType()
	{
		OF_enable = false;
		OF_busy = false;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}
	
	public boolean isOF_busy() {
		return OF_busy;
	}

	public void setOF_enable(boolean oF_enable) {
//		System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tOF OFF");
		OF_enable = oF_enable;
	}
	
	public void setOF_busy(boolean oF_busy) {
		OF_busy = oF_busy;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}
	
	public int getRdEX() {
		return RdEX;
	}
	
	public void setRdEX(int newRdEX) {
		this.RdEX = newRdEX;
	}

	public int getRdMA() {
		return RdMA;
	}
	
	public void setRdMA(int newRdMA) {
		System.out.println("Change Here.");
		this.RdMA = newRdMA;
	}

	public int getRdRW() {
		return RdRW;
	}
	
	public void setRdRW(int newRdRW) {
		this.RdRW = newRdRW;
	}

	public int getx31EX() {
		return x31EX;
	}
	
	public void setx31EX(int newRdEX) {
		this.x31EX = newRdEX;
	}

	public int getx31MA() {
		return x31MA;
	}
	
	public void setx31MA(int newRdMA) {
		this.x31MA = newRdMA;
	}

	public int getx31RW() {
		return x31RW;
	}
	
	public void setx31RW(int newRdRW) {
		this.x31RW = newRdRW;
	}

}
