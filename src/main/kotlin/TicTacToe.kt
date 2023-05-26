import java.util.Scanner

fun main() {
    // 객체 생성
    val gameBoard = GameBoard()
    while (true) {
        // 턴이 바뀔 때 처리
        gameBoard.nextTurn()
        // 게임판 출력
        gameBoard.printBoard()
        // 플레이어 수 놓기
        gameBoard.playerInput()
        // 게임판 승리 검사
        val chk = gameBoard.checkWinner()
        // 승리조건이 맞다면 게임종료
        if (chk == true) {
            gameBoard.printBoard()
            break
        }
        // 승리조건에 맞지 않다면 다음 플레이어 변경
        gameBoard.changePlayer()
    }
}

class GameBoard {

    val scanner = Scanner(System.`in`)

    // 턴 번호
    var turnNumber = 0

    // 보드 배열
    val gameBoardData = arrayOf(
        arrayOf(" ", " ", " "),
        arrayOf(" ", " ", " "),
        arrayOf(" ", " ", " ")
    )


    // 플레이어 객체 생성
    val player1 = Player("Player 1", "O")
    val player2 = Player("Player 2", "X")

    // 첫번째 사용자 설정
    var currentPlayer = player1

    // 턴이 바뀔 때 카운팅
    fun nextTurn() {
        turnNumber++
    }

    // 보드를 출력한다.
    // 보드를 출력한다.
    fun printBoard() {
//        println()
//        println("${turnNumber}번째 턴")
//        println("  0 1 2")
//        println("0 ${gameBoardData[0][0]}|${gameBoardData[0][1]}|${gameBoardData[0][2]}")
//        println("  -+-+-")
//        println("1 ${gameBoardData[1][0]}|${gameBoardData[1][1]}|${gameBoardData[1][2]}")
//        println("  -+-+-")
//        println("2 ${gameBoardData[2][0]}|${gameBoardData[2][1]}|${gameBoardData[2][2]}")

        println()
        println("${turnNumber}번째 턴")
        println("  0 1 2")
        for (rowIdx in 0 until gameBoardData.size) {
            print("$rowIdx ")
            for (colIdx in 0 until gameBoardData[rowIdx].size) {
                print(gameBoardData[rowIdx][colIdx])
                // 맨 오른쪽 생성방지
                if (colIdx < gameBoardData[rowIdx].size - 1) {
                    print("|")
                }
            }
            println()
            // 맨 아래 생성방지
            if (rowIdx < gameBoardData.size - 1) {
                println("  -+-+-")
            }
        }
    }

    // 플레이어가 보드에 마크표시
    fun playerInput() {
        while (true) {
            println()
            print("${currentPlayer.playName} turn (행,열) : ")
            // 현재 플레이어가 마크를 표시
            val newMarkPosition = currentPlayer.setPlayerMark()
            // 플레이어가 마크를 표시 할 위치가 가능한지 판별
            val check = isMarkPositionAvailable(newMarkPosition)

            // 플레이어가 표시 할 수 있는 수라면 반복 중단
            if (check == true) {
                // 플레이어가 선택한 위치에 마크표시
                val temp = newMarkPosition.split(",")
                val row = temp[0].toInt()
                val col = temp[1].toInt()
                // 위치값 게임판배열에 저장
                gameBoardData[row][col] = currentPlayer.playMark
                break
            } else (
                    println("놓을 자리를 다시 선택해 주세요")
                    )
        }
    }

    // 사용자 교체
    fun changePlayer() {
        if (currentPlayer == player1) currentPlayer = player2
        else currentPlayer = player1
    }

    // 사용자가 놓은 수가 놓을 수 있는 수인지 검사
    // 표시할 위치값을 받아 가능한지 Boolean타입으로 반환
    fun isMarkPositionAvailable(newMarkPosition: String): Boolean {
        // 행과 열로 나눔
        val temp1 = newMarkPosition.split(",")
        val row = temp1[0].toInt()
        val col = temp1[1].toInt()

        // 입력값이 게임판 범위를 벗어 나는지 확인
        if (row < 0 || row > 2) {
            return false
        }
        if (col < 0 || col > 2) {
            return false
        }

        // 현재 위치에 다른 수가 놓여 있는지
        if (gameBoardData[row][col] != " ") {
            return false
        }
        // 모든 조건에 부합하지 않다면 가능하다는 true 반환
        return true
    }

    // 승리의 유무를 반환
    fun checkWinner(): Boolean {
        // 행 검사
        for (row in gameBoardData) {
            // 가로줄이 비어있지 않고 저장된 값들이 같다면
            if (row[0] != " " && row[0] == row[1] && row[1] == row[2]) {
                // 표시된 마크가 어느 플레이어의 마크인지
                if (row[0] == player1.playMark) {
                    println("${player1.playName} 승리")
                    // 승리(true) 반환
                    return true
                } else {
                    println("${player2.playName} 승리")
                    return true
                }
            }
        }
        // 열 검사
        for (col in 0 until gameBoardData[0].size) {
            // 세로줄이 비어있지 않고 저장된 값들이 같다면
            if (gameBoardData[0][col] != " " && gameBoardData[0][col] == gameBoardData[1][col] && gameBoardData[1][col] == gameBoardData[2][col]) {
                if (gameBoardData[0][col] == player1.playMark) {
                    println("${player1.playName} 승리")
                    return true
                } else {
                    println("${player2.playName} 승리")
                    return true
                }
            }
        }

        // (\)대각선 검사
        if (gameBoardData[0][0] != " " && gameBoardData[0][0] == gameBoardData[1][1] &&
            gameBoardData[1][1] == gameBoardData[2][2]
        ) {
            if (gameBoardData[0][0] == player1.playMark) {
                println("${player1.playName} 승리!!")
                return true
            } else {
                println("${player2.playName} 승리!!")
                return true
            }
        }
        // (/)대각선 검사
        if (gameBoardData[0][2] != " " && gameBoardData[0][2] == gameBoardData[1][1] &&
            gameBoardData[1][1] == gameBoardData[2][0]
        ) {
            if (gameBoardData[0][0] == player1.playMark) {
                println("${player1.playName} 승리")
                return true
            } else {
                println("${player2.playName} 승리")
                return true
            }
        }
        // 모든 승리조건이 맞지 않다면 게임을 계속 진행하기 위해 false 반환
        return false
    }
}

// 플레이어의 이름, 사용할 마크를 주생성자
data class Player(var playName: String, var playMark: String) {

    val scanner = Scanner(System.`in`)

    // 자기 차례에 게임 진행
    fun setPlayerMark(): String {
        try {
            // 입력 방식이 x,y 인지 유효성 검사
            val newMarkPosition = scanner.next()
            val temp = newMarkPosition.split(",")
            val x = temp[0].toInt()
            val y = temp[1].toInt()
            return newMarkPosition
        } catch (e: Exception) {
            println("형식에 맞춰 다시 입력해주세요")
            return "0"
        }
    }
}
