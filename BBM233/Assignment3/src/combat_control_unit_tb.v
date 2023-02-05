`timescale 1us/1ns

module kizilelma_tb;

    reg rst;
    reg track_target_command; 
    reg clk; 
    reg radar_echo; 
    reg fire_command;

    wire [13:0] distance_to_target; 
    wire trigger_radar_transmitter; 
    wire launch_missile;

    wire [1:0] TTU_state;
    wire [1:0] WCU_state;
    wire [3:0] remaining_missiles;

    combat_control_unit uut(
        rst, 
        track_target_command,  
        clk,  
        radar_echo,  
        fire_command, 
        distance_to_target, 
        trigger_radar_transmitter,  
        launch_missile, 
        TTU_state, 
        WCU_state, 
        remaining_missiles);

    initial begin
        $dumpfile("combat.vcd");
        $dumpvars;
        rst = 1; // BASE
        track_target_command = 0;
        radar_echo = 0;
        fire_command = 0;
        #10;
        rst = 0; // START
        #20;
        track_target_command = 1;
        #10;
        track_target_command = 0;
        #70
        radar_echo = 1; //FIRTS ECHO
        #2
        radar_echo = 0;
        #99;
        fire_command = 1; // FIRE 1
        #20;
        fire_command = 0;
        #20;
        fire_command = 1; // FIRE 2
        #20;
        fire_command = 0;
        #200;
        track_target_command = 1;
        #10;
        track_target_command = 0;
        #200;
        track_target_command = 1;
        #10;
        track_target_command = 0;
        #70;
        radar_echo = 1; // SECOND ECHO
        #2;
        radar_echo = 0;
        fire_command = 1; // FIRE 3
        #19;
        fire_command = 0;
        #10;
        track_target_command = 1;
        #10;
        track_target_command = 0;
        #55;
        radar_echo = 1; // THIRD ECHO
        #2;
        radar_echo = 0;
        fire_command = 1; // FIRE 4
        #19;
        fire_command = 0;
        #20;
        fire_command = 1; // FIRE 5
        #20;
        fire_command = 0;
        #100;
        $finish;
    end


    initial begin
        clk = 0;
        forever begin
            #5;
            clk = ~clk;
        end
    end

endmodule