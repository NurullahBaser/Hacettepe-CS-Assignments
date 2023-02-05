module two_s_complement(In,Out);
    input [3:0] In;
    output [3:0] Out;
    
    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
    assign Out = (~In) + 1;
endmodule  
