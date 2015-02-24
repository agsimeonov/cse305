(* Examples of Programs for Type Inference *)

fun f(x,y) = (x*x, x/y);

fun ident(x) = x;

fun loop(x) = loop(x);

fun bigloop(x) = bigloop(bigloop(x));

fun mystery([]) = []
  | mystery(h::t) = h @ mystery(t);

fun comp(f,g) = fn x => f(g(x));	(* fn x => E is lambda x.E *)

fun map(f) = let fun g([]) = []
		   | g(h::t) = f(h) :: g(t)
	      in g
	     end;

fun chain(f1,f2) = comp(map(f1), map(f2));


(*  Inferred Types Shown Below:

	val f = fn : real * real -> real * real
	val ident = fn : 'a -> 'a
	val loop = fn : 'a -> 'b
	val bigloop = fn : 'a -> 'a
	val mystery = fn : 'a list list -> 'a list
	val comp = fn : ('a -> 'b) * ('c -> 'a) -> 'c -> 'b
	val map = fn : ('a -> 'b) -> 'a list -> 'b list
	val chain = fn : ('a -> 'b) * ('c -> 'a) -> 'c list -> 'b list
*)
