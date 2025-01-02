import controller.WiseSayingController;
import exception.ErrorMessage;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static final WiseSayingController controller = new WiseSayingController(scanner);
    private static final Set<String> VALID_COMMANDS = new HashSet<>();
    private static final String HEADER = "== 명언 앱 ==";
    private static final String PROMPT = "명령) ";

    static {
        VALID_COMMANDS.add("등록");
        VALID_COMMANDS.add("목록");
        VALID_COMMANDS.add("빌드");
    }

    public void run() {
        printHeader();

        while (true) {
            printPrompt();
            String command = inputCommand();

            if (isValidCommand(command)) {
                controller.processCommand(command);
            } else if (command.equals("종료")) {
                close();
                break;
            } else {
                System.out.println(ErrorMessage.INVALID_COMMAND);
            }
        }
    }

    private void printHeader() {
        System.out.println(HEADER);
    }

    private void printPrompt() {
        System.out.print(PROMPT);
    }

    private String inputCommand() {
        return scanner.nextLine();
    }

    private boolean isValidCommand(String command) {
        return VALID_COMMANDS.contains(command) || command.matches("(수정|삭제)\\?id=\\d+");
    }

    private void close() {
        scanner.close();
    }
}