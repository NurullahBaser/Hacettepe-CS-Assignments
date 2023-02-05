`timescale 1ns/10ps
module four_bit_2x1_mux_tb;
	
	// Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
	
	reg [3:0] In_0;
	reg [3:0] In_1;
	reg Select;
	wire [3:0] Out;

	four_bit_2x1_mux DUT(In_1,In_0,Select,Out);

	initial begin
		$dumpfile("four_bit_2x1_mux.vcd");
    	$dumpvars;
		for(integer s = 0; s < 2 ; s++) begin
			Select = s;
			for(integer i = 0; i < 16 ; i++) begin
				In_1 = i;
				for(integer j = 0; j < 16 ; j++) begin
					In_0 = j;
					#100;
				end;
			end;
		end;
	end;

endmodule
