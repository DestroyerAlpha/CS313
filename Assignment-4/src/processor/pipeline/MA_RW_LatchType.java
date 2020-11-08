package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	int Final_result;
	int load_result;
	int rd;
	int instruction ;
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}
	
	public void setFinal_result(int al){
		Final_result = al;
	}

	public void setLoadResult(int ld){
		load_result = ld;
	}
	
	public int getFinal_result(){
		return Final_result;
	}

	public int getLoadResult(){
		return load_result;
	}
	
	public void setrd(int r){
		this.rd = r;
	}
	
	public int getrd(){
		return rd;
	}
	public int getInstruction() {
		return instruction;
	}

	

	public void setInstruction(int instruction2) {
		this.instruction = instruction2;
		
	}
}
