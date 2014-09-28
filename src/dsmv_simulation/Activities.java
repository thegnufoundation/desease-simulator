package dsmv_simulation;

import java.util.stream.IntStream;

public class Activities {
    
    private final int working_hours;
    private final int commuting_hours;
    private final int leisuring_hours;
    private final int resting_hours;
    private final int sleeping_hours;
    
    public Activities(int activity_hours[]){
        if(IntStream.of(activity_hours).sum()!=24)
            throw new IllegalArgumentException("The sum of the hours has to be 24.");
        this.working_hours = activity_hours[0];
        this.commuting_hours = activity_hours[1];
        this.leisuring_hours = activity_hours[2];
        this.resting_hours = activity_hours[3];
        this.sleeping_hours = activity_hours[4];
    }
    
    public int getWorkingHours(){
        return this.working_hours;
    }
    
    public int getCommutingHours(){
        return this.commuting_hours;
    }
    
    public int getLeisuringHours(){
        return this.leisuring_hours;
    }
    
    public int getSleepingHours(){
        return this.sleeping_hours;
    }
    
    public int getRestingHours(){
        return this.resting_hours;
    }
    
    public int[] getActivityHours(){
        int activity_hours[] = {working_hours,commuting_hours,leisuring_hours,
                                resting_hours,sleeping_hours};
        return activity_hours;
    }
    
}