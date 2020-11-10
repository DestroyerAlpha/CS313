package processor.pipeline;

public class EX_MA_LatchType {
	
	boolean MA_enable,MA_busy;
	int Final_Result;
	int op2;
	int rd;
	int instruction;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}
	public void setop2(int op){
		op2=op;
	}
	
	public void setMA_busy(boolean ma_busy){
		MA_busy=ma_busy;
	}

	public boolean isMA_busy() {
		return MA_busy;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}
	public void setFinal_Result(int a){
		Final_Result=a;
		
	}
	public void setrd(int r){
		this.rd=r;
		
	}
	public int getrd(){
		return this.rd;
		
	}
	public int getFinal_Result(){
		return Final_Result;
	}
	public int getop2(){
		return op2;
	}
	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}
}
