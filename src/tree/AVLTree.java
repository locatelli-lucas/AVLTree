package tree;

public class AVLTree {
    private TreeNode root;
    private TreeNode current;

    public AVLTree(TreeNode root) {
        this.root = root;
        this.current = root;
    }

    public void insert(TreeNode node) {
        boolean inserted = false;
        int data = node.getNodeData();
        if(data == current.getNodeData()) throw new IllegalArgumentException("Data already exists in the tree");

        while(!inserted) {
            if(data < current.getNodeData()) {
                TreeNode leftChild = current.getLeftChild();
                if(leftChild == null) {
                    current.setLeftChild(node);
                    inserted = true;
                } else {
                    current = current.getLeftChild();
                }
            } else {
                TreeNode rightChild = current.getRightChild();
                if(rightChild == null) {
                    current.setRightChild(node);
                    inserted = true;
                } else {
                    current = current.getRightChild();
                }
            }
        }
    }
}
