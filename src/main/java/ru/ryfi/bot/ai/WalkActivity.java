/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.ai;


import lombok.extern.log4j.Log4j2;
import ru.ryfi.bot.network.Bot;
import ru.ryfi.bot.network.BotState;
import ru.ryfi.bot.world.World;
import ru.ryfi.bot.world.block.BlockType;
import ru.ryfi.bot.world.pathfinding.PathNode;
import ru.ryfi.bot.world.pathfinding.PathSearch;
import ru.ryfi.bot.world.position.BlockLocation;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
@Log4j2
public class WalkActivity extends Activity{

    private static double defaultSpeed = 0.25, defaultJumpFactor = 3, defaultFallFactor = 4, defaultLiquidFactor = 0.5;
    private static int defaultTimeout = 60000;

    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private final BlockLocation target;

    private final long startTime;

    private Future<PathNode> thread;
    private PathNode nextStep;
    private int ticksSinceStepChange = 0;
    private int timeout = defaultTimeout;
    private double speed = defaultSpeed, jumpFactor = defaultJumpFactor, fallFactor = defaultFallFactor, liquidFactor = defaultLiquidFactor;
    private final Bot bot;

    public WalkActivity(BlockLocation target, boolean keepWalking, Bot bot) {
        this.bot = bot;
        this.target = target;
        log.info("Walking");
        bot.setState(BotState.PATHCALC);
        if(keepWalking) {
            Activity activity = bot.getActivity();
            if(activity != null && activity instanceof WalkActivity && ((WalkActivity) activity).isMoving()) {
                WalkActivity walkActivity = (WalkActivity) activity;
                nextStep = walkActivity.nextStep;
                ticksSinceStepChange = walkActivity.ticksSinceStepChange;
            }
        }
        thread = service.submit(new Callable<PathNode>() {
            @Override
            public PathNode call() throws Exception {
                World world = bot.getWorld();
                log.info("Calculating path");
                if(world == null || target == null)
                    return null;
                BlockLocation ourLocation = bot.getWorldLocation().toBlockLocation();
                PathSearch search = world.getPathFinder().provideSearch(ourLocation, target);
                while(!search.isDone() && (thread == null || !thread.isCancelled())) {
                    log.debug("Stepping...");
                    search.step();
                }
                bot.setState(BotState.WALKING);
                return search.getPath();
            }
        });
        startTime = System.currentTimeMillis();

    }

