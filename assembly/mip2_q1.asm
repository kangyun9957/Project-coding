        .data
c: .word 5
d: .word 7
t: .word 1
        .text

main:
        lw $t0, c
        lw $t1, d
        lw $t2, t
        sub $s0, $t0, $t1
        sub $s1, $t1, $t0

        slt $s2, $s0, $s1 

        bne $s2, $t2, Else  
        sub $s3, $t1, $t0
        j Exit 
        Else: 
        sub $s3, $t0, $t1
        Exit:
        move $a0, $s3
        li $v0, 1
        syscall
        li $v0, 10
        syscall


