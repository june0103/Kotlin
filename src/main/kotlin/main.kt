import java.util.Scanner

fun main() {

//    var scanner = Scanner(System.`in`)
//    print("학생 수 입력")
//    val studentNum = scanner.nextInt()
//
//    val nameList = ArrayList<String>()
//    val gradeList = ArrayList<Int>()
//    val koreanList = ArrayList<Int>()
//    val englishList = ArrayList<Int>()
//    val mathList = ArrayList<Int>()
//    var koreanSum = 0
//    var englishSum = 0
//    var mathSum = 0
//
//    for (number in 1..studentNum) {
//        println("이름 입력 : ")
//        var name = scanner.next()
//        nameList.add(name)
//        println("학년 입력 : ")
//        var grade = scanner.nextInt()
//        gradeList.add(grade)
//        println("국어점수 입력 : ")
//        var korean = scanner.nextInt()
//        koreanList.add(korean)
//        println("영어점수 입력 : ")
//        var english = scanner.nextInt()
//        englishList.add(english)
//        println("수학점수 입력 : ")
//        var math = scanner.nextInt()
//        mathList.add(math)
//    }
//
//    for (name in nameList) {
//        println("학생이름 : $name")
//    }
//    for (grade in gradeList) {
//        println("학년 : $grade")
//    }
//    for (korean in koreanList) {
//        println("국어점수 : $korean")
//        koreanSum += korean
//    }
//    for (eng in englishList) {
//        println("영어점수 : $eng")
//        englishSum += eng
//    }
//    for (math in mathList) {
//        println("수학점수 : $math")
//        mathSum += math
//    }
//    val koreanAvg = koreanSum / studentNum
//    val englishAvg = englishSum / studentNum
//    val mathAvg = mathSum / studentNum
//    println("국어총점 : $koreanSum  영어총점 : $englishSum  수학총점 : $mathSum")
//    println("국어평균 : $koreanAvg  영어평균 : $englishAvg  수학평균 : $mathAvg")


    // 학생 수를 입력받는다.
    val studentCount = inputStudentCount()
    // println(studentCount)
    // 학생 정보를 입력받는다.
    inputStudentInfo(studentCount)
    // 학생 정보를 출력한다.
    printStudentInfo(studentCount)

}

// 점수를 담을 ArrayList
val nameList = ArrayList<String>()
val gradeList = ArrayList<Int>()
val koreanList = ArrayList<Int>()
val englishList = ArrayList<Int>()
val mathList = ArrayList<Int>()

val Scanner = Scanner(System.`in`)

/// 학생 수를 입력받는 함수
fun inputStudentCount(): Int {
    print("학생 수를 입력해주세요 : ")
    val studentCount = Scanner.nextInt()
    return studentCount
}

// 학생의 정보를 입력받는 함수
fun inputStudentInfo(studentCount: Int) {
    for (a1 in 1..studentCount) {
        print("이름 : ")
        val name = Scanner.next()
        print("학년 : ")
        val grade = Scanner.nextInt()
        print("국어 : ")
        val korean = Scanner.nextInt()
        print("영어 : ")
        val english = Scanner.nextInt()
        print("수학 : ")
        val math = Scanner.nextInt()

        nameList.add(name)
        gradeList.add(grade)
        koreanList.add(korean)
        englishList.add(english)
        mathList.add(math)
// 과목별 총점과 평균을 출력한다.
        printTotalAvg(studentCount)
    }
}

/// 학생들의 정보를 출력하는 함수
fun printStudentInfo(studentCount: Int) {
    for (a1 in 1..studentCount) {
        println("이름 : ${nameList[a1 - 1]}")
        println("학년 : ${gradeList[a1 - 1]}")
        println("국어 : ${koreanList[a1 - 1]}")
        println("영어 : ${englishList[a1 - 1]}")
        println("수학 : ${mathList[a1 - 1]}")
    }
}

// 각 과목별 총점과 평균을 출력하는 함수
fun printTotalAvg(studentCount: Int) {
    // 과목별 점수를 담을 변수
    var koreanTotal = 0
    var englishTotal = 0
    var mathTotal = 0

    for (a1 in 1..studentCount) {
        koreanTotal += koreanList[a1 - 1]
        englishTotal += englishList[a1 - 1]
        mathTotal += mathList[a1 - 1]
    }

    // 과목별 평균을 구한다.
    val koreanAvg = koreanTotal / studentCount
    val englishAvg = englishTotal / studentCount
    val mathAvg = mathTotal / studentCount

    // 출력한다
    print("국어 총점 : $koreanTotal")
    print("영어 총점 : $englishTotal")
    print("수학 총점 : $mathTotal")
    print("국어 평균 : $koreanAvg")
    print("영어 평균 : $englishAvg")
    print("수학 평균 : $mathAvg")
}