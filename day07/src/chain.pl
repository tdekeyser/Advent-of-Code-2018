:- use_module(library(clpfd)).

findChain(Vars) :-
    Vars = [A, B, C, D, E, F],
    Vars ins 0..5,
    all_different(Vars),
    C #< A,
    C #< F,
    A #< B,
    A #< D,
    B #< E,
    D #< E,
    F #< E,
    labeling([min(A), min(B), min(C), min(D), min(E), min(F)], Vars),
    !.
