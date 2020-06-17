import kotlin.random.Random

val colors = listOf('R', 'G', 'B', 'Y', 'W')

const val BOARD_LENGTH = 4

fun main() {
    val differentLetters = false
    print(
        "Available colors: Red, Green, Blue, Yellow, White." +
                "\nPlease use capitalised initials of each color."
    )
    playCB(differentLetters)
}

fun playCB(differentLetters: Boolean, secret: String = generateSecret(differentLetters)) {
    println(generateSecret(true))
    var evaluation: Analyse

    do {
        print("\nYour guess: ")
        var guess = readLine()!!
        while (hasErrorsInInput(guess)) {
            println(
                "Incorrect input: $guess. " +
                        "It should consist of $BOARD_LENGTH characters from this list $colors. " +
                        "\nPlease try again." + "\nYour guess: "
            )
            guess = readLine()!!
        }
        evaluation = analyseGuess(secret, guess)
        if (evaluation.isComplete()) {
            println("The guess is correct!")
        } else {
            println(
                "Right positions: ${evaluation.rightLocation}; " +
                        "wrong positions: ${evaluation.wrongLocation}."
            )
        }
    } while (!evaluation.isComplete())
}

fun Analyse.isComplete(): Boolean = rightLocation == BOARD_LENGTH

fun hasErrorsInInput(guess: String): Boolean {
    val possibleLetters = colors.toSet()
    return guess.length != BOARD_LENGTH || guess.any { it !in possibleLetters }
}

fun generateSecret(differentLetters: Boolean): String {
    val chars = colors.toMutableList()
    return buildString {
        for (i in 1..BOARD_LENGTH) {
            val letter = chars[Random.nextInt(chars.size)]
            append(letter)
            if (differentLetters) {
                chars.remove(letter)
            }
        }
    }
}