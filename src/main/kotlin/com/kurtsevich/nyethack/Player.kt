package com.kurtsevich.nyethack

import java.io.File

class Player(
    _name: String,
    private var healthPoints: Int = 100,
    val isBlessed: Boolean,
    private val isImmortal: Boolean
) {
    var name = _name
        get() = "${field.replaceFirstChar { it.uppercase() }} of $hometown"
        private set(value) {
            field = value.trim()
        }
    private val hometown by lazy { selectHometown() }


    init {
        require(healthPoints > 0) { "healthPoints must be greater than zero." }
        require(name.isNotBlank()) { "player must have a name." }
    }

    constructor(name: String) : this(
        _name = name,
        isBlessed = true,
        isImmortal = false
    )

    fun castFireball(numFireballs: Int = 2) =
        println("A glass of Fireball springs into existence. (x$numFireballs)")

    fun auraColor() =
        if (isBlessed && healthPoints > 50 || isImmortal) "GREEN" else "NONE"

    fun formatHealthStatus() =
        when (healthPoints) {
            100 -> "is in excellent condition!"

            in 90..99 -> "has a few scratches."

            in 75..89 -> if (isBlessed) "has some minor wounds but is healing quite quickly!"
            else "has some minor wounds."

            in 15..74 -> "looks pretty hurt."

            else -> "is in awful condition!"
        }

    private fun selectHometown() = File("data/towns.txt")
        .readText()
        .split("\n")
        .shuffled()
        .first()
}