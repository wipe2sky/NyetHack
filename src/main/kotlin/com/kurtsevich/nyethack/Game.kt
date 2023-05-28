package com.kurtsevich.nyethack

import kotlin.system.exitProcess

fun main(args: Array<String>) {
    Game.play()
}


object Game {

    private val player = Player("Madrigal")
    private var currentRoom: Room = TownSquare()
    private val worldMap = listOf(
        listOf(currentRoom, Room("Tavern"), Room("Back Room")),
        listOf(Room("Long Corridor"), Room("Generic Room"))
    )

    init {
        println("Welcome, adventurer.\n")
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

    private fun move(directionInput: String) =
        try {
            val direction = Direction.valueOf(directionInput.uppercase())
            val newPosition = direction.updateCoordinate(player.currentPosition)

            if (!newPosition.isInBounds) {
                throw IllegalStateException("$direction is out of bounds")
            }

            val newRoom = worldMap[newPosition.y][newPosition.x]
            player.currentPosition = newPosition
            currentRoom = newRoom

            "OK, you move $direction to the ${newRoom.name}.\n${newRoom.load()}"
        } catch (e: Exception) {
            "Invalid direction: $directionInput"
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

        fun processCommand(): String = when (command.lowercase()) {
            "move" -> move(argument)
            "map" -> writeMap()
            "ring" -> ring(argument)
            "fight" -> fight()
            "quit" -> quitGame()
            else -> commandNotFound()
        }
    }

    private fun ring(count: String): String {
        var bell = ""
        try {
            if (currentRoom is TownSquare && count.isNotBlank()) {
                bell = "${(currentRoom as TownSquare).ringBell()}\n"
                    .repeat(count.toInt())
            } else {
                bell = "Impossible to find a bell in this place!\n"
            }
        } catch (e: NumberFormatException) {
            bell = "You can't strike the bell \"$count\" times! Try again."
        }
        return bell
    }

    private fun writeMap(): String {
        var map = ""
        for (y in worldMap.indices) {
            for (x in worldMap[y].indices) {
                map = if (Coordinate(x, y) == player.currentPosition) {
                    map.plus("X")
                } else {
                    map.plus("O")
                }
            }
            map = map.plus("\n")
        }
        return map
    }

    private fun fight() = currentRoom.monster?.let {
        println()
        while (player.healthPoints > 0 && it.healthPoints > 0) {
            slay(it)
            Thread.sleep(1000)
        }
        "Combat complete.\n"
    } ?: "There's nothing here to fight."

    private fun slay(monster: Monster) {
        println("${monster.name} did ${monster.attack(player)} damage! ")
        println("${player.name} did ${player.attack(monster)} damage! ")

        if (player.healthPoints <= 0) {
            println(">>>> You have been defeated! Thanks for playing. <<<<")
            exitProcess(0)
        }

        if (monster.healthPoints <= 0) {
            println(">>>> ${monster.name} has been defeated! <<<<")
            currentRoom.monster = null
        }
    }

    private fun quitGame(): Nothing {
        println("Game over! Have a nice day.")
        exitProcess(0)
    }
}



