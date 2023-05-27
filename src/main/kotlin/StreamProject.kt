import java.io.*
import java.util.*
import kotlin.system.exitProcess

// 화면 입력 구현부터 진행
// 화면을 구성하기 위해 필요한 데이터 중 변하는 것들, 사용자가 입력하는것들, 데이터들을 각 상태별로 정리
// 정리한 데이터들 중 저장해야 하는것들을 선별
// 선별한 데이터들을 클래스로 묶어본다
// 데이터 저장과 연동하여 나머지 기능들을 구현

fun main() {
    val mainClass = MainClass()
    mainClass.running()
}

// 메인 클래스
// 프로그램의 흐름에 관련된 내용만 작성
class MainClass {

    val scanner = Scanner(System.`in`)

    // 각 상태별 객체 생성
    val mainMenuClass = MainMenuClass(scanner)
    val inputRecordClass = InputRecordClass(scanner, this)
    val showRecordClass = ShowRecordClass(scanner,this)

    // 프로그램 상태를 담는 변수에 초기 상태를 설정한다.
    var programState = ProgramState.PROGRAM_STATE_SHOW_MENU

    // 기록된 운동을 보는 상태 변수
    lateinit var showRecordState: ShowRecordState

    // 운동 기록 정보를 담을 클래스
    data class RecordClass(var type: String, var count: Int, var set: Int) : Serializable

    // 운동 기록을 담을 리스트
    val recordList = mutableListOf<RecordClass>()
    // 파일 목록을 담을 리스트
    val recordFileList = mutableListOf<String>()

    // 프로그램 상태 전체를 관리하며 운영하는 메서드
    fun running() {
        while (true) {
            // 프로그램 상태에 따른 분기
            when (programState) {
                // 메인메뉴 보여주는 상태
                ProgramState.PROGRAM_STATE_SHOW_MENU -> {
                    // 메인메뉴 출력
                    val inputMainMenuNumber = mainMenuClass.inputMainMenuNumber()

                    when (inputMainMenuNumber) {
                        // 오늘의 운동 기록 메뉴
                        MainMenuItem.MAIN_MENU_ITEM_WRITE_TODAY_RECORD.itemNumber -> {
                            // 프로그램 상태를 운동을 기록하는 상태로 변경
                            programState = ProgramState.PROGRAM_STATE_WRITE_TODAY_RECORD
                        }
                        // 운동 기록 보기 메뉴
                        MainMenuItem.MAIN_MENU_ITEM_SHOW_RECORD.itemNumber -> {
                            // 프로그램 상태를 운동 기록 보기 상태로 변경
                            programState = ProgramState.PROGRAM_STATE_SHOW_RECORD
                            // 날짜별 운동 기록을 보는 상태의 세부 상태를 설정
                            showRecordState = ShowRecordState.SHOW_RECORD_STATE_SELECT_DATE
                        }
                        // 종료
                        MainMenuItem.MAIN_MENU_ITEM_EXIT.itemNumber -> {
                            // 프로그램 상태를 종료상태로 변경
                            programState = ProgramState.PROGRAM_STATE_EXIT
                        }
                    }
                }
                // 오늘의 운동을 기록하는 상태
                ProgramState.PROGRAM_STATE_WRITE_TODAY_RECORD -> {
                    // 오늘의 운동을 기록하는 메서드 호출
                    inputRecordClass.inputTodayRecord()
                    // 운동 기록이 종료되면 메인 메뉴상태로 변경
                    programState = ProgramState.PROGRAM_STATE_SHOW_MENU
                }
                // 날짜별 운동기록 보는상태
                ProgramState.PROGRAM_STATE_SHOW_RECORD -> {

                    // 날짜별 운동 기록 상태의 세부상태로 분기
                    when (showRecordState) {
                        // 기록한 날짜 목록을 보여주고 선택하는 상태
                        ShowRecordState.SHOW_RECORD_STATE_SELECT_DATE -> {
                            val inputNumber = showRecordClass.selectRecordDay()
                            if (inputNumber == 0) {
                                programState = ProgramState.PROGRAM_STATE_SHOW_MENU
                            } else {
                                // 사용자가 선택한 날짜의 번호를 변수에 저장
                                showRecordClass.selectedRecordNumber = inputNumber
                                showRecordState = ShowRecordState.SHOW_RECORD_STATE_SHOW_RECORD
                            }
                        }
                        // 선택한 날짜의 운동기록을 보여주는 상태
                        ShowRecordState.SHOW_RECORD_STATE_SHOW_RECORD -> {
                            // 선택한 날짜의 운동기록을 보여준다
                            showRecordClass.showSelectedRecord()
                            // 날짜 선택 상태로 바꾼다
                            showRecordState = ShowRecordState.SHOW_RECORD_STATE_SELECT_DATE
                        }
                    }
                }
                // 종료
                ProgramState.PROGRAM_STATE_EXIT -> {
                    println("프로그램 종료")
                    // 프로그램 강제 종료 (파라미터값은 상태값)
                    // 0 : 정상 종료를 나타내는 코드
                    exitProcess(0)
                }
            }
        }
    }

