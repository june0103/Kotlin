fun main() {

    // 코틀린 범위 지정 함수
    // 객체를 생성할 때 파라미터에 값을 설정하는 것과 관련
    // let, apply, run, with, also

    val t1 = TestClass1(100, 200)
    t1.a3 = 300
    t1.a4 = 400
    t1.testMethod1()

    println("-----------------------")
    val t2 = TestClass1(100, 200)
    t2.let {
        it.a3 = 300
        it.a4 = 400
    }
    t2.testMethod1()

    println("-----------------------")

    val t3 = TestClass1(100, 200)
    t3.apply {
        a3 = 300
        a4 = 400
    }
    t3.testMethod1()
    println("-----------------------")

    val t4 = TestClass1(100, 200)
    t4.run {
        a3 = 300
        a4 = 400
    }
    t4.testMethod1()

    println("-----------------------")

    val t5 = TestClass1(100, 200)
    with(t5) {
        a3 = 300
        a4 = 400
    }
    t5.testMethod1()

    println("-----------------------")

    val t6 = TestClass1(100, 200)
    t6.also {
        it.a3 = 300
        it.a4 = 400
    }
    t6.testMethod1()

    println("-----------------------")

    // let : 객체에 접근할 때 it 변수를 사용해야 한다.
    // 코드 블럭 마지막에 작성된 값이나 수식을 반환해준다.
    // let 객체 생성 후 나중에 프로퍼티에 값을 저장하고자 할 때 사용한다.
    // 만약 아래와 같이 객체 생성시 프로퍼티의 값을 저장하고 객체의 ID를
    // 변수에 담겠다면 람다식 마지막에 객체의 ID(it을 통해)를 반환해야 한다.
    val t7 = TestClass1(100, 200).let {
        it.a3 = 300
        it.a4 = 400
        // t7 변수에 객체의 ID를 담기 위해 반환해줘야 한다.
        it
    }
    println(t7)
    t7.testMethod1()


    println("-----------------------")
    // apply : 생성된 객체의 ID가 apply 쪽으로 전달되어
    // this(혹은 생략)을 통해 객체의 프로퍼티에 접근할 수 있다.
    // { } 블럭 내부의 코드가 수행이 끝나면 생성된 객체의 ID가 변수에 담긴다.
    // 이에 { } 블럭 내부에서 객체의 ID를 반환하는 코드를 작성하지 않는다.
    val t8 = TestClass1(100, 200).apply {
        a3 = 300
        a4 = 400
    }
    println(t8)
    t8.testMethod1()

    println("-----------------------")
    // run : 객체를 생성한 후에 run 코드 블럭 내로 객체가 전달되기 때문에
    // this(혹은 생량)을 통해 프러퍼티에 접근할 수 있다.
    // run { } 블럭의 수행이 끝나면 제일 마지막에 작성한 값이 변환되어
    // 변수에 담긴다. 이에 생성된 객체의 ID를 전달하고자 한다면 run { }
    // 블럭 제일 아래에 this를 작성하여 객체의 ID를 반환해야 한다.
    val t9 = TestClass1(100, 200).run {
        a3 = 300
        a4 = 400
        this
    }
    println(t9)
    t9.testMethod1()

    // 객체를 생성하면서 바로 프로퍼티의 값을 설정하겠다면 -> apply
    // 객체를 생성하고 나중에 프로퍼티의 값을 설정하겠다면 -> run
    println("-----------------------")

    // with : 생성한 객체를 ( ) 안에 넣어줘야 한다.
    // { } 내에는 with에 지정된 객체의 ID가 전달되기 때문에
    // this(혹은 생략)로 프로퍼티에 접할 수 있다.
    // 객체를 생성할 때 프로퍼티에 값을 설정하기 보단 run 처럼
    // 객체 생성한 이후 나중에 프로퍼티의 값을 새롭게 저장하기 위해
    // 사용한다. run 보다 작성하는 코드가 더 많기 때문에 잘 사용하지는 않는다.
    val t10 = with(TestClass1(100, 200)) {
        a3 = 300
        a4 = 400
        this
    }
    println(t10)
    t10.testMethod1()

    println("-----------------------")

    // 객체를 생성과 함께 프로퍼티의 값을 설정하는 작업을 하면 객체의 ID가
    // it 이라는 변수로 전달되고 그 것을 통해 객체의 프로퍼티에 접근할 수 있다.
    // 하지만 let과 다르게 생성된 객체의 ID가 반환되므로 마지막에 객체의 ID를
    // 반환하지 않아도 된다.
    val t11 = TestClass1(100, 200).also {
        it.a3 = 300
        it.a4 = 400
    }
    println(t11)
    t11.testMethod1()


}

class TestClass1(var a1: Int, var a2: Int) {
    var a3: Int = 0
    var a4: Int = 0

    fun testMethod1() {
        println("a1 : $a1")
        println("a2 : $a2")
        println("a3 : $a3")
        println("a4 : $a4")
    }
}