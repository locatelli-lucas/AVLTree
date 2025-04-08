package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
//        System.out.println(this.printTree());
    }

    public void delete(int index, int doubleChildMethod) {
        TreeNode current = this.search(index);
        if(current == null) throw new RuntimeException("Node not found");

        if(current.hasSingleChild())
            this.singleChildDelete(current);
        else if(current.hasDoubleChild())
            if(doubleChildMethod == 1)
                this.doubleChildDeleteByMerge(current);
            else if(doubleChildMethod == 2)
                this.doubleChildDeleteByCopy(current);
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
            if(parent.getNodeData() < node.getNodeData())
                parent.setLeftChild(child);
            else
                parent.setRightChild(child);
        } else if(node.isLeaf())
            this.deleteLeaf(node);
    }

    public void doubleChildDeleteByCopy(TreeNode node) {
        TreeNode current = node.getLeftChild();

        while(!current.isLeaf()) {
            current = current.getRightChild();
        }

        node.setNodeData(current.getNodeData());
        TreeNode leafParent = current.getParent();

        if(current == node.getLeftChild())
            leafParent.setLeftChild(null);
        else
            leafParent.setRightChild(null);

        current = null;
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
            current.setParent(leftChild);
            current.setLeftChild(null);
            leftChild.setRightChild(current);
            leftChild.setParent(parent);

            if(parent.getLeftChild() == current) parent.setLeftChild(leftChild);
            else parent.setRightChild(leftChild);

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
        }
    }

    public void simpleLeftRotation(TreeNode current) {
        TreeNode rightChild = current.getRightChild();

        if(rightChild.hasSingleChild() && !current.isRoot()) {
            TreeNode parent = current.getParent();
            if(parent.getParent() == null) this.setRoot(parent);
            parent.setRightChild(rightChild);
            rightChild.setParent(parent);
            rightChild.setLeftChild(current);
            current.setParent(rightChild);
            current.setRightChild(null);
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

    public List<TreeNode> runTree() {
        TreeNode current = this.root;
        List<TreeNode> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        list.add(current);

        while(current != null) {
            do {
                if(current.hasSingleChild())
                    current = current.getChild();
                else if(current.hasDoubleChild()) {
                    if(!current.isRoot() && !list.contains(current))
                        list.add(current);
                    stack.push(current);
                    current = current.getLeftChild();
                }
                list.add(current);
            } while(!current.isLeaf());

            if(stack.isEmpty()) break;
            current = stack.pop();
            current = current.getRightChild();
        }
        return list;
    }

    public String printTree() {
        StringBuilder sb = new StringBuilder();
        TreeNode current;
        List<TreeNode> list = runTree();
        list.forEach(e -> System.out.print(e.getNodeData() + " "));
        System.out.println();

        sb.append(list.getFirst().getNodeData()).append("\n");

        for (int i = 1; i < list.size(); i++) {
            TreeNode node = this.search(list.get(i).getNodeData());

            if(node.getNodeData() < this.root.getNodeData()) {
            }
        }

        return sb.toString();
    }
}