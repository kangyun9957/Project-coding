    .data


    .text
main:
    li $v0, 5
    syscall
    move $t0, $v0
    li $v0, 5
    syscall
    move $t1, $v0
    jal loop
    lw $a0,($sp)
    li $v0,1
    syscall
    li $v0,10
    syscall

loop:
    addi $sp,$sp,-4
    div $t0, $t1
    move $t0, $t1
    mfhi $t1
    sw $t0, 4($sp)
    addi $sp, $sp, 4
    bne $t1,0,loop
    jr $ra
    
    





    


