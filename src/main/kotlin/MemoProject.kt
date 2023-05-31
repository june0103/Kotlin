import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Scanner
import kotlin.system.exitProcess

fun main() {
    val mainClass = MainClass()
    mainClass.run()
}

class MainClass {

    val scanner = Scanner(System.`in`)

    var programState = ProgramState.PROGRAM_STATE_PW

    val mainMenuClass = MainMenuClass(scanner)
    val passwordClass = PasswordClass(scanner, this)
    val categoryClass = CategoryClass(scanner, this)
    val memoClass = MemoClass(scanner, this,categoryClass)

    // 메모 저장용
    data class MemoClass(var name : String,var content: String) : Serializable

    // 파일목록(카테고리) 리스트
    val categoryFileList = mutableListOf<String>()

    // 메모를 담을 리스트
    val memoList = mutableListOf<MemoClass>()

    fun run() {
        while (true) {
            when (programState) {
                // 로그인단계
                ProgramState.PROGRAM_STATE_PW -> {
                    passwordClass.inputPassword()
                    programState = ProgramState.PROGRAM_STATE_SHOW_MENU
                }

                // 메인메뉴 상태
                ProgramState.PROGRAM_STATE_SHOW_MENU -> {
                    // 메인메뉴 선택
                    val inputMainMenuNumber = mainMenuClass.inputMainMenuNumber()

                    when (inputMainMenuNumber) {
                        // 1. 메모 카테고리 관리 상태
                        MainMenuItem.MAIN_MENU_MEMO_CTGRY_MNGMNT.menuNumber -> {
                            while (true) {
                                // 카테고리 목록 출력
                                categoryClass.readCtgryList()
                                // 카테고리 관리메뉴 선택 상태
                                val inputCtgryMenuItem = mainMenuClass.ctgryMenuNumber()
                                when (inputCtgryMenuItem) {
                                    // 1. 카테고리 등록
                                    CtgryMenuItem.CTGRY_MENU_CREATE.menuNumber -> {
                                        categoryClass.createCtgry()
                                    }
                                    // 2. 카테고리 삭제
                                    CtgryMenuItem.CTGRY_MENU_DELETE.menuNumber -> {
                                        categoryClass.deleteCtgry()
                                    }
                                    // 3. 카테고리 수정
                                    CtgryMenuItem.CTGRY_MENU_UPDATE.menuNumber -> {
                                        categoryClass.updateCtgry()
                                    }
                                    // 4. 이전
                                    CtgryMenuItem.CTGRY_MENU_EXIT.menuNumber -> {
                                        programState = ProgramState.PROGRAM_STATE_SHOW_MENU
                                        break
                                    }
                                } // while (when) End
                            } // while End
                        }



                        // 2. 메모 카테고리 선택
                        MainMenuItem.MAIN_MENU_MEMO_CTGRY_CHOICE.menuNumber -> {
                            while (true) {
                                // 카테고리 목록 출력
                                categoryClass.readCtgryList()
                                // 카테고리 선택
                                categoryClass.selectCtgryNumber = categoryClass.choiceCtgry()
                                if (categoryClass.selectCtgryNumber == 0) {
                                    programState = ProgramState.PROGRAM_STATE_SHOW_MENU
                                    break
                                } else {
                                    while(true){
                                        memoClass.readMemoList()
                                        // 메모 관리메뉴
                                        val inputMemoMenuItem = mainMenuClass.memoMenuNumber()
                                        when (inputMemoMenuItem) {
                                            // 1. 메모보기
                                            MemoMenuItem.MEMO_MENU_READ.menuNumber -> {
                                                memoClass.readMemo()
                                            }
                                            // 2. 메모등록
                                            MemoMenuItem.MEMO_MENU_CREATE.menuNumber -> {
                                                memoClass.createMemo()
                                            }
                                            // 3. 메모수정
                                            MemoMenuItem.MEMO_MENU_UPDATE.menuNumber -> {
                                                memoClass.updateMemo()
                                            }
                                            // 4. 메모삭제
                                            MemoMenuItem.MEMO_MENU_DELETE.menuNumber -> {
                                                memoClass.deleteMemo()
                                            }
                                            // 5. 이전
                                            MemoMenuItem.MEMO_MENU_EXIT.menuNumber -> {
                                                programState = ProgramState.PROGRAM_STATE_SHOW_MENU
                                                break
                                            }
                                        } // else (while) when End
                                    } // else (while) End
                                } //else End
                            } //while End
                        }


                        // 3. 메모 내용 전체보기
                        MainMenuItem.MAIN_MENU_MEMO_SHOW_ALL.menuNumber -> {
                            memoClass.showAllMemo()
                            programState = ProgramState.PROGRAM_STATE_SHOW_MENU
                        }


                        // 4. 메모 메뉴 종료
                        MainMenuItem.MAIN_MENU_EXIT.menuNumber -> {
                            programState = ProgramState.PROGRAM_STATE_EXIT
                        }
                    }
                }
                // 프로그램 종료
                ProgramState.PROGRAM_STATE_EXIT -> {
                    println("프로그램 종료")
                    exitProcess(0)
                }
            } //programState End
        } // while End
    } // fun run() End

