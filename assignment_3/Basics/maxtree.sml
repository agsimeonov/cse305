datatype 'a tree = leaf of 'a 
                 | node of 'a tree * 'a tree;

val leaf1 = leaf(10);
val leaf2 = leaf(20);
val leaf3 = leaf(30);
val leaf4 = leaf(40);

val node3 = node(leaf3,leaf4);
val node2 = node(leaf1,leaf2);
val node1 = node(node2,node3);

fun maxtree (leaf(x:int)) = x
  | maxtree (node(t1,t2)) = let val m1 = maxtree(t1);
		                val m2 = maxtree(t2)
			    in
				if m1>m2 then m1 else m2
			    end;
		
