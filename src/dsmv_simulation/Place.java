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

/**
 *
 * @author Christos Petropoulos, Paula Subías
 */
public class Place {
    
    private Area area;
    private int buildingCode;
    
    public Place(Area area, int bc){
        this.area = area;
        this.buildingCode = bc;
    }
    
    public void setArea(Area area){
        this.area = area;
    }
    
    public void setBuilding(int bc){
        this.buildingCode = bc;
    }
    
    public Area getArea(){
        return this.area;
    }
    
    public int getBuildingCode(){
        return this.buildingCode;
    }
    
    public void set(Place place){
        this.area = place.getArea();
        this.buildingCode = place.getBuildingCode();
    }
    
    public boolean equals(Place p){
        return this.area==p.getArea() && this.buildingCode==p.getBuildingCode();
    }
}
