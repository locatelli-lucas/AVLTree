package tree;

public class TreeNode {
    private int data;
    private TreeNode leftChild;
    private TreeNode rightChild;
    private TreeNode parent;

    public TreeNode(int data) {
        // Construtor que inicializa o nó com um valor e filhos nulos.
        this.data = data;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }

    public int getNodeData() {
        // Retorna o valor armazenado no nó.
        return this.data;
    }

    public void setNodeData(int data) {
        // Define o valor armazenado no nó.
        this.data = data;
    }

    public TreeNode getLeftChild() {
        // Retorna o filho à esquerda do nó.
        return this.leftChild;
    }

    public TreeNode getRightChild() {
        // Retorna o filho à direita do nó.
        return this.rightChild;
    }

    public void setLeftChild(TreeNode leftChild) {
        // Define o filho à esquerda do nó.
        this.leftChild = leftChild;
    }

    public void setRightChild(TreeNode rightChild) {
        // Define o filho à direita do nó.
        this.rightChild = rightChild;
    }

    public TreeNode getParent() {
        // Retorna o nó pai.
        return parent;
    }

    public void setParent(TreeNode parent) {
        // Define o nó pai.
        this.parent = parent;
    }

    public boolean isLeaf() {
        // Verifica se o nó é uma folha (não possui filhos).
        return this.leftChild == null && this.rightChild == null;
    }

    public boolean isRoot() {
        // Verifica se o nó é a raiz (não possui pai).
        return this.parent == null;
    }

    public boolean hasSingleChild() {
        // Verifica se o nó possui apenas um filho.
        return (this.leftChild != null && this.rightChild == null) ||
                (this.leftChild == null && this.rightChild != null);
    }

    public boolean hasDoubleChild() {
        // Verifica se o nó possui dois filhos.
        return this.leftChild != null && this.rightChild != null;
    }

    public TreeNode getChild() {
        // Retorna o único filho do nó, se existir.
        if(!this.hasSingleChild()) throw new RuntimeException("Node does not have a single child");
        if(this.getLeftChild() != null) return this.leftChild;
        return this.rightChild;
    }

    @Override
    public String toString() {
        // Retorna uma representação em string do nó, incluindo seus filhos e pai.
        String leftChild = this.leftChild == null ? "null" : String.valueOf(this.leftChild.getNodeData());
        String rightChild = this.rightChild == null ? "null" : String.valueOf(this.rightChild.getNodeData());
        String parent = this.parent == null ? "null" : String.valueOf(this.parent.getNodeData());
        return "TreeNode{" +
                "data=" + this.data +
                ", leftChild=" + leftChild +
                ", rightChild=" + rightChild +
                ", parent=" + parent +
                '}';
    }
}
