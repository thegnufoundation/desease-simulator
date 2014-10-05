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

import java.util.Random;

/**
 *
 * @author Christos Petropoulos, Paula Subías
 */
public class Agent {
    
    private final double leisureProb;
    private final Place workingPlace;
    private final Place homePlace;
    private final Activities activities;
    private Place currentPlace; 
    private Place leisurePlace;
    private Activity current_activity;
    private Activities currentActivityHours;
    private HealthStatus healthStatus;
    private Infection infection;
    private Building[] leisureBuildings;
    
    public Agent(Place homePlace,Place workingPlace,Building[] leisureBuildings, 
                 int workingHours, int sleepingHours, double leisureProb){
        
        int activity_hours[] = getActivities(workingHours,sleepingHours,
                                             homePlace,workingPlace);
        this.homePlace = homePlace;
        this.leisureProb = leisureProb;
        this.workingPlace = workingPlace;
        this.leisureBuildings = leisureBuildings;
        this.activities = new Activities(activity_hours);
        this.currentActivityHours = new Activities(activity_hours);
        this.currentPlace = new Place(homePlace);
        this.leisurePlace = new Place(null,0);
        this.healthStatus = HealthStatus.SUSPECTIBLE;
        this.infection = null;
        this.reset();  
    }
 
    public void Infect(Infection infection){
        this.infection = new Infection(infection);
    }

    public HealthStatus getHealthStatus(){
        return this.healthStatus;
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
    
    public void setCurrentPlace(Place p){
        this.currentPlace.set(p);
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
 
    public void clock(){
        if(this.infection!=null){
            this.healthStatus = this.infection.getInfectionStatus();
            this.infection.clock();
        }
        
        if(currentActivityHours.hoursLeft()==true){
            performActivity(this.current_activity);
            Activity nextActivity = getNextActivity(this.current_activity);
            this.current_activity = nextActivity;
        }
        else
            reset();
    }

    private int[] getActivities(int workingHours, int sleepingHours, 
                                Place homePlace, Place workingPlace){
        
        int commutingHours = Route.getPathDistance(homePlace.getArea(),
                                                   workingPlace.getArea());
        workingHours += commutingHours;
        return new int[]{workingHours,0,
                        (24-workingHours-sleepingHours),sleepingHours};
    }    
    
    private int getLeisureTime(Area leisureArea){
        int leisureTime = 0;
        int mlt = getMaxLeisureTime(leisureArea);
        leisureTime += Route.getPathDistance(workingPlace.getArea(), leisureArea);
        leisureTime += new Random().nextInt(mlt)+1;
        return leisureTime;
    }
 
    private int getMaxLeisureTime(Area leisureArea){
        int hours = 24;
        hours -= currentActivityHours.getSleepingHours();
        hours -= currentActivityHours.getWorkingHours();
        hours -= Route.getPathDistance(workingPlace.getArea(), leisureArea);
        hours -= Route.getPathDistance(leisureArea, homePlace.getArea());
        hours -= 2;
        return hours;
    }
    
    private int getFreeHours(){
        int hours = 24;
        hours -= currentActivityHours.getSleepingHours();
        hours -= currentActivityHours.getWorkingHours();
        return hours;
    }
    
    private int getRestingHours(){
        int hours = 24;
        hours -= currentActivityHours.getSleepingHours();
        hours -= currentActivityHours.getWorkingHours();
        hours -= currentActivityHours.getLeisuringHours();
        return hours;
    }
    
    private void reset(){
        this.setCurrentPlace(this.homePlace);
        this.current_activity = Activity.SLEEPING;
        this.currentActivityHours = new Activities(activities.getActivityHours());
        boolean haveLeisure = makeLeisureDecision(this.leisureProb);
        if(haveLeisure){
            Area leisureArea = Area.getRandom();
            int lid = new Random().nextInt(leisureBuildings.length-1);
            this.leisurePlace.set(leisureBuildings[lid].getPlace());
            this.currentActivityHours.setLeisuringHours(this.getLeisureTime(leisureArea));
            this.currentActivityHours.setRestingHours(getRestingHours());
        }
        else{
            this.currentActivityHours.setLeisuringHours(0);
            this.currentActivityHours.setRestingHours(getFreeHours());
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
        for(int i=1;i<=4;i++){
            if(this.currentActivityHours.get((currentActivity.getValue()+i)%4)>0){
                nextActivity = Activity.values()[(currentActivity.getValue()+i)%4];
                break;
            }     
        }
        return nextActivity;
    }
    
    private void performActivity(Activity a){
        switch(a){
            case WORKING:   goWork();
                break;
            case LEASURING: goLeasure();
                break;
            case RESTING:   goRest();  
                break;
            case SLEEPING:  goSleep();
                break;
        }
    }
    
    private void travel(Place source, Place dest){
        Route route = new Route(source,dest);
        Area a = route.getNextArea();
        this.currentPlace.setArea(a);
        this.currentPlace.setBuilding(-1);
    }
    
    private void goSleep(){
        if(this.currentPlace.getArea()!=this.homePlace.getArea()){
            //System.out.println("TRAVELING TO HOME FOR SLEEP "+this.healthStatus);
            travel(this.currentPlace,this.homePlace);
        }
        else{
            this.setCurrentPlace(homePlace);
            //System.out.println("SLEEPING "+this.healthStatus);
        }
    }    
    
    private void goRest(){
        if(this.currentPlace.getArea()!=this.homePlace.getArea()){
            //System.out.println("TRAVELING TO HOME "+this.healthStatus);
            travel(this.currentPlace,this.homePlace);
        }
        else{
            this.setCurrentPlace(homePlace);
            //System.out.println("JUST WENT HOME TO REST " +this.healthStatus);
        }
    }    
    
    private void goWork(){
        if(this.currentPlace.getArea()!=this.workingPlace.getArea()){
            //System.out.println("TRAVELING TO WORK "+this.healthStatus);
            travel(this.currentPlace,this.workingPlace);
        }
        else{
            this.setCurrentPlace(workingPlace);
            //System.out.println("JUST WENT TO WORK "+this.healthStatus);
        }        
    }

    private void goLeasure(){
        if(this.currentPlace.getArea()!=this.leisurePlace.getArea()){
            //System.out.println("TRAVELING TO LEISURE "+this.healthStatus);
            travel(this.currentPlace,this.leisurePlace);
        }
        else{
            this.setCurrentPlace(leisurePlace);
            //System.out.println("JUST WENT FOR LEISURE "+this.healthStatus);
        }
    }    
}
