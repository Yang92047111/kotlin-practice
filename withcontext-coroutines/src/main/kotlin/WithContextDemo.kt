import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Main starts on ${Thread.currentThread().name}")

    val result = withContext(Dispatchers.IO) {
        println("Running in IO on ${Thread.currentThread().name}")
        delay(1000)
        "Result from IO context"
    }

    println("Back to main: $result on ${Thread.currentThread().name}")
}
