	.data
a:
	70
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main:
	add %x0, %x0, %x5
	load %x0, $n, %x11
	add %x0, %x11, %x12
	add %x0, %x11, %x13
	subi %x13, 1, %x13
	addi %x0, 65535, %x9
loop:
	add %x0, %x0, %x3
	beq %x5, %x11, loop2
	blt %x5, %x11, loop1
loop1:
	load %x3, $a, %x4
	bgt %x4, %x8, assgn
loop12:
	addi %x3, 1, %x3
	bgt %x3, %x11, storee
	jmp loop1
assgn:
	add %x0, %x4, %x8
	add %x0, %x3, %x7
	jmp loop12
storee:
	addi %x5, 1, %x5
	store %x8, 0, %x9
	subi %x9, 1, %x9
	subi %x0, 10000000, %x8
	store %x8, $a, %x7
	subi %x0, 1, %x7
	jmp loop
loop2:
	addi %x9, 1, %x9
	bgt %x12, %x0, loop3
	beq %x12, %x0, endl
loop3:
	load %x9, $a, %x15
	store %x15, $a, %x13
	subi %x13, 1, %x13
	subi %x12, 1, %x12
	jmp loop2
endl:
	end
