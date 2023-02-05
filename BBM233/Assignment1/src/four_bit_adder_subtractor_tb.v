`timescale 1ns/1ps
module four_bit_adder_subtractor_tb;

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
    
    reg[3:0] A,B;
    reg subtract;
    output[3:0] Result;
    output Cout;

    four_bit_adder_subtractor DUT(A,B,subtract,Result,Cout);


    initial begin
        $dumpfile("four_bit_adder_subtractor.vcd");
        $dumpvars;

        for(integer s = 0; s < 2; s++) begin
            subtract = s;
            for(integer j = 0; j < 16; j++) begin 
                A = j;
                for(integer h = 0; h < 16; h++) begin
                    B = h;
                    #100;
                end;
            end;
        end;
    end;


endmodule
