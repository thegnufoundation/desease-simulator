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
import java.util.Stack;

/**
 *
 * @author Christos Petropoulos, Paula Subías
 */
public class Route {

    private Vertex v[];    
    private Place currentLocation;
    private Place destination;
    
    
    public Route(Place currentLocation, Place destination){
        this.currentLocation = currentLocation;
        this.destination = destination;
        initGraph();
        setPath(currentLocation,destination);
    }
 
    private void initGraph(){
        this.v = new Vertex[8];
        for(int i=0;i<8;i++){
            this.v[i] = new Vertex(i);
        }        
        
	v[0].adjacencies = new Edge[]{new Edge(v[1], 2),new Edge(v[2], 2),  // From Central.
                                      new Edge(v[3], 2)};   
        
        v[1].adjacencies = new Edge[]{new Edge(v[0], 2),new Edge(v[4], 2)}; // From Monumento.                      
        v[2].adjacencies = new Edge[]{new Edge(v[0], 2),new Edge(v[5], 1)}; // From Baclaran.                     
        v[3].adjacencies = new Edge[]{new Edge(v[0], 2),new Edge(v[6], 2)}; // From Mapa. 
        v[4].adjacencies = new Edge[]{new Edge(v[1], 2),new Edge(v[6], 2)}; // From Roosevelt.
        v[5].adjacencies = new Edge[]{new Edge(v[2], 2),new Edge(v[6], 2)}; // From Boni.
        
        v[6].adjacencies = new Edge[]{new Edge(v[3], 2),new Edge(v[4], 2),  // From Cubac.
                                      new Edge(v[5], 1),new Edge(v[7], 2)};
        
        v[7].adjacencies = new Edge[]{new Edge(v[6], 2)};                   // From Katipunan.
    }
    
    private void setPath(Place source, Place destination){
        computeShortestPath(v[source.getArea().getValue()]);
        for (int i=0;i<8;i++){
	    List<Vertex> path = getShortestPathTo(v[i]);
            if(path.get(path.size()-1)==v[destination.getArea().getValue()])
                System.out.println("Path: " + path);
	}     
    }
    
    private void computeShortestPath(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<>();
      	vertexQueue.add(source);

	while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
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
    
    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }    
    
}
