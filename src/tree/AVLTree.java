package tree;

public class AVLTree {
    private TreeNode root;

    public AVLTree(TreeNode root) {
        this.root = root;
    }

    public TreeNode getRoot() {
        return this.root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void insert(TreeNode node) {
        TreeNode current = this.root;

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

    public void delete(int index, int doubleChildMethod) {
        TreeNode current = this.search(index);

        if(current.hasSingleChild())
            this.singleChildDelete(current);
        else if(current.hasDoubleChild())
            if(doubleChildMethod == 1)
                this.doubleChildDeleteByMerge(current);
            else if(doubleChildMethod == 2)
                this.doubleChildDeleteByCopy(current);
        else if(current.isLeaf())
            this.deleteLeaf(current);
    }

    public void deleteLeaf(TreeNode node) {
        TreeNode parent = node.getParent();
        if(parent != null) {
            if(parent.getLeftChild() == node)
                parent.setLeftChild(null);
            else
                parent.setRightChild(null);
        }
    }

    public void singleChildDelete(TreeNode node) {
        TreeNode child = node.getChild();
        TreeNode parent = node.getParent();

        if(parent != null && child != null) {
            if(parent.getNodeData() < node.getNodeData())
                parent.setLeftChild(child);
            else
                parent.setRightChild(child);
        }
    }

    public void doubleChildDeleteByCopy(TreeNode node) {
        TreeNode leaf = node.getLeftChild();

        while(!leaf.isLeaf())
            leaf = leaf.getRightChild();

        node.setNodeData(leaf.getNodeData());
        TreeNode leafParent = leaf.getParent();
        leafParent.setRightChild(null);
    }

    public void doubleChildDeleteByMerge(TreeNode node) {
        TreeNode rightChild = node.getRightChild();
        TreeNode newRoot = node.getLeftChild();

        while(!newRoot.isLeaf())
            newRoot = newRoot.getRightChild();

        newRoot.setRightChild(rightChild);
        node.setRightChild(null);

        TreeNode leftChild = node.getLeftChild();
        TreeNode parent = node.getParent();

        parent.setRightChild(leftChild);
    }

    public TreeNode search(int index) {
        TreeNode current = this.root;
        boolean found = false;
        while(!found) {
            if(index == current.getNodeData())
                found = true;
            else if(index < current.getNodeData() && current.getLeftChild() != null)
                current = current.getLeftChild();
            else if(index > current.getNodeData() && current.getRightChild() != null)
                current = current.getRightChild();
        }
        return current;
    }

    public int getBalanceFactor(TreeNode current) {
        int leftHeight = 0;
        int rightHeight = 0;
        TreeNode root = current;
        do {
            current = current.getLeftChild();
            leftHeight++;
        } while(!current.isLeaf());

        current = root;

        do {
            current = current.getRightChild();
            rightHeight++;
        } while(!current.isLeaf());

        return leftHeight - rightHeight;
    }

    public boolean isBalanced(TreeNode current) {
        int fb = this.getBalanceFactor(current);
        return fb >= -1 && fb <= 1;
    }

    public void simpleRightRotation(TreeNode current) {
        TreeNode leftChild = current.getLeftChild();

        if(leftChild.hasSingleChild() && current.getParent() != null) {
            TreeNode parent = current.getParent();
            parent.setLeftChild(leftChild);
            leftChild.setRightChild(current);
        } else if(leftChild.hasSingleChild() && current.getParent() == null) {
            this.setRoot(leftChild);
            leftChild.setRightChild(current);
        } else if(leftChild.hasDoubleChild() && current.getParent() == null) {
            TreeNode rightChild = leftChild.getRightChild();
            this.setRoot(leftChild);
            leftChild.setRightChild(current);
            current.setLeftChild(rightChild);
        }
    }

    public void simpleLeftRotation(TreeNode current) {
        TreeNode rightChild = current.getRightChild();
        if(rightChild.hasSingleChild() && current.getParent() != null) {
            TreeNode parent = current.getParent();
            parent.setRightChild(rightChild);
            rightChild.setLeftChild(current);
        } else if(rightChild.hasSingleChild() && current.getParent() == null) {
            this.setRoot(rightChild);
            rightChild.setLeftChild(current);
        } else if(rightChild.hasDoubleChild() && current.getParent() == null) {
            TreeNode leftChild = rightChild.getLeftChild();
            this.setRoot(rightChild);
            rightChild.setLeftChild(current);
            current.setRightChild(leftChild);
        }
    }

    public void doubleRotation(TreeNode current) {
        int fb = this.getBalanceFactor(current);

        if(fb > 1) {

        }

    }


}
