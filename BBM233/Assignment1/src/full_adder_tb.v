`timescale 1 ns/10 ps
module full_adder_tb;

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!

    reg A;
    reg B;
    reg Cin;
    wire S;
    wire Cout;

    full_adder DUT(A,B,Cin,S,Cout);

    initial begin

        $dumpfile("full_adder.vcd");
        $dumpvars;
        for(integer i = 0; i < 2; i++) begin
            Cin = i;
            for(integer j = 0; j < 2; j++) begin
                A = j;
                for(integer h = 0; h < 2; h++) begin
                    B = h;
                    #100;
                end;
            end;
        end;
    end;

endmodule