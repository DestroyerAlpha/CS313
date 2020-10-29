package processor.pipeline;

import generic.Statistics;
import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	boolean isEND = false;
	ControlUnit cu = new ControlUnit();
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public int convertbin( String s) {
		int n = s.length();
		int i = Integer.parseInt(s,2);
		if (s.charAt(0) == '1') {
			i = i - (int)Math.pow(2, n);

			
			return i;
		}
		else return i;
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable() && !isEND)
		{
			//TODO
			
			int instruction = IF_OF_Latch.getInstruction();
			cu.setInstruction(instruction);
			String instructionString = Integer.toBinaryString(instruction);
			
			int n = instructionString.length();
			String todo="";
			for (int i=0;i<32-n;i++){
				todo = todo + "0" ;
			}
			instructionString = todo + instructionString;
			String opcode="",rs1="",rs2="",rd="",immx = "";
			rs1 = instructionString.substring(5,10);
			rs2 = instructionString.substring(10,15);
			opcode=instructionString.substring(0,5);
			
			if (instructionString.substring(0,5).equals("11101"))
				isEND = true;
			if(cu.isR3(opcode))
			{
					rd=instructionString.substring(15,20);
					immx=instructionString.substring(20,32);
			}
			if(cu.isR2I(opcode))
			{
					rd=instructionString.substring(10,15);
					immx=instructionString.substring(15,32);
			}
			if(cu.isRI(opcode))
			{
					rd=instructionString.substring(5,10);
					immx=instructionString.substring(10,32);
			}
			String rp1 ;
			String rp2 ;
			if(!(opcode.equals("10111"))){ // if store
				rp1 = rs1;
				rp2 = rs2;
			}
			else{
				rp1 = rd;
				rp2 = rs1;
			}
			int operand1 = containingProcessor.getRegisterFile().getValue( Integer.parseInt(rp1,2) );
			int operand2 = containingProcessor.getRegisterFile().getValue( Integer.parseInt(rp2,2) );
			
			
			boolean conflict = false;
			if(cu.isR3(opcode))
			{
					if(containingProcessor.getEXUnit().cu.opcode.equals("00111") || containingProcessor.getEXUnit().cu.opcode.equals("00110")) {
						if(rs1.equals("11111")){
							conflict = true;
						}							//Division before arithemtic operations
						if(rs2.equals("11111")){
							conflict = true;
						}
					}
					if((cu.isR3(containingProcessor.getEXUnit().cu.opcode) || cu.isR2I1(containingProcessor.getEXUnit().cu.opcode)) || (cu.isR3(containingProcessor.getMAUnit().cu.opcode) || cu.isR2I1(containingProcessor.getMAUnit().cu.opcode)) || (cu.isR3(containingProcessor.getRWUnit().cu.opcode) || cu.isR2I1(containingProcessor.getRWUnit().cu.opcode))) {
						if(rs1.equals(containingProcessor.getEXUnit().cu.rd) || rs1.equals(containingProcessor.getMAUnit().cu.rd) || rs1.equals(containingProcessor.getRWUnit().cu.rd)){
							conflict = true;
						}
						if(rs2.equals(containingProcessor.getEXUnit().cu.rd) || rs2.equals(containingProcessor.getMAUnit().cu.rd) || rs2.equals(containingProcessor.getRWUnit().cu.rd)){
								conflict = true;
						}                        //Immediate Arithemetic before arithmetic
					}
					if((containingProcessor.getEXUnit().cu.opcode.equals("10110")) || (containingProcessor.getMAUnit().cu.opcode.equals("10110"))) {
						if(rs1.equals(containingProcessor.getEXUnit().cu.rd) || rs1.equals(containingProcessor.getMAUnit().cu.rd)){
							conflict = true;
						}
						if(rs2.equals(containingProcessor.getEXUnit().cu.rd) || rs2.equals(containingProcessor.getMAUnit().cu.rd)){
							conflict = true;
						}                      //Load before arithmetic
					}
			}
			if(cu.isR2I1(opcode))
			{
					if(containingProcessor.getEXUnit().cu.opcode.equals("00111") || containingProcessor.getEXUnit().cu.opcode.equals("00110")) {
						if(rs1.equals("11111")){
							conflict = true;               //Division not completed before arithemtic operations
						}
					}
					if((cu.isR3(containingProcessor.getEXUnit().cu.opcode) || cu.isR2I1(containingProcessor.getEXUnit().cu.opcode)) || (cu.isR3(containingProcessor.getMAUnit().cu.opcode) || cu.isR2I1(containingProcessor.getMAUnit().cu.opcode)) || (cu.isR3(containingProcessor.getRWUnit().cu.opcode) || cu.isR2I1(containingProcessor.getRWUnit().cu.opcode))) {
							
						if(rs1.equals(containingProcessor.getEXUnit().cu.rd) || rs1.equals(containingProcessor.getMAUnit().cu.rd) || rs1.equals(containingProcessor.getRWUnit().cu.rd)){
							conflict = true;
						}                                //Arithemetic operations before Immediate arithemtic
					}
					if((containingProcessor.getEXUnit().cu.opcode.equals("10110")) || (containingProcessor.getMAUnit().cu.opcode.equals("10110"))) {
						if(rs1.equals(containingProcessor.getEXUnit().cu.rd) || rs1.equals(containingProcessor.getMAUnit().cu.rd)){
							conflict = true;              //Load before immediate arithemtic
						}
					}
			}
			if(cu.isR2I3(opcode))
			{
				if(containingProcessor.getEXUnit().cu.opcode.equals("00111") || containingProcessor.getEXUnit().cu.opcode.equals("00110")) {
					if(rs1.equals("11111")){
						conflict = true;
					}				//Branching before division is computed
					if(rd.equals("11111")){
						conflict = true;
					}
				}
				if((cu.isR3(containingProcessor.getEXUnit().cu.opcode) || cu.isR2I1(containingProcessor.getEXUnit().cu.opcode)) || (cu.isR3(containingProcessor.getMAUnit().cu.opcode) || cu.isR2I1(containingProcessor.getMAUnit().cu.opcode)) || (cu.isR3(containingProcessor.getRWUnit().cu.opcode) || cu.isR2I1(containingProcessor.getRWUnit().cu.opcode))) {
					if(rs1.equals(containingProcessor.getEXUnit().cu.rd) || rs1.equals(containingProcessor.getMAUnit().cu.rd) || rs1.equals(containingProcessor.getRWUnit().cu.rd)){
						conflict = true;
					}
					if(rd.equals(containingProcessor.getEXUnit().cu.rd) || rd.equals(containingProcessor.getMAUnit().cu.rd) || rd.equals(containingProcessor.getRWUnit().cu.rd)){
							conflict = true;
					}                        //Branching before arithmetic computations are completed
				}
				if((containingProcessor.getEXUnit().cu.opcode.equals("10110")) || (containingProcessor.getMAUnit().cu.opcode.equals("10110"))) {
					if(rs1.equals(containingProcessor.getEXUnit().cu.rd) || rs1.equals(containingProcessor.getMAUnit().cu.rd)){
						conflict = true;
					}
					if(rd.equals(containingProcessor.getEXUnit().cu.rd) || rd.equals(containingProcessor.getMAUnit().cu.rd)){
						conflict = true;
					}                         //Branching before load is completed
				}
			}
			if(cu.isR2I2(opcode))
			{
				if(opcode.equals("10110"))
				{
					if(containingProcessor.getEXUnit().cu.opcode.equals("00111") || containingProcessor.getEXUnit().cu.opcode.equals("00110")) {
						if(rs1.equals("11111")){
							conflict = true;   //Loading remainder of division before it is computed in EX
						}
					}
					if((cu.isR3(containingProcessor.getEXUnit().cu.opcode) || cu.isR2I1(containingProcessor.getEXUnit().cu.opcode)) || (cu.isR3(containingProcessor.getMAUnit().cu.opcode) || cu.isR2I1(containingProcessor.getMAUnit().cu.opcode)) || (cu.isR3(containingProcessor.getRWUnit().cu.opcode) || cu.isR2I1(containingProcessor.getRWUnit().cu.opcode))) {
						if(rs1.equals(containingProcessor.getEXUnit().cu.rd) || rs1.equals(containingProcessor.getMAUnit().cu.rd) || rs1.equals(containingProcessor.getRWUnit().cu.rd)){
							conflict = true;         //Arithemetic operations in EX stage being loaded before computing
						}
					}
					if((containingProcessor.getEXUnit().cu.opcode.equals("10110")) || (containingProcessor.getMAUnit().cu.opcode.equals("10110"))) {
						if(rs1.equals(containingProcessor.getEXUnit().cu.rd) || rs1.equals(containingProcessor.getMAUnit().cu.rd)){
							conflict = true;        //Store instruction in EX being loaded
						}
					}
					if((containingProcessor.getEXUnit().cu.opcode.equals("10111")) || (containingProcessor.getMAUnit().cu.opcode.equals("10111"))) {
						if(((Integer.parseInt(rs1,2) + convertbin(immx) )== 
										Integer.parseInt((containingProcessor.getEXUnit().cu.rd),2) +
										convertbin(containingProcessor.getEXUnit().cu.Imm) ) ||
										((Integer.parseInt(rs1,2) + convertbin(immx) )== 
										Integer.parseInt((containingProcessor.getMAUnit().cu.rd),2) +
										convertbin(containingProcessor.getMAUnit().cu.Imm) )) {
							conflict = true;          //Loading in same location
						}
					}
				}
				else if(opcode.equals("10111"))
				{
					if(containingProcessor.getEXUnit().cu.opcode.equals("00111") || containingProcessor.getEXUnit().cu.opcode.equals("00110")) {
						if(rs1.equals("11111")){
							conflict = true;
						}                                  //Storing R31 before division is completed
						if(rd.equals("11111")){
							conflict = true;
						}
					}
					if((cu.isR3(containingProcessor.getEXUnit().cu.opcode) || cu.isR2I1(containingProcessor.getEXUnit().cu.opcode)) || (cu.isR3(containingProcessor.getMAUnit().cu.opcode) || cu.isR2I1(containingProcessor.getMAUnit().cu.opcode)) || (cu.isR3(containingProcessor.getRWUnit().cu.opcode) || cu.isR2I1(containingProcessor.getRWUnit().cu.opcode))) {
						if(rs1.equals(containingProcessor.getEXUnit().cu.rd) || rs1.equals(containingProcessor.getMAUnit().cu.rd) || rs1.equals(containingProcessor.getRWUnit().cu.rd)){
							conflict = true;
						}
						if(rd.equals(containingProcessor.getEXUnit().cu.rd) || rd.equals(containingProcessor.getMAUnit().cu.rd) || rd.equals(containingProcessor.getRWUnit().cu.rd)){
								conflict = true;
						}         //Storing arithemetic results before they are computed
					}
					if((containingProcessor.getEXUnit().cu.opcode.equals("10110")) || (containingProcessor.getMAUnit().cu.opcode.equals("10110"))) {
						if(rs1.equals(containingProcessor.getEXUnit().cu.rd) || rs1.equals(containingProcessor.getMAUnit().cu.rd)){
							conflict = true;
						}
						if(rd.equals(containingProcessor.getEXUnit().cu.rd) || rd.equals(containingProcessor.getMAUnit().cu.rd)){
							conflict = true;
						}         //Storing before load is completed
					}
				}
			}
			if(!conflict)
			{
					OF_EX_Latch.setInstruction(instruction);
					OF_EX_Latch.setimmx(convertbin(immx));
					OF_EX_Latch.setbranchtarget(convertbin(immx) + containingProcessor.getRegisterFile().getProgramCounter()-1);
					//containingProcessor.getcontrol_unit().setopcode(instructionString.substring(0,5));
					OF_EX_Latch.setoperand1(operand1);
					OF_EX_Latch.setoperand2(operand2);
					OF_EX_Latch.setrd(Integer.parseInt(rd,2));
					containingProcessor.getIFUnit().conflict = false ;
					IF_OF_Latch.setOF_enable(false);
					OF_EX_Latch.setEX_enable(true);
			}
			else
			{
					Statistics.data_hazards++;
					containingProcessor.getIFUnit().conflict = true ;
			}

		}
		
	}

}
