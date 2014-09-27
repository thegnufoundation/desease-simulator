package dsmv_simulation;

import java.util.ArrayList;
import java.util.List;

public class Agent {
    
    private final int age;
    
    // Change the working/home area to the class of Place
    private final int working_area;
    private final int home_area;
    private final List<Agent> friends;
    private final Activities activities;
    private int current_area;
    private Activity current_activity;
    private boolean isReserved;
    
    public Agent(int age, int working_area, int home_area, int activity_hours[]){
        this.age = age;
        this.working_area = working_area;
        this.home_area = home_area;
        this.activities = new Activities(activity_hours);
        this.friends = new ArrayList<>();
        this.current_activity = Activity.SLEEPING;
        this.isReserved = false;
    }
    
    public Activities getActivities(){
        return this.activities;
    }
    
    public int getWorkingArea(){
        return this.working_area;
    }
            
    public int getHomeArea(){
        return this.home_area;
    }        
    
    public void setCurrentArea(int area_id){
        this.current_area = area_id;
    }
    
    public int getCurrentArea(){
        return this.current_area;
    }
    
    public Activity getCurrentActivity(){
        return this.current_activity;
    }
    
    public void setCurrentActivity(Activity activity){
        this.current_activity = activity;
    }
    
    private void goHome(){
        this.current_area = this.home_area;
    }
}
