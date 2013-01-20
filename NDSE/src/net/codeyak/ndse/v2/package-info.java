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
 */
package net.codeyak.ndse.v2;