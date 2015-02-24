datatype piece = king
	       | queen of int
	       | bishop of int
	       | knight of int
	       | rook of int
	       | pawn of int;

fun value(king) = 100
  | value(queen(x)) = 50*x
  | value(bishop(x)) = 25*x
  | value(knight(x)) = 15*x
  | value(rook(x)) = 25*x
  | value(pawn(x)) = 3*x;

fun netvalue([]) = 0
  | netvalue(c::t) = value(c) + netvalue(t);

fun strength(c,u) = netvalue(c) - netvalue(u);

datatype gametree = leaf of string * piece list * piece list
		  | node of string * gametree list;

val leaf1 = leaf("L1", [king,queen(1),bishop(1),pawn(5)], [king,rook(1),pawn(5)]);
val leaf2 = leaf("L2", [king,queen(1),pawn(5)], [king,rook(1),pawn(5)]);
val leaf3 = leaf("L3", [king,queen(1),bishop(1),pawn(5)], [king,pawn(5)]);
val leaf4 = leaf("L4", [king,queen(1),bishop(1),pawn(5)], [king,knight(1),pawn(4)]);
val leaf5 = leaf("L5", [king,bishop(1),pawn(5)], [king,knight(1),pawn(4)]);
val leaf6 = leaf("L6", [king,bishop(1),pawn(5)], [king,pawn(5)]);
val leaf7 = leaf("L7", [king,bishop(1),pawn(5)], [king,knight(1),pawn(5)]);
val leaf8 = leaf("L8", [king,queen(1),bishop(1),pawn(5)], [knight(1),pawn(5)]);

val node4 = node("N4", [leaf1,leaf2]);
val node5 = node("N5", [leaf3,leaf4]);
val node6 = node("N6", [leaf5,leaf6]);
val node7 = node("N7", [leaf7,leaf8]);
val node2 = node("N2", [node5,node6]);
val node3 = node("N3", [node7]);
val node1 = node("N1", [node2,node3,node4]);

fun max(leaf(ls,pl1,pl2)) = strength(pl1,pl2)
  | max(node(ns,gt1::[])) = max(gt1)
  | max(node(ns,gt::gtl)) = let fun parsegtl(a::[]) = max(a)
			          | parsegtl(b::c) = let val p1 = parsegtl([b]);
						     val p2 = parsegtl(c)
						 in
						     if p1>p2 then p1 else p2
						 end;;
			        val m1 = max(gt);
			        val m2 = parsegtl(gtl)
			    in
			        if m1>m2 then m1 else m2
			    end;

fun min(leaf(ls,pl1,pl2)) = strength(pl1,pl2)
  | min(node(ns,gt1::[])) = min(gt1)
  | min(node(ns,gt::gtl)) = let fun parsegtl(a::[]) = min(a)
			          | parsegtl(b::c) = let val p1 = parsegtl([b]);
						     val p2 = parsegtl(c)
						 in
						     if p1<p2 then p1 else p2
						 end;;
			        val m1 = min(gt);
			        val m2 = parsegtl(gtl)
			    in
			        if m1<m2 then m1 else m2
			    end;


fun nextmax(node(ns,gtl)) = let fun select(a::[]) = a
				  | select(b::c) = if max(b)>max(select(c)) then b else select(c)
			    in
			        select(gtl)
			    end;

fun nextmin(node(ns,gtl)) = let fun select(a::[]) = a
				  | select(b::c) = if min(b)<min(select(c)) then b else select(c)
			    in
			        select(gtl)
			    end;

fun minimax2(_,leaf(ls,pl1,pl2)) = [ls]
  | minimax2(true,node(ns,gtl)) = [ns] @ minimax2(false,nextmax(node(ns,gtl)))
  | minimax2(false,node(ns,gtl)) = [ns] @ minimax2(true,nextmin(node(ns,gtl)));

fun minimax(node(ns,gtl)) = minimax2(true,node(ns,gtl));
