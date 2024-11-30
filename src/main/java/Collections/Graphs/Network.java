package Collections.Graphs;

import Collections.Lists.ArrayUnorderedList;
import Collections.Queues.LinkedQueue;
import Collections.Stacks.LinkedStack;
import Collections.Trees.LinkedHeap;

import java.util.Iterator;

public class Network<T> extends Graph<T> implements NetworkADT<T> {

    protected double[][] adjMatrix;

    public Network() {
        numVertices = 0;
        this.adjMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    @Override
    public void addEdge(T vertex1, T vertex2){
        addEdge(getIndex(vertex1), getIndex(vertex2), 0);
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        addEdge(getIndex(vertex1), getIndex(vertex2), weight);
    }

    public void addEdge(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight;
            adjMatrix[index2][index1] = weight;
        }
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        removeEdge(getIndex(vertex1), getIndex(vertex2));
    }

    @Override
    public void removeEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = Double.POSITIVE_INFINITY;
            adjMatrix[index2][index1] = Double.POSITIVE_INFINITY;
        }
    }

    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            expandCapacity();
        }

        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = Double.POSITIVE_INFINITY;
            adjMatrix[i][numVertices] = Double.POSITIVE_INFINITY;
        }
        numVertices++;
    }

    public void addVertex() {
        if (numVertices == vertices.length) {
            expandCapacity();
        }

        vertices[numVertices] = null;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = Double.POSITIVE_INFINITY;
            adjMatrix[i][numVertices] = Double.POSITIVE_INFINITY;
        }
        numVertices++;
    }

    @Override
    public void removeVertex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertex.equals(vertices[i])) {
                removeVertex(i);
                return;
            }
        }
    }

    @Override
    public void removeVertex(int index) {
        if (indexIsValid(index)) {
            numVertices--;

            for (int i = index; i < numVertices; i++) {
                vertices[i] = vertices[i + 1];
            }

            for (int i = index; i < numVertices; i++) {
                for (int j = 0; j <= numVertices; j++) {
                    adjMatrix[i][j] = adjMatrix[i + 1][j];
                }
            }

            for (int i = index; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    adjMatrix[j][i] = adjMatrix[j][i + 1];
                }
            }
        }
    }

    @Override
    public Iterator<T> iteratorDFS(T startVertex) {
        return iteratorDFS(getIndex(startVertex));
    }

    public Iterator<T> iteratorDFS(int startIndex) {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        boolean[] visited = new boolean[numVertices];

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;

            //Find a vertex adjacent to x that has not been visited and push it
            //on the stack
            for (int i = 0; (i < numVertices) && !found; i++) {
                if ((adjMatrix[x][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }
        return resultList.iterator();
    }

    @Override
    public Iterator<T> iteratorBFS(T startVertex) {
        return iteratorBFS(getIndex(startVertex));
    }

    public Iterator<T> iteratorBFS(int startIndex) {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x]);

            //Find all vertices adjacent to x that have not been visited and
            //queue them up
            for (int i = 0; i < numVertices; i++) {
                if ((adjMatrix[x][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    protected Iterator<Integer> iteratorShortestPathIndices(int startIndex, int targetIndex) {
        int index;
        double weight;
        // Array para armazenar o predecessor de cada vértice no caminho mais curto
        int[] predecessor = new int[numVertices];
        // Min-heap para selecionar o vértice com a menor distância a cada iteração
        LinkedHeap<Double> traversalMinHeap = new LinkedHeap<>();
        // Lista para armazenar o caminho mais curto (em índices dos vértices)
        ArrayUnorderedList<Integer> resultList = new ArrayUnorderedList<>();
        // Pilha para reconstruir o caminho após encontrar o caminho mais curto
        LinkedStack<Integer> stack = new LinkedStack<>();

        // Arrays para armazenar os índices dos vértices e as distâncias dos vértices
        int[] pathIndex = new int[numVertices];
        double[] pathWeight = new double[numVertices];

        // Inicializa todas as distâncias como infinito
        for (int i = 0; i < numVertices; i++) {
            pathWeight[i] = Double.POSITIVE_INFINITY;
        }

        // Inicializa o array de visitados como falso para todos os vértices
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        // Verifica se os índices fornecidos são válidos e se o grafo não está vazio
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)
                || (startIndex == targetIndex) || isEmpty()) {
            return resultList.iterator(); // Retorna uma lista vazia se algo for inválido
        }

        // Define a distância do vértice de origem como 0 e o predecessor como -1 (nenhum)
        pathWeight[startIndex] = 0;
        predecessor[startIndex] = -1;
        visited[startIndex] = true; // Marca o vértice de origem como visitado

        // Adiciona as distâncias dos vizinhos imediatos do vértice de origem ao heap
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                // Se houver uma aresta entre o vértice de origem e o vizinho
                pathWeight[i] = pathWeight[startIndex] + adjMatrix[startIndex][i];
                predecessor[i] = startIndex;
                traversalMinHeap.addElement(pathWeight[i]); // Adiciona a distância ao heap
            }
        }

        // Loop principal: enquanto houver vértices a serem processados e o destino não for visitado
        do {
            // Remove o vértice com a menor distância (menor peso) do heap
            weight = traversalMinHeap.removeMin();
            traversalMinHeap.removeAllElements(); // Limpa o heap após remover o mínimo

            // Se não houver mais caminhos possíveis (todas as distâncias são infinitas), retorna lista vazia
            if (weight == Double.POSITIVE_INFINITY) {
                return resultList.iterator();
            } else {
                // Encontra o índice do vértice adjacente com a menor distância e o marca como visitado
                index = getIndexOfAdjVertexWithWeightOf(visited, pathWeight, weight);
                visited[index] = true;
            }

            // Atualiza as distâncias dos vizinhos do vértice atual se um caminho mais curto for encontrado
            for (int i = 0; i < numVertices; i++) {
                if (!visited[i]) {
                    // Verifica se existe uma aresta entre o vértice atual e o vizinho
                    // e se a distância atual é menor do que a registrada
                    if ((adjMatrix[index][i] < Double.POSITIVE_INFINITY)
                            && (pathWeight[index] + adjMatrix[index][i]) < pathWeight[i]) {
                        // Atualiza a distância do vértice i
                        pathWeight[i] = pathWeight[index] + adjMatrix[index][i];
                        predecessor[i] = index; // Atualiza o predecessor de i
                    }
                    // Adiciona o vértice i no heap para ser processado em etapas seguintes
                    traversalMinHeap.addElement(pathWeight[i]);
                }
            }
        } while (!traversalMinHeap.isEmpty() && !visited[targetIndex]); // Loop até processar todos os vértices ou chegar ao destino

        // Reconstrução do caminho: começa do destino e vai até a origem
        index = targetIndex;
        stack.push(index); // Empilha o destino
        do {
            index = predecessor[index]; // Move para o predecessor
            stack.push(index); // Empilha o predecessor
        } while (index != startIndex); // Continua até chegar ao vértice de origem

        // Desempilha os vértices do caminho e os adiciona à lista resultante
        while (!stack.isEmpty()) {
            resultList.addToRear(stack.pop()); // Adiciona o vértice ao caminho final
        }

        // Retorna o iterador da lista contendo os índices do caminho mais curto
        return resultList.iterator();
    }


    @Override
    public Iterator<T> iteratorShortestPath(int startIndex, int targetIndex) {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<>();
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return templist.iterator();
        }

        Iterator<Integer> it = iteratorShortestPathIndices(startIndex, targetIndex);
        while (it.hasNext()) {
            templist.addToRear(vertices[it.next()]);
        }
        return templist.iterator();
    }

    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        return iteratorShortestPath(getIndex(startVertex),getIndex(targetVertex));
    }

    protected int getIndexOfAdjVertexWithWeightOf(boolean[] visited, double[] pathWeight, double weight) {
        for (int i = 0; i < numVertices; i++) {
            if ((pathWeight[i] == weight) && !visited[i]) {
                for (int j = 0; j < numVertices; j++) {
                    if ((adjMatrix[i][j] < Double.POSITIVE_INFINITY) && visited[j]) {
                        return i;
                    }
                }
            }
        }

        return -1;  // should never get to here
    }


    //OTHERS METHODS
    public Network<T> mstNetwork() {
    int x, y;
    int index;
    double weight;
    int[] edge = new int[2];
    LinkedHeap<Double> minHeap = new LinkedHeap<Double>();
    Network<T> resultGraph = new Network<T>();

    if (isEmpty() || !isConnected()) {
        return resultGraph;
    }
    resultGraph.adjMatrix = new double[numVertices][numVertices];
    for (int i = 0; i < numVertices; i++) {
        for (int j = 0; j < numVertices; j++) { // Corrected loop condition
            resultGraph.adjMatrix[i][j] = Double.POSITIVE_INFINITY;
        }
    }
    resultGraph.vertices = (T[]) (new Object[numVertices]);
    boolean[] visited = new boolean[numVertices];
    for (int i = 0; i < numVertices; i++) {
        visited[i] = false;
    }
    edge[0] = 0;
    resultGraph.vertices[0] = this.vertices[0];
    resultGraph.numVertices++;
    visited[0] = true;

    // Add all edges, which are adjacent to the starting vertex, to the heap
    for (int i = 0; i < numVertices; i++) {
        minHeap.addElement(adjMatrix[0][i]);
    }

    while ((resultGraph.size() < this.size()) && !minHeap.isEmpty()) {
        // Get the edge with the smallest weight that has exactly one vertex already in the resultGraph
        do {
            weight = minHeap.removeMin();
            edge = getEdgeWithWeightOf(weight, visited);
        } while (!indexIsValid(edge[0]) || !indexIsValid(edge[1]));

        x = edge[0];
        y = edge[1];
        if (!visited[x]) {
            index = x;
        } else {
            index = y;
        }

        // Add the new edge and vertex to the resultGraph
        resultGraph.vertices[index] = this.vertices[index];
        visited[index] = true;
        resultGraph.numVertices++;

        resultGraph.adjMatrix[x][y] = this.adjMatrix[x][y];
        resultGraph.adjMatrix[y][x] = this.adjMatrix[y][x];

        // Add all edges, that are adjacent to the newly added vertex, to the heap
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i] && (this.adjMatrix[i][index] < Double.POSITIVE_INFINITY)) {
                edge[0] = index;
                edge[1] = i;
                minHeap.addElement(adjMatrix[index][i]);
            }
        }
    }
    return resultGraph;
}

    protected int[] getEdgeWithWeightOf(double weight, boolean[] visited) {
        int[] edge = new int[2];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if ((adjMatrix[i][j] == weight) && (visited[i] ^ visited[j])) {
                    edge[0] = i;
                    edge[1] = j;
                    return edge;
                }
            }
        }

        /**
         * Will only get to here if a valid edge is not found
         */
        edge[0] = -1;
        edge[1] = -1;
        return edge;
    }

    public double shortestPathWeight(int startIndex, int targetIndex) {
        double result = 0;
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return Double.POSITIVE_INFINITY;
        }

        int index1, index2;
        Iterator<Integer> it = iteratorShortestPathIndices(startIndex,
                targetIndex);

        if (it.hasNext()) {
            index1 = it.next();
        } else {
            return Double.POSITIVE_INFINITY;
        }

        while (it.hasNext()) {
            index2 = it.next();
            result += adjMatrix[index1][index2];
            index1 = index2;
        }

        return result;
    }

    @Override
    public double shortestPathWeight(T startVertex, T targetVertex) {
        return shortestPathWeight(getIndex(startVertex), getIndex(targetVertex));
    }

    @Override
    protected void expandCapacity() {
        T[] largerVertices = (T[]) (new Object[vertices.length * 2]);
        double[][] largerAdjMatrix
                = new double[vertices.length * 2][vertices.length * 2];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                largerAdjMatrix[i][j] = adjMatrix[i][j];
            }
            largerVertices[i] = vertices[i];
        }

        vertices = largerVertices;
        adjMatrix = largerAdjMatrix;
    }

    public String toString() {
        if (numVertices == 0) {
            return "Graph is empty";
        }
        String result = "";

        //Print the adjacency Matrix
        result += "Adjacency Matrix\n";
        result += "----------------\n";
        result += "index\t";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i;
            if (i < numVertices) {
                result += " ";
            }
        }
        result += "\n\n";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";

            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j] < Double.POSITIVE_INFINITY) {
                    result += "1 ";
                } else {
                    result += "0 ";
                }
            }
            result += "\n";
        }

        //Print the vertex values
        result += "\n\nVertex Values";
        result += "\n-------------\n";
        result += "index\tvalue\n\n";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";
            result += vertices[i].toString() + "\n";
        }

        //Print the weights of the edges
        result += "\n\nWeights of Edges";
        result += "\n----------------\n";
        result += "index\tweight\n\n";

        for (int i = 0; i < numVertices; i++) {
            for (int j = numVertices - 1; j > i; j--) {
                if (adjMatrix[i][j] < Double.POSITIVE_INFINITY) {
                    result += i + " to " + j + "\t";
                    result += adjMatrix[i][j] + "\n";
                }
            }
        }

        result += "\n";
        return result;
    }
}