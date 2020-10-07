	AREA BOOTHALGO,CODE
		ENTRY
	EXPORT __START
__START
	MOV R0,#-4	; MULTIPLIER OR B
	MOV R1,#-2	; MULTIPLICAND
	MOV R2,#0	; A
	MOV R3,#0	; Q_-1
	MOV R4,R1	; Q AND (AT LAST WILL GIVE FINAL ANSWER)
	MOV R6,#0	; COUNT
LOOP
	CMP R6,#32
	BEQ ENDL
	AND R7,R4,#1
	ADD R7,R3,R7,LSL #1	;Q_0Q_-1
	CMP R7,#1
	ADD LR,PC,#2
	BEQ ADDM
	CMP R7,#2
	ADD LR,PC,#2
	BEQ SUBM
	AND R7,R4,#1
	MOV R3,R7	; Q_-1 SHIFTED
	MOV R4,R4,LSR #1
	AND R7,R2,#1
	CMP R7,#1
	ADD LR,PC,#4
	BEQ ADDQ
	MOV R2,R2,ASR #1	; A SHIFTED
	ADD R6,R6,#1
	B LOOP
ADDM
	ADD R2,R2,R0	; A+B IF EQ
	MOV PC,LR
SUBM
	SUB R2,R2,R0	; A-B IF EQ
	MOV PC,LR
ADDQ
	ADD R4,R4,#0X80000000	; Q SHIFTED
	MOV PC,LR
ENDL
	END