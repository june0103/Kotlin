import java.util.Scanner

fun main() {
//    var scanner = Scanner(System.`in`)
//    print("숫자 입력")
//    val a1 = scanner.nextInt()
//    println("a1 : $a1")
//
//    val arrayList = ArrayList<String>()
//    arrayList.add("어레이1")
//    arrayList.add("어레이2")
//
//    for (str1 in arrayList){
//        println(str1)
//    }
//    println(arrayList[0])
//    println(arrayList[1])



    var scanner = Scanner(System.`in`)
    print("학생 수 입력")
    val studentNum = scanner.nextInt()

    val nameList = ArrayList<String>()
    val gradeList = ArrayList<Int>()
    val koreanList = ArrayList<Int>()
    val englishList = ArrayList<Int>()
    val mathList = ArrayList<Int>()
    var koreanSum = 0
    var englishSum = 0
    var mathSum = 0


    for (number in 1..studentNum) {
        println("이름 입력 : ")
        var name = scanner.next()
        nameList.add(name)
        println("학년 입력 : ")
        var grade = scanner.nextInt()
        gradeList.add(grade)
        println("국어점수 입력 : ")
        var korean = scanner.nextInt()
        koreanList.add(korean)
        println("영어점수 입력 : ")
        var english = scanner.nextInt()
        englishList.add(english)
        println("수학점수 입력 : ")
        var math = scanner.nextInt()
        mathList.add(math)
    }

    for (name in nameList) {
        println("학생이름 : $name")
    }
    for (grade in gradeList) {
        println("학년 : $grade")
    }
    for (korean in koreanList) {
        println("국어점수 : $korean")
        koreanSum += korean
    }
    for (eng in englishList) {
        println("영어점수 : $eng")
        englishSum += eng
    }
    for (math in mathList) {
        println("수학점수 : $math")
        mathSum += math
    }
    val koreanAvg = koreanSum / studentNum
    val englishAvg = englishSum / studentNum
    val mathAvg = mathSum / studentNum
    println("국어총점 : $koreanSum  영어총점 : $englishSum  수학총점 : $mathSum")
    println("국어평균 : $koreanAvg  영어평균 : $englishAvg  수학평균 : $mathAvg")


}