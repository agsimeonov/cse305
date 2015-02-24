open Math;

datatype root = imaginary | 
		one of real | 
		two of real*real;

fun quad(a,b,c)=   let val disc = b*b - 4.0*a*c;
	               val den  = 2.0*a;
		       fun abs(x) = if x < 0.0 then ~x else x;
		       fun realeq(x,y) = abs(x-y) < 0.00001;
                    in
			if realeq(disc, 0.0) then one(~b/den) else
			if disc > 0.0 then 
				let val  sq   = sqrt(disc)
				in  two((sq-b)/den, (~sq-b)/den) 
				end else
			imaginary 
		    end;

