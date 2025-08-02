import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import kotlin.test.assertTrue

class WithContextDemoTest {

    @Test
    fun `test withContext switches to IO dispatcher`() = runTest {
        var ioThreadName: String? = null
        var mainThreadName: String? = null
        
        mainThreadName = Thread.currentThread().name
        
        val result = withContext(Dispatchers.IO) {
            ioThreadName = Thread.currentThread().name
            delay(100) // Simulate IO work
            "Result from IO context"
        }
        
        assertEquals("Result from IO context", result)
        assertNotNull(ioThreadName)
        assertNotNull(mainThreadName)
        // IO dispatcher should use different thread
        assertNotEquals(mainThreadName, ioThreadName)
    }

    @Test
    fun `test withContext switches to Default dispatcher`() = runTest {
        var defaultThreadName: String? = null
        var mainThreadName: String? = null
        
        mainThreadName = Thread.currentThread().name
        
        val result = withContext(Dispatchers.Default) {
            defaultThreadName = Thread.currentThread().name
            // Simulate CPU-intensive work
            var sum = 0
            repeat(1000) { sum += it }
            "Computed sum: $sum"
        }
        
        assertTrue(result.startsWith("Computed sum:"))
        assertNotNull(defaultThreadName)
        assertNotNull(mainThreadName)
    }

    @Test
    fun `test multiple withContext calls maintain structured concurrency`() = runTest {
        val results = mutableListOf<String>()
        
        val job1 = async {
            withContext(Dispatchers.IO) {
                delay(50)
                "IO Result 1"
            }
        }
        
        val job2 = async {
            withContext(Dispatchers.Default) {
                delay(30)
                "Default Result 2"
            }
        }
        
        results.add(job1.await())
        results.add(job2.await())
        
        assertEquals(2, results.size)
        assertTrue(results.contains("IO Result 1"))
        assertTrue(results.contains("Default Result 2"))
    }

    @Test
    fun `test withContext exception handling`() = runTest {
        assertThrows(RuntimeException::class.java) {
            runBlocking {
                withContext(Dispatchers.IO) {
                    delay(10)
                    throw RuntimeException("Test exception")
                }
            }
        }
    }

    @Test
    fun `test withContext returns correct value type`() = runTest {
        val stringResult = withContext(Dispatchers.IO) {
            delay(10)
            "String result"
        }
        
        val intResult = withContext(Dispatchers.Default) {
            42
        }
        
        val listResult = withContext(Dispatchers.IO) {
            delay(10)
            listOf(1, 2, 3)
        }
        
        assertEquals("String result", stringResult)
        assertEquals(42, intResult)
        assertEquals(listOf(1, 2, 3), listResult)
    }
}