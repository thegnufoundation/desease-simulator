package dsmv_simulation;

public enum Activity {
    WORKING(0),
    COMMUTING(1),
    LEASURING(2),
    RESTING(3),
    SLEEPING(4);
    
    private final int value;

    private Activity(final int newValue) {
            value = newValue;
    }
    public int getValue(){ 
        return value; 
    }
}
