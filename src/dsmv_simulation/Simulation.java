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
public class Simulation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         
        int days = 120;
        int currentHours = 0;
        int totalHours = days*24;
        Infection infection = new Infection(4,4);
        City city = new City(1000,10,infection);
    
        while(currentHours<totalHours){
            city.clock();
            currentHours++;
            if(currentHours%24==0){
                int[] SEIR = city.getSEIR();
                System.out.println(SEIR[0]+" "+SEIR[1]+" "+SEIR[2]+" "+SEIR[3]);
            }
        }
    }
    
}
