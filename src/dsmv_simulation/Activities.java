package dsmv_simulation;

import java.util.stream.IntStream;

public class Activities {
    
    private int activity_hours[];
    
    public Activities(int activity_hours[]){
        if(IntStream.of(activity_hours).sum()!=24)
            throw new IllegalArgumentException("The sum of the hours has to be 24.");
        this.activity_hours = activity_hours.clone();
    }
    
    public void setWorkingHours(int hours){
        this.activity_hours[0] = hours;
    }
    
    public void setCommutingHours(int hours){
        this.activity_hours[1]  = hours;
    }
    
    public void setLeisuringHours(int hours){
        this.activity_hours[2]  = hours;
    }
    
    public void setRestingHours(int hours){
        this.activity_hours[3]  = hours;
    }
     
    public void setSleepingHours(int hours){
        this.activity_hours[4]  = hours;
    }   
    
    public void decreaseWorkingHours(){
        this.activity_hours[0]--;
    }
    
    public void decreaseCommutingTime(){
        this.activity_hours[1]--;
    }
    
    public void decreaseLeisuringHours(){
        this.activity_hours[2]--;
    }
    
    public void decreaseRestingHours(){
        this.activity_hours[3]--;
    }
    
    public void decreaseSleepingHours(){
        this.activity_hours[4]--;
    }
    
    public int getWorkingHours(){
        return this.activity_hours[0];
    }
    
    public int getCommutingHours(){
        return this.activity_hours[1];
    }
    
    public int getLeisuringHours(){
        return this.activity_hours[2];
    }
    
    public int getRestingHours(){
        return this.activity_hours[3];
    }
    
    public int getSleepingHours(){
        return this.activity_hours[4];
    }
    
    public int[] getActivityHours(){
        return this.activity_hours.clone();
    }
    
    public boolean hoursLeft(){
        if(this.activity_hours[4]>1)
                return true;
        return false;
    }
    
    public void set(int i, int value){
        this.activity_hours[i] = value;
    }
    
    public int get(int i){
        return this.activity_hours[i];
    }
    
    public void decrease(int i){
        this.activity_hours[i]--;
    }    
    
    public void increase(int i){
        this.activity_hours[i]++;
    }  
}