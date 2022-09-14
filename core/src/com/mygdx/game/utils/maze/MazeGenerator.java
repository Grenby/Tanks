package com.mygdx.game.utils.maze;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Queue;

import java.util.Random;

public class MazeGenerator<N> {



    private final static Random rand = new Random();
    private final Array<N> freeNode = new Array<>(4);
    private final Queue<N> queue = new Queue<>(10);
    private final ObjectSet<N> visited = new ObjectSet<>(10);

    private void clear(){
        freeNode.clear();
        queue.clear();
        visited.clear();
    }

    public void generate(Graph<N> graph, N fromNode ,GraphBuilder<N> out){
        clear();

        queue.addLast(fromNode);
        while (queue.size > 0) {
            N currentNode = queue.last();
            visited.add(currentNode);
            freeNode.clear();

            for (Connection<N> c : graph.getConnections(currentNode)) {
                if (!visited.contains(c.getToNode()))
                    freeNode.add(c.getToNode());
            }

            int indexFreeNeighbour;

            if (freeNode.size == 1) {
                indexFreeNeighbour = 0;
            } else if (freeNode.size > 1){
                indexFreeNeighbour = rand.nextInt(freeNode.size);
            }else {
                queue.removeLast();
                continue;
            }
            N to  = freeNode.get(indexFreeNeighbour);
            out.addDoubleConnection(currentNode,to);
            queue.addLast(to);
        }
    }

    public void generate(Graph<N> graph,N fromNode, int numNodes,GraphBuilder<N> out){
        visited.ensureCapacity(numNodes - visited.size);
        generate(graph,fromNode,out);
    }

}
