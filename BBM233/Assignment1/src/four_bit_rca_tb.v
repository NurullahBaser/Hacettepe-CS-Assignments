`timescale 1 ns/10 ps
module four_bit_rca_tb;

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
    reg[3:0] A,B;
    reg Cin;

    wire[3:0] S;
    wire Cout;

    four_bit_rca DUT(A,B,Cin,S,Cout);

    initial begin
        $dumpfile("four_bit_rca.vcd");
        $dumpvars;

        for(integer i = 0; i < 2; i++) begin
            Cin = i;
            for(integer j = 0; j < 16 ; j++) begin 
                A = j;
                for(integer h = 0; h < 16; h++) begin
                    B = h;
                    #100;
                end;
            end;
        end;
    end;

endmodule