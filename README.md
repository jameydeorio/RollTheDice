# Roll The Dice

Allows you to see your chances of beating a DC with any combination of dice and modifiers.

Inspired by the % readout in the Pathfinder ACG app, and sorely missing it while playing the real card game.

## How to use

Tap a dice to add it to the pool.

Long press a dice to remove one from the pool.

Reset will clear all the dice but keep the DC.

## Bugs

Sample spaces in probability get really huge really fast. So adding too many dice, especially larger
ones, will crash the app. Need to figure out how to turn that into a generator/iterator.

The current design is a BUG. Help me.
