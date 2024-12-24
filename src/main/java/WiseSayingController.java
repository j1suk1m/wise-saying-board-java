import java.io.*;
import java.util.Scanner;

public class WiseSayingController {
    private final Scanner scanner;
    private static WiseSayingService service;
    private static final String[] PROMPTS = {"명언 : ", "작가 : "};
    private static final String LIST_HEADER = "번호 / 작가 / 명언\n----------------------";

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
        } else if (command.equals("목록")) {
            System.out.println(LIST_HEADER);
            WiseSayingListDto wiseSayingListDto = list();
            printWiseSayingList(wiseSayingListDto);
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

    private WiseSayingListDto list() {
        WiseSayingListDto wiseSayingListDto = null;
        
        try {
            wiseSayingListDto = service.list();
        } catch (IOException e) {
            System.out.println(ErrorMessage.LIST_READ_FAILURE);
        }
        
        return wiseSayingListDto;
    }

    private void printWiseSayingList(WiseSayingListDto wiseSayingListDto) {
        for (WiseSayingDto wiseSayingDto : wiseSayingListDto.getWiseSayingDtos()) {
            Long id = wiseSayingDto.getId();
            String author = wiseSayingDto.getAuthor();
            String content = wiseSayingDto.getContent();

            System.out.printf("%d / %s / %s%n", id, author, content);
        }
    }
}
