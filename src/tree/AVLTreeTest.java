package tree;

import org.junit.jupiter.api.Test;
import tree.TreeNode;

import static org.junit.Assert.assertEquals;

class AVLTreeTest {

    @Test
    void getBalanceFactor_rootNode() {
        AVLTree tree = new AVLTree();
        tree.insert(new TreeNode(10));
        assertEquals(0, tree.getBalanceFactor(tree.getRoot()));
    }

    @Test
    void getBalanceFactor_leftHeavy() {
        AVLTree tree = new AVLTree();
        tree.insert(new TreeNode(10));
        tree.insert(new TreeNode(5));
        assertEquals(1, tree.getBalanceFactor(tree.getRoot()));
    }

    @Test
    void getBalanceFactor_rightHeavy() {
        AVLTree tree = new AVLTree();
        tree.insert(new TreeNode(10));
        tree.insert(new TreeNode(15));
        assertEquals(-1, tree.getBalanceFactor(tree.getRoot()));
    }

    @Test
    void getBalanceFactor_balancedTree() {
        AVLTree tree = new AVLTree();
        tree.insert(new TreeNode(10));
        tree.insert(new TreeNode(5));
        tree.insert(new TreeNode(15));
        assertEquals(0, tree.getBalanceFactor(tree.getRoot()));
    }

    @Test
    void getBalanceFactor_unbalancedTree() {
        AVLTree tree = new AVLTree();
        tree.insert(new TreeNode(10));
        tree.insert(new TreeNode(5));
        tree.insert(new TreeNode(3));
        assertEquals(2, tree.getBalanceFactor(tree.getRoot()));
    }
}