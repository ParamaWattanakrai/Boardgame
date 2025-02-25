package src.utils;

import src.entities.Civilian;

public class ActorActionPair {
    private Civilian civilian;
    private Runnable runnable;
    
    public ActorActionPair(Civilian civilian, Runnable runnable) {
        this.civilian = civilian;
        this.runnable = runnable;
    }

    public Civilian getCivilian() {
        return civilian;
    }
    public Runnable getRunnable() {
        return runnable;
    }
}
