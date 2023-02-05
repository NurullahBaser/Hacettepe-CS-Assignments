module dff_sync_res(D, clk, sync_reset, Q);
    input D;
    input clk;
    input sync_reset;
    output reg Q;

    always @(posedge clk) begin
        if (sync_reset) // if reset is 1 than Q equals 0
            Q <= 1'b0;
        else // if reset is 0 than Q equals D
            Q <= D;
    end

endmodule