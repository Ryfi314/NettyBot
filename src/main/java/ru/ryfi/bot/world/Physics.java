/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.world;

import ru.ryfi.bot.network.Bot;
import ru.ryfi.bot.network.BotState;
import ru.ryfi.bot.world.block.BlockType;

public class Physics {
    public double gravity = 0.08; // blocks/tick^2 https://minecraft.gamepedia.com/Entity#Motion_of_entities
    public double airdrag = Math.round(1 - 0.02); // actually (1 - drag)
    public double yawSpeed = 3.0;
    public double pitchSpeed = 3.0;
    public double playerSpeed = 0.1;
    public double sprintSpeed = 0.3;
    public double sneakSpeed = 0.3;
    public double stepHeight = 0.6; // how much height can the bot step on without jump
    public double negligeableVelocity = 0.003; // actually 0.005 for 1.8; but seems fine
    public double soulsandSpeed = 0.4;
    public double honeyblockSpeed = 0.4;
    public double honeyblockJumpSpeed = 0.4;
    public double ladderMaxSpeed = 0.15;
    public double ladderClimbSpeed = 0.2;
    public double playerHalfWidth = 0.3;
    public double playerHeight = 1.8;
    public double waterInertia = 0.8;
    public double lavaInertia = 0.5;
    public double liquidAcceleration = 0.02;
    public double airborneInertia = 0.91;
    public double airborneAcceleration = 0.02;
    public double defaultSlipperiness = 0.6;
    public double outOfLiquidImpulse = 0.3;
    public double autojumpCooldown = 10; // ticks (0.5s)
    public double slowFalling = 0.125;
    public double movementSpeedAttribute = 0.1;
    public String sprintingUUID = "662a6b8d-da3e-4c1c-8813-96ea6097278d"; // SPEED_MODIFIER_SPRINTING_UUID is from LivingEntity.java

    public double waterGravity = gravity / 16;
    public double lavaGravity = gravity / 4;

    private final Bot bot;
    public Physics(Bot bot){
        this.bot = bot;
    }

    double vertical_speed = 0;
    public void fallTick() {
        if (bot.getState() == BotState.WAITING) {
            if (bot.getWorldLocation() != null) {

                if (bot.getWorld().getBlock(bot.getWorldLocation().toBlockLocation().add(0, -1, 0)).getMaterial() == BlockType.AIR) {

                    bot.getWorldLocation().add(0,-1,0);

                }

            }
        }
    }
}
