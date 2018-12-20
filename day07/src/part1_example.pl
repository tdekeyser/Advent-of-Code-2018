/*
 * Solution to the test example of Advent of Code 2018, day 7.
 *
 * Receive the result by querying
 * ?- solveProblem(S).
 * S = [c, a, b, d, f, e]
*/
:- use_module(library(clpfd)).

solveProblem(Solution) :-
    Steps = [A, B, C, D, E, F],
    Steps ins 0..5,
    all_different(Steps),
    C #< A,
    C #< F,
    A #< B,
    A #< D,
    B #< E,
    D #< E,
    F #< E,
    labeling([min(A), min(B), min(C), min(D), min(E), min(F)], Steps),
    mapToLetter(Steps, [a, b, c, d, e, f], 6, Solution),
    !.

mapToLetter([], [], Len, M) :-
    length(M, Len).
mapToLetter([S|TStep], [L|TLetter], Len, M) :-
    nth0(S, M, L),
    mapToLetter(TStep, TLetter, Len, M).
