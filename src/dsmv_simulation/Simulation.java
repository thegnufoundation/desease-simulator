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

import java.util.Scanner;

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
        int currentHours = 0;
        final int days,population,infected,totalHours;
        
        //(int transPeriod,int exposedPeriod,int infectedPeriod,int tSTD,int eSTD,int iSTD)
        final Infection infection = new Infection(15,10,7,8,2,1);
        
        if(args.length<3){
            Scanner in = new Scanner(System.in);
            System.err.print("Days:");
            days = in.nextInt();
            System.err.print("Population:");
            population = in.nextInt();
            System.err.print("Infected:");
            infected = in.nextInt();
        }
        else{
            days = Integer.parseInt(args[0]);
            population = Integer.parseInt(args[1]);
            infected = Integer.parseInt(args[2]);
        }
        totalHours = days*24;
        
        if(infected>population){
            System.err.println("Error: The number of infected people has to be less than the whole population.");
            System.out.println("-1");
            return;
        }

        System.err.println("Starting simulation for a period of ("+days+") days.");
        System.err.println("Parameters = {population="+population+", infected="+infected +"}");        
        System.err.print("Initializing...\r");
        City city = new City(population,infected,infection);
        System.err.print("Initializing...(DONE)\r\n");
        
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