    // 비밀번호 가져오기
    fun getPassword(): Int {
        val fis = FileInputStream("Password.dat")
        val dis = DataInputStream(fis)
        // 저장된 데이터 pw변수에 저장
        val pw = dis.readInt()

        dis.close()
        fis.close()
        return pw
    }

    // 카테고리파일에 있는 메모객체데이터 역직렬화하여 가져오기
    fun getMemoData(CtgryName: String) {
        memoList.clear()
        // 파일에 기록된 데이터를 모두 불러온다.
        val fis = FileInputStream(CtgryName)
        val ois = ObjectInputStream(fis)
        try {
            while (true) {
                val memoClass = ois.readObject() as MainClass.MemoClass
                memoList.add(memoClass)
            }
        } catch (e: Exception) {
            ois.close()
            fis.close()
        }
    }
} // MainClass End

enum class ProgramState {
    // 메인 메뉴를 보여주는 상태
    PROGRAM_STATE_SHOW_MENU,
    // 비밀번호
    PROGRAM_STATE_PW,
    // 종료
    PROGRAM_STATE_EXIT
//    // 카테고리 crud
//    PROGRAM_STATE_CTGRY_CREATE,
//    PROGRAM_STATE_CTGRY_READE,
//    PROGRAM_STATE_CTGRY_UPDATE,
//    PROGRAM_STATE_CTGRY_DELETE,
//
//    // 메모 crud
//    PROGRAM_STATE_MEMO_CREATE,
//    PROGRAM_STATE_MEMO_READE,
//    PROGRAM_STATE_MEMO_UPDATE,
//    PROGRAM_STATE_MEMO_DELETE,
}


class PasswordClass(var scanner: Scanner, var mainClass: MainClass) {

    fun inputPassword(){
        while(true){
            try {
                val file = File("Password.dat")
                if(file.exists() == true){
                    val pw = mainClass.getPassword()

                    println()
                    print("로그인 하시려면 비밀 번호를 입력하세요 : ")
                    val temp1 = scanner.next()
                    val loginPw = temp1.toInt()

                    if( pw==loginPw){
                        println()
                        println("로그인 완료")
                        break
                    } else{
                        println()
                        println("다시 입력해주세요.")
                    }

                }
                else {
                    println()
                    print("설정할 비밀번호를 입력해주세요 : ")
                    val temp = scanner.next()
                    val setPw = temp.toInt()

                    println()
                    print("한번 더 입력해주세요 : ")
                    val temp1 = scanner.next()
                    val setPw1 = temp1.toInt()

                    if(setPw == setPw1){
                        writePassword(setPw)
                    } else{
                        println()
                        println("다시 입력해 주세요")
                    }
                }

            }
            catch (e:Exception){
                println()
                println("잘못 입력하였습니다.")
            }

        }
    }

    fun writePassword(password:Int){

        val fos = FileOutputStream("Password.dat")
        val dos = DataOutputStream(fos)

        dos.writeInt(password)

        dos.flush()
        dos.close()
        fos.close()
    }


}


class MainMenuClass(var scanner: Scanner) {

    // 메인 메뉴를 보여주고 메뉴 번호 입력
    fun inputMainMenuNumber(): Int {
        while (true) {
            try {
                println()
                println("1. 메모 카테고리 관리")
                println("2. 메모 카테고리 선택")
                println("3. 메모 내용 전체 보기")
                println("4. 종료")
                println()
                print("메뉴를 선택해주세요 : ")
                val inputNumberTemp = scanner.next()
                val inputNumber = inputNumberTemp.toInt()

                if (inputNumber !in 1..4) {
                    println("잘못 입력 하였습니다")
                } else {
                    return inputNumber
                }
            } catch (e: Exception) {
                println("inputMainMenuNumber오류")
                println("잘못 입력 하였습니다")
            }
        }
    }