    // 특정 날짜의 운동 기록을 가져오는 메서드
    fun getRecordData(fileName: String) {
        // 리스트 초기화
        recordList.clear()

        // 파일에 기록된 데이터를 모두 불러온다.
        val fis = FileInputStream(fileName)
        val ois = ObjectInputStream(fis)

        try {
            while (true) {
                val recordClass = ois.readObject() as MainClass.RecordClass
                recordList.add(recordClass)
            }
        } catch (e: Exception) {
            ois.close()
            fis.close()
        }
    }
}
class MainMenuClass(var scanner: Scanner) {

    // 메인 메뉴를 보여주고 메뉴 번호 입력
    fun inputMainMenuNumber(): Int {
        while (true) {
            try {
                println("메뉴를 선택해주세요")
                println("1. 오늘의 운동 기록")
                println("2. 날짜별 운동 기록 보기")
                println("3. 종료")
                print("번호 입력 : ")
                val inputNumberTemp = scanner.next()
                val inputNumber = inputNumberTemp.toInt()

                if (inputNumber !in 1..3) {
                    println("잘못 입력 하였습니다")
                } else {
                    return inputNumber
                }
            } catch (e: Exception) {
                println("잘못 입력 하였습니다")
            }

        }
    }
}

// 입력에 관련된 모든 기능 작성
class InputRecordClass(var scanner: Scanner, var mainClass: MainClass) {

    // 오늘의 운동을 기록
    fun inputTodayRecord() {

        while (true) {
            try {
                println()

                // scanner.nextInt() ->  100 을 입력하고 엔터를 치면 100까지만 수를 인식하고 엔터가 남아있다
                // 다음 스캐너가 작동할 때 남아있는 엔터값을 인식하는 오류가 발생하여 무한실행이 되는 오류가 발생할 수도 잇기에
                // 입력한 한줄의 모든 값을 인식시켜버리고 다음 스캐너를 사용한다
                scanner.nextLine()

                print("운동 종류 : ")
                val type = scanner.nextLine()

                print("횟수 : ")
                val temp1 = scanner.next()
                val count = temp1.toInt()

                print("세트 : ")
                val temp2 = scanner.next()
                val set = temp2.toInt()

                // 오늘 날짜의 운동 기록을 가져온다.
                // 파일이름 가져오기
                val todayRecordFileName = makeTodayFileName()
                // 전에 읽어온 데이터가 있을수 있으니 초기화
                mainClass.recordList.clear()
                val file = File(todayRecordFileName)
                if (file.exists() == true) {
                    // 오늘의 운동 기록 데이터를 읽어온다
                    mainClass.getRecordData(todayRecordFileName)
                }

                // 새로운 데이터를 담는 객체 생성
                val recordClass = MainClass.RecordClass(type,count, set)
                mainClass.recordList.add(recordClass)

                // 현재 입력한 데이터 저장
                writeRecord(todayRecordFileName)

                break
            } catch (e: Exception) {
                println("잘못 입력하였습니다")
            }
        }


    }



    // 오늘 날짜를 통해 파일이름을 만들어준다
    fun makeTodayFileName(): String {
        // 오늘 날짜를 가져온다
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val date = calendar.get(Calendar.DAY_OF_MONTH)

        // 2023-01-01 형식으로 변경
        return "%d-%02d-%02d.record".format(year, month, date)
        // 과거날짜 데이터 삽입
        // return "2023-05-21.record"
    }

