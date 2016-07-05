import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Your implementation of an AVL Tree.
 *
 * @author jredston3
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {

    // Do not make any new instance variables.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree. DO
     * NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
        root = null;
        size = 0;
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data should
     * be added in the same order it is in the Collection.
     *
     * @param data
     *            the data to add to the tree
     * @throws IllegalArgumentException
     *             if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data entered is null");
        }
        size = 0;
        for (T item : data) {
            add(item);
        }

    }

    /**
     * Calculates height and balance factor of all nodes
     *
     * @param node
     *            node who's height being calculated
     * @return int of height
     */
    private int calcHeight(AVLNode<T> node) {

        if (node == null) {
            return 0;
        }
        if (node.getLeft() == null && node.getRight() == null) {
            node.setBalanceFactor(0);
            node.setHeight(0);
            return 0;
        } else {

            node.setHeight(Math.max(calcHeight(node.getLeft()),
                    calcHeight(node.getRight())) + 1);

            int leftHeight;
            int rightHeight;
            if (node.getLeft() == null) {
                leftHeight = -1;
            } else {
                leftHeight = node.getLeft().getHeight();
            }
            if (node.getRight() == null) {
                rightHeight = -1;
            } else {
                rightHeight = node.getRight().getHeight();
            }
            node.setBalanceFactor(leftHeight - rightHeight);
            return node.getHeight();
        }
    }

    @Override
    public void add(T data) {

        if (data == null) {
            throw new IllegalArgumentException("data given is null");
        }
        if (root == null) {

            root = new AVLNode<T>(data);
            root.setBalanceFactor(0);
            root.setHeight(0);
            size++;
        } else {

            root = add(data, root);

        }

        calcHeight(root);
        // System.out.println("Before balancing my tree looks like this");

        // balanceTree(root, null);
        // System.out.println("After balancing my tree looks like this");

        // calcHeight(root);
    }

    /**
     * @param node
     *            node we're currently at
     * @param data
     *            data being added
     * @return AVLNode new node
     */

    private AVLNode<T> add(T data, AVLNode<T> node) {
        if (node == null) {
            node = new AVLNode<T>(data);
            size++;
        } else {
            int i = data.compareTo(node.getData());

            if (i < 0) {
                node.setLeft(add(data, node.getLeft()));
                int leftHeight;
                if (node.getLeft() == null) {
                    leftHeight = -1;
                } else {
                    leftHeight = node.getLeft().getHeight();
                }
                int rightHeight;
                if (node.getRight() == null) {
                    rightHeight = -1;
                } else {
                    rightHeight = node.getRight().getHeight();
                }
                if (leftHeight - rightHeight == 2) {
                    if (data.compareTo(node.getLeft().getData()) < 0) {
                        node = rotateWithLeftChild(node);
                    } else {
                        node = doubleWithLeftChild(node);
                    }
                }
            } else if (i > 0) {
                node.setRight(add(data, node.getRight()));
                int leftHeight;
                if (node.getLeft() == null) {
                    leftHeight = -1;
                } else {
                    leftHeight = node.getLeft().getHeight();
                }
                int rightHeight;
                if (node.getRight() == null) {
                    rightHeight = -1;
                } else {
                    rightHeight = node.getRight().getHeight();
                }
                if (rightHeight - leftHeight == 2) {
                    if (data.compareTo(node.getRight().getData()) > 0) {
                        node = rotateWithRightChild(node);
                    } else {
                        node = doubleWithRightChild(node);
                    }
                }
            }
        }

        int leftHeight;
        if (node.getLeft() == null) {
            leftHeight = -1;
        } else {
            leftHeight = node.getLeft().getHeight();
        }
        int rightHeight;
        if (node.getRight() == null) {
            rightHeight = -1;
        } else {
            rightHeight = node.getRight().getHeight();
        }

        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        return node;
    }

    /**
     * Rotates with left child
     *
     * @param node2
     *            node being rotated around
     * @return AVLNode nodes in correct order
     */
    private AVLNode<T> rotateWithLeftChild(AVLNode<T> node2) {
        AVLNode<T> node1 = node2.getLeft();
        node2.setLeft(node1.getRight());
        node1.setRight(node2);
        int leftHeight;
        if (node2.getLeft() == null) {
            leftHeight = -1;
        } else {
            leftHeight = node2.getLeft().getHeight();
        }
        int rightHeight;
        if (node2.getRight() == null) {
            rightHeight = -1;
        } else {
            rightHeight = node2.getRight().getHeight();
        }
        int leftHeight2;
        if (node1.getLeft() == null) {
            leftHeight2 = -1;
        } else {
            leftHeight2 = node1.getLeft().getHeight();
        }
        node2.setHeight(Math.max(leftHeight, rightHeight) + 1);

        node1.setHeight(Math.max(leftHeight2, node2.getHeight()) + 1);
        return node1;
    }

    /**
     * Rotate binary tree node with right child. For AVL trees, this is a single
     * rotation for case 4. Update heights, then return new root.
     * 
     * @param node1
     *            node being moved around
     * @return AVLNode nodes in correct order
     */
    private AVLNode<T> rotateWithRightChild(AVLNode<T> node1) {
        AVLNode<T> node2 = node1.getRight();
        node1.setRight(node2.getLeft());
        node2.setLeft(node1);
        int leftHeight;
        if (node1.getLeft() == null) {
            leftHeight = -1;
        } else {
            leftHeight = node1.getLeft().getHeight();
        }
        int rightHeight;
        if (node1.getRight() == null) {
            rightHeight = -1;
        } else {
            rightHeight = node1.getRight().getHeight();
        }
        int rightHeight2;
        if (node2.getRight() == null) {
            rightHeight2 = -1;
        } else {
            rightHeight2 = node2.getRight().getHeight();
        }
        node1.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node2.setHeight(Math.max(rightHeight2, node1.getHeight()) + 1);
        return node2;
    }

    /**
     * Double Rotates with left child
     *
     * @param node3
     *            node being rotated around
     * @return AVLNode nodes in correct order
     */
    private AVLNode<T> doubleWithLeftChild(AVLNode<T> node3) {
        node3.setLeft(rotateWithRightChild(node3.getLeft()));
        return rotateWithLeftChild(node3);
    }

    /**
     * Double Rotates with right child
     *
     * @param node1
     *            node being rotated around
     * @return AVLNode nodes in correct order
     */
    private AVLNode<T> doubleWithRightChild(AVLNode<T> node1) {
        node1.setRight(rotateWithLeftChild(node1.getRight()));
        return rotateWithRightChild(node1);
    }

    /**
     * balances the tree
     *
     * @param node
     *            node being rotated around
     * @return AVLNode nodes in correct order
     */
    private AVLNode<T> balanceTree(AVLNode<T> node) {
        if (node == null) {
            return null;
        } else {

            balanceTree(node.getLeft());
            balanceTree(node.getRight());
            System.out.println(node);

            if (node.getBalanceFactor() == 2) {
                if (node.getLeft().getBalanceFactor() > -1) {
                    System.out.println("single left");

                    node = rotateWithLeftChild(node);
                } else {
                    System.out.println("double left");

                    node = doubleWithLeftChild(node);
                }
            } else if (node.getBalanceFactor() == -2) {
                if (node.getRight().getBalanceFactor() < 1) {
                    System.out.println("single right");

                    node = rotateWithRightChild(node);
                } else {
                    System.out.println("double right");

                    node = doubleWithRightChild(node);
                }
            }
        }
        int leftHeight;
        if (node.getLeft() == null) {
            leftHeight = -1;
        } else {
            leftHeight = node.getLeft().getHeight();
        }
        int rightHeight;
        if (node.getRight() == null) {
            rightHeight = -1;
        } else {
            rightHeight = node.getRight().getHeight();
        }

        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node.setBalanceFactor(leftHeight - rightHeight);
        return node;

    }

    @Override
    public T remove(T data) {
        T removed;
        if (root == null) {

            throw new java.util.NoSuchElementException(
                    "Element is not in AVL cannot be removed");
        }
        if (data == null) {

            throw new IllegalArgumentException("Data given is null");
        }

        if (root.getData().equals(data)) {
            size--;
            AVLNode<T> myNode = root;
            if (myNode.getLeft() != null && myNode.getRight() != null) {
                // has two children
                // //System.out.println("two children");
                AVLNode<T> previous = myNode.getRight();
                AVLNode<T> current = myNode.getRight();
                if (current.getLeft() == null) {
                    root = (current);
                    current.setLeft(myNode.getLeft());
                } else {
                    while (current.getLeft() != null) {
                        previous = current;
                        current = current.getLeft();
                    }
                    current.setLeft(myNode.getLeft());
                    current.setRight(myNode.getRight());
                    previous.setLeft(null);
                    root = (current);
                }

                removed = myNode.getData();
            } else if (myNode.getLeft() == null && myNode.getRight() == null) {
                // node is a leaf simply remove it

                root = null;
                removed = myNode.getData();
            } else if (myNode.getLeft() != null) {
                // only a left branch simply replace
                root = (myNode.getLeft());
                removed = myNode.getData();
            } else {
                // only right branch simply replace
                root = (myNode.getRight());
                removed = myNode.getData();
            }
        } else {

            removed = remove(root, data);
        }

        calcHeight(root);

        root = balanceTree(root);

        calcHeight(root);
        return removed;

    }

    /**
     * @param node
     *            node we're currently at
     * @param data
     *            data being removed
     * @return T data that was removed
     */
    private T remove(AVLNode<T> node, T data) {
        int i = node.getData().compareTo(data);

        if (i < 0) {
            // data is greater than node data

            if (node.getRight() == null) {

                throw new java.util.NoSuchElementException(
                        "Data is not in AVL cannot be removed");
            } else {
                if (node.getRight().getData().equals(data)) {
                    size--;
                    // found our node
                    AVLNode<T> myNode = node.getRight();
                    if (myNode.getLeft() != null && myNode.getRight() != null) {

                        // has two children
                        AVLNode<T> previous = myNode.getRight();
                        AVLNode<T> current = myNode.getRight();
                        if (current.getLeft() == null) {
                            node.setRight(current);
                            current.setLeft(myNode.getLeft());
                        } else {
                            while (current.getLeft() != null) {
                                previous = current;
                                current = current.getLeft();
                            }
                            AVLNode<T> currentRight = current;
                            while (currentRight.getRight() != null) {
                                currentRight = currentRight.getRight();
                            }

                            current.setLeft(myNode.getLeft());
                            currentRight.setRight(myNode.getRight());
                            previous.setLeft(null);
                            node.setRight(current);
                        }
                        return myNode.getData();

                    } else if (myNode.getLeft() == null
                            && myNode.getRight() == null) {

                        // node is a leaf simply remove it
                        node.setRight(null);
                        return myNode.getData();
                    } else if (myNode.getLeft() != null) {
                        // only a left branch simply replace

                        node.setRight(myNode.getLeft());
                        return myNode.getData();
                    } else {
                        // only right branch simply replace

                        node.setRight(myNode.getRight());
                        return myNode.getData();
                    }
                } else {
                    return remove(node.getRight(), data);
                }
            }

        } else {
            // data less than node data
            if (node.getLeft() == null) {

                throw new java.util.NoSuchElementException(
                        "Data is not in AVL cannot be removed");
            } else {
                if (node.getLeft().getData().equals(data)) {
                    size--;
                    // found our node
                    AVLNode<T> myNode = node.getLeft();
                    if (myNode.getLeft() != null && myNode.getRight() != null) {
                        // has two children
                        // //System.out.println("two children");
                        AVLNode<T> previous = myNode.getRight();
                        AVLNode<T> current = myNode.getRight();
                        if (current.getLeft() == null) {
                            node.setLeft(current);
                            current.setLeft(myNode.getLeft());
                        } else {
                            while (current.getLeft() != null) {
                                previous = current;
                                current = current.getLeft();
                            }
                            current.setLeft(myNode.getLeft());
                            current.setRight(myNode.getRight());
                            previous.setLeft(null);
                            node.setLeft(current);
                        }

                        return myNode.getData();
                    } else if (myNode.getLeft() == null
                            && myNode.getRight() == null) {
                        // node is a leaf simply remove it

                        node.setLeft(null);
                        return myNode.getData();
                    } else if (myNode.getLeft() != null) {
                        // only a left branch simply replace
                        node.setLeft(myNode.getLeft());
                        return myNode.getData();
                    } else {
                        // only right branch simply replace
                        node.setLeft(myNode.getRight());
                        return myNode.getData();
                    }
                } else {
                    return remove(node.getLeft(), data);
                }
            }
        }
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data entered is null");
        }
        if (root == null) {
            throw new java.util.NoSuchElementException("Data is not in AVL");
        }
        AVLNode<T> dataNode = find(root, data);
        if (dataNode == null) {
            // data was not found
            throw new java.util.NoSuchElementException("Data is not in AVL");
        }
        return dataNode.getData();

    }

    /**
     * @param node
     *            node we're currently at
     * @param data
     *            data being found
     * @return AVLNode of nodes
     */
    private AVLNode<T> find(AVLNode<T> node, T data) {
        int i = node.getData().compareTo(data);

        if (i == 0) {
            return node;
        }
        if (i < 0) {
            // data is greater than node data
            if (node.getRight() == null) {
                return null;
            } else {
                return find(node.getRight(), data);
            }

        } else {
            // data less than node data
            if (node.getLeft() == null) {
                return null;
            } else {
                return find(node.getLeft(), data);
            }
        }

    }

    @Override
    public boolean contains(T data) {
        if (root == null) {
            throw new java.util.NoSuchElementException(
                    "AVL does not contain that element");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data given is null");
        }
        if (find(root, data) == null) {
            return false;
        }
        return true;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        List<T> nodes = new ArrayList<T>();
        return preorder(nodes, root);
    }

    /**
     * @param nodes
     *            list of nodes so far
     * @param node
     *            currently at
     * @return List of data
     */
    private List<T> preorder(List<T> nodes, AVLNode<T> node) {
        if (node != null) {
            nodes.add(node.getData());
            preorder(nodes, node.getLeft());
            preorder(nodes, node.getRight());
        }
        return nodes;
    }
    /**
     * Get the postorder traversal of the tree.
     *
     * @return a postorder traversal of the tree, or an empty list
     */
    public List<T> postorder() {
        List<T> nodes = new ArrayList<T>();
        return postorder(nodes, root);
    }

    /**
     * @param nodes
     *            list of nodes so far
     * @param node
     *            currently at
     * @return List of data
     */
    private List<T> postorder(List<T> nodes, AVLNode<T> node) {
        if (node != null) {
            postorder(nodes, node.getLeft());
            postorder(nodes, node.getRight());
            nodes.add(node.getData());

        }
        return nodes;
    }

    @Override
    public List<T> inorder() {
        List<T> nodes = new ArrayList<T>();
        return inorder(nodes, root);
    }

    /**
     * @param nodes
     *            list of nodes so far
     * @param node
     *            currently at
     * @return List of data
     */
    private List<T> inorder(List<T> nodes, AVLNode<T> node) {
        if (node != null) {
            inorder(nodes, node.getLeft());
            nodes.add(node.getData());
            inorder(nodes, node.getRight());

        }
        return nodes;
    }

    @Override
    public List<T> levelorder() {
        List<T> listData = new LinkedList<T>();
        List<AVLNode<T>> listNodes = new LinkedList<AVLNode<T>>();
        listNodes.add(root);
        while (!listNodes.isEmpty()) {

            AVLNode<T> current = listNodes.remove(0);

            listData.add(current.getData());

            if (current.getLeft() != null) {

                listNodes.add(current.getLeft());
            }
            if (current.getRight() != null) {

                listNodes.add(current.getRight());

            }
        }
        return listData;

    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Compares two AVLs and checks to see if the trees are the same. If the
     * trees have the same data in a different arrangement, this method should
     * return false. This will only return true if the tree is in the exact same
     * arrangement as the other tree.
     *
     * You may assume that you won't get an AVL with a different generic type.
     * For example, if this AVL holds Strings, then you will not get as an input
     * an AVL that holds Integers.
     * 
     * Be sure to also implement the other general checks that .equals() should
     * check as well.
     * 
     * @param other
     *            the Object we are comparing this AVL to
     * @return true if other is equal to this AVL, false otherwise.
     */
    public boolean equals(Object other) {
        if (other == null || !(other instanceof AVL)) {

            return false;
        }
        AVL<T> otherAVL = (AVL<T>) other;
        return equals(root, otherAVL.getRoot());
    }

    /**
     * @param node
     *            our node
     * @param other
     *            node comparing to
     * @return boolean whether its equals or not
     */
    private boolean equals(AVLNode<T> node, AVLNode<T> other) {
        if (node == null && other == null) {

            return true;
        }
        if (node == null || other == null) {

            return false;
        }
        if (node.getData().equals(other.getData())) {

            return (equals(node.getRight(), other.getRight()) && equals(
                    node.getLeft(), other.getLeft()));
        }
        return false;

    }

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }

    
}
