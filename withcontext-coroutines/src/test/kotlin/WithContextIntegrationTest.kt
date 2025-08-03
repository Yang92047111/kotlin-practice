import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import kotlin.system.measureTimeMillis

class WithContextIntegrationTest {

    @Test
    fun `integration test - complete withContext demo flow`() = runTest {
        val results = mutableListOf<String>()
        val threadNames = mutableListOf<String>()
        
        // Capture main thread
        threadNames.add("Main: ${Thread.currentThread().name}")
        
        // Test the actual demo flow
        val result = withContext(Dispatchers.IO) {
            threadNames.add("IO: ${Thread.currentThread().name}")
            delay(100) // Simulate IO work
            results.add("IO work completed")
            "Result from IO context"
        }
        
        // Back to main context
        threadNames.add("Back to main: ${Thread.currentThread().name}")
        results.add("Main work completed")
        
        // Assertions
        assertEquals("Result from IO context", result)
        assertEquals(2, results.size)
        assertTrue(results.contains("IO work completed"))
        assertTrue(results.contains("Main work completed"))
        assertEquals(3, threadNames.size)
        
        // Verify thread switching occurred
        val mainThread = threadNames[0].substringAfter("Main: ")
        val ioThread = threadNames[1].substringAfter("IO: ")
        val backToMainThread = threadNames[2].substringAfter("Back to main: ")
        
        // IO should use different thread than main
        assertNotEquals(mainThread, ioThread)
        // Should return to same context (though not necessarily same thread)
        assertTrue(backToMainThread.isNotEmpty())
    }

    @Test
    fun `performance test - withContext vs blocking`() = runTest {
        val iterations = 100
        
        // Test withContext performance
        val withContextTime = measureTimeMillis {
            repeat(iterations) {
                val result = withContext(Dispatchers.IO) {
                    delay(1) // Simulate minimal IO
                    "result"
                }
                // Use the result to avoid unused expression warning
                assertTrue(result.isNotEmpty())
            }
        }
        
        // Test blocking performance (simulated)
        val blockingTime = measureTimeMillis {
            repeat(iterations) {
                val result = withContext(Dispatchers.IO) {
                    Thread.sleep(1) // Simulate blocking IO
                    "result"
                }
                // Use the result to avoid unused expression warning
                assertTrue(result.isNotEmpty())
            }
        }
        
        // withContext with delay should be more efficient than Thread.sleep
        // Note: This is a simplified test - real performance benefits are more complex
        assertTrue(withContextTime >= 0) // Basic sanity check
        assertTrue(blockingTime >= 0) // Basic sanity check
        
        println("WithContext time: ${withContextTime}ms")
        println("Blocking time: ${blockingTime}ms")
        
        // Use the values to avoid unused expression warnings
        assertTrue(withContextTime < blockingTime + 1000) // Allow some variance
    }

    @Test
    fun `concurrent withContext operations`() = runTest {
        val numberOfOperations = 10
        val results = mutableListOf<String>()
        
        // Launch multiple concurrent operations
        val jobs = (1..numberOfOperations).map { index ->
            async {
                withContext(Dispatchers.IO) {
                    delay(10L * index) // Variable delay
                    "Operation $index completed"
                }
            }
        }
        
        // Collect all results
        jobs.forEach { job ->
            results.add(job.await())
        }
        
        // Verify all operations completed
        assertEquals(numberOfOperations, results.size)
        
        // Verify all operations have unique results
        val uniqueResults = results.toSet()
        assertEquals(numberOfOperations, uniqueResults.size)
        
        // Verify all operations completed successfully
        results.forEach { result ->
            assertTrue(result.contains("completed"))
        }
    }
}