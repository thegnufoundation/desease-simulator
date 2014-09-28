/*
 * The MIT License
 *
 * Copyright 2014 Christos Petropoulos, Paula Subías.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package dsmv_simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Christos Petropoulos, Paula Subías
 */
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
    private Activities currentActivityHours;
    
    public Agent(int age, Place workingPlace, Place homePlace, int activity_hours[], double leisureProb){
        this.age = age;
        this.homePlace = homePlace;
        this.workingPlace = workingPlace;
        this.leisureProb = leisureProb;
        this.friends = new ArrayList<>();
        this.activities = new Activities(activity_hours);
        this.currentActivityHours = new Activities(activity_hours);
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
        if(currentActivityHours.hoursLeft()==true){
            Activity nextActivity = getNextActivity(this.current_activity);
            this.current_activity = nextActivity;
        }
        else
            reset();
    }
    
    private void reset(){
        this.isReserved = false;
        this.current_activity = Activity.WORKING;
        this.currentActivityHours = new Activities(activities.getActivityHours());
        boolean haveLeisure = makeLeisureDecision(this.leisureProb);
        if(!haveLeisure){
            this.currentActivityHours.setLeisuringHours(0);
            this.currentActivityHours.setRestingHours
                                      (this.activities.getLeisuringHours()
                                       +this.activities.getRestingHours());
        }
    }
    
    private boolean makeLeisureDecision(double threshold){
        double p = new Random().nextDouble();
        return p<=threshold;
    }
    
    private Activity getNextActivity(Activity currentActivity){
        Activity nextActivity = currentActivity;
        this.currentActivityHours.decrease(currentActivity.getValue());
        if(this.currentActivityHours.get(currentActivity.getValue())<=0)
            nextActivity = getNextInOrderActivity(currentActivity);
        return nextActivity;
    }
    
    private Activity getNextInOrderActivity(Activity currentActivity){
        Activity nextActivity = currentActivity;
        for(int i=1;i<=5;i++){
            if(this.currentActivityHours.get((currentActivity.getValue()+i)%5)>0){
                nextActivity = Activity.values()[(currentActivity.getValue()+i)%5];
                break;
            }     
        }
        return nextActivity;
    }
 
}
