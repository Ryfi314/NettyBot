package ru.ryfi.bot.world.chunk.palette;
import lombok.EqualsAndHashCode;

/**
 * A global palette that maps 1:1.
 */
@EqualsAndHashCode
public class GlobalPalette implements Palette {
    @Override
    public int size() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int stateToId(int state) {
        return state;
    }

    @Override
    public int idToState(int id) {
        return id;
    }
}
