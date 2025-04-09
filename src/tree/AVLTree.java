package tree;

import java.util.ArrayList;
import java.util.List;

public class AVLTree {
    private TreeNode root;

    public AVLTree() {

    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void insert(TreeNode node) {
        if(this.root == null) {
            this.root = node;
        } else {
            TreeNode current = this.root;

            boolean inserted = false;
            int data = node.getNodeData();
            if (this.search(data) != null) throw new IllegalArgumentException("Data already exists in the tree");

            while (!inserted) {
                if (data < current.getNodeData()) {
                    TreeNode leftChild = current.getLeftChild();
                    if (leftChild == null) {
                        current.setLeftChild(node);
                        current.getLeftChild().setParent(current);
                        inserted = true;
                    } else {
                        current = current.getLeftChild();
                    }
                } else {
                    TreeNode rightChild = current.getRightChild();
                    if (rightChild == null) {
                        current.setRightChild(node);
                        current.getRightChild().setParent(current);
                        inserted = true;
                    } else {
                        current = current.getRightChild();
                   }
               }
          }
        }
        if(!node.isRoot())
            this.balanceTree(node);
        System.out.println("Nó " + node.getNodeData() + "inserido\n\n" + this.printTree());
    }

    public void delete(int index, int doubleChildMethod) {
        TreeNode current = this.search(index);
        if(current == null) throw new RuntimeException("Node not found");
        TreeNode parent = current.getParent();

        if(current.hasSingleChild())
            this.singleChildDelete(current);
        else if(current.hasDoubleChild()) {
            if (doubleChildMethod == 1)
                this.doubleChildDeleteByMerge(current);
            else if (doubleChildMethod == 2)
                this.doubleChildDeleteByCopy(current);
        } else
            this.deleteLeaf(current);
        this.balanceTree(parent);
        System.out.println("Nó " + index + " deletado\n\n" + this.printTree());
    }

    public void deleteLeaf(TreeNode node) {
        TreeNode current = node.getParent();
        if(current != null) {
            if(current.getLeftChild() == node)
                current.setLeftChild(null);
            else
                current.setRightChild(null);
        } else
            node = null;
    }

    public void singleChildDelete(TreeNode node) {
        TreeNode child = node.getChild();
        TreeNode parent = node.getParent();

        if(parent != null && child != null) {
            if(parent.getNodeData() > node.getNodeData())
                parent.setLeftChild(child);
            else
                parent.setRightChild(child);
            child.setParent(parent);
        }
    }

    public void doubleChildDeleteByCopy(TreeNode node) {
        TreeNode current = node.getLeftChild();

        while(!current.isLeaf() && current.getRightChild() != null)
            current = current.getRightChild();

        node.setNodeData(current.getNodeData());

        TreeNode parent = current.getParent();

        TreeNode child;
        if(current.hasSingleChild()) {
            child = current.getChild();
        } else if(current.hasDoubleChild()) {

        }


        if(parent.getNodeData() > child.getNodeData())
            parent.setLeftChild(child);
        else
            parent.setRightChild(child);

        child.setParent(parent);
    }

    public void doubleChildDeleteByMerge(TreeNode node) {
        TreeNode rightChild = node.getRightChild();
        TreeNode newRoot = node.getLeftChild();

        while(!newRoot.isLeaf() && newRoot.getRightChild() != null)
            newRoot = newRoot.getRightChild();

        newRoot.setRightChild(rightChild);
        rightChild.setParent(newRoot);

        TreeNode leftChild = node.getLeftChild();
        TreeNode parent = node.getParent();

        parent.setRightChild(leftChild);
        leftChild.setParent(parent);
    }

    public TreeNode search(int index) {
        TreeNode current = this.root;
        while(current != null) {
            if(index == current.getNodeData())
                return current;
            else if(index < current.getNodeData())
                current = current.getLeftChild();
            else if(index > current.getNodeData())
                current = current.getRightChild();
        }
        return null;
    }

    public int getBalanceFactor(TreeNode current) {
        if(current.isLeaf()) return 0;
        int leftHeight = 0;
        int rightHeight = 0;
        if(current.getLeftChild() != null) leftHeight = this.calculateHeight(current.getLeftChild(), 0, 1);
        if(current.getRightChild() != null) rightHeight = this.calculateHeight(current.getRightChild(), 0, 2);

        return leftHeight - rightHeight;
    }

    public int calculateHeight(TreeNode current, int height, int calcType) {
        if(current.isLeaf()) return height + 1;
        if(current.hasDoubleChild()) {
            int leftHeight = calculateHeight(current.getLeftChild(), height + 1, calcType);
            int rightHeight = calculateHeight(current.getRightChild(), height + 1, calcType);
            if(calcType == 0) return Math.max(leftHeight, rightHeight);
            else if(calcType == 1) return leftHeight;
            else return rightHeight;
        }
        return calculateHeight(current.getChild(), height + 1, calcType);
    }

    public boolean isBalanced(TreeNode current) {
        int fb = this.getBalanceFactor(current);
        return fb >= -1 && fb <= 1;
    }

    public void simpleRightRotation(TreeNode current) {
        TreeNode leftChild = current.getLeftChild();
        TreeNode parent = current.getParent();

        if(leftChild.hasSingleChild() && !current.isRoot()) {
            if(leftChild.getLeftChild() == null) {
                TreeNode newParent = leftChild.getRightChild();

                leftChild.setParent(newParent);
                leftChild.setRightChild(null);
                newParent.setLeftChild(leftChild);
                newParent.setParent(parent);
                newParent.setRightChild(current);
                current.setLeftChild(null);
                current.setParent(newParent);
                parent.setRightChild(newParent);
            } else {
                current.setParent(leftChild);
                current.setLeftChild(null);
                leftChild.setRightChild(current);
                leftChild.setParent(parent);

                if(parent.getLeftChild() == current) parent.setLeftChild(leftChild);
                else parent.setRightChild(leftChild);
            }
        } else if(leftChild.hasSingleChild() && current.isRoot()) {
            this.setRoot(leftChild);
            leftChild.setRightChild(current);
            current.setParent(leftChild);
        } else if(leftChild.hasDoubleChild() && current.isRoot()) {
            TreeNode rightChild = leftChild.getRightChild();
            this.setRoot(leftChild);
            leftChild.setRightChild(current);
            current.setParent(leftChild);
            current.setLeftChild(rightChild);
            rightChild.setParent(current);
        } else if(leftChild.hasDoubleChild() && !current.isRoot()) {
            TreeNode rightChild = leftChild.getRightChild();
            leftChild.setParent(current.getParent());
            current.setParent(leftChild);
            current.setLeftChild(rightChild);
            rightChild.setParent(current);
            leftChild.setRightChild(current);
            leftChild.getParent().setLeftChild(leftChild);
        }
    }

    public void simpleLeftRotation(TreeNode current) {
        TreeNode rightChild = current.getRightChild();
        TreeNode parent = current.getParent();

        if(rightChild.hasSingleChild() && !current.isRoot()) {
            if(rightChild.getRightChild() == null) {
                TreeNode newParent = rightChild.getLeftChild();

                rightChild.setParent(newParent);
                rightChild.setRightChild(null);
                newParent.setRightChild(rightChild);
                newParent.setParent(parent);
                newParent.setLeftChild(current);
                current.setRightChild(null);
                current.setParent(newParent);
                parent.setRightChild(newParent);
            } else {
                current.setParent(rightChild);
                current.setRightChild(null);
                rightChild.setLeftChild(current);
                rightChild.setParent(parent);

                if(parent.getRightChild() == current) parent.setRightChild(rightChild);
                else parent.setLeftChild(rightChild);
            }
        } else if(rightChild.hasSingleChild() && current.isRoot()) {
            this.setRoot(rightChild);
            rightChild.setParent(null);
            rightChild.setLeftChild(current);
            current.setParent(rightChild);
            current.setRightChild(null);
        } else if(rightChild.hasDoubleChild() && current.isRoot()) {
            TreeNode leftChild = rightChild.getLeftChild();
            this.setRoot(rightChild);
            rightChild.setParent(null);
            rightChild.setLeftChild(current);
            current.setParent(rightChild);
            current.setRightChild(leftChild);
            leftChild.setParent(current);
        } else if(rightChild.hasDoubleChild() && !current.isRoot()) {
            rightChild.setParent(current.getParent());
            current.setParent(rightChild);
            current.setRightChild(rightChild.getLeftChild());
            rightChild.getLeftChild().setParent(current);
            rightChild.getParent().setLeftChild(rightChild);
            rightChild.setLeftChild(current);
        }
    }

    public void balanceTree(TreeNode current) {
        if(current == null) throw new RuntimeException("Node is null");
        current = current.getParent();
        while(current != null || !this.isBalanced(this.root)) {
            if(current == null) break;
            int fb = this.getBalanceFactor(current);
            if(fb > 1)
                this.simpleRightRotation(current);
            else if(fb < -1)
                this.simpleLeftRotation(current);
            current = current.getParent();
        }
    }

    public String printTree() {
        StringBuilder sb = new StringBuilder();
        printTreeHelper(this.root, sb, "", true);
        return sb.toString();
    }

    private void printTreeHelper(TreeNode node, StringBuilder sb, String prefix, boolean isTail) {
        if (node == null) return;

        sb.append(prefix)
                .append(isTail ? "└── " : "├── ")
                .append(node.getNodeData())
                .append("\n");

        List<TreeNode> children = new ArrayList<>();
        if (node.getLeftChild() != null) children.add(node.getLeftChild());
        if (node.getRightChild() != null) children.add(node.getRightChild());

        for (int i = 0; i < children.size(); i++) {
            printTreeHelper(children.get(i), sb, prefix + (isTail ? "    " : "│   "), i == children.size() - 1);
        }
    }

}