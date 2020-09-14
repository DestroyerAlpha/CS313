	.data
a:
	10
	.text
main:
	load %x0, $a, %x3
	addi %x0, 01, %x4
	and %x4, %x3, %x5
	bgt %x4, %x0, success
	beq %x5, %x4, endl	
success:
	addi %x0, 1, %x10
	end
endl:
	subi %x0, 1, %x10
	end