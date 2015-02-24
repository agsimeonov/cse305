open Math;

datatype point = cart of real*real
	       | polar of real*real;

fun xcoord(cart(x,y)) = x
  | xcoord(polar(r,t)) = r*cos(t);

fun ycoord(cart(x,y)) = y
  | ycoord(polar(r,t)) = r*sin(t);

fun dist(p1,p2) = let val delta1 = xcoord(p2) - xcoord(p1);
		      val delta2 = ycoord(p2) - ycoord(p1);
		   in sqrt(delta1*delta1 + delta2*delta2)
		  end;
