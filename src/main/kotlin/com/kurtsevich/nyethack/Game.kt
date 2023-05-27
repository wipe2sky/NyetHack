package com.kurtsevich.nyethack

fun main(args: Array<String>) {

    Game.play()
}


object Game {

    private val player = Player("Madrigal")
    private val currentRoom: Room = TownSquare()

    init {
        println("Welcome, adventurer.")
        player.castFireball()
    }

    fun play() {
        while (true) {
            println(currentRoom.description())
            println(currentRoom.load())

            printPlayerStatus(player)

            print("> Enter you command: ")
            println(GameInput(readlnOrNull()).processCommand())
        }
    }

    private fun printPlayerStatus(player: Player) {
        println("(Aura: ${player.auraColor()}) (Blessed: ${if (player.isBlessed) "YES" else "NO"})")
        println("${player.name} ${player.formatHealthStatus()}")
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) { "" }

        private fun commandNotFound() = "I'm not quite sure what you're trying to do!\n"

        fun processCommand() = when(command.lowercase()){
            else -> commandNotFound()
        }
    }
}



