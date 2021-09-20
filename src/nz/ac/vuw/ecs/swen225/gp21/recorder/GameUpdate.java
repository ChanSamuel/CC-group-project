package nz.ac.vuw.ecs.swen225.gp21.recorder;

public interface GameUpdate {
    public boolean isPlayerUpdate();
    public void applyAction();
    public void undoAction();
    public long getTimeStamp();
    public long getUpdateCount();
}
