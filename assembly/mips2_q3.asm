    .data
a: .word 100
    .text

main:
    lw $a1, a
    jal plus_one
    move $a0, $v1
    li $v0, 1
    syscall
    li $v0, 10
    syscall




plus_one:
    addi $v1, $a1, 1
    jr $ra

