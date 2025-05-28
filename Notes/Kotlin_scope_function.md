In Kotlin, functions like `run`, `let`, `with`, `also`, and `apply` are **scope functions** — they let you execute a block of code within the context of an object. These are all **higher-order functions**, meaning they take a lambda as a parameter.

Here's a summary of each with examples and best use cases:

---

## 🔹 `let`

**Context object**: `it`
**Return value**: Lambda result
**Use case**: Transform or execute code on non-null objects (commonly used with `?.let { ... }`)

```kotlin
val name: String? = "Kotlin"
name?.let {
    println("Name is $it")
}
```

---

## 🔹 `run`

**Context object**: `this`
**Return value**: Lambda result
**Use case**: Group statements, especially when you want to return the last expression.

```kotlin
val result = "Kotlin".run {
    println("The string is $this")
    length
}
// result = 6
```

---

## 🔹 `with`

**Context object**: `this`
**Return value**: Lambda result
**Use case**: Call multiple methods on the same object, more Java-like syntax.

```kotlin
val builder = StringBuilder()
val result = with(builder) {
    append("Hello, ")
    append("World!")
    toString()
}
```

---

## 🔹 `apply`

**Context object**: `this`
**Return value**: The object itself
**Use case**: Object configuration / initialization

```kotlin
val person = Person().apply {
    name = "Alice"
    age = 30
}
```

---

## 🔹 `also`

**Context object**: `it`
**Return value**: The object itself
**Use case**: Side effects (logging, validation, debugging)

```kotlin
val numbers = mutableListOf(1, 2, 3)
numbers
    .also { println("Before adding: $it") }
    .add(4)
```

---

## 🔸 Comparison Table

| Function | Object Ref | Return Value  | Use Case                     |
| -------- | ---------- | ------------- | ---------------------------- |
| `let`    | `it`       | Lambda result | Null checks, transformations |
| `run`    | `this`     | Lambda result | Expression blocks            |
| `with`   | `this`     | Lambda result | Multi-method calls           |
| `apply`  | `this`     | Object        | Object configuration         |
| `also`   | `it`       | Object        | Side effects, logging        |

---

## Bonus: `takeIf` and `takeUnless`

### `takeIf`

Returns the object if it satisfies the predicate.

```kotlin
val number = 42
val even = number.takeIf { it % 2 == 0 } // 42
```

### `takeUnless`

Returns the object if it does **not** satisfy the predicate.

```kotlin
val odd = number.takeUnless { it % 2 == 0 } // null
```

---

Would you like a cheat sheet graphic or real-life use case examples in your code?
