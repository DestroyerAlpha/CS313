package processor.pipeline;

public class OF_EX_LatchType {
	
	boolean EX_enable;
	boolean EX_busy;
	int immediate;
	long branchTarget;
	int op1;
	int op2;
	int rd;
	int opcode;
	int reg2;
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
		EX_busy = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}
	
	public boolean isEX_busy() {
		return EX_busy;
	}

	public void setEX_busy(boolean eX_busy) {
		EX_busy = eX_busy;
	}

	public int getImmediate() {
		return immediate;
	}

	public void setImmediate(int immediate) {
		this.immediate = immediate;
	}

	public long getBranchTarget() {
		return branchTarget;
	}

	public void setBranchTarget(long BranchTarget) {
		this.branchTarget = BranchTarget;
	}
	
	public int getop1() {
		return op1;
	}

	public void setop1(int OP1) {
		this.op1 = OP1;
	}
	
	public int getop2() {
		return op2;
	}

	public void setop2(int OP2) {
		this.op2 = OP2;
	}

	public int getOpcode() {
		return this.opcode;
	}

	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}

	public int getrd() {
		return this.rd;
	}

	public void setRd(int RegD) {
		this.rd = RegD;
	}
	
	public int getreg2() {
		return this.reg2;
	}

	public void setReg2(int Reg2) {
		this.reg2 = Reg2;
	}
	
}
