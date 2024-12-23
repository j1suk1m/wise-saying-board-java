import java.io.*;
import java.util.Scanner;

public class WiseSayingController {
    private final Scanner scanner;
    private static WiseSayingService service;
    private static final String[] PROMPTS = {"명언 : ", "작가 : "};

    static {
        try {
            service = new WiseSayingService();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public WiseSayingController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void processCommand(String command) {
        if (command.equals("등록")) {
            Long createdWiseSayingId = create();
            printSuccessMessage(createdWiseSayingId);
        }
    }

    private String inputWiseSayingInfo(String prompt) {
        System.out.print(prompt);

        return scanner.nextLine();
    }

    private WiseSayingCreationDto getWiseSayingCreationDto() {
        String content = inputWiseSayingInfo(PROMPTS[0]);
        String author = inputWiseSayingInfo(PROMPTS[1]);

        return new WiseSayingCreationDto(content, author);
    }

    private static void printSuccessMessage(Long createdWiseSayingId) {
        System.out.println(createdWiseSayingId + "번 명언이 등록되었습니다.");
    }

    private Long create() {
        Long id = 0L;
        WiseSayingCreationDto wiseSayingCreationDto = getWiseSayingCreationDto();

        try {
            id = service.create(wiseSayingCreationDto);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return id;
    }
}
