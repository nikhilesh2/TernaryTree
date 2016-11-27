package cs445.a5;

import java.util.Iterator;
import java.util.NoSuchElementException;
import StackAndQueuePackage.*; // Needed by tree iterators


public class TernaryTree<T> implements TernaryTreeInterface<T> {
	private TernaryNode<T> root;

    public TernaryTree() {
        root = null;
    }

    public TernaryTree(T rootData) {
        root = new TernaryNode<>(rootData);
    }

    public TernaryTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> middleTree,
                      TernaryTree<T> rightTree) {
        privateSetTree(rootData, leftTree, middleTree, rightTree);
    }

    public void setTree(T rootData) {
        root = new TernaryNode<>(rootData);
    }

    public void setTree(T rootData, TernaryTreeInterface<T> leftTree,TernaryTreeInterface<T> middleTree,
                        TernaryTreeInterface<T> rightTree) {
        privateSetTree(rootData, (TernaryTree<T>)leftTree,(TernaryTree<T>)middleTree,
                       (TernaryTree<T>)rightTree);
    }

    private void privateSetTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> middleTree,
                                TernaryTree<T> rightTree) {
        root = new TernaryNode<>(rootData);

        if ((leftTree != null) && !leftTree.isEmpty()) {
            root.setLeftChild(leftTree.root);
        }

        if ((rightTree != null) && !rightTree.isEmpty()) {
            if (rightTree != leftTree && rightTree != middleTree) {
                root.setRightChild(rightTree.root);
            } else {
                root.setRightChild(rightTree.root.copy());
            }
        }
        if ((middleTree != null) && !middleTree.isEmpty()) {
            if (middleTree != leftTree && middleTree != rightTree) {
                root.setMiddleChild(middleTree.root);
            } else {
                root.setMiddleChild(middleTree.root.copy());
            }
        }
        

        if ((leftTree != null) && (leftTree != this)) {
            leftTree.clear();
        }

        if ((rightTree != null) && (rightTree != this)) {
            rightTree.clear();
        }
        if ((middleTree != null) && (middleTree != this)) {
            middleTree.clear();
        }
    }

    public T getRootData() {
        if (isEmpty()) {
            throw new EmptyTreeException();
        } else {
            return root.getData();
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }

    protected void setRootData(T rootData) {
        root.setData(rootData);
    }

    protected void setRootNode(TernaryNode<T> rootNode) {
        root = rootNode;
    }

    protected TernaryNode<T> getRootNode() {
        return root;
    }

    public int getHeight() {
        return root.getHeight();
    }

    public int getNumberOfNodes() {
        return root.getNumberOfNodes();
    }
    public Iterator<T> getPreorderIterator() {
        return new PreorderIterator();
    }

    public Iterator<T> getInorderIterator() {
        return new InorderIterator();
    }

    public Iterator<T> getPostorderIterator() {
        return new PostorderIterator();
    }

    public Iterator<T> getLevelOrderIterator() {
        return new LevelOrderIterator();
    }

    private class PreorderIterator implements Iterator<T> {
        private StackInterface<TernaryNode<T>> nodeStack;

        public PreorderIterator() {
            nodeStack = new LinkedStack<>();
            if (root != null) {
                nodeStack.push(root);
            }
        }

        public boolean hasNext() {
            return !nodeStack.isEmpty();
        }

        public T next() {
            TernaryNode<T> nextNode;

            if (hasNext()) {
                nextNode = nodeStack.pop();
                TernaryNode<T> leftChild = nextNode.getLeftChild();
                TernaryNode<T> middleChild = nextNode.getMiddleChild();
                TernaryNode<T> rightChild = nextNode.getRightChild();

                // Push into stack in reverse order of recursive calls
                if (rightChild != null) {
                    nodeStack.push(rightChild);
                }
                if (middleChild != null) {
                    nodeStack.push(middleChild);
                }

                if (leftChild != null) {
                    nodeStack.push(leftChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public void iterativePreorderTraverse() {
        StackInterface<TernaryNode<T>> nodeStack = new LinkedStack<>();
        if (root != null) {
            nodeStack.push(root);
        }
        TernaryNode<T> nextNode;
        while (!nodeStack.isEmpty()) {
            nextNode = nodeStack.pop();
            TernaryNode<T> leftChild = nextNode.getLeftChild();
            TernaryNode<T> middleChild = nextNode.getMiddleChild();
            TernaryNode<T> rightChild = nextNode.getRightChild();

            // Push into stack in reverse order of recursive calls
            if (rightChild != null) {
                nodeStack.push(rightChild);
            }
            if (middleChild != null) {
                nodeStack.push(middleChild);
            }

            if (leftChild != null) {
                nodeStack.push(leftChild);
            }

            System.out.print(nextNode.getData() + " ");
        }
    }

    private class InorderIterator implements Iterator<T> {
        private StackInterface<TernaryNode<T>> nodeStack;
        private TernaryNode<T> currentNode;

        public InorderIterator() {
            nodeStack = new LinkedStack<>();
            currentNode = root;
        }

        public boolean hasNext() {
            return !nodeStack.isEmpty() || (currentNode != null);
        }

        public T next() {
            TernaryNode<T> nextNode = null;

            // Find leftmost node with no left child
            while (currentNode != null) {
                nodeStack.push(currentNode);
                currentNode = currentNode.getLeftChild();
            }

            // Get leftmost node, then move to its right subtree
            if (!nodeStack.isEmpty()) {
                nextNode = nodeStack.pop();
                assert nextNode != null; // Since nodeStack was not empty
                // before the pop
                currentNode = nextNode.getRightChild();
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public void iterativeInorderTraverse() {
        StackInterface<TernaryNode<T>> nodeStack = new LinkedStack<>();
        TernaryNode<T> currentNode = root;

        while (!nodeStack.isEmpty() || (currentNode != null)) {
            // Find leftmost node with no left child
            while (currentNode != null) {
                nodeStack.push(currentNode);
                currentNode = currentNode.getLeftChild();
            }

            // Visit leftmost node, then traverse its right subtree
            if (!nodeStack.isEmpty()) {
                TernaryNode<T> nextNode = nodeStack.pop();
                assert nextNode != null; // Since nodeStack was not empty
                // before the pop
                System.out.print(nextNode.getData() + " ");
                currentNode = nextNode.getRightChild();
            }
        }
    }

    private class PostorderIterator implements Iterator<T> {
    	
        private StackInterface<TernaryNode<T>> nodeStack;
        private TernaryNode<T> currentNode;

        public PostorderIterator() {
            nodeStack = new LinkedStack<>();
            currentNode = root;
        }

        public boolean hasNext() {
            return !nodeStack.isEmpty() || (currentNode != null);
        }
        public T next() {
            boolean foundNext = false;
            TernaryNode<T> leftChild, middleChild, rightChild, nextNode = null;

            // Find leftmost leaf
            while (currentNode != null) {
                nodeStack.push(currentNode);
                leftChild = currentNode.getLeftChild();
                if (leftChild == null) {
                    currentNode = currentNode.getMiddleChild();
                    middleChild = currentNode;
                    if(middleChild == null){
                    	  currentNode = currentNode.getRightChild();
                    }else{
                    	currentNode = middleChild;
                    }
                } else {
                    currentNode = leftChild;
                }
            }

            // Stack is not empty either because we just pushed a node, or
            // it wasn't empty to begin with since hasNext() is true.
            // But Iterator specifies an exception for next() in case
            // hasNext() is false.

            if (!nodeStack.isEmpty()) {
                nextNode = nodeStack.pop();
                // nextNode != null since stack was not empty before pop

                TernaryNode<T> parent = null;
                if (!nodeStack.isEmpty()) {
                    parent = nodeStack.peek();
                    if (nextNode == parent.getLeftChild()) {
                        currentNode = parent.getMiddleChild();
                    } else if(nextNode == parent.getMiddleChild()) {
                    	 currentNode = parent.getRightChild();
                    }else{
                    	currentNode = null;
                    }
                } else {
                    currentNode = null;
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }


        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class LevelOrderIterator implements Iterator<T> {
        private QueueInterface<TernaryNode<T>> nodeQueue;

        public LevelOrderIterator() {
            nodeQueue = new LinkedQueue<>();
            if (root != null) {
                nodeQueue.enqueue(root);
            }
        }

        public boolean hasNext() {
            return !nodeQueue.isEmpty();
        }

        public T next() {
            TernaryNode<T> nextNode;

            if (hasNext()) {
                nextNode = nodeQueue.dequeue();
                TernaryNode<T> leftChild = nextNode.getLeftChild();
                TernaryNode<T> middleChild = nextNode.getMiddleChild();
                TernaryNode<T> rightChild = nextNode.getRightChild();

                // Add to queue in order of recursive calls
                if (leftChild != null) {
                    nodeQueue.enqueue(leftChild);
                }
                if (middleChild != null) {
                    nodeQueue.enqueue(middleChild);
                }

                if (rightChild != null) {
                    nodeQueue.enqueue(rightChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }



}
