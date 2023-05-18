fun main() {

    // null을 허용하는 변수 선언
    var a1: String? = null

    // null을 허용하지 않는 변수 선언
    // var a2:String = null

    testFun1("안녕하세요")
    // testFun1(null)

    testFun2("안녕")
    testFun2(null)

    testFun3("반갑습니다")
    testFun3(null)

    val t100 = TestClass1(100, "문자열")
    testFunction1(t100)

    // testFunction1(null)

    testFunction200(t100)
    println("------------------")
    testFunction200(null)

    testFunction300(t100)
    println("------------------")
    testFunction300(null)

}

fun testFun1(str1: String?) {
    // !!연산자
    // Null을 허용하는 타입의 변수값을 null을 하용하지 않는 타입으로 변환
    // null을 허용하지 않는 타입의 변수에 담을수 있도록 한다
    val value1: String = str1!!
    println("value1 : $value1")
}

fun testFun2(str1: String?) {
    // str1 에 null 아닌 객체의 ID가 들어있으면 그 ID를 value1변수에 담고
    // null이 있으면 지정한 값인 "기본문자열" 객체가 value1 변수에 담긴다
    val value1: String = str1 ?: "기본문자열"
    println("value1 : $value1")
}

fun testFun3(str1: String?) {
    // 만약 변수의 값이 null인 경우 코드가 동작하지 않도록 처리해주면 null 안전성을 확보할 수있다
    // null을 허용하는 변수를 null을 허용하지 않는 변수처럼 자유롭게 사용할 수있다
    if (str1 != null) {
        val value1: String = str1
        println("value1 : $value1")
    }
}

class TestClass1(var str1: Int, var str2: String) {

    fun testMethod1() {
        System.out.println("TestClass1의 testMethod1")
    }
}

fun testFunction1(t1: TestClass1?) {
    // !! 변수를 통해 객체의 맴버에 접근
    // null안정성을 확보하지 않고 맴버에 접근하려면 !!를 붙혀준다

    // !! 연산자는 null을 허용하는 변수에 담긴 값을 추출하여 null을 허용하지 않는 타입으로 변환
    // null값이 들어있으면 오류 발생
    println("t1.str1 : ${t1!!.str1}")
    println("t1.str2 : ${t1!!.str2}")
    t1!!.testMethod1()
}

fun testFunction200(t1: TestClass1?) {
    // ? 연산자
    // 참조변수?.멤버변수 : 참조변수에 null값이 들어있다면 null 반환
    // 참조변수?.멤버 메서드 : 참조변수에 null값이 들어있다면 메서드 호출하지 않는다
    println("t1.str1 : ${t1?.str1}")
    println("t1.str2 : ${t1?.str2}")
    t1?.testMethod1()
}

fun testFunction300(t1:TestClass1?){
    // null이 저장되어 있는지 여부를 확인
    // if문 내부에서는 null을 아무런 연산자도 붙히지 않고 멤버 접근 가능
    if(t1 != null){

        println("t1.str1 : ${t1.str1}")
        println("t1.str2 : ${t1.str2}")
        t1.testMethod1()
    }
}