package com.kurtsevich.nyethack

fun main(args: Array<String>) {
    val player = Player()

    printPlayerStatus(player)

    player.castFireball()
}

private fun printPlayerStatus(player: Player) {
    println("(Aura: ${player.auraColor()}) (Blessed: ${if (player.isBlessed) "YES" else "NO"})")
    println("${player.name} ${player.formatHealthStatus()}")
}



