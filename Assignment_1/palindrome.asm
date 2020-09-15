	.data
a:
	384664832
	.text
main:
	addi %x0, %x0, %x3
	load %x0, $a, %x4
	load %x0, $a, %x5
loop:
	divi %x5, 10, %x5
	add %x3, %x31, %x3
	beq %x0, %x5, cont
	muli %x3, 10, %x3
	jmp loop
cont:
	beq %x4, %x3, success
	bne %x4, %x3, endl
success:
	addi %x0, 1, %x10
	end
endl:
	subi %x0, 1, %x10
	end
