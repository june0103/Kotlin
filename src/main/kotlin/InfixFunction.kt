fun main() {
    // infix : 함수 호출을 연산자 사용하듯이 할 수 있는 함수
    // 값1 함수이름 값2 형태로 호출
    // 값1 객체를 통해 함수를 호출하고 매개변수로 값2를 전달

    val v1 = 100
    // 값( 또는 객체 ). 함수
    val r1 = v1.add2(50)
    println("r1 : $r1")

    // infix가 있으므로 다음과 같이 호출 할 수 있다
    val r2 = v1 add2 50
    println("r2 : $r2")

    val obj1 = TestClass1()
    val obj2 = TestClass1()
    obj1.number1 = 100
    obj2.number1 = 200

    val r3 = obj1 add4 obj2
    println(r3.number1)

    val r4 = obj1.add4(obj2)
    println(r4.number1)
}

// infix fun 값1의 타입, 함수이름(값2를 담을 매개변수) : 반환타입
infix fun Int.add2(a1: Int): Int {

    // this는 첫 번째 값을 의미
    return this + a1
}

infix fun Int.minus2(a1: Int): Int {
    return this - a1
}

// 일반적인 함수는 infix로 정의 할 수없고 확장함수에만 정의 할 수 있다
// infix fun add3(a1:Int) : Int{
// }

// 클래스에 정의한 멤버 메서드
class TestClass1 {
    var number1 = 0

    infix fun add4(target: TestClass1): TestClass1{
        val r1 = this.number1 + target.number1
        val t1 = TestClass1()
        t1.number1 = r1
        return t1
    }
}
