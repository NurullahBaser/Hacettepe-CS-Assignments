module counter_d(input reset, input clk, input mode, output [2:0] count);
    // This module implements a 3-bit counter using D flip-flops
    // Input determines count based on mode and current count values

    // First D flip-flop in the counter
    dff_sync_res dffA((~mode & ~count[2] & count[1] & count[0]) |
    (~mode & count[2] & ~count[0]) |
    (~mode & count[2] & ~count[1]) |
    (mode & count[1] & ~count[0]) |
    (mode & count[2] & count[0]),
    clk, reset, count[2]);

    // Second D flip-flop in the counter
    dff_sync_res dffB((count[1] & ~count[0]) | 
    (~mode & ~count[1] & count[0]) | 
    (mode & ~count[2] & count[0]), 
    clk, reset, count[1]);

    // Third D flip-flop in the counter
    dff_sync_res dffC((~mode & ~count[0]) | 
    (mode & ~count[2] & ~count[1]) | 
    (mode & count[2] & count[1]), 
    clk, reset, count[0]);

endmodule