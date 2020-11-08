package processor.pipeline;

public class ControlUnit {
	int instruction;
	String opcode="",rs1="",rs2="",rd="",Imm = "";
	public boolean isR3(String op) {
		if(op.equals("")) return false;
		if(op.charAt(4)=='0' && !(op.charAt(0)=='1' && op.charAt(1)=='1') && !op.equals("10110"))
			return true;
		else return false;
		
	}
	public boolean isR2I(String op) {
		if(op.equals("")) return false;
		if( ((op.charAt(4)=='1') || op.equals("10110") || op.equals("11010") ||op.equals("11100") ) && !op.equals("11101"))
			return true;
		else return false;
		
	}
	public boolean isR2I1(String op) {
		if(op.equals("")) return false;
		if(op.charAt(4)=='1'  && !(op.charAt(0)=='1' && op.charAt(1)=='1') && !op.equals("10111"))
			return true;
		else return false;
		
	}
	
	public boolean isR2I2(String op) {//load or store
		if(op.equals("")) return false;
		if((op.equals("10110") || op.equals("10111")))
			return true;
		else return false;
		
	}
	
	public boolean isR2I3(String op) { // branch
		if(op.equals("")) return false;
		if((op.charAt(0)=='1' && op.charAt(1)=='1')&&!op.equals("11000")&&!op.equals("11101")  )
			return true;
		else return false;
		
	}
	public boolean isRI(String op) {
		if(op.equals("")) return false;
		if(op.equals("11101") || op.equals("11000"))
			return true;
		else return false;
		
	}
	public void setInstruction(int instruction) {
		this.instruction = instruction;
		String instructionString = Integer.toBinaryString(instruction);
		int n = instructionString.length();
		String todo="" ;
		for (int i=0;i<32-n;i++){
			todo = todo + "0" ;
		}
		instructionString = todo + instructionString;
		rs1 = instructionString.substring(5,10);
		rs2 = instructionString.substring(10,15);
		opcode=instructionString.substring(0,5);
		switch(String.valueOf(isR3(opcode)))
		{
			case "true":
			rd=instructionString.substring(15,20);
		}
		switch(String.valueOf(isR2I(opcode)))
		{
			case "true":
			rd=instructionString.substring(10,15);
			Imm=instructionString.substring(15,32);
		}
		switch(String.valueOf(isRI(opcode)))
		{
			case "true":
			rd=instructionString.substring(5,10);
			Imm=instructionString.substring(10,32);
		}
	} 
	public boolean isRegisterWriteBack(){
		if ((opcode.charAt(0) == '1' && opcode.charAt(1) =='1') || (opcode.equals("10111"))){
			return false;
		}
			return true;
	}
	public boolean isimm(){
		if (( opcode.charAt(4)=='1'|| opcode.equals("10110")) && !( opcode.equals("11101") || opcode.equals("11001") || opcode.equals("11011"))){

			return true;
		}
		else return false ;
	}
	public boolean isSt(){
		if(opcode.equals("10111")){
				return true;
		}
		else return false ;
	}

}
