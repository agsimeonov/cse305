datatype coin = penny of int |
		nickel of int |
		dime of int |
		quarter of int |
		halfd of int |
		dollar of int;

fun value(penny(x)) = x
  | value(nickel(x)) = 5*x
  | value(dime(x)) = 10*x
  | value(quarter(x)) = 25*x
  | value(halfd(x)) = 50*x
  | value(dollar(x)) = 100*x;

fun netvalue([]) = 0
  | netvalue(c::t) = value(c) + netvalue(t);
