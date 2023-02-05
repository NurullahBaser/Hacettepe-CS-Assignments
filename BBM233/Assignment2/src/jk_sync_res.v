module jk_sync_res(J, K, clk, sync_reset, Q);
    input J;
    input K;
    input clk;
    input sync_reset;
    output reg Q;

    always @(posedge clk) begin
        if (sync_reset) //if reset is 1 than Q = 0
            Q <= 1'b0;
        else begin
            if (J && K) // 11 = complement
                Q <= ~Q;
            else if (J && !K) // 10 reset 
                Q <= 1'b1;
            else if (!J && K) // 01 set
                Q <= 1'b0;
        end
    end

endmodule