fun f(a::b) = a;


fun mystery([ ]) = [ ] 
         		 | mystery([]::t) = f(t)
        		   | mystery((h1::t1)::t2) = (h1::t1)  @  mystery(t2);
