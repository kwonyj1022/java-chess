package chess.view;

import chess.dto.request.CommandDto;

import java.util.List;
import java.util.Scanner;

public final class InputView {

    private static final String DELIMITER = " ";
    private static final Scanner scanner = new Scanner(System.in);
    private static final int START_END_COMMAND_NECESSARY_WORD = 1;
    private static final int MOVE_COMMAND_NECESSARY_WORD = 3;

    private InputView() {
    }

    public static CommandDto readInitialCommand() {
        List<String> input = readUserInput();
        GameCommand gameCommand = recognizeGameCommand(input);
        while (gameCommand == GameCommand.MOVE) {
            OutputView.printNotStartedGameMessage();
            input = readUserInput();
            gameCommand = recognizeGameCommand(input);
        }
        return CommandDto.of(gameCommand, input);
    }

    private static List<String> readUserInput() {
        String input = scanner.nextLine();
        return List.of(input.split(DELIMITER));
    }

    private static GameCommand recognizeGameCommand(List<String> input) {
        return GameCommand.of(input.get(0));
    }

    public static CommandDto readPlayingCommand() {
        List<String> input = readUserInput();
        if (input.size() != START_END_COMMAND_NECESSARY_WORD && input.size() != MOVE_COMMAND_NECESSARY_WORD)  {
            throw new IllegalArgumentException("[ERROR] 명령어 형식이 올바르지 않습니다.");
        }
        GameCommand gameCommand = recognizeGameCommand(input);
        if (gameCommand == GameCommand.START) {
            throw new IllegalArgumentException("[ERROR] 게임 진행 중에는 move와 end 명령어만 입력 가능합니다.");
        }
        return CommandDto.of(gameCommand, input);
    }
}
