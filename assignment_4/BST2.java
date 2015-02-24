// Binary Search Tree with Duplicates: Duptree extends Tree extends Abstree

//************************  Tree ******************************

class notfound extends Exception {
	public notfound(int n) {
		super(n + " was not found in the Abstree and could not be removed.");
	}
}

abstract class Abstree {
	protected int value;
	protected Abstree left;
	protected Abstree right;
	
	public Abstree(int n) { 
		value = n;
		left = null;
		right = null; 
	} 
	
	public void insert(int n) {
		if(value == n) {
			count_duplicates();
		}
		else if(value < n) {
			if (right == null) {
				right = add_node(n);
			}
         	else {
         		right.insert(n);
         	}
		}
		else if(left == null) {
			left = add_node(n);
		}
		else {
			left.insert(n);
		}
 	} 
	
	public void remove(int n) throws notfound {
		if(n == value) {
			if((left==null) && (right!=null)) {
				value = right.value;
				modify_count(right.get_count());
				left = right.left;
				right = right.right;
			}
			else if((left!=null) && (right==null)) {
				value = left.value;
				modify_count(left.get_count());
				right = left.right;
				left = left.left;
			}
			else if((left!=null) && (right!=null)) {
				Abstree a = right;
				if(a.left == null) {
					value = a.value;
					modify_count(a.get_count());
					right = a.right;
				}
				else {
					while(a.left!=null) {
						if(a.left.left==null) {			
							value = a.left.value;
							modify_count(a.left.get_count());
							if(a.left.right==null) {
								a.left = null;
								break;
							}
							else {
								a.left.remove(a.left.value);
								break;
							}
						}
						a = a.left;
					}
				}		
			}
		}
		else if((n<value) && (left!=null)) {
			left.remove(n);
		}
		else if((n>value) && (right!=null)) {
			right.remove(n);
		}
		else {
			throw new notfound(n);
		}
	}
	
	public int min() { 
		if(left != null) 
			return left.min();
		else 
			return value;
 	}

	public int max() { 
		if(right != null) 
			return right.max();
		else 
			return value;
 	}

	protected abstract void count_duplicates();
	
	protected abstract void modify_count(int n);
	
	protected abstract int get_count();
	
	protected abstract Abstree add_node(int n);
}

//*************************  Tree   ******************************

class Tree extends Abstree {
	
	public Tree(int n) {
		super(n);
	}
 
	protected Abstree add_node(int n) {
		return new Tree(n);
	}
	
	protected void modify_count(int n) {
		
	}
	
	protected int get_count() {
		return 0; //return value does not matter for tree
	}

	protected void count_duplicates() {
		
	}
}

//************************  Duptree ******************************

class Duptree extends Tree {
	protected int count;
	
	public Duptree(int n) {
		super(n);
		count = 1;
	}
	
	protected Abstree add_node(int n) {
		return new Duptree(n);
	}
	
	protected void modify_count(int n) {
		count = n;
	}
	
	protected int get_count() {
		return count;
	}
	
	protected void count_duplicates() {
		count++;
	}
}


//***********************  Client Program *************************

public class BST2 { 
	public static void main (String args[]) throws java.io.IOException {
		try {
			int i;
			Abstree tr;
			
			tr = new Tree(100);
			tr.insert(50);
			tr.insert(150);
			tr.insert(75);
			tr.insert(175);
			tr.insert(25);   
			tr.insert(125);

//**********************************************************************
	       	   
			tr = new Duptree(100);
			tr.insert(50);
			tr.insert(150);
			tr.insert(75);
			tr.insert(80);
			tr.insert(85);
			tr.insert(85);
			tr.insert(85);
			tr.insert(175);
			tr.insert(25);
			tr.insert(125);
			tr.insert(120);
			tr.insert(115);
			tr.insert(115);
			
			//Test tree for removal
			tr = new Duptree(100);
			tr.insert(50);
			tr.insert(150);
			tr.insert(75);
			tr.insert(80);
			tr.insert(85);
			tr.insert(85);
			tr.insert(85);
			tr.insert(175);
			tr.insert(25);
			tr.insert(125);
			tr.insert(120);
			tr.insert(115);
			tr.insert(115);
			tr.remove(115);
			tr.remove(100);
			tr.remove(80);
		}
		catch(notfound nf) {
			nf.printStackTrace();
		}
	}
}