package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.util.List;

/**
 * A wrapper class for saving a recording. Contains the list of ticks and the level.
 * 
 * @author Peter Liley
 */
public class Recording {
    private final List<TickTemp> ticks;
    private final int level;

    public Recording(List<TickTemp> ticks, int level) {
        this.ticks = ticks;
        this.level = level;
    }

    /**
     * Get list of ticks in recording.
     * 
     * @return list of ticks in recording
     */
    public List<TickTemp> getTicks() {
        return ticks;
    }

    /**
     * Get level to play recording on.
     * 
     * @return level this recording relates to.
     */
    public int getLevel() {
        return level;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + level;
        result = prime * result + ((ticks == null) ? 0 : ticks.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Recording other = (Recording) obj;
        if (level != other.level)
            return false;
        if (ticks == null) {
            if (other.ticks != null)
                return false;
        } else if (!ticks.equals(other.ticks))
            return false;
        return true;
    }

    
}
