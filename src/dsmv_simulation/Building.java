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
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Christos Petropoulos, Paula Subías
 */
public class Building {
    
    private final int capacity;
    private final Place buildingPlace;
    private List<Agent> agents;
    public int availability;

    public Building(int capacity,Place buildingPlace){
        this.capacity = capacity;
        this.availability = capacity;
        this.buildingPlace = new Place(buildingPlace);
        this.agents =  new ArrayList<>();
    }
    
    public void addAgent(Agent a){
        this.agents.add(a);
        availability = this.agents.size();
    }
    
    public void removeAgent(Agent a){
        this.agents.remove(a);
        availability = this.agents.size();
    }
    
    public void removeAllAgents(){
        this.agents.clear();
        availability = this.agents.size();
    }
    
    public double getDensity(){
        return this.countAgentsInside()/this.capacity;
    }
    
    public Area getBuildingArea(){
        return this.buildingPlace.getArea();
    }
    
    public int getBuildingCode(){
        return this.buildingPlace.getBuildingCode();
    }
    
    public Place getPlace(){
        return this.buildingPlace;
    }
    
    public double getInfectedPercentage(){
        int total_infected = this.countInfected();
        return total_infected/this.agents.size();
    }
    
    public int countInfected(){
        int total_infected = 0;
        for(Agent a : agents){
            if(isAgentInside(a) && a.getHealthStatus()==HealthStatus.INFECTIOUS)
                total_infected++;
        }
        return total_infected;
    }
    
    public float getInfectionRate(){
        float rate;
        rate = this.countAgentsInside()/this.capacity;
        rate = rate * this.countInfected()/this.countAgentsInside();
        return rate;
    }
    
    private boolean isAgentInside(Agent a){
        return a.getCurrentPlace().equals(this.buildingPlace);
    }
    
    private int countAgentsInside(){
        int count = 0;
        for(Agent a : this.agents){
            if(a.getCurrentPlace().equals(this.buildingPlace))
                count++;
        }
        return count;
    }
    
    public void clock(Infection infection){
        int totalNewInfected = (int)(countAgentsInside()*getInfectionRate());
        for (Agent a : agents) {
            if(totalNewInfected>0){
                if(isAgentInside(a) && a.getHealthStatus()==HealthStatus.SUSPECTIBLE){
                    a.Infect(infection);
                    totalNewInfected--;
                }
            }
            else
                break;
        }  
    }
}