    // 운동 기록 데이터 저장
    fun writeRecord(fileName:String){

        // 파일을 열고 내용을 삭제한다 (파라미터값 true를 주지 않을경우)
        val fos = FileOutputStream(fileName)
        val oos = ObjectOutputStream(fos)

        for(recordClass in mainClass.recordList){
            oos.writeObject(recordClass)
        }

        oos.flush()
        oos.close()
        fos.close()
    }


}

// 출력에 관련된 모든 기능 작성
class ShowRecordClass(var scanner: Scanner, val mainClass: MainClass) {

    // 선택한 날짜 번호
    var selectedRecordNumber = 0

    // 기록을 볼 날짜를 선택하는 메서드
    fun selectRecordDay(): Int {

        while (true) {
            try {
                println()
//                println("1. 2023-05-22")
//                println("2. 2023-05-23")
//                println("3. 2023-05-24")
//                println("4. 2023-05-25")
                // 등록된 날짜 출력
                getRecordFileList()
                print("날짜를 선택해주세요(0. 이전) : ")

                val temp1 = scanner.next()
                val inputNumber = temp1.toInt()
                if (inputNumber !in 0..4) {
                    println("잘못 입력하였습니다")
                } else {
                    return inputNumber
                }


            } catch (e: Exception) {
                println("잘못 입력하였습니다.")
            }
        }
    }

    // 선택한 날짜의 운동 기록을 보여주는 메서드
    fun showSelectedRecord() {
        // 사용자가 선택한 번호의 파일 이름 생성
        val fileName = "${mainClass.recordFileList[selectedRecordNumber-1]}.record"
        // 선택한 날짜의 운동 기록 데이터를 불러온다
        mainClass.getRecordData(fileName)

        println()
        println("${mainClass.recordFileList[selectedRecordNumber - 1]}의 운동 기록입니다")
        for(recordClass in mainClass.recordList){
            println()
            println("운동 타입 : ${recordClass.type}")
            println("횟수 : ${recordClass.count}")
            println("세트 : ${recordClass.set}")
        }
    }

    // 운동 기록 날짜 파일 목록을 불러온다
    fun getRecordFileList() {
        // 파일 목록 리스트를 초기화
        mainClass.recordFileList.clear()

        // 현재 위치의 파일 목록을 가져온다
        val dir = File(".")
        var fileList = dir.list()
        // 오름차순 정렬
        fileList = fileList.sortedArray()

        // 파일 목록에서 .record로 끝나는 것들만 담는다
        for (temp1 in fileList) {
            if (temp1.endsWith(".record")) {
                val temp2 = temp1.replace(".record", "")
                mainClass.recordFileList.add(temp2)
            }
        }

        // 출력
        if (mainClass.recordFileList.size == 0) println("등록된 운동 기록이 없습니다.")
        else {
            for (idx1 in 1..mainClass.recordFileList.size) {
                println("$idx1 : ${mainClass.recordFileList[idx1 - 1]}")
            }
        }

    }

}

enum class ShowRecordState {

    // 기록된 날짜를 선택하는 상태
    SHOW_RECORD_STATE_SELECT_DATE,

    // 선택된 날짜의 운동 기록을 보여주는 상태
    SHOW_RECORD_STATE_SHOW_RECORD

}

// 메인 메뉴 항목
// 매직넘버(어떤 의미에 해당하는 값들)를 사용하지 말자
// 추 후 유지보수나 다시 코드를 리딩했을시 이 숫자가 뭐였는지 구분하기 쉽지 않다

// 리턴값이 int타입이기에 타입을 지정해서 사용
enum class MainMenuItem(val itemNumber:Int){
    // 오늘의 운동 기록
    MAIN_MENU_ITEM_WRITE_TODAY_RECORD(1),
    // 날짜별 운동 기록 보기
    MAIN_MENU_ITEM_SHOW_RECORD(2),
    // 종료
    MAIN_MENU_ITEM_EXIT(3)
}

// 프로그램 상태를 나타내는 enum
enum class ProgramState {
    // 상태를 나타내는 값들을 정의한다.
    // 메인 메뉴를 보여주는 상태
    PROGRAM_STATE_SHOW_MENU,

    // 운동을 기록하는 상태
    PROGRAM_STATE_WRITE_TODAY_RECORD,

    // 날짜별 운동 기록을 보는 상태
    PROGRAM_STATE_SHOW_RECORD,

    // 종료
    PROGRAM_STATE_EXIT
}