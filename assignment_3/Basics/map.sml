	(* Illustration of the map higher-order function *)


fun square1 ([])           = []
  | square1 ((h:int) :: t) = (h*h) :: square1(t);


fun halve1 ([])            = []
  | halve1 ((h:real) :: t) = (h/2.0) :: halve1(t);


fun distr1 (e, [])         = []
  | distr1 (e, h::t)       = (e,h) :: distr1(e, t);



(*_____________________________map(f)_________________________________*)



fun map (f) = let fun g ([])   = []
		    | g (h::t) = f(h) :: g(t)
	       in g
	      end;


(*____________________________________________________________________*)


fun square2(l)  =  let fun isq(x):int = x*x 
		   in  map(isq) (l)
		   end;


fun halve2(l)   =  let fun rhav(x):real = x/2.0 
		   in  map(rhav) (l)
		   end; 



fun distr2(e, l) = let fun pair x y = (x,y)
		   in  map(pair e) (l)
		   end;
