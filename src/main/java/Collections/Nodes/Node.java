package Collections.Nodes;

/**
 * A class representing a node
 *
 * @param <T> the type of the element stored in the node.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class Node<T> {

    /**
     * The element of the node.
     */
    private T element;

    /**
     * The next node.
     */
    private Node<T> next;

    /**
     * The constructor por the node
     *
     * @param element - The element i want to insert
     */
    public Node(T element) {
        this.element = element;
        this.next = null;
    }

    /**
     * Getter for the element inside the node
     *
     * @return - The generic element
     */
    public T getElement() {
        return this.element;
    }

    /**
     * Setter for the element i want to set inside the node
     *
     * @param element - The generic element
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Getter for the next node
     *
     * @return - The next node
     */
    public Node<T> getNext() {
        return this.next;
    }

    /**
     * Setter for the next node
     *
     * @param nextNode - The next node i want to set
     */
    public void setNext(Node<T> nextNode) {
        this.next = nextNode;
    }

}

