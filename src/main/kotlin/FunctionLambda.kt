fun main() {
    // Kotlin 에서 매개변수로 들어오는 값을 가지고 처리를 하여
    // 처리된 결과를 반환하는 함수에 대해 보다 쉽고 편하게 작성할 수 있는
    // 다양한 문법을 제공

    var r1 = testFun1(100, 200)
    println("r1 : $r1")

    val r2 = testFun2(100, 200)
    println("r2 : $r2")

    var r3 = testFun3(100, 200)
    println("r3 : $r3")

    var r4 = lambda1(100, 200)
    println("r4 : $r4")

    var r5 = lambda2(100, 200)
    println("r5 : $r5")

    var r6 = lambda3(100, 200)
    println("r6 : $r6")

    var r7 = testFun4(100, 200)
    println("r7 : $r7")

    var r8 = lambda4(100, 200)
    println("r8 : $r8")
}

// 매개변수로 들어오는 값을 계산하여 반환하는 함수
// 코드 한줄
fun testFun1(a1: Int, a2: Int): Int {
    return a1 + a2
}

// 제일 마지막에 작성한 수식의 결과가 함수의 반환값이 된다.
fun testFun2(a1: Int, a2: Int): Int = a1 + a2

// 제일 마지막에 작성한 수식에서 사용한 변수의 타입을 통해
// 계산된 결과의 타입을 알 수 있으므로 반환 타입 생략이 가능하다.
fun testFun3(a1: Int, a2: Int) = a1 + a2

// 람다함수
// 람다식을 작성하여 변수에 담아두면 그 것을 이용해 람다식을 계산하고 결과를 사용
val lambda1: (Int, Int) -> Int = { a1: Int, a2: Int -> a1 + a2 }

// 타입 생략 가능
val lambda2 = { a1: Int, a2: Int -> a1 + a2 }
val lambda3: (Int, Int) -> Int = { a1, a2 -> a1 + a2 }

// 코드가 여러 줄 일때
fun testFun4(a1: Int, a2: Int): Int {
    val r1 = a1 + a2
    var r2 = a1 - a2
    var r3 = r1 * r2
    return r3
}

// 제일 마지막에 작성한 값이 반환
val lambda4 = { a1: Int, a2: Int ->
    val r1 = a1 + a2
    var r2 = a1 - a2
    r1 * r2
}