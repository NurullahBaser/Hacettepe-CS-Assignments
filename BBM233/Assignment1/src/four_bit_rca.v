module four_bit_rca(
    input [3:0] A,
    input [3:0] B,
    input Cin,
    output [3:0] S,
    output Cout
);

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
    wire[2:0] Carries;
    full_adder A1(.A(A[0]), .B(B[0]), .Cin(Cin), .S(S[0]), .Cout(Carries[0]));
    full_adder A2(.A(A[1]), .B(B[1]), .Cin(Carries[0]), .S(S[1]), .Cout(Carries[1]));
    full_adder A3(.A(A[2]), .B(B[2]), .Cin(Carries[1]), .S(S[2]), .Cout(Carries[2]));
    full_adder A4(.A(A[3]), .B(B[3]), .Cin(Carries[2]), .S(S[3]), .Cout(Cout));

endmodule