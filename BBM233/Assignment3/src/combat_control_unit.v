`timescale 1us/1ns

module combat_control_unit(
    input rst,
    input track_target_command, 
    input clk, 
    input radar_echo, 
    input fire_command,
    output [13:0] distance_to_target, 
    output trigger_radar_transmitter, 
    output launch_missile,
    output [1:0] TTU_state,
    output [1:0] WCU_state,
    output [3:0] remaining_missiles
);

    wire target_locked;

    target_tracking_unit TTU_System(rst,track_target_command,clk,radar_echo, //inputs
    trigger_radar_transmitter,distance_to_target,target_locked,TTU_state); //outputs

    weapons_control_unit WCU_System(target_locked,clk,rst,fire_command, //inputs
    launch_missile,remaining_missiles,WCU_state); //outputs

endmodule