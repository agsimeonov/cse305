(* The function partition takes a "pivot" value and a list, L, and    *)
(* returns a pair of lists: one list consisting only of those numbers *)
(* in L that are less than the pivot, and the other list containing   *)
(* the remaining numbers of L.                                        *)


fun partition(mid, []) = ([], [])
  | partition(mid, h::t) =
                       let val (left, right) = partition(mid, t)
                       in  if h < mid then (h::left, right)
                                          else (left, h::right)
                       end;

(* Suppose that L is a list of integers to be sorted in ascending order. *)
(* The function qsort returns a new list consisting of the same integers *)
(* as in L but in ascending order.  The function qsort invokes a partition *)
(* giving it the first number, h, in its input list and the rest of the input *)
(* list, t.  The two partitions returned are then recursively sorted and the *)
(* resulting lists appended with [h] in between to obtain the result. *)


fun qsort([]) = []
  | qsort(h :: t) = let val (left, right) = partition(h, t)
                     in qsort(left) @ [h] @ qsort(right)
                    end;
