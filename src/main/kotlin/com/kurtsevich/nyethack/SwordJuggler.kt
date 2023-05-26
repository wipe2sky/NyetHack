package com.kurtsevich.nyethack

fun main(args: Array<String>) {
    var swordsJuggling: Int? = null
    val isJugglingProficient = (1..3).shuffled().last() == 3
    if (isJugglingProficient) {
        swordsJuggling = 2
    }
    try {
        proficiencyCheck(swordsJuggling)
        swordsJuggling = swordsJuggling!!.plus(1)
    } catch (e: Exception) {
        println(e)
    }
    println("You jungle $swordsJuggling swords")
}

private fun proficiencyCheck(swordsJuggling: Int?) {
    checkNotNull(swordsJuggling) { "Player cannot juggle swords" }
}