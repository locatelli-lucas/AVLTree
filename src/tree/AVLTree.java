package tree;

public class AVLTree {
    private TreeNode root;
    private TreeNode current;

    public AVLTree(TreeNode root) {
        this.root = root;
        this.current = root;
    }

    public TreeNode getRoot() {
        return this.root;
    }

    public TreeNode getCurrent() {
        return this.current;
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

    public void delete(int index) {

    }

    public void singleChildDelete(int index) {
        current = this.search(index);
        TreeNode child = current.getChild();
        TreeNode parent = current.getParent();

        if(parent != null && child != null) {
            if(parent.getNodeData() < current.getNodeData())
                parent.setLeftChild(child);
            else
                parent.setRightChild(child);
        }
    }

    public void doubleChildDeleteByCopy(int index) {
        current = this.search(index);
        TreeNode leaf = current.getLeftChild();

        while(!leaf.isLeaf())
            leaf = leaf.getRightChild();

        current.setNodeData(leaf.getNodeData());
        TreeNode leafParent = leaf.getParent();
        leafParent.setRightChild(null);
    }

    public void doubleChildDeleteByMerge(int index) {
        current = this.search(index);
        TreeNode rightChild = current.getRightChild();
        TreeNode newRoot = current.getLeftChild();

        while(!newRoot.isLeaf())
            newRoot = newRoot.getRightChild();

        newRoot.setRightChild(rightChild);
        current.setRightChild(null);

        TreeNode leftChild = current.getLeftChild();
        TreeNode parent = current.getParent();

        parent.setRightChild(leftChild);
    }

    public TreeNode search(int index) {
        boolean found = false;
        while(!found) {
            if(index == current.getNodeData())
                found = true;
            else if(index < current.getNodeData() && current.getLeftChild() != null)
                current = current.getLeftChild();
            else  if(index > current.getNodeData() && current.getRightChild() != null)
                current = current.getRightChild();
        }
        return current;
    }

    public boolean isBalanced() {
        int leftHeight = 1;
        int rightHeight = 1;
        TreeNode root = current;
        while(!current.isLeaf()) {
            leftHeight++;
            current = current.getLeftChild();
        }

        current = root;

        while(!current.isLeaf()) {
            rightHeight++;
            current = current.getRightChild();
        }

        int fb = leftHeight - rightHeight;

        return fb >= -1 && fb <= 1;

    }
}
