package dsmv_simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Agent {
    
    private final int age;
    private final double leisureProb;
    private final Place workingPlace;
    private final Place homePlace;
    private final Activities activities;
    private Place currentPlace;
    private Activity current_activity;
    private List<Agent> friends;
    private boolean isReserved;
    private int current_activity_hours[];
    
    private Activities current_activity_hours;
    
    public Agent(int age, Place workingPlace, Place homePlace, int activity_hours[], double leisureProb){
        this.age = age;
        this.workingPlace = workingPlace;
        this.homePlace = homePlace;
        this.activities = new Activities(activity_hours);
        this.friends = new ArrayList<>();
        this.current_activity_hours = activity_hours.clone();
        this.leisureProb = leisureProb;
        this.reset();
    }
    
    public Activities getActivities(){
        return this.activities;
    }
    
    public Place getWorkingArea(){
        return this.workingPlace;
    }
            
    public Place getHomeArea(){
        return this.homePlace;
    }        
    
    public void setCurrentArea(Place p){
        this.currentPlace = p;
    }
    
    public Place getCurrentPlace(){
        return this.currentPlace;
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
        this.currentPlace = this.homePlace;
    }
    
    public void clock(){
        if(current_activity_hours[4]==1)
            reset();
        else{
            Activity nextActivity = getNextActivity(this.current_activity);
            this.current_activity = nextActivity;
        }
    }
    
    private void reset(){
        this.isReserved = false;
        this.current_activity = Activity.WORKING;
        this.current_activity_hours = activities.getActivityHours();
        boolean haveLeisure = makeLeisureDecision(this.leisureProb);
        if(!haveLeisure){
            this.current_activity_hours[2] = 0;
            this.current_activity_hours[3] += this.activities.getLeisuringHours();
        }
    }
    
    private boolean makeLeisureDecision(double threshold){
        double p = new Random().nextDouble();
        if(p<=threshold)
            return true;
        return false;
    }
    
    private Activity getNextActivity(Activity currentActivity){
        Activity nextActivity = currentActivity;
        this.current_activity_hours[currentActivity.getValue()]--;
        if(this.current_activity_hours[currentActivity.getValue()]<=0)
            nextActivity = getNextInOrderActivity(currentActivity);
        return nextActivity;
    }
    
    private Activity getNextInOrderActivity(Activity currentActivity){
        Activity nextActivity = currentActivity;
        for(int i=1;i<=5;i++){
            if(this.current_activity_hours[(currentActivity.getValue()+i)%5]>0){
                nextActivity = Activity.values()[(currentActivity.getValue()+i)%5];
                break;
            }     
        }
        return nextActivity;
    }
 
}
