package dsmv_simulation;

import java.util.ArrayList;
import java.util.List;

public class Agent {
    
    private final int age;
    private final Place working_place;
    private final Place home_place;
    private final Activities activities;
    private Place current_place;
    private Activity current_activity;
    private List<Agent> friends;
    private boolean isReserved;
    private int current_activity_hours[];
    
    
    public Agent(int age, Place working_place, Place home_place, int activity_hours[]){
        this.age = age;
        this.working_place = working_place;
        this.home_place = home_place;
        this.activities = new Activities(activity_hours);
        this.friends = new ArrayList<>();
        this.current_activity = Activity.WORKING;
        this.isReserved = false;
        this.current_activity_hours = activity_hours.clone();
    }
    
    public Activities getActivities(){
        return this.activities;
    }
    
    public Place getWorkingArea(){
        return this.working_place;
    }
            
    public Place getHomeArea(){
        return this.home_place;
    }        
    
    public void setCurrentArea(Place p){
        this.current_place = p;
    }
    
    public Place getCurrentPlace(){
        return this.current_place;
    }
    
    public Activity getCurrentActivity(){
        return this.current_activity;
    }
    
    public void setCurrentActivity(Activity activity){
        this.current_activity = activity;
    }
    
    public void addFriend(Agent a){
        this.friends.add(a);
    }
    
    private void goHome(){
        this.current_place = this.home_place;
    }
    
    public void clock(){
        Activity nextActivity = getNextActivity(this.current_activity);
        this.current_activity = nextActivity;
    }
    
    public void reset(){
        this.current_activity_hours = activities.getActivityHours();
    }
    
    private Activity getNextActivity(Activity currentActivity){
        Activity nextActivity = currentActivity;
        this.current_activity_hours[currentActivity.getValue()]--;
        if(this.current_activity_hours[currentActivity.getValue()]==0)
            nextActivity = Activity.values()[(currentActivity.getValue()+1)%5];
        return nextActivity;
    }
    
}
