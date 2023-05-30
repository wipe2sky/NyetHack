package com.kurtsevich.nyethack

import java.io.File
import com.kurtsevich.nyethack.extensions.random as randomizer

private const val TAVERN_NAME = "Taernyl's Folly"
private const val WELCOME_PHRASE = "*** Welcome to $TAVERN_NAME ***"
private const val LINE_LEGHT = WELCOME_PHRASE.length


val patronList = mutableListOf("Eli", "Mordoc", "Sophie")
val lastName = listOf("Ironfoot", "Fernsworth", "Baggins")
val uniquePatrons = mutableSetOf<String>()
val menuList = File("data/tavern-menu-items.txt")
    .readText()
    .split("\n")
val patronGold = mutableMapOf<String, Double>()
private fun String.toDragonSpeak() = this.replace(Regex("[aeiouAEIOU]")) {
    when (it.value) {
        "a", "A" -> "4"
        "e", "E" -> "3"
        "i", "I" -> "1"
        "o", "O" -> "0"
        "u", "U" -> "|_|"
        else -> it.value
    }
}

private fun String.frame(padding: Int): String {
    val greeting = "$this!"
    val formatChar = "*"
    val middle = formatChar.padEnd(padding)
        .plus(greeting)
        .plus(formatChar.padStart(padding))
    val end = (0 until middle.length).joinToString("") { formatChar }
    return "$end\n$middle\n$end"
}

fun main(args: Array<String>) {

    println(WELCOME_PHRASE.frame(5))

    printMenu()

    uniquePatrons.addAll(createPatrons())
    println(uniquePatrons)

    uniquePatrons.forEach { patronGold[it] = 15.0 }

    orders()

    displayPatronBalances()
}

private fun displayPatronBalances() {
    patronGold.forEach { (patron, balance) ->
        println("$patron, balance: ${"%.2f".format(balance)}")
    }
}

private fun orders() {
    var orderCount = 0
    while (orderCount < 10) {
        placeOrder(
            uniquePatrons.randomizer(),
            menuList.randomizer()
        )
        orderCount++
    }
}

private fun createPatrons(): Set<String> = generateSequence {
    val first = patronList.random()
    val last = lastName.random()
    "$first $last"
}
    .distinct()
    .take(9)
    .toSet()


private fun placeOrder(patronName: String, menuData: String) {
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0, indexOfApostrophe)
    println("$patronName speaks with $tavernMaster about their order.")

    val (type, name, price) = menuData.split(",")

    if (isEnoughMoney(patronName, price.toDouble())) {
        println("$patronName buys a $name ($type) for $price")

        performPurchase(price.toDouble(), patronName)

        val phrase = if (name == "Dragon's Breath") {
            "$patronName exclaims: ${"Ah, delicious $name!".toDragonSpeak()}"
        } else {
            "$patronName says: Thanks for the $name"
        }
        println(phrase)
    }
    println()
}

fun isEnoughMoney(patronName: String, price: Double): Boolean {
    if (patronGold.getValue(patronName) - price < 0) {
        println("Screw $patronName and come back when you have enough money!")
        uniquePatrons.remove(patronName)
        patronGold.remove(patronName)
        return false
    }
    return true
}

private fun performPurchase(price: Double, patronName: String) {
    val totalPurse = patronGold.getValue(patronName)
    patronGold[patronName] = totalPurse - price
}

private fun printMenu() {
    val menuType = mutableSetOf<String>()
    menuList.forEach { data ->
        val type = data.split(",")[0]
        menuType.add(type)
    }

    menuType.forEach { type ->
        println("           ~[$type]~")
        menuList.forEach { data ->
            val (typeCurrent, name, price) = data.split(",")
            var dots = ""
            while (name.length + price.length + dots.length <= LINE_LEGHT) {
                dots += "."
            }

            if (typeCurrent == type) println("${
                name.split(" ")
                    .joinToString(" ") { it.replaceFirstChar { it.uppercase() } }
            }$dots$price")
        }
    }
    println()
}