    fun ctgryMenuNumber():Int {
        while (true) {
            try {
                println()
                println("1. 카테고리 등록")
                println("2. 카테고리 삭제")
                println("3. 카테고리 수정")
                println("4. 이전")
                println()
                print("메뉴를 선택해주세요 : ")
                val inputNumberTemp = scanner.next()
                val inputNumber = inputNumberTemp.toInt()

                if (inputNumber !in 0..4) {
                    println("잘못 입력 하였습니다")
                } else {
                    return inputNumber
                }
            } catch (e: Exception) {
                println("CtgryMenuNumber오류")
                println("잘못 입력 하였습니다")
            }
        }
    }

    fun memoMenuNumber():Int {
        while (true) {
            try {
                println()
                print("1. 메모보기  2. 메모등록  3. 메모수정  4. 메모삭제  5. 이전 : ")
                println()
                val inputNumberTemp = scanner.next()
                val inputNumber = inputNumberTemp.toInt()

                if (inputNumber !in 1..5) {
                    println("잘못 입력 하였습니다")
                } else {
                    return inputNumber
                }
            } catch (e: Exception) {
                println("memoMenuNumber오류")
                println("잘못 입력 하였습니다")
            }
        }
    }
}

// 메인 메뉴 항목
enum class MainMenuItem(val menuNumber:Int){
    // 1. 메모 카테고리 관리
    MAIN_MENU_MEMO_CTGRY_MNGMNT(1),
    // 2. 메모 카테고리 선택
    MAIN_MENU_MEMO_CTGRY_CHOICE(2),
    // 3. 메모 내용 전체 보기
    MAIN_MENU_MEMO_SHOW_ALL(3),
    // 4. 종료
    MAIN_MENU_EXIT(4)
}
// 카테고리 메뉴 항목
enum class CtgryMenuItem(val menuNumber:Int){
    // 1. 카테고리 등록
    CTGRY_MENU_CREATE(1),
    // 2. 카테고리 삭제
    CTGRY_MENU_DELETE(2),
    // 3. 카테고리 수정
    CTGRY_MENU_UPDATE(3),
    // 4. 이전
    CTGRY_MENU_EXIT(4)
}
// 메모 메뉴 항목
enum class MemoMenuItem(val menuNumber:Int){
    // 1. 메모보기
    MEMO_MENU_READ(1),
    // 2. 메모등록
    MEMO_MENU_CREATE(2),
    // 3. 메모수정
    MEMO_MENU_UPDATE(3),
    // 4. 메모삭제
    MEMO_MENU_DELETE(4),
    // 5. 이전
    MEMO_MENU_EXIT(5)
}

class CategoryClass(var scanner: Scanner, val mainClass: MainClass) {

    var selectCtgryNumber = 0

    fun createCtgry() {
        println()
        while (true) {
            try {
                scanner.nextLine()

                print("등록할 카테고리 이름을 입력해주세요 : ")
                val ctgry = scanner.nextLine()

                val fileName = File("$ctgry.record")

                var memoClass = MainClass.MemoClass("", "")
                mainClass.memoList.add(memoClass)

                val fos = FileOutputStream(fileName)
                val oos = ObjectOutputStream(fos)

                oos.flush()
                oos.close()
                fos.close()
                break
            } catch (e: Exception) {
                println("createCtgry오류")
                println("잘못 입력 하였습니다")
            }
        }
    }

    fun readCtgryList() {
        println()
        mainClass.categoryFileList.clear()

        val dir = File(".")
        var fileList = dir.list()
        for (temp1 in fileList) {
            if (temp1.endsWith(".record")) {
                val temp2 = temp1.replace(".record", "")
                //record
                mainClass.categoryFileList.add(temp2)
            }
        }
        // 출력
        if (mainClass.categoryFileList.size == 0) println("등록된 카테고리가 없습니다.")
        else {
            for (idx1 in 1..mainClass.categoryFileList.size) {
                println("$idx1 : ${mainClass.categoryFileList[idx1 - 1]}")
            }
        }
    }

    fun deleteCtgry() {
        try {
            if (mainClass.categoryFileList.size == 0) println("등록된 카테고리가 없습니다.")
            else {
                print("삭제할 카테고리 번호를 입력해주세요 : ")
                val temp1 = scanner.next()
                val inputNumber = temp1.toInt()
                if (inputNumber !in 1..mainClass.categoryFileList.size) {
                    println()
                    println("잘못 입력하였습니다.")
                } else {
                    val fileName = "${mainClass.categoryFileList[inputNumber - 1]}.record"
                    File(fileName).delete()
                }
            }
        } catch (e: Exception) {
            println("deleteCtgry오류")
            println("잘못 입력하였습니다.")
        }
    }

