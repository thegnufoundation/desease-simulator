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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author Christos Petropoulos, Paula Subías
 */
public class Route {

    private Vertex v[];    
    private Area currentArea;
    private Place destination;
    private List<Vertex> path;
    private boolean finished;
    
    public Route(Place currentPlace, Place destination){
        this.v = getGraph();
        this.finished = false;
        this.currentArea = currentPlace.getArea();
        this.destination = destination;
        this.setPath(currentArea,destination);
    }
 
    public boolean isFinished(){
        return this.finished;
    }
    
    public Area getNextArea(){
        Area nextArea = this.currentArea;
        if(this.currentArea!=this.destination.getArea()){
            for(int i=0;i<path.size();i++){
                if(currentArea.getValue()==path.get(i).id){
                    nextArea = Area.valueOf(path.get(i+1).id);
                    break;
                }
            }
        }
        this.currentArea = nextArea;
        if(this.currentArea==this.destination.getArea())
            this.finished = true;
        return nextArea;
    }
    
    public static Vertex[] getGraph(){
        Vertex v[] = new Vertex[8];
        for(int i=0;i<8;i++){
            v[i] = new Vertex(i);
        }        
        
	v[0].adjacencies = new Edge[]{new Edge(v[1], 2),new Edge(v[2], 2),  // From Central.
                                           new Edge(v[3], 2)};   
        
        v[1].adjacencies = new Edge[]{new Edge(v[0], 2),new Edge(v[4], 2)}; // From Monumento.                      
        v[2].adjacencies = new Edge[]{new Edge(v[0], 2),new Edge(v[5], 2)}; // From Baclaran.                     
        v[3].adjacencies = new Edge[]{new Edge(v[0], 2),new Edge(v[6], 2)}; // From Mapa. 
        v[4].adjacencies = new Edge[]{new Edge(v[1], 2),new Edge(v[6], 2)}; // From Roosevelt.
        v[5].adjacencies = new Edge[]{new Edge(v[2], 2),new Edge(v[6], 2)}; // From Boni.
        
        v[6].adjacencies = new Edge[]{new Edge(v[3], 2),new Edge(v[4], 2),  // From Cubac.
                                           new Edge(v[5], 2),new Edge(v[7], 2)};
        
        v[7].adjacencies = new Edge[]{new Edge(v[6], 2)};                   // From Katipunan.
        return v;
    }
    
    private void setPath(Area source, Place destination){
        computeShortestPath(v[source.getValue()]);
        for (int i=0;i<8;i++){
	    this.path = getShortestPathTo(v[i]);
            if(this.path.get(this.path.size()-1)==v[destination.getArea().getValue()])
                break;
	}     
    }
    
    public static void computeShortestPath(Vertex source){
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<>();
      	vertexQueue.add(source);
	while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();
            for (Edge e : u.adjacencies){
                Vertex tv = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
		if (distanceThroughU < tv.minDistance) {
		    vertexQueue.remove(tv);
		    tv.minDistance = distanceThroughU ;
		    tv.previous = u;
		    vertexQueue.add(tv);
		}
            }
        }
    }
    
    public static List<Vertex> getShortestPathTo(Vertex target){
        List<Vertex> path = new ArrayList<>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }      
    
    public static int getPathDistance(Area source, Area dest){
        Vertex vg[] = getGraph();
        List<Vertex>vertex = null;
        computeShortestPath(vg[source.getValue()]);
        for (int i=0;i<8;i++){
	    vertex = getShortestPathTo(vg[i]);
            if(vertex.get(vertex.size()-1)==vg[dest.getValue()])
                break;
	}    
        if(vertex!=null)
            return vertex.size()-1;
        return 0;
    }
}
