% Illustration of case analysis over the constructors.
%
% Coins represented by six constructors: 
%
%		dollar(X), halfdollar(X), quarter(X), 
%		dime(X), nickel(X), penny(X)
%
% | ?- netvalue([dime(6), quarter(5), nickel(3)], V).
%		V = 200  


value(penny(X), X).
value(nickel(X), V) :- V is 5*X.
value(dime(X), V) :- V is 10*X.
value(quarter(X), V) :- V is 25*X.
value(halfdollar(X), V) :- V is 50*X.
value(dollar(X), V) :- V is 100*X.

netvalue([], 0).
netvalue([C|T], V) :-
	value(C,V1),
	netvalue(T,V2),
	V is V1+V2.
