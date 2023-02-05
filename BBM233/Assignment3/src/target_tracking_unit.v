`timescale 1us/1ns

module target_tracking_unit(
    input rst,
    input track_target_command,
    input clk,
    input echo,
    output reg trigger_radar_transmitter,
    output reg [13:0] distance_to_target,
    output reg target_locked,
    output reg [1:0] TTU_state
);

    // states for the state machine
    parameter IDLE = 2'b00;
    parameter TRANSMIT = 2'b01;
    parameter LISTEN_FOR_ECHO = 2'b10;
    parameter TRACK = 2'b11;

    // registers to store timing information
    reg[63:0] transmit_time;
    reg[63:0] echo_timer;
    reg[63:0] target_update_timer;

    integer is_echo = 0;


    // state machine to control the operation of the target tracking unit
    always @(posedge clk) begin
        if(rst) begin // reset all outputs and state
            distance_to_target <= 0;
            trigger_radar_transmitter <= 0;
            target_locked <= 0;
            TTU_state <= IDLE;
        end
        else begin
            case (TTU_state)
                IDLE: begin
                    if(track_target_command) begin // go to TRANSMIT state
                        TTU_state <= TRANSMIT;
                        transmit_time <= $time;
                    end
                end
                TRANSMIT: begin
                    if($time - transmit_time >= 50) begin // wait for 50 time units than go to LISTEN_FOR_ECHO state
                        TTU_state <= LISTEN_FOR_ECHO;
                    end
                end
                LISTEN_FOR_ECHO: begin
                    if($time - echo_timer >= 100) begin // if no echo has been received after 100 time units, go to IDLE state
                        distance_to_target <= 0;
                        target_locked <= 0;
                        TTU_state <= IDLE;
                    end
                    else if(is_echo == 1) begin // if an echo has been received, go to TRACK state
                        is_echo = 0;
                        target_update_timer <= $time;
                        TTU_state <= TRACK;
                    end
                end
                TRACK: begin
                    if($time - target_update_timer >= 300) begin // if no new track_target_command has been received after 300 time units, go to IDLE state
                        distance_to_target <= 0;
                        target_locked <= 0;
                        TTU_state <= IDLE;
                    end
                    else if (track_target_command) begin // if a new track target command is received, go to TRANSMIT state
                        target_locked <= 0;
                        transmit_time <= $time;
                        TTU_state <= TRANSMIT;
                    end
                end
            endcase
        end
    end

    always @(*) begin
        if(track_target_command) begin // if a track_target_command has been received
            trigger_radar_transmitter <= 1; #50 // wait for 50 time units with 1 than assign it to 0
            trigger_radar_transmitter <= 0;
            echo_timer <= $time;
        end
        if(echo) begin // if an echo has been received
            is_echo = 1;
            // lock onto the target and calculate the distance to the target
            target_locked <= 1; 
            distance_to_target = (3 * 100 * ($time - echo_timer)) / 2;
        end
    end
endmodule