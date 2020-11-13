    .data
a: .word 2
    .text
main:
    li $v0, 5
    syscall
    move $s0, $v0
    lw $s2, a
    addi $s1, $s0, 1 
    mul $s3, $s0, $s1
    div $s3, $s3, $s2

    move $a0, $s3


    li $v0, 1
    syscall
    li $v0, 10
    syscall
    

    
