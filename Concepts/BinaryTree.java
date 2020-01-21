package Concepts;

public class BinaryTree {
    class Node {
        String s;
        Node left, right;

        Node(String s, Node left, Node right) {
            this.s = s;
            this.left = left;
            this.right = right;
        }
    }

    Node root;

    public BinaryTree(Node root) {
        this.root = root;
    }

    BinaryTree(String s) {
        root = new Node(s, null, null);
    }

    Node createNode(String s, Node left, Node right) {
        return new Node(s, left, right);
    }

    void printInorder(Node t) {
        if (t == null) return;
        printInorder(t.left);
        System.out.print(t.s + " ");
        printInorder(t.right);
    }

    /**
     * Space: O(1) i.e. no extra space
     * Time: O(n)
     * Left pointers are not changed. Right pointers point to inorder successor during thr traversal
     * but are then changed back to normal. This is an in-place algorithm that modifies the tree but brings
     * it back to normal at the end.
     * @param t
     */
    void printInorderMorris(Node t) {
        while (t != null) {
            if (t.left == null) {
                System.out.print(t.s + " ");
                t = t.right;
            } else {
                // find max element from left sub-tree of 't'
                Node pre = t.left;
                while (pre.right != null && pre.right != t) {
                    pre = pre.right;
                }

                if (pre.right == null) {
                    pre.right = t; // setting the thread
                    t = t.left;
                } else {
                    // pre.right == t, i.e. we have followed a thread and come up
                    pre.right = null; // revert the thread pointers of the tree
                    System.out.print(t.s + " ");
                    t = t.right;
                }
            }
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree("D");
        Node A = tree.createNode("A", null, null);
        Node C = tree.createNode("C", null, null);
        Node B = tree.createNode("B", A, C);
        Node F = tree.createNode("F", null, null);
        Node G = tree.createNode("G", F, null);
        Node E = tree.createNode("E", null, G);
        tree.root.left = B;
        tree.root.right = E;

        tree.printInorder(tree.root);
        System.out.println();
        tree.printInorderMorris(tree.root);
        System.out.println();
        tree.printInorderMorris(tree.root);
        System.out.println();
        tree.printInorderMorris(tree.root);
        System.out.println();
    }
}
