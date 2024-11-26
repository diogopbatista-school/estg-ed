package Collections.Trees;

import Collections.Exceptions.UnsupportedOperationException;
import Collections.Nodes.NodeAVL;

public class AVLTree<T> extends LinkedBinarySearchTree<T> {

    public AVLTree() {
        super();
    }

    public AVLTree(T element) {
        super(element);
        root = new NodeAVL<>(element);
        size = 1;
    }

    // Função para obter a altura de um nó
    private int height(NodeAVL<T> node) {
        if (node == null) {
            return 0;
        }
        return node.getHeight();
    }

    // Função para calcular o fator de balanceamento de um nó
    private int getBalance(NodeAVL<T> node) {
        if (node == null) {
            return 0;
        }
        // Altura da subárvore esquerda - Altura da subárvore direita
        return height((NodeAVL<T>) node.getLeft()) - height((NodeAVL<T>) node.getRight());
    }

    // Rotação para a direita para corrigir desbalanceamento
    private NodeAVL<T> rotateRight(NodeAVL<T> y) {
        NodeAVL<T> x = (NodeAVL<T>) y.getLeft();
        NodeAVL<T> T2 = (NodeAVL<T>) x.getRight();

        // Realizar a rotação
        x.setRight(y);
        y.setLeft(T2);

        // Atualizar as alturas dos nós rotacionados
        y.setHeight(Math.max(height((NodeAVL<T>) y.getLeft()), height((NodeAVL<T>) y.getRight())) + 1);
        x.setHeight(Math.max(height((NodeAVL<T>) x.getLeft()), height((NodeAVL<T>) x.getRight())) + 1);

        // Retornar a nova raiz da subárvore
        return x;
    }

    // Rotação para a esquerda para corrigir desbalanceamento
    private NodeAVL<T> rotateLeft(NodeAVL<T> x) {
        NodeAVL<T> y = (NodeAVL<T>) x.getRight();
        NodeAVL<T> T2 = (NodeAVL<T>) y.getLeft();

        // Realizar a rotação
        y.setLeft(x);
        x.setRight(T2);

        // Atualizar as alturas dos nós rotacionados
        x.setHeight(Math.max(height((NodeAVL<T>) x.getLeft()), height((NodeAVL<T>) x.getRight())) + 1);
        y.setHeight(Math.max(height((NodeAVL<T>) y.getLeft()), height((NodeAVL<T>) y.getRight())) + 1);

        // Retornar a nova raiz da subárvore
        return y;
    }

    // Adicionar um novo elemento na árvore AVL
    @Override
    public void addElement(T element) throws UnsupportedOperationException {
        // Verificar se o elemento implementa Comparable para garantir que pode ser comparado
        if (!(element instanceof Comparable)) {
            throw new UnsupportedOperationException();
        }

        // Se a árvore estiver vazia, o novo elemento será a raiz
        if (isEmpty()) {
            root = new NodeAVL<>(element);
        } else {
            // Inserir o elemento usando o método auxiliar `add`
            root = add((NodeAVL<T>) root, element);
        }
        size++; // Incrementa o tamanho da árvore
    }

    // Função auxiliar para inserir um elemento de forma recursiva e balancear a árvore
    private NodeAVL<T> add(NodeAVL<T> node, T element) {
        // Se o nó atual é nulo, cria um novo nó com o elemento
        if (node == null) {
            return new NodeAVL<>(element);
        }

        // Fazer casting para Comparable para permitir a comparação dos elementos
        Comparable<T> comparableElement = (Comparable<T>) element;

        // Inserir o elemento na posição correta (esquerda ou direita)
        if (comparableElement.compareTo(node.getElement()) < 0) {
            node.setLeft(add((NodeAVL<T>) node.getLeft(), element));
        } else if (comparableElement.compareTo(node.getElement()) > 0) {
            node.setRight(add((NodeAVL<T>) node.getRight(), element));
        } else {
            return node; // Elemento duplicado, não faz nada
        }

        // Atualizar a altura do nó após a inserção
        node.setHeight(1 + Math.max(height((NodeAVL<T>) node.getLeft()), height((NodeAVL<T>) node.getRight())));

        // Obter o fator de balanceamento do nó para verificar se está desbalanceado
        int balance = getBalance(node);

        // Verificar e corrigir os 4 tipos de desbalanceamento

        // Caso Esquerda-Esquerda
        if (balance > 1 && comparableElement.compareTo(node.getLeft().getElement()) < 0) {
            return rotateRight(node);
        }

        // Caso Direita-Direita
        if (balance < -1 && comparableElement.compareTo(node.getRight().getElement()) > 0) {
            return rotateLeft(node);
        }

        // Caso Esquerda-Direita
        if (balance > 1 && comparableElement.compareTo(node.getLeft().getElement()) > 0) {
            node.setLeft(rotateLeft((NodeAVL<T>) node.getLeft()));
            return rotateRight(node);
        }

        // Caso Direita-Esquerda
        if (balance < -1 && comparableElement.compareTo(node.getRight().getElement()) < 0) {
            node.setRight(rotateRight((NodeAVL<T>) node.getRight()));
            return rotateLeft(node);
        }

        // Retorna o nó (já balanceado)
        return node;
    }
}
