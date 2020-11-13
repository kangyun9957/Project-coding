    .data
array: .space 20
c: .word 0
i: .word 0
j: .word 0
d: .word 10
    .text
main:
    la $t0, array
    lw $s0, c
    lw $s1, d
    lw $s2, i
    lw $s3, j
    jal loop
    
    
loop:
    sw $s1, 0($t0)
    addi $t0, $t0, 4
    addi $s1, $s1, -1
    addi $s0, $s0, 1   
    bne $s0,5,loop


  
    lw $t2, -4($t0)
    lw $t3, -8($t0)
    lw $t4, -12($t0)
    lw $t5, -16($t0)
    lw $t6, -20($t0)
    li $v0, 10
    syscall
    
    