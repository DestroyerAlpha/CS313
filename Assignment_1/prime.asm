	.data
a:
	2
	.text
main:
	addi %x0, 2, %x3
	load %x0, $a, %x4
	beq %x0, %x4, endl
	beq %x3, %x4, success
	addi %x0, 1, %x7
	beq %x7, %x4, endl
	divi %x4, 2, %x5
loop:
	div %x4, %x3, %x6
	beq %x0, %x31, endl
	addi %x3, 1, %x3
	bgt %x3, %x5, success
	jmp loop
success:
	addi %x0, 1, %x10
	end
endl:
	subi %x0, 1, %x10
	end
