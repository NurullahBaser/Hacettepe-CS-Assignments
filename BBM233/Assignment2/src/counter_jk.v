module counter_jk(input reset, input clk, input mode, output [2:0] count);
    // This module implements a 3-bit counter using JK flip-flops
    // J and K inputs determine count based on mode and current count values

    // First JK flip-flop in the counter
    jk_sync_res jkffA(
        (count[1] & ~count[0] & mode) | (count[1] & count[0] & ~mode),
        (~count[1] & ~count[0] & mode) | (count[1] & count[0] & ~mode),
        clk,
        reset,
        count[2]
    );

    // Second JK flip-flop in the counter
    jk_sync_res jkffB(
        (count[0] & ~mode) | (~count[2] & count[0]),
        (count[2] & count[0]) | (count[0] & ~mode),
        clk,
        reset,
        count[1]
    );

    // Third JK flip-flop in the counter
    jk_sync_res jkffC(
        (~count[2] & ~count[1]) | (count[2] & count[1]) | (~mode),
        (~count[2] & count[1]) | (count[2] & ~count[1]) | (~mode),
        clk,
        reset,
        count[0]
    );

endmodule