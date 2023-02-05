`timescale 1ns/1ps

module counter_tb;
reg reset, clk, mode;
    wire [2:0] count;
    integer i = 0;

    //counter_d uut(reset, clk, mode, count);
    counter_jk c1(reset, clk, mode, count);

    initial begin
        $dumpfile("counter.vcd");
        $dumpvars;
        reset = 1;#50; // in the beginning reset is 1 and system is closed
        reset = 0;#420; // reset is 0 for running code
        reset = 1;#50; // at the beginning reset is 1 and system is closed
        $finish;
    end

    initial begin // clock timer
        clk = 0;
        forever begin
            #10;
            clk = ~clk;
        end
    end

    always@(posedge clk) begin //if i is below than 12 make mode 0 for running binary counter else make mode 1 for running gray counter
        #10;
        mode = i<12 ? 0 : 1;
        i = i+1;
    end

endmodule