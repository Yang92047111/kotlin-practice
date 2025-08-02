# Kotlin Coroutines with withContext

This module demonstrates the use of `withContext` in Kotlin Coroutines for context switching between different dispatchers.

## Concepts Demonstrated

- **Context Switching**: Moving coroutine execution between different thread pools
- **Dispatchers**: Understanding `Dispatchers.IO`, `Dispatchers.Default`, and `Dispatchers.Main`
- **Structured Concurrency**: How `withContext` maintains structured concurrency principles
- **Thread Management**: Observing which threads execute different parts of coroutine code

## Code Example

The main demo shows:
```kotlin
fun main() = runBlocking {
    println("Main starts on ${Thread.currentThread().name}")

    val result = withContext(Dispatchers.IO) {
        println("Running in IO on ${Thread.currentThread().name}")
        delay(1000)
        "Result from IO context"
    }

    println("Back to main: $result on ${Thread.currentThread().name}")
}
```

## Key Learning Points

1. **Context Switching**: `withContext` allows switching to different dispatchers without breaking structured concurrency
2. **Thread Pools**: Different dispatchers use different thread pools optimized for different workloads
3. **Suspending Functions**: `withContext` is a suspending function that returns the result of its block
4. **Performance**: Using appropriate dispatchers (IO for I/O operations, Default for CPU-intensive work) improves performance

## Running the Demo

```bash
mvn exec:java -Dexec.mainClass=WithContextDemoKt
```

## Expected Output

You should see output showing the coroutine executing on different threads:
- Main thread initially
- IO dispatcher thread pool for the withContext block
- Back to main thread after withContext completes

This demonstrates how Kotlin Coroutines efficiently manage thread switching while maintaining code readability.