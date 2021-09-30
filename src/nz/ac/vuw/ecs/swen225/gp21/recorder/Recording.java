package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.util.List;

/**
 * A wrapper class for saving a recording. Contains the list of updates and the level.
 * 
 * @author Peter Liley
 */
public class Recording {
    private final List<GameUpdate> updates;
    private final int level;

    /**
     * Makes a new recording object.
     * @param updates The list of commands that constitute this recording.
     * @param level The level to which this recording relates.
     */
    public Recording(List<GameUpdate> updates, int level) {
        this.updates = updates;
        this.level = level;
    }

    /**
     * Get list of updates in recording.
     * 
     * @return list of updates in recording
     */
    public List<GameUpdate> getUpdates() {
        return updates;
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
        result = prime * result + ((updates == null) ? 0 : updates.hashCode());
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
        if (updates == null) {
            if (other.updates != null)
                return false;
        } else if (!updates.equals(other.updates))
            return false;
        return true;
    }

    
}
