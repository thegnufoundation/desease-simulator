/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsmv_simulation;

/**
 *
 * @author chris
 */
public class Place {
    private Area area;
    private int building_code;
    
    public Place(Area area, int building_code){
        this.area = area;
        this.building_code = building_code;
    }
    
    public Area getArea(){
        return this.area;
    }
    
    public int getBuildingCode(){
        return this.building_code;
    }
    
}
