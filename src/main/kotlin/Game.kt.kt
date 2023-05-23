fun main(args: Array<String>) {
    val name = "Madrial"
    var healthPoints = 89
    val isBlessed = true
    val isImmortal = false

    //Aura
    val auraVisible = isBlessed && healthPoints > 50 || isImmortal
    val auraColor = if (auraVisible) "GREEN" else "NONE"
    println(auraColor)

    val healthStatus = when (healthPoints) {
        100 -> {
            "is in excellent condition!"
        }

        in 90..99 -> {
            "has a few scratches."
        }

        in 75..89 -> if (isBlessed) {
            "has some minor wounds but is healing quite quickly!"
        } else {
            "has some minor wounds."
        }

        in 15..74 -> {
            "looks pretty hurt."
        }

        else -> {
            "is in awful condition!"
        }
    }

    //Players status
    println("$name $healthStatus")

    val race = "gnome"
    val faction = when (race) {
        "dwarf" -> "Keepers of the Mines"
        "gnome" -> "Keepers of the Mines"
        "orc" -> "Free People of the Rolling Hills"
        "human" -> "Free People of the Rolling Hills"
        else -> "I don't know who I am!"
    }
}