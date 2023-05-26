import kotlin.math.roundToInt

private const val TAVERN_NAME = "Taernyl's Folly"

var playerGold = 10
var playerSilver = 10

fun main(args: Array<String>) {
    placeOrder("shandy,Dragon's Breath,5.91")
}

private fun placeOrder(menuData: String) {
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0, indexOfApostrophe)
    println("Madrigal speaks with $tavernMaster about their order.")

    val (type, name, price) = menuData.split(",")

    val isEnoughMoney = performPurchase(price.toDouble())

    if (isEnoughMoney) {
        println("Madrigal buys a $name ($type) for $price")
        val phrase = if (name == "Dragon's Breath") {
            "Madrigal exclaims: ${toDragonSpeak("Ah, delicious $name!")}"
        } else {
            "Madrigal says: Thanks for the $name"
        }
        println(phrase)
    } else {
        println("You don't have enough money")
    }


}

fun performPurchase(price: Double): Boolean {
    displayBalance()
    val totalPurse = playerGold + playerSilver / 100.0
    println("Purchasing item for $price")

    val remainingBalance = totalPurse - price


    if (remainingBalance >= 0) {
        println("Remaining balance: ${"%.2f".format(remainingBalance)}")
        playerGold = remainingBalance.toInt()
        playerSilver = (remainingBalance % 1 * 100).roundToInt()
        return true
    }
    displayBalance()
    return false
}

fun displayBalance() {
    println("Player's purse balance: Gold: $playerGold , Silver: $playerSilver")
}

private fun toDragonSpeak(phrase: String) =
    phrase.replace(Regex("[aeiouAEIOU]")) {
        when (it.value) {
            "a" -> "4"
            "A" -> "4"
            "e" -> "3"
            "E" -> "3"
            "i" -> "1"
            "I" -> "1"
            "o" -> "0"
            "O" -> "0"
            "u" -> "|_|"
            "U" -> "|_|"
            else -> it.value
        }
    }