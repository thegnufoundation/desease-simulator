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
         
        int SEIR[];
        int days = Integer.parseInt(args[0]);
        int currentHours = 0;
        int totalHours = days*24;
        int population = Integer.parseInt(args[1]);
        int infected = Integer.parseInt(args[2]);
        
        // if arguments not given, print message to give arguments 
        
        Infection infection = new Infection(2,10);
        
        System.err.println("Starting simulation for a period of ("+days+") days.");
        System.err.println("Parameters = {population="+population+ ", infected="+infected +"}");
        
        City city = new City(population,infected,infection);
    
  
        while(currentHours<totalHours){
            city.clock();
            currentHours++;
            SEIR = city.getSEIR();
            System.out.println(SEIR[0]+" "+SEIR[1]+" "+SEIR[2]+" "+SEIR[3]);
            System.err.print("Simulation in process...("+100*currentHours/totalHours+"%)\r");
        }
        System.err.print("Simulation in process...(DONE)\r\n");
        System.err.println("[Simulation completed]");
    }
    
}
