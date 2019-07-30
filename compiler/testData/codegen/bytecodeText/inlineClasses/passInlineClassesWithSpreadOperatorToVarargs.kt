// This test used to contain an additional check for 2 occurrences of CHECKCAST [LUint
// However, the first of these does not exist in the IR backend, which allocates
// an array of the correct type instead of using Arrays.copyOf

// !LANGUAGE: +InlineClasses

// FILE: utils.kt

inline class UInt(val u: Int)

// FILE: test.kt

fun <T> takeVarargs(vararg e: T) {}

fun test(u1: UInt, u2: UInt, us: Array<UInt>) {
    takeVarargs(*us) // copy + checkcast
    takeVarargs(u1, u2, *us) // 2 box + ...
}

// @TestKt.class:
// 2 INVOKESTATIC UInt\.box
// 0 INVOKEVIRTUAL UInt.unbox

// 0 CHECKCAST \[Ljava/lang/Integer

// 0 intValue
// 0 valueOf