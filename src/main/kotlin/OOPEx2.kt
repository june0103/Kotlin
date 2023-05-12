import java.util.Scanner
// 내가 작성했던 코드
fun main() {
    // 공장객체 생성
    val factory = Factory()
    // 번호 입력
    val inputNumber = factory.inputToyNumber()
    // 생산
    val exit = factory.exitToy()
}

// 장난감공장 클래스
class Factory {
    val scanner = Scanner(System.`in`)
    var total = 0
    var toy1Sum = 0
    var toy2Sum = 0
    var toy3Sum = 0
    var toy4Sum = 0

    // 생산할 장난감 종류 선택 하고 갯수 더함
    fun inputToyNumber() {
        var choice = 0
        do {
            println("생산할 장난감의 종류를 선택해주세요")
            print("1. 로보트 장난감, 2. 레고, 3. BB탄 총, 4. 잠만보인형, 0. 생산끝")
            choice = scanner.nextInt()

            if (choice !in 1..4) {
                println("다시 입력해주세요")
                println("----------------------------------")
            } else if (choice in 1..4) {
                when (choice) {
                    1 -> {
                        toy1Sum += 1
                        total += 1
                        println("1. 로보트 장난감 생산")
                        println("현재 ${toy1Sum}개")
                        println("----------------------------------")
                    }

                    2 -> {
                        toy2Sum += 1
                        total += 1
                        println("2. 레고 생산")
                        println("현재 ${toy2Sum}개")
                        println("----------------------------------")
                    }

                    3 -> {
                        toy3Sum += 1
                        total += 1
                        println("3. BB탄 총 생산")
                        println("현재 ${toy3Sum}개")
                        println("----------------------------------")
                    }

                    4 -> {
                        toy4Sum += 1
                        total += 1
                        println("4. 잠만보인형 생산")
                        println("현재 ${toy4Sum}개")
                        println("----------------------------------")
                    }
                }
            }
        } while (choice !== 0)
        println("생산 종료")
        println("----------------------------------")
    }

    // 장난감 갯수과 총 갯수, 합을 출력
    fun exitToy() {
        var toy1 = ToyItem("로보트 장난감", 5000, "로보트만큼 크다")
        var toy2 = ToyItem("레고", 50000, "레고만큼 크다")
        var toy3 = ToyItem("BB탄 총", 10000, "BB탄 총 만큼 크다")
        var toy4 = ToyItem("잠만보인형", 20000, "잠만보 만큼 크다")
        val totalPrice = (toy1Sum*toy1.price) + (toy2Sum*toy2.price) + (toy3Sum*toy3.price) + (toy4Sum*toy4.price)

        println("총 : ${total}개")
        println("로보트 장난감 : ${toy1Sum}개")
        println("레고 : ${toy2Sum}개")
        println("BB탄 총 : ${toy3Sum}개")
        println("잠만보인형 : ${toy4Sum}개")
        println("----------------------------------")
        toy1.printItemInfo()
        println("----------------------------------")
        toy2.printItemInfo()
        println("----------------------------------")
        toy3.printItemInfo()
        println("----------------------------------")
        toy4.printItemInfo()
        println("----------------------------------")
        println("생산된 장난감 총 가격 : ${totalPrice}원")
        println("생산된 장난감 평균 가격 : ${totalPrice/total}원")
    }

}

// 장난감 클래스
class ToyItem(var name: String, var price: Int, var volume: String) {

    // 장난감 정보를 출력하는 함수
    fun printItemInfo() {
        println(name)
        println("가격 : ${price}원")
        println("크기 : $volume")
    }
}