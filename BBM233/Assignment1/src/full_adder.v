module full_adder(
    input A,
    input B,
    input Cin,
    output S,
    output Cout
);

    assign S = A ^ B ^ Cin;
    assign Cout = (A & B) | (Cin & A) | (Cin & B);

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!

endmodule