package Collections.Nodes;

public class NodeAVL<T> extends BinaryTreeNode<T>{

    private int height;

    public NodeAVL(T element) {
        super(element);
        this.height = 0;
    }

    public NodeAVL(T element, NodeAVL left, NodeAVL right) {
        super(element, left, right);
        this.height = 0;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
