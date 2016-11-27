package cs445.a5;

public class Test {
	public static void main(String [] args){
		String root = "root";
		String l = "left";
		String m = "mid";
		String r = "right";
		//TernaryTree root = new TernaryTree(root);
		TernaryTree left = new TernaryTree(l);
		TernaryTree middle = new TernaryTree(m);
		TernaryTree right = new TernaryTree(r);
		TernaryTree fullTree =  new TernaryTree(root,left,middle,right);
		//PostorderIterator i = new TernaryTree();
		BinaryTree left2 = new BinaryTree(l);
		
		BinaryTree right2 = new BinaryTree(r);
		BinaryTree fullTree2 =  new BinaryTree(root,left2,right2);
		//fullTree.hasNext();
		System.out.println("Number of nodes for root: " + fullTree.getNumberOfNodes());
		System.out.println("Data is: " + fullTree.getRootNode().getMiddleChild().getData());
		System.out.println("Number of nodes for root Binary: " + fullTree2.getNumberOfNodes());
		System.out.println("Height for root: " + fullTree.getHeight());
		System.out.println("Root Node empty?: " + fullTree.isEmpty());
		
		//System.out.println("Number of nodes for left: " + left.getNumberOfNodes());
	//	System.out.println("Height for left: " + left.getHeight());
		//System.out.println("Left Node empty?: " + left.isEmpty());
		
		
	}
}
