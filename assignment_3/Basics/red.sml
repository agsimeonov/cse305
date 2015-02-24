	(* Illustration of the reduce higher-order function *)


fun sum1 ([])   = 0
  | sum1 (h::t) = h + sum1 (t);

fun prod1 ([])   = 1
  | prod1 (h::t) = h * prod1 (t);

fun any1 ([])   = false
  | any1 (h::t) = h orelse any1 (t);

fun all1 ([])   = true
  | all1 (h::t) = h andalso all1 (t);

fun append1 ([], l2)     = l2
  | append1 (h1::t1, l2) = h1::append1(t1, l2);



(*__________________________reduce(f,b)_________________________________*)


fun reduce(f, b)  = let fun g ([])   = b
			  | g (h::t) = f(h, g(t))
		     in g
		    end;

(*______________________________________________________________________*)



fun sum2(l)  = let fun iplus(x, y): int = x+y 
	       in  reduce(iplus, 0) (l)
	       end;

fun prod2(l) = let fun imult(x, y): int = x*y;
	       in  reduce(imult, 1) (l) 
	       end;

fun any2(l)  = let fun or2(x,y) = x orelse y
	       in  reduce(or2, false) (l)
	       end;

fun all2(l)  = let fun and2(x,y) = x andalso y;
	       in reduce(and2, true) (l)
	       end;

fun append2(l1, l2) = let fun cons(h,t) = h::t
		      in  reduce(cons, l2) (l1)
		      end;



