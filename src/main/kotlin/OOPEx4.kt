import java.util.Scanner

//자동차공장
//
//생산할 자동차를 선택해주세요
//1. 붕붕, 2. 승용차, 3. 버스, 4. 트럭, 0. 생산종료
//
//입력이 완료되면 다음과 같이 출력한다.
//총 생산 자동차 수 : 000대
//붕붕 : 00대
//승용차 : 00대
//버스 : 00대
//트럭 : 00대
//
//종류 : 붕붕
//최대속도 : 300km/h
//연료 : 꽃향기
//탑승인원수 : 1명
//
//종류 : 승용차
//최대속도 : 200km/h
//연로 : 휘발유
//탑승인원수 : 4명
//
//종류 : 버스
//최대속도 : 150km/h
//연료 : 가스
//탑승인원수 : 50명
//
//종류 : 트럭
//최대속도 : 100km/h
//연료 : 가스
//탑승인원수 : 3명
//
//생산된 자동차들의 평균 속도 : 000km/h
//생산된 자동차들의 총 탑승인원수 : 0000명
//연료가 꽃향기인 자동차의 수 : 00대
//연료가 휘발유인 자동차의 수 : 00대
//연료가 가스인 자동차의 수 : 00대

fun main(){
    val carFactory = CarFactory()
    // 생산할 자동차 번호를 입력 받는다.
    var selectNumber = 0
    // 0 을 입력할 때 까지 반복한다.
    do {
        selectNumber = carFactory.selectCarType()
        // print(selectNumber)
        // 자동차 생산한다.
        if(selectNumber != 0){
            val car = carFactory.createCar(selectNumber)
            // 자동차을 저장한다.
            carFactory.addCar(car)
        }

    }while (selectNumber != 0)



    // 자동차들의 정보를 출력한다.
    carFactory.printCarInfo()

    // 자동차평균속도, 탑승인원수, 자동차대수을 출력한다.
    carFactory.printCarTotalAvgPrice()
    // 생산이 완료되면 생산된 자동차 개수를 출력한다.
    carFactory.printCarCount()
}

// 자동차 공장 클래스
class CarFactory{

    private val scanner = Scanner(System.`in`)
    val carList = ArrayList<Car>()

    // 생산할 자동차의 종류를 선택하는 기능
    fun selectCarType() : Int{

        var selectNumber = 0

        do {
            println("생산할 자동차를 선택해주세요")
            println("1. 붕붕, 2. 승용차, 3. 버스, 4. 트럭, 0. 생산종료")
            print("번호 입력 : ")
            selectNumber = scanner.nextInt()

            if(selectNumber !in 0..4){
                println("잘못 입력하였습니다")
            }
        }while(selectNumber !in 0..4)

        return selectNumber
    }
    // 생산한 자동차의 수를 구해 출력하는 기능
    fun printCarCount(){

        var flowerCount = 0
        var oilCount = 0
        var gasCount = 0

        // 각 자동차 개수를 계산한다.
        for(tempCar in carList){
            when(tempCar.fuel){
                "꽃향기" -> flowerCount++
                "휘발유" -> oilCount++
                "가스" -> gasCount++
            }
        }
        println("연료가 꽃향기인 자동차의 수 : $flowerCount 대")
        println("연료가 휘발유인 자동차의 수 : $oilCount 대")
        println("연료가 가스인 자동차의 수 : $gasCount 대")
    }
    // 생산한 자동차들의 정보를 출력하는 기능
    fun printCarInfo(){
        // 자동차 수 만큼 반복한다.
        for(tempCar in carList){
            tempCar.printCarInfo()
        }
    }
    // 생산한 자동차들의 평균 속도, 총 탑승인원수를 구해 출력하는 기능
    fun printCarTotalAvgPrice(){
        // 자동차들의 탑승인원 총합을 담을 변수
        var passengersTotal = 0
        // 자동차들의 평균속도를 구하기위해 총합을 담을 변수
        var maxTotal = 0
        //

        // 자동차의 수 만큼 반복한다.
        for(tempCar in carList){
            passengersTotal += tempCar.passengers
            maxTotal += tempCar.max
        }
        // 평균을 구한다.
        val maxAvg = maxTotal / carList.size

        println("생산된 자동차들의 평균 속도 : ${maxAvg}km/h")
        println("생산된 자동차들의 총 탑승인원수 : ${passengersTotal}명")

    }
    // 자동차를 생산하여 반환하는 메서드
    fun createCar(carType:Int) = when(carType){
        1 -> Car("붕붕", 300, "꽃향기",1)
        2 -> Car("승용차", 200, "휘발유",4)
        3 -> Car("버스", 50, "가스",50)
        4 -> Car("트럭", 100, "가스",3)
        else -> Car("아무", 0, "아무",0)
    }
    // 생산된 자동차 저장한다.
    fun addCar(car:Car){
        carList.add(car)
    }

}

// 자동차 클래스
class Car (var Type:String, var max:Int, var fuel:String, var passengers:Int){

    fun printCarInfo(){
        println(Type)
        println("최대속도 : $max")
        println("연료 : $fuel")
        println("탑승인원수 : $passengers")
    }
}