    fun updateCtgry() {
        // 수정
        try {
            if (mainClass.categoryFileList.size == 0) println("등록된 카테고리가 없습니다.")
            else {
                print("수정할 카테고리 번호를 입력해주세요 : ")
                val temp1 = scanner.next()
                val inputNumber = temp1.toInt()
                if (inputNumber !in 1..mainClass.categoryFileList.size) {
                    println()
                    println("잘못 입력하였습니다.")
                } else {
                    scanner.nextLine()
                    print("${mainClass.categoryFileList[inputNumber - 1]} - > ")
                    val temp2 = scanner.nextLine()
                    val rename = Paths.get("$temp2.record")
                    val fileName = Paths.get("${mainClass.categoryFileList[inputNumber - 1]}.record")

                    // 파일명 바꾸기
                    Files.move(fileName, rename)
                }
            }
        } catch (e: Exception) {
            println("updateCtgry오류")
            println("잘못 입력하였습니다.")
        }
    }

    fun choiceCtgry(): Int {
        if (mainClass.categoryFileList.size == 0) {
            println("등록된 카테고리가 없습니다.")
            return selectCtgryNumber
        } else {
            while (true) {
                try {

                    print("선택할 카테고리 번호를 입력해주세요(0. 이전) : ")
                    val temp1 = scanner.next()
                    selectCtgryNumber = temp1.toInt()
                    if (mainClass.categoryFileList.size == 0 && selectCtgryNumber == 0) {
                        return selectCtgryNumber
                    } else if (selectCtgryNumber !in 0..mainClass.categoryFileList.size) {
                        println()
                        println("잘못 입력하였습니다.")
                    } else {
                        return selectCtgryNumber
                    }
                } catch (e: Exception) {
                    println("choiceCtgry오류")
                    println("잘못 입력하였습니다.")
                }
            }
        }
    }
}


class MemoClass(var scanner: Scanner, val mainClass: MainClass, val categoryClass: CategoryClass) {

    var selectMemoNumber = 0

    // 메모리스트를 출력
    fun readMemoList() {
        var idx = 1
        val ctgryName = "${mainClass.categoryFileList[categoryClass.selectCtgryNumber - 1]}.record"
        // 선택한 카테고리 파일의 내용 불러오기
        mainClass.getMemoData(ctgryName)
        println()
        if (mainClass.memoList.size == 0) println("등록된 메모가 없습니다.")
        else {
            for (memoClass in mainClass.memoList) {
                println("$idx : ${memoClass.name}")
                idx++
            }
        }
    }

    // 선택한 메모 출력
    fun readMemo() {
        println()
        while (true) {
            try {
                if (mainClass.memoList.size == 0) {
                    println("등록된 메모가 없습니다.")
                    break
                }
                print("확인할 메모의 번호를 입력해주세요 (0. 이전) : ")
                val temp1 = scanner.next()
                selectMemoNumber = temp1.toInt()
                if (selectMemoNumber !in 0..mainClass.memoList.size) {
                    println()
                    println("잘못 입력하였습니다.")
                } else if (selectMemoNumber == 0) {
                    break
                } else {
                    println("제목 : ${mainClass.memoList[selectMemoNumber - 1].name}")
                    println("내용 : ${mainClass.memoList[selectMemoNumber - 1].content}")
                    print("이전으로 돌아가려면 0을 입력해주세요 (0. 이전) : ")
                    val temp2 = scanner.next()
                    selectMemoNumber = temp2.toInt()
                    if (selectMemoNumber != 0) println("잘못 입력하였습니다")
                    else break
                }
            } catch (e: Exception) {
                println("readMemo오류")
                println("잘못 입력하였습니다.")
            }
        }
    }

    fun createMemo() {
        println()
        try {
            scanner.nextLine()
            print("메모 제목 : ")
            val memoName = scanner.nextLine()
            print("메모 내용 : ")
            val memoContent = scanner.nextLine()

            mainClass.memoList.clear()
            val ctgryName = "${mainClass.categoryFileList[categoryClass.selectCtgryNumber - 1]}.record"
            val file = File(ctgryName)
            mainClass.getMemoData(ctgryName)

            var memoClass = MainClass.MemoClass(memoName, memoContent)
            mainClass.memoList.add(memoClass)

            val fos = FileOutputStream(ctgryName)
            val oos = ObjectOutputStream(fos)

            for (memoClass in mainClass.memoList) {
                oos.writeObject(memoClass)
            }

            oos.flush()
            oos.close()
            fos.close()
        } catch (e: Exception) {
            println("createMemo오류")
            println("잘못 입력 하였습니다")
        }
    }

