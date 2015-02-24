//************************  Itree ******************************

interface ITree {
	void insert(int n);
	int min();
	int max();
	void count_duplicates();
	ITree add_node(int n);
}

//************************  Abstree ******************************

class Abstree implements ITree {
 public Abstree(ITree it, int n) { abstree = it; value = n; left = null; right = null; } 
 public void insert(int n) {
      if (value == n) abstree.count_duplicates();
      else if (value < n) 
	 	if (right == null) right = abstree.add_node(n);
         	else right.insert(n);
      else if (left == null) left = abstree.add_node(n);
              else left.insert(n);
 	} 
	
 public int min() { 
 	if (left != null) return left.min();
 	else return value;
 	}

 public int max() { 
 	if (right != null) return right.max();
 	else return value;
 	}

 protected int value;
 protected ITree left;
 protected ITree right;
 ITree abstree;

 public void count_duplicates() {}
 public ITree add_node(int n) {return new Abstree(this, n);}

}

//*************************  Tree   ******************************

class Tree implements ITree {
 public Tree(int n) {tree = new Abstree(this, n);}
 public Tree(ITree tr, int n) {tree = new Abstree(tr, n);}
 public void insert(int n) {tree.insert(n);}
 public int min() {return tree.min();}
 public int max() {return tree.max();}
 public ITree add_node(int n) {return new Tree(n);}
 public void count_duplicates() {tree.count_duplicates();}
 ITree tree;
}

//************************  Duptree ******************************

class Duptree implements ITree {
 public Duptree(int n) {duptree = new Tree(this, n); count = 1;}
 public void insert(int n) {duptree.insert(n);}
 public int min() {return duptree.min();}
 public int max() {return duptree.max();}
 public ITree add_node(int n) {return new Duptree(n);}
 public void count_duplicates() {count++;}
 ITree duptree;
 protected int count;
}


//***********************  Client Program *************************

public class BSTequiv { 
	public static void main (String args[]) throws java.io.IOException {
	   int i;
	   ITree tr;
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
       	   }
}
