module four_bit_2x1_mux(In_1, In_0, Select, Out);
	input [3:0] In_1;
	input [3:0] In_0;
	input Select;
	output [3:0] Out;
	
	// Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
	assign Out = Select ? In_1 : In_0;
endmodule
