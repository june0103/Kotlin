fun main() {
    // 추상클래스
    // 추상메서드를 가지고있는 클래스
    // 추상메서드
    // 구현되지 않은 메서드
    // 추상클래스는 완벽한 클래스가 아니기 때문에 객체 생성불가능
    // 추상클래스를 상속받은 클래스를 만들고 추상메서드를 오버라이딩하여 사용
    // 추상클래스와 메서드는 abstract 키워드를 사용하며 상속이 가능해야하기 때문에
    // 클래스에는 open 키워드를 사용, 오버라이딩이 가능해야하기에 추상메서드에 open키워드 사용
    // 추상클래스는 메서드 오버라이딩에 대한 강제성을 주기위해 사용

    val t2 = TestClass2()
    t2.testMethod()

    val t3:TestClass1 = TestClass2()
    t3.testMethod()
}

// 추상클래스 정의
open abstract class TestClass1{
    // 추상메서드
    open abstract fun testMethod()
}

// 추상클래스를 상속받은 클래스
class TestClass2 : TestClass1(){
    override fun testMethod() {
        println("TestClass2의 testMethod")
    }
}