    public BlockLocation getTarget() {
        return target;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * Walk speed, in blocks/tick. Default is 0.15.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Set walk speed.
     *
     * @param speed
     *            Walk speed, in blocks/tick. Default is 0.15.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getJumpFactor() {
        return jumpFactor;
    }

    public void setJumpFactor(double jumpFactor) {
        this.jumpFactor = jumpFactor;
    }

    public double getFallFactor() {
        return fallFactor;
    }

    public void setFallFactor(double fallFactor) {
        this.fallFactor = fallFactor;
    }

    public double getLiquidFactor() {
        return liquidFactor;
    }

    public void setLiquidFactor(double liquidFactor) {
        this.liquidFactor = liquidFactor;
    }
    private boolean isActive;
    @Override
    public void run() {
        if(thread != null && !thread.isDone()) {
            if(timeout > 0 && System.currentTimeMillis() - startTime > timeout) {
                thread.cancel(true);
                thread = null;
                nextStep = null;
                bot.setState(BotState.WAITING);

                return;
            }
        } else if(thread != null && thread.isDone() && !thread.isCancelled()) {
            try {
                nextStep = thread.get();
                log.info("Path found, walking...");
                ticksSinceStepChange = 0;
                bot.setState(BotState.WALKING);
            } catch(Exception exception) {
                exception.printStackTrace();
                nextStep = null;
                bot.setState(BotState.WAITING);
                return;
            } finally {
                thread = null;
            }
        }
        if(nextStep != null) {

            log.debug(" -> Moving from " + bot.getWorldLocation().toBlockLocation()+ " to " + nextStep);
            if(nextStep.getNext() != null && bot.getWorldLocation().toBlockLocation().distanceSq(nextStep.getNext().getLocation()) < 0.2) {
                nextStep = nextStep.getNext();
                ticksSinceStepChange = 0;
            }
            if(bot.getWorldLocation().toBlockLocation().distanceSq(nextStep.getLocation()) > 4) {
                nextStep = null;
                return;
            }
            ticksSinceStepChange++;
            if(ticksSinceStepChange > 80) {
                nextStep = null;
                return;
            }
            double speed = this.speed;
            BlockLocation location = nextStep.getLocation();
            BlockLocation block = bot.getWorldLocation().toBlockLocation();
            double x = location.getX() + 0.5, y = location.getY(), z = location.getZ() + 0.5;
            boolean inLiquid = bot.getWorld().getBlock(bot.getWorldLocation().toBlockLocation().add(0,-1,0)).getMaterial() == BlockType.WATER;

            if(BlockType.getById(bot.getWorld().getBlockIdAt(block.offset(0, -1, 0))) == BlockType.SOUL_SAND) {
                if(BlockType.getById(bot.getWorld().getBlockIdAt(location.offset(0, -1, 0))) == BlockType.SOUL_SAND)
                    y -= 0.12;
                speed *= liquidFactor;
            } else if(inLiquid)
                speed *= liquidFactor;
            if(bot.getWorldLocation().getY() != y) {
                if(!inLiquid && !bot.getWorld().getPathFinder().getWorldPhysics().canClimb(block))
                    if(bot.getWorldLocation().getY() < y)
                        speed *= jumpFactor;
                    else
                        speed *= fallFactor;
                bot.getWorldLocation().setY(bot.getWorldLocation().getY() + (bot.getWorldLocation().getY() < y ? Math.min(speed, y - bot.getWorldLocation().getY()) : Math.max(-speed, y - bot.getWorldLocation().getY())));
            }
            if(bot.getWorldLocation().getX() != x)
                bot.getWorldLocation().setX(bot.getWorldLocation().getX() + (bot.getWorldLocation().getX() < x ? Math.min(speed, x - bot.getWorldLocation().getX()) : Math.max(-speed, x - bot.getWorldLocation().getX())));
            if(bot.getWorldLocation().getZ() != z)
                bot.getWorldLocation().setZ(bot.getWorldLocation().getZ() + (bot.getWorldLocation().getZ() < z ? Math.min(speed, z - bot.getWorldLocation().getZ()) : Math.max(-speed, z - bot.getWorldLocation().getZ())));

            if(bot.getWorldLocation().getX() == x && bot.getWorldLocation().getY() == y && bot.getWorldLocation().getZ() == z) {
                nextStep = nextStep.getNext();
                ticksSinceStepChange = 0;
                if(nextStep == null){
                    bot.setState(BotState.WAITING);
                }
            }
        }

    }


    @Override
    public void stop() {
        if(thread != null && !thread.isDone())
            thread.cancel(true);
        nextStep = null;
        bot.setState(BotState.WAITING);
    }
    public boolean isMoving() {
        return nextStep != null;
    }

    @Override
    public boolean isActive() {
        return thread != null || nextStep != null;
    }

    public static double getDefaultSpeed() {
        return defaultSpeed;
    }

    public static void setDefaultSpeed(double defaultSpeed) {
        WalkActivity.defaultSpeed = defaultSpeed;
    }

    public static double getDefaultJumpFactor() {
        return defaultJumpFactor;
    }

    public static void setDefaultJumpFactor(double defaultJumpFactor) {
        WalkActivity.defaultJumpFactor = defaultJumpFactor;
    }

    public static double getDefaultFallFactor() {
        return defaultFallFactor;
    }

    public static void setDefaultFallFactor(double defaultFallFactor) {
        WalkActivity.defaultFallFactor = defaultFallFactor;
    }

    public static double getDefaultLiquidFactor() {
        return defaultLiquidFactor;
    }

    public static void setDefaultLiquidFactor(double defaultLiquidFactor) {
        WalkActivity.defaultLiquidFactor = defaultLiquidFactor;
    }

    public static int getDefaultTimeout() {
        return defaultTimeout;
    }

    public static void setDefaultTimeout(int defaultTimeout) {
        WalkActivity.defaultTimeout = defaultTimeout;
    }
}
