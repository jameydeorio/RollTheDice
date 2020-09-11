package com.jameyiguess.rollthedice.util

import kotlin.math.roundToInt

class ProbabilityMachine {
    var dice = arrayListOf<Int>()
    var modifier = 0

    fun getSampleSpace(): ArrayList<ArrayList<Int>> {
        val pools = arrayListOf<IntArray>()
        for (die in dice) {
            val pool = IntArray(die) { it + 1 }
            pools.add(pool)
        }
        var result = arrayListOf<ArrayList<Int>>()
        result.add(arrayListOf())
        for (pool in pools) {
            val newResult = arrayListOf<ArrayList<Int>>()
            for (item in result) {
                for (p in pool) {
                    val newSingleItem = ArrayList<Int>()
                    newSingleItem.addAll(item)
                    newSingleItem.add(p)
                    newResult.add(newSingleItem)
                }
            }
            result = newResult.filter { it.isNotEmpty() } as ArrayList<ArrayList<Int>>
        }
        return result
    }

    private fun getNumFavorableOutcomes(target: Int): Int {
        var favorableOutcomes = 0
        for (outcome in getSampleSpace()) {
            if (outcome.sum() + modifier >= target)
                favorableOutcomes++
        }
        return favorableOutcomes
    }

    fun getProbabilityToBeatDc(dc: Int): Int {
        val numPossibleOutcomes = dice.reduce { acc, i -> acc * i }
        val numFavorableOutcomes = getNumFavorableOutcomes(dc)
        val probability = numFavorableOutcomes / numPossibleOutcomes.toDouble()
        return (probability * 100).roundToInt()
    }
}