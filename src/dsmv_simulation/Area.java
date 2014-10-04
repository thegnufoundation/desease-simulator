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
public enum Area {
    
    CENTRAL(0),
    MONUMENTO(1),
    BACLARAN(2),
    MAPA(3),
    ROOSEVELT(4),
    BONI(5),
    CUBAC(6),
    KATIPUNAN(7);

    private final int value;

    private Area(final int newValue) {
            value = newValue;
    }
    
    public int getValue(){ 
        return value; 
    }    
    
    public static Area valueOf(int AreaNo) {
        for (Area area : Area.values()) {
            if (area.getValue() == AreaNo) 
                return area;
        }   
        throw new IllegalArgumentException("Invalid area id.");
    }    
    
    public static Area getRandom(){
        int area_code = new Random().nextInt(7);
        return Area.valueOf(area_code);
    }
    
}
