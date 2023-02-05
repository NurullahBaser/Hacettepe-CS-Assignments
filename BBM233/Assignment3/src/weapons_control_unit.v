`timescale 1us/1ns

module weapons_control_unit(
    input target_locked,
    input clk,
    input rst,
    input fire_command,
    output reg launch_missile,
    output reg [3:0] remaining_missiles,
    output reg [1:0] WCU_state
);

    // states for the state machine
    parameter IDLE = 2'b00;
    parameter TARGET_LOCKED = 2'b01;
    parameter FIRE = 2'b10;
    parameter OUT_OF_AMMO = 2'b11;

     // state machine to control the operation of the weapons control unit
    always @(posedge clk) begin
        if (rst) begin // reset the state and outputs
            WCU_state <= IDLE;
            launch_missile <= 0;
            remaining_missiles <= 4;
        end else begin
            case (WCU_state)
                IDLE: begin
                    if(target_locked) begin // go to TARGET_LOCKED state if a target is locked
                        WCU_state <= TARGET_LOCKED;
                    end
                end
                TARGET_LOCKED: begin
                    if(!target_locked) begin  // go to IDLE if the target is no longer locked
                        WCU_state <= IDLE;
                    end
                    else if (fire_command) begin  // go to FIRE state and launch a missile if the fire command is received
                        WCU_state <= FIRE;
                        launch_missile <= 1;
                        if (remaining_missiles > 0) begin // decrement the remaining missiles if there are any left
                            remaining_missiles <= remaining_missiles -1;
                        end
                    end
                end
                FIRE: begin
                    if (target_locked && remaining_missiles > 0) begin // go to TARGET_LOCKED if the target is still locked and there are missiles remaining
                        WCU_state <= TARGET_LOCKED;
                        launch_missile <= 0;
                    end 
                    else if (!target_locked && remaining_missiles > 0) begin // go to IDLE if the target is no longer locked and there are missiles remaining
                        WCU_state <= IDLE;
                        launch_missile <= 0;
                    end 
                    else begin // go to OUT_OF_AMMO if there are no missiles remaining
                        WCU_state <= OUT_OF_AMMO;
                        launch_missile <= 0;
                    end
                end
            endcase
        end
    end

    // always block to cancel the launch if the target is no longer locked
    always @(negedge target_locked) begin
        launch_missile <= 0;
        WCU_state <= IDLE;
    end
endmodule