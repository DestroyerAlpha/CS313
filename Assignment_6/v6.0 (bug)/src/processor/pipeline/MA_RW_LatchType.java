package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	boolean RW_busy;
	int opcode;
	int ldResult;
	int ALUResult;
	int reg2;
	int rd;
	int remainder;
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
		RW_busy = false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

	public boolean isRW_busy() {
		return RW_busy;
	}

	public void setRW_busy(boolean rW_busy) {
		RW_busy = rW_busy;
	}
	public int getOpcode() {
		return opcode;
	}

	public void setOpcode(int Opcode) {
		this.opcode = Opcode;
	}

	public int getALUResult() {
		return ALUResult;
	}

	public void setALUResult(int AR) {
		this.ALUResult = AR;
	}

	public int getldResult() {
		return ldResult;
	}

	public void setldResult(int ldResult) {
		this.ldResult = ldResult;
	}

	public int getreg2() {
		return reg2;
	}

	public void setreg2(int re2) {
		this.reg2 = re2;
	}

	public int getrd() {
		return rd;
	}

	public void setrd(int rd) {
		this.rd = rd;
	}
	
	public int getremainder() {
		return remainder;
	}

	public void setremainder(int rem) {
		this.remainder = rem;
	}

}
