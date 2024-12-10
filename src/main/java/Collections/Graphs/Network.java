package Collections.Graphs;

import Collections.Lists.ArrayUnorderedList;
import Collections.Lists.UnorderedListADT;
import Collections.Queues.LinkedQueue;
import Collections.Stacks.LinkedStack;
import Collections.Stacks.StackADT;
import Collections.Trees.LinkedHeap;

import java.util.Iterator;

/**
 * Network represents an adjacency matrix implementation of a network.
 *
 * @param <T> the type of object that the network will hold
 * @Author Diogo Pereira Batista LSIRC - 8230367
 * @Author Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class Network<T> extends Graph<T> implements NetworkADT<T> {

    /**
     * The default capacity of the network
     */
    protected double[][] adjMatrix;

    /**
     * The constructor for the Network class with default capacity
     */
    public Network() {
        numVertices = 0;
        this.adjMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * The constructor for the Network class with a given capacity
     *
     * @param size The capacity of the network
     */
    public Network(int size) {
        numVertices = 0;
        this.adjMatrix = new double[size][size];
        this.vertices = (T[]) (new Object[size]);
    }

    /**
     * Method to add an edge between two vertices with a weight of 0
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    @Override
    public void addEdge(T vertex1, T vertex2) {
        addEdge(getIndex(vertex1), getIndex(vertex2), 0);
    }

    /**
     * Method to add an edge between two vertices with a weight
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param weight  the weight
     */
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        addEdge(getIndex(vertex1), getIndex(vertex2), weight);
    }

    /**
     * Method to add an edge between two vertices with a weight
     *
     * @param index1 the index of the first vertex
     * @param index2 the index of the second vertex
     * @param weight the weight
     */
    protected void addEdge(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight;
            adjMatrix[index2][index1] = weight;
        }
    }

    /**
     * Method to remove an edge between two vertices
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) {
        removeEdge(getIndex(vertex1), getIndex(vertex2));
    }

    /**
     * Method to remove an edge between two vertices
     *
     * @param index1 the first index vertex
     * @param index2 the second index vertex
     */
    @Override
    protected void removeEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = Double.POSITIVE_INFINITY;
            adjMatrix[index2][index1] = Double.POSITIVE_INFINITY;
        }
    }

    /**
     * Method to add a vertex to the network
     *
     * @param vertex the vertex to be added to this graph
     */
    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            this.expandCapacity();
        }

        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = Double.POSITIVE_INFINITY;
            adjMatrix[i][numVertices] = Double.POSITIVE_INFINITY;
        }
        numVertices++;
    }

    /**
     * Method to remove a vertex from the network
     *
     * @param vertex the vertex to be removed from this graph
     */
    @Override
    public void removeVertex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertex.equals(vertices[i])) {
                removeVertex(i);
                return;
            }
        }
    }

    /**
     * Method to remove a vertex from the network
     *
     * @param index the index of the vertex to be removed from this graph
     */
    @Override
    protected void removeVertex(int index) {
        super.removeVertex(index);
    }

    /**
     * Iterator for the depth first search
     *
     * @param startVertex the starting vertex
     * @return a depth first iterator starting at the given vertex
     */
    @Override
    public Iterator<T> iteratorDFS(T startVertex) {
        return this.iteratorDFS(getIndex(startVertex));
    }

    /**
     * Iterator for the depth first search
     *
     * @param startIndex the index to begin the search traversal from
     * @return a depth first iterator starting at the given index
     */
    @Override
    protected Iterator<T> iteratorDFS(int startIndex) {
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

    /**
     * Iterator for the breadth first search
     *
     * @param startVertex the starting vertex
     * @return a breadth first iterator beginning at the given vertex
     */
    @Override
    public Iterator<T> iteratorBFS(T startVertex) {
        return this.iteratorBFS(getIndex(startVertex));
    }

    /**
     * @param startIndex the index to begin the search from
     * @return a breadth first iterator beginning at the given index
     */
    @Override
    protected Iterator<T> iteratorBFS(int startIndex) {
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


            for (int i = 0; i < numVertices; i++) {
                if ((adjMatrix[x][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    /**
     * Method to determine the weight of the shortest path between two vertices
     * To implement this method , we used the Dijkstra algorithm to find the shortest path
     * So first of all , we learn at the slides of the discrete mathematics class and tried to adapt to the code
     * plus we consulted the "Data Structures and Algorithm Analysis in Java" by Mark Allen Weiss , and we tried to adapt the code to our project
     *
     * @param startIndex  the index of the starting vertex
     * @param targetIndex the index of the target vertex
     * @return an iterator that contains the indices of the vertices that make up the shortest path between the two vertices
     */
    public Iterator<Integer> iteratorShortestPathIndices(int startIndex, int targetIndex) {
        int index;
        double weight;
        // array para armazenar o predecessor de cada vertice no caminho mais curto
        int[] predecessor = new int[numVertices];

        // min-heap para selecionar o vertice com a menor distância a cada iteração
        LinkedHeap<Double> traversalMinHeap = new LinkedHeap<>();

        // lista para armazenar o caminho mais curto (em índices dos vértices)
        UnorderedListADT<Integer> resultList = new ArrayUnorderedList<>();


        // stack para reconstruir o caminho apos encontrar o caminho mais curto
        StackADT<Integer> stack = new LinkedStack<>(); // Melhor uma linkedstack pois nao sei o tamanho do caminho , e se fosse array
        // poderia usar muitas operaçoes de resize . Entao usei linkedstack para o tamanho ser dinamico
        // ira usar mais processamento mas é mais seguro

        // Arrays para armazenar os índices dos vértices e as distâncias dos vértices
        double[] pathWeight = new double[numVertices];


        for (int i = 0; i < numVertices; i++) {
            pathWeight[i] = Double.POSITIVE_INFINITY;
        }

        // Array para marcar os vértices visitados
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        // verificaçao de validade dos indices e se o grafo é vazio
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)
                || (startIndex == targetIndex) || isEmpty()) {
            return resultList.iterator();
        }

        // define a distancia do vertice de origem como 0 e o predecessor como -1
        pathWeight[startIndex] = 0;
        predecessor[startIndex] = -1;
        visited[startIndex] = true; // O vertice de origem já é visitado

        // adicionar as distancias dos vizinhos do vertice de origem à heap
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                // se houver uma aresta entre o vertice de origem e o viziho
                pathWeight[i] = pathWeight[startIndex] + adjMatrix[startIndex][i];
                predecessor[i] = startIndex;
                traversalMinHeap.addElement(pathWeight[i]); // add a distancia ao heap
            }
        }

        // enquanto houver vértices a serem processados e o destino não for visitado
        do {
            // remove o vertice com a menor peso da heap
            weight = traversalMinHeap.removeMin();

            traversalMinHeap.removeAllElements(); // limpa o heap após remover o mínimo

            // se nao houver mais caminhos possíveis retorna lista vazia //-------------------------------- aqui é que é o core pois usa a min heap para organizar e remove o minimo
            if (weight == Double.POSITIVE_INFINITY) {
                return resultList.iterator();
            } else {
                // encontrar o indice do vertice adjacente com a menor distancia e o "coloca" como visitado
                index = getIndexOfAdjVertexWithWeightOf(visited, pathWeight, weight);
                visited[index] = true;
            }

            // aqui atualiza as distancias dos vizinhos do vertice atual se um caminho mais curto for encontrado
            for (int i = 0; i < numVertices; i++) {
                if (!visited[i]) {
                    // Verifica se existe uma aresta entre o vertice atual e o vizinho
                    // e se a distancia atual é menor do que a registada
                    if ((adjMatrix[index][i] < Double.POSITIVE_INFINITY)
                            && (pathWeight[index] + adjMatrix[index][i]) < pathWeight[i]) {
                        // atualiza a distância do vertice i
                        pathWeight[i] = pathWeight[index] + adjMatrix[index][i];
                        predecessor[i] = index; // Atualiza o predecessor de i
                    }
                    // adiciona o vertice i no heap para ser tratado em etapas seguintes
                    traversalMinHeap.addElement(pathWeight[i]);
                }
            }
        } while (!traversalMinHeap.isEmpty() && !visited[targetIndex]);

        // reconstri o caminho: começa do destino e vai ate a origem
        index = targetIndex;
        stack.push(index); // coloca na stack o destino
        do {
            index = predecessor[index]; // Move para o predecessor
            stack.push(index); // coloca na stack o predecessor
        } while (index != startIndex); // Continua até chegar ao vértice de origem

        // retira os vertices do caminho e os adiciona à lista resultante
        while (!stack.isEmpty()) {
            resultList.addToRear(stack.pop()); // coloca o vértice no caminho final
        }

        // fim da explicacao
        // o algoritmo de djiskstra retirei dos slides da prof. de Matematica discreta e tentei adaptar ao codigo
        // nao sei se deveria ter usado min-heap mas pronto


        return resultList.iterator();
    }

    /**
     * Method to determine the shortest path between two vertices
     *
     * @param startIndex  the starting index vertex
     * @param targetIndex the ending index vertex
     * @return an iterator that contains the shortest path between the two vertices
     */
    @Override
    protected Iterator<T> iteratorShortestPath(int startIndex, int targetIndex) {
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
        return this.iteratorShortestPath(getIndex(startVertex), getIndex(targetVertex));
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

        return -1;
    }

    /**
     * Method to determine the weight of the shortest path between two vertices
     *
     * @param startIndex  the starting index vertex
     * @param targetIndex the ending index vertex
     * @return the weight of the shortest path between the two vertices
     */
    private double shortestPathWeight(int startIndex, int targetIndex) {
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

    /**
     * Method to determine the weight of the shortest path between two vertices
     *
     * @param startVertex  the first vertex
     * @param targetVertex the second vertex
     * @return the weight of the shortest path between the two vertices
     */
    @Override
    public double shortestPathWeight(T startVertex, T targetVertex) {
        return this.shortestPathWeight(getIndex(startVertex), getIndex(targetVertex));
    }


    private void expandCapacity() {
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

    /**
     * Method to return a string representation of the network
     *
     * @return a string representation of the network
     */
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