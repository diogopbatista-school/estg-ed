package Collections.Nodes;

/**
 * A class representing a node for the AVL tree.
 *
 * @param <T> the type of the stored element.
 * @Author ESTG Diogo Pereira Batista LSIRC - 8230367
 * @Author ESTG Rodrigo Fernandes Ribeiro LSIRC - 8190315
 */
public class NodeAVL<T> extends BinaryTreeNode<T> {

    /**
     * The height of the node.
     */
    private int height;

    /**
     * Creates a new AVL node with the specified element.
     *
     * @param element the element that will become a part of the new AVL node
     */
    public NodeAVL(T element) {
        super(element);
        this.height = 0;
    }

    /**
     * Creates a new AVL node with the specified element and children.
     *
     * @param element the element that will become a part of the new AVL node
     * @param left    the left child of the new AVL node
     * @param right   the right child of the new AVL node
     */
    public NodeAVL(T element, NodeAVL left, NodeAVL right) {
        super(element, left, right);
        this.height = 0;
    }

    /**
     * Getter for the height of the node.
     *
     * @return the height of the node
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter for the height of the node.
     *
     * @param height the height of the node
     */
    public void setHeight(int height) {
        this.height = height;
    }

}
