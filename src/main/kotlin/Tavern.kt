fun main(args: Array<String>) {
    var beverage = readLine()
//    beverage = null

    if (beverage != null) {
        beverage = beverage.replaceFirstChar { it.uppercase() }
    } else {
        println("I can't do that without crashing - beverage was null!")
    }


    val beverageServed: String = beverage ?: "Buttered Ale"
    println(beverageServed)
}
