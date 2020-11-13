    .data
    .text
main:
    li $v0, 5
    syscall
    move $t0, $v0
    sw $t0, ($sp)
    jal recursion
    lw $a0, ($sp)
    li $v0, 1
    syscall
    li $v0, 10
    syscall
recursion:
    addi $sp, $sp, -4
    lw $t1, 4($sp)
    addi $t0,$t0,-1
    mul $t1, $t1, $t0
    sw $t1, 4($sp)
    addi $sp, $sp, 4
    bne $t0,1,recursion
    jr $ra
    

	