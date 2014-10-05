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

import java.util.List;

/**
 *
 * @author Christos Petropoulos, Paula Subías
 */
public class Building {
    
    private final int capacity;
    private final Place buildingPlace;
    private List<Agent> agentsInside;
    
    public Building(int capacity,Place buildingPlace){
        this.capacity = capacity;
        this.buildingPlace = new Place(buildingPlace);
    }
    
    public void addAgent(Agent a){
        this.agentsInside.add(a);
    }
    
    public void removeAgent(Agent a){
        this.agentsInside.remove(a);
    }
    
    public double getDensity(){
        return this.agentsInside.size()/this.capacity;
    }
    
    public Area getBuildingArea(){
        return this.buildingPlace.getArea();
    }
    
    public int getBuildingCode(){
        return this.buildingPlace.getBuildingCode();
    }
    
    public double getInfectedPercentage(){
        int total_infected = this.countInfected();
        return total_infected/this.agentsInside.size();
    }
    
    public int countInfected(){
        int total_infected = 0;
        for(Agent a : agentsInside){
            if(a.getHealthStatus()==HealthStatus.INFECTIOUS)
                total_infected++;
        }
        return total_infected;
    }
    
    public int getInfectionRate(){
        return (int)((this.getInfectedPercentage()/this.getDensity())*this.agentsInside.size());
    }
    
    public void clock(Infection infection){
        int totalNewInfected = getInfectionRate();
        for(Agent a : agentsInside){
            if(totalNewInfected>0){
                if(a.getHealthStatus()==HealthStatus.SUSPECTIBLE){
                    a.Infect(infection);
                    totalNewInfected--;
                }
            }
            else
                break;
        }  
    }
    
}
