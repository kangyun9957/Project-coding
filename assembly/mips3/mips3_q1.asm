    .data
prompt: .asciiz "Enter a : "
prompt2: .asciiz "Enter c : "
a: .word 0
c: .word 0
    .text

main:
    jal compare
compare:
    li $v0, 4
    la $a0, prompt
    syscall

    li $v0, 5
    syscall

    sw $v0, a

    li $v0, 4
    la $a0, prompt2
    syscall
    
    li $v0, 5
    syscall

    sw $v0, c
    lw $t0, a
    lw $t1, c

    bne $t0,$t1, compare
    li $v0, 10
    syscall
