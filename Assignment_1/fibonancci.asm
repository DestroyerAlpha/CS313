	.data
n:
	10
	.text
main:
	addi %x0, 1, %x3
	addi %x0, 1, %x4
	addi %x0, 0, %x6
	load %x0, $n, %x5
	addi %x0, 65535, %x9	
	beq %x3, %x5, endt
	subi %x9, 1, %x9
	addi %x0, 2, %x2
	beq %x5, %x2, endl
	store %x3, 0, %x9
	subi %x9, 1, %x9
	addi %x0, 3, %x14
	beq %x5, %x14, endl
	store %x3, 0, %x9
	subi %x9, 1, %x9
loop:
	add %x3, %x4, %x6
	store %x6, 0, %x9
	subi %x9, 1, %x9
	addi %x14, 1, %x14
	beq %x14, %x5, endt
	add %x0, %x4, %x3
	add %x0, %x6, %x4
	jmp loop
endl:
	store %x3, 0, %x9
	subi %x9, 1, %x9
	end
endt:
	end
