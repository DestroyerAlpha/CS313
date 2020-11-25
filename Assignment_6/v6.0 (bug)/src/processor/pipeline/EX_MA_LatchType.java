package processor.pipeline;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	boolean MA_busy;
	int ALUResult;
	int op1;
	int rd;
	int opcode;
	int reg2;
	int remainder;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
		MA_busy = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}

	public boolean isMA_busy() {
		return MA_busy;
	}

	public void setMA_busy(boolean mA_busy) {
		MA_busy = mA_busy;
	}
	public int getALUResult() {
		return ALUResult;
	}

	public void setALUResult(int AR) {
		this.ALUResult = AR;
	}
	
	public int getop1() {
		return op1;
	}

	public void setop1(int OP1) {
		this.op1 = OP1;
	}
	
	public int getrd() {
		return rd;
	}

	public void setrd(int Rd) {
		this.rd = Rd;
	}
	
	public int getOpcode() {
		return opcode;
	}

	public void setOpcode(int opc) {
		this.opcode = opc;
	}

	public int getreg2() {
		return reg2;
	}

	public void setreg2(int Reg2) {
		this.reg2 = Reg2;
	}
	
	public int getremainder() {
		return remainder;
	}

	public void setremainder(int rem) {
		this.remainder = rem;
	}
	
}
