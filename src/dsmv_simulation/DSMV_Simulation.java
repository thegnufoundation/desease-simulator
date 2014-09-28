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
public class DSMV_Simulation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Place home = new Place(Area.BACLARAN,1);
        Place work = new Place(Area.BONI,1);
        Agent a = new Agent(20,home,work,new int[]{7,2,3,4,8},0.3);
        Activity activity;
        for(int i=0;i<50;i++){
            activity = a.getCurrentActivity();
            System.out.println(activity.getValue());
            a.clock();
            if((i+1)%24==0){
                System.out.print("New date\n");
            }
        }
    }
    
}
