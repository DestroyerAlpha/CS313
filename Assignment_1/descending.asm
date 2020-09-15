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
	addi %x0, 0, %x3
	load %x0, $n, %x4
	subi %x4, 1, %x4
oloop:
	beq %x3, %x4, endl
	addi %x0, 0, %x5
	sub %x4, %x3, %x6
iloop:
	beq %x5, %x6, reset
	load %x5, $a, %x7
	addi %x5, 1, %x8
	load %x8, $a, %x9
	blt %x7, %x9, swap
	addi %x5, 1, %x5
	jmp iloop
swap:
	add %x0, %x7, %x10
	add %x0, %x8, %x11
	store %x10, 0, %x11
	add %x0, %x9, %x10
	add %x0, %x5, %x11
	store %x10, 0, %x11
	jmp loopend
reset:
	addi %x3, 1, %x3
	jmp oloop
loopend:
	addi %x5, 1, %x5
	jmp iloop
endl:
	end