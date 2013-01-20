/**
 * With the v2 version, I decided to adopt a very object oriented approach.
 * This vastly simplifies the algorithms involved, and the objects themselves do lots of the work.
 * It is also very flexible.
 * <p>
 * These are the core objects. Each of these contains detailed descriptions of their functions, and associated objects.
 * <br>OOBoard is made up of a set of OOSquares.
 * <br>OOSquares can have an OOTile placed on them.
 * <br>OOTiles represent game tiles (with a letter and a value)
 * <br>OORack contains a number of OOTiles
 * <br>OOBag contains remaining OOTiles that have not been selected or played
 * 
 * <p>Ultimately the speed of the v2 implementation is disappointing (albeit not unexpected). Many objects are created
 * in order to generate each play. This is what led to the decision to write v3.
 * 
 * <p>Breaking down the problem in this manner is very flexible though. Dropping in a new algorithm to generate potential play
 * patterns in OOBoard to adhere to rules in games like Scrabble Zing (at king.com) would require significant changes to the
 * v3 algorithm.
 * <br>The getPattern method in OOBoard is probably the most complex part of the v2 code. It was non-trivial to abstract to n-dimension,
 * so I haven't done this (instead concentrating) on v3).
 * <br>The overall algorithm is quite simple
 * <ul>
 * <li>generate all possible valid play patterns
 * <li>iterate over all tiles in rack, in all possible orderings
 * <li>place a tile on a given square in a given pattern
 * <li>record if all square in pattern are full and all words formed are valid
 * <li>if pattern is not full, apply early cutoff logic, by determining whether fragments of words formed are valid
 * </ul>
 * 
 * Scrabble Zing consist of 5 rounds, during each round you get 7 tiles (which aren't carried over). You can make any arrangement
 * with these 7 tiles on the board. ie make multiple Scrabble plays in a single round. 
 */
package net.codeyak.ndse.v2;