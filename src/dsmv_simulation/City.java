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
public class City {
    
    private Building[] leisureBuildings;
    private Building[] workBuildings;
    private Building[] homeBuildings;
    private Building[] publicPlaces;
    private int currentBuildingID = 0;
    private int currentHomeID = 0;
    private Infection infection;
    private Agent[] agents;
    
    public City(int population,int infected,Infection infection) {
        this.infection = infection;
        this.setLeisureBuildings(10);
        this.setWorkBuildings(20);
        this.setHomeBuildings((int)(population/2));
        this.setAgents(population);
        for(int i=0;i<infected;i++){
            agents[i].Infect(infection);
        }
        setPublicAreas();
    }
    
    private void setPublicAreas(){
        int j;
        int population = this.agents.length;
        this.publicPlaces = new Building[Area.values().length];
        for(int i=0;i<Area.values().length;i++){
            this.publicPlaces[i] = new Building(population/25,new Place(Area.valueOf(i),-1));
            for(j=0;j<this.agents.length;j++){
                this.publicPlaces[i].addAgent(agents[j]);
            }
        }
    }
    
    private void setAgents(int n)  {
        this.agents = new Agent[n];
        for(int i=0;i<n;i++){
            this.agents[i] = generateAgent();
        }
    }
    
    private Agent generateAgent() {
        
        int pos = (int)( 5 + (new Random().nextGaussian()) * 3);
        int workingHours = pos;
       pos = (int)( 5 + (new Random().nextGaussian()) * 3);
        int sleepingHours = pos;
        int homeBuildingID = this.currentHomeID;
        int workBuildingID = getBuildingMax();
        double leisureProb = new Random().nextDouble();
        Place workPlace = this.workBuildings[workBuildingID].getPlace();
        Place homePlace = this.homeBuildings[homeBuildingID].getPlace();
        this.currentHomeID = (this.currentHomeID+1)%homeBuildings.length;
        Agent a = new Agent(homePlace,workPlace,leisureBuildings,workingHours,
                            sleepingHours,leisureProb);
        this.workBuildings[workBuildingID].addAgent(a);
        this.homeBuildings[homeBuildingID].addAgent(a);
        return a;
    }

    private int getBuildingMax(){
        int max = -1;
        int max_id = -1;
        for(int i=0;i<this.workBuildings.length;i++){
            if(this.workBuildings[i].availability>max){
                max = this.workBuildings[i].availability;
                max_id = i;
            }
        } 
        if(max_id==-1)
            max_id = new Random().nextInt(this.workBuildings.length);
        return max_id;
    }
    
    private void setLeisureBuildings(int n){
        Place buildingPlace;
        this.leisureBuildings = new Building[n];
        for(int i=0;i<n;i++){
            buildingPlace = new Place(Area.valueOf(i%8),this.currentBuildingID);
            this.leisureBuildings[i] = new Building((i%10+1)*20,buildingPlace);
            this.currentBuildingID++;
        } 
    }
    
    private void setWorkBuildings(int n){
        Place buildingPlace;
        this.workBuildings = new Building[n];
        for(int i=0;i<n;i++){
            buildingPlace = new Place(Area.valueOf(i%8),this.currentBuildingID);
            this.workBuildings[i] = new Building((i%5+1)*10,buildingPlace);
            this.currentBuildingID++;
        } 
    }    
    
    private void setHomeBuildings(int n){
        Place buildingPlace;
        this.homeBuildings = new Building[n];
        for(int i=0;i<n;i++){
            buildingPlace = new Place(Area.valueOf(i%8),this.currentBuildingID);
            this.homeBuildings[i] = new Building((i%4+1),buildingPlace);
            this.currentBuildingID++;
        } 
    }        
    
    public void clock(){
        int i;
        for(i=0;i<this.workBuildings.length;i++){
            this.workBuildings[i].clock(this.infection);
        }
        for(i=0;i<this.homeBuildings.length;i++){
            this.homeBuildings[i].clock(this.infection);
        }     
        for(i=0;i<this.publicPlaces.length;i++){
            this.publicPlaces[i].clock(this.infection);
        }
        
        for(i=0;i<this.agents.length;i++){
            this.agents[i].clock();
        }   
    }
    
    public int[] getSEIR(){
        int[] SEIR = new int[4];
        for (Agent agent : agents) {
            SEIR[agent.getHealthStatus().getValue()]++;
        }
        return SEIR;
    }
    
}
