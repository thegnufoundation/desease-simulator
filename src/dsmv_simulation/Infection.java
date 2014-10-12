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
public class Infection {
    
    private int transitionPeriod, exposedPeriod, infectedPeriod;
    private final int tSTD, eSTD, iSTD, tpMean;

    public Infection(int transPeriod,int exposedPeriod,int infectedPeriod,int tSTD,int eSTD,int iSTD){
        this.tSTD = tSTD;
        this.eSTD = eSTD;
        this.iSTD = iSTD;
        this.tpMean = transPeriod;
        this.exposedPeriod = exposedPeriod*24;
        this.infectedPeriod = infectedPeriod*24;
        this.transitionPeriod = (int)(this.tpMean+(new Random().nextGaussian())*tSTD);
    }
    
    public Infection(Infection infection){
        int exp_days = infection.getExposedPeriod()/24;
        int inf_days = infection.getInfectedPeriod()/24;
        //exp_days = (int)(exp_days + (new Random().nextGaussian())*infection.getESTD()); 
        //inf_days = (int)(inf_days + (new Random().nextGaussian()*infection.getISTD()));        
        this.tSTD = infection.getTSTD();
        this.eSTD = infection.getESTD();
        this.iSTD = infection.getISTD();
        this.tpMean = infection.getTPMean();
        this.exposedPeriod = infection.getExposedPeriod();
        this.infectedPeriod = infection.getInfectedPeriod();
        this.transitionPeriod = infection.getTrantitionPeriod();
        if((new Random().nextBoolean()))
            this.exposedPeriod = exp_days*24+(new Random().nextInt(3)*24);
        else
            this.exposedPeriod = exp_days*24-(new Random().nextInt(3)*24);
        this.infectedPeriod = inf_days*24+(int)(new Random().nextGaussian()*infection.getISTD()*24);
        this.transitionPeriod = (int) (this.tpMean+(new Random().nextGaussian())*tSTD);
    }
 
    public Infection clone(Infection infection){
        int ctSTD = infection.getTSTD();
        int ceSTD = infection.getESTD();
        int ciSTD = infection.getISTD();
        int exp_days = infection.getExposedPeriod()/24;
        int inf_days = infection.getInfectedPeriod()/24;
        int tpm = infection.getTPMean();
        Infection cp = new Infection(tpm,exp_days,inf_days,ctSTD,ceSTD,ciSTD);
        return cp;
    }
    
    public void clock(){
        if(this.transitionPeriod>0)
            this.transitionPeriod--;
        else if(this.exposedPeriod>0)
            this.exposedPeriod--;
        else if(this.infectedPeriod>0)
            this.infectedPeriod--;
    }    
    
    public HealthStatus getInfectionStatus(){
        if(this.transitionPeriod>0)
            return HealthStatus.SUSPECTIBLE;
        else if(this.exposedPeriod>0)
            return HealthStatus.EXPOSED;
        else if(this.infectedPeriod>0)
            return HealthStatus.INFECTIOUS;
        else
            return HealthStatus.RECOVERED;
    }
    
    public int getTPMean(){
        return this.tpMean;
    }
    
    public int getTSTD(){
        return this.tSTD;
    }
    
    public int getISTD(){
        return this.iSTD;
    }
    
    public int getESTD(){
        return this.eSTD;
    }
    
    public int getTrantitionPeriod(){
        return this.transitionPeriod;
    }
    
    public int getExposedPeriod(){
        return this.exposedPeriod;
    }
    
    public int getInfectedPeriod(){
        return this.infectedPeriod;
    } 
}
