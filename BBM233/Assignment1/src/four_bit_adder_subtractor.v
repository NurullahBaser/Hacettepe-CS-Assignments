module four_bit_adder_subtractor(A, B, subtract, Result, Cout);
    input [3:0] A;
    input [3:0] B;
    input subtract;
    output [3:0] Result;
    output Cout;

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!

    wire[3:0] negatif_B;
    wire[3:0] sub_or_add_B;

    two_s_complement make_negatif(B,negatif_B);
    four_bit_2x1_mux sub_or_add(negatif_B,B,subtract,sub_or_add_B);
    four_bit_rca result(A,sub_or_add_B,1'B0,Result,Cout);

endmodule