    fun updateMemo() {
        println()
        while (true) {
            try {
                if (mainClass.memoList.size == 0) {
                    println("등록된 메모가 없습니다.")
                    break
                }
                print("수정할 메모의 번호를 입력해주세요 (0. 이전) : ")
                val temp2 = scanner.next()
                selectMemoNumber = temp2.toInt()
                if (selectMemoNumber !in 0..mainClass.memoList.size) {
                    println()
                    println("잘못 입력하였습니다.")
                } else if (selectMemoNumber == 0) {
                    break
                } else {
                    scanner.nextLine()
                    println("제목 : ${mainClass.memoList[selectMemoNumber - 1].name}")
                    print("메모의 새로운 제목을 입력해주세요(0 입력시 무시합니다) : ")
                    var updateName = scanner.nextLine()
                    if (updateName.equals("0")) {
                        updateName = mainClass.memoList[selectMemoNumber - 1].name
                    }
                    println("내용 : ${mainClass.memoList[selectMemoNumber - 1].content}")
                    print("메모의 새로운 내용을 입력해주세요(0 입력시 무시합니다) : ")
                    var updateContent = scanner.nextLine()
                    if (updateContent.equals("0")) {
                        updateContent = mainClass.memoList[selectMemoNumber - 1].content
                    }
                    //
                    mainClass.memoList.clear()
                    val ctgryName = "${mainClass.categoryFileList[categoryClass.selectCtgryNumber - 1]}.record"
                    val file = File(ctgryName)
                    mainClass.getMemoData(ctgryName)

                    var memoClass = MainClass.MemoClass(updateName, updateContent)
                    mainClass.memoList[selectMemoNumber - 1] = memoClass

                    val fos = FileOutputStream(ctgryName)
                    val oos = ObjectOutputStream(fos)

                    for (memoClass in mainClass.memoList) {
                        oos.writeObject(memoClass)
                    }

                    oos.flush()
                    oos.close()
                    fos.close()
                    //
                    break
                }
            } catch (e: Exception) {
                println("updateMemo오류")
                println("잘못 입력하였습니다.")
            }
        }
    }

    fun deleteMemo() {
        println()
        scanner.nextLine()
        while (true) {
            try {
                if (mainClass.memoList.size == 0) {
                    println("등록된 메모가 없습니다.")
                    break
                }
                print("삭제할 메모의 번호를 입력해주세요 (0. 이전) : ")
                val temp1 = scanner.next()
                selectMemoNumber = temp1.toInt()
                if (selectMemoNumber !in 0..mainClass.memoList.size) {
                    println()
                    println("잘못 입력하였습니다.")
                } else if (selectMemoNumber == 0) {
                    break
                } else {
                    mainClass.memoList.clear()
                    val ctgryName = "${mainClass.categoryFileList[categoryClass.selectCtgryNumber - 1]}.record"
                    val file = File(ctgryName)
                    mainClass.getMemoData(ctgryName)

                    mainClass.memoList.removeAt(selectMemoNumber - 1)

                    val fos = FileOutputStream(ctgryName)
                    val oos = ObjectOutputStream(fos)

                    for (memoClass in mainClass.memoList) {
                        oos.writeObject(memoClass)
                    }

                    oos.flush()
                    oos.close()
                    fos.close()
                    //
                    break
                }
            } catch (e: Exception) {
                println("deleteMemo오류")
                println("잘못 입력하였습니다.")
            }
        }
    }

    fun showAllMemo() {
        println()
        mainClass.categoryFileList.clear()
        mainClass.memoList.clear()
        val dir = File(".")
        var fileList = dir.list()
        for (temp1 in fileList) {
            if (temp1.endsWith(".record")) {
                val temp2 = temp1.replace(".record", "")
                mainClass.categoryFileList.add(temp2)
            }
        }
        // 출력
        if (mainClass.categoryFileList.size == 0) println("등록된 카테고리가 없습니다.")
        else {
            for (idx1 in 1..mainClass.categoryFileList.size) {
                val ctgryName = "${mainClass.categoryFileList[idx1 - 1]}.record"
                // 선택한 카테고리 파일의 내용 불러오기
                mainClass.getMemoData(ctgryName)


                println("----------------------------------------------------------------")
                println("$idx1 : ${mainClass.categoryFileList[idx1 - 1]}")
                println("----------------------------------------------------------------")
                println()
                if (mainClass.memoList.size == 0) {
                    println("등록된 메모가 없습니다.")
                    println()
                }   else {
                    for (memoClass in mainClass.memoList) {
                        println("제목 : ${memoClass.name}")
                        println("내용 : ${memoClass.content}")
                        println()
                    }
                }

                mainClass.memoList.clear()
            }


        }

    }
}
