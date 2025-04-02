package tree;

public class TreeNode {
    private int data;
    private TreeNode leftChild;
    private TreeNode rightChild;
    private TreeNode parent;

    public TreeNode(int data) {
        this.data = data;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }

    public int getNodeData() {
        return this.data;
    }

    public void setNodeData(int data) {
        this.data = data;
    }

    public TreeNode getLeftChild() {
        return this.leftChild;
    }

    public TreeNode getRightChild() {
        return this.rightChild;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public boolean isLeaf() {
        return this.leftChild == null && this.rightChild == null;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean hasSingleChild() {
        return (this.leftChild != null && this.rightChild == null) ||
                (this.leftChild == null && this.rightChild != null);
    }

    public boolean hasDoubleChild() {
        return this.leftChild != null && this.rightChild != null;
    }

    public TreeNode getChild() {
        if(!this.hasSingleChild()) throw new RuntimeException("Node does not have a single child");
        if(this.getLeftChild() != null) return this.leftChild;
        return this.rightChild;
    }

    @Override
    public String toString() {
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
