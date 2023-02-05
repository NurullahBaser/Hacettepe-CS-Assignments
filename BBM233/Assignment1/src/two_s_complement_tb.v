`timescale 1ns/10ps
module two_s_complement_tb;

   // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!

   reg [3:0] In;
   reg [3:0] count = 4'b0000;
   wire [3:0] Out;

   two_s_complement DUT(.In(In), .Out(Out));

   initial begin
      $dumpfile("two_s_complement.vcd");
      $dumpvars;
      for(integer i=0;i<16;i++) begin
         {In[3],In[2],In[1],In[0]} = count;
         count += 1;
         #10;
      end
      $finish;
   end
endmodule 