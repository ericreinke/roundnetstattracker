package com.example.roundnetstattracker;

public class Rally {

    private int server;
    private int receiver;
    private String theRally;
    /*  0: player 0
     *   1: player 1
     *   2: player 2
     *   3: player 3
     *   S: "first serve" followed by server
     *   s: "second serve" followed by server
     *   F: first service fault (pocket, rim, height, etc...)
     *   f: second service fault (pocket, rim, height, etc...)
     *   N: ball bounced off the net successfully NOT A SERVE (change of possession)
     *   n: ball successfully SERVED off the net (change of possession)
     *   P: putaway.  Spiker put the ball away. Point award, no clear Error made.
     *   E: SOMEONE MADE A CLEAR ERROR (different from putaway) Followed by player who made Error
     *
     *   Example 1: Player 0 serves, hits rim, Player 0 serves again, Player 2 gets a touch, but aced
     *       "S0F0s0n2P"
     *   Example 2: Player 3 double faults:
     *       "S2Fs2f"
     *   Example 3: Player 1 serves to player 2, teams have a sick rally player 0 whiffs his spike
     *       "S1n232N101N323N01N01E0"  //note player 1 had last touch, but player 0 had the error
     *   Example 4: typical 1-2-3 put away:
     *       "S0n232NP
     */
}
