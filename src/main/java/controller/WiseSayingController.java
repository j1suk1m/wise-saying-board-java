package controller;

import dto.WiseSayingCreationDto;
import dto.WiseSayingDto;
import dto.WiseSayingListDto;
import exception.ErrorMessage;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import service.WiseSayingService;

public class WiseSayingController {
    private final Scanner scanner;
    private static WiseSayingService service;
    private static final String[][] PROMPTS = {{"명언(기존) : ", "작가(기존) : "}, {"명언 : ", "작가 : "}};
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
        Pattern pattern = Pattern.compile("(수정|삭제)\\?id=(\\d+)");

        if (command.equals("등록")) {
            Long createdWiseSayingId = create();
            printSuccessMessage(createdWiseSayingId, command);
        } else if (command.equals("목록")) {
            System.out.println(LIST_HEADER);
            WiseSayingListDto wiseSayingListDto = list();
            printWiseSayingList(wiseSayingListDto);
        } else if (command.equals("빌드")) {
            build();
        } else {
            Matcher matcher = pattern.matcher(command);

            if (matcher.find()) {
                Long targetId = Long.parseLong(matcher.group(2));

                if (command.startsWith("삭제")) {
                    if (delete(targetId)) {
                        printSuccessMessage(targetId, matcher.group(1));
                    }
                } else if (command.startsWith("수정")) {
                    update(targetId);
                }
            }
        }
    }

    private String inputWiseSayingInfo(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private WiseSayingCreationDto getWiseSayingCreationDto() {
        String content = inputWiseSayingInfo(PROMPTS[Status.NEW.getId()][0]);
        String author = inputWiseSayingInfo(PROMPTS[Status.NEW.getId()][1]);

        return new WiseSayingCreationDto(content, author);
    }

    private static void printSuccessMessage(Long createdWiseSayingId, String commandType) {
        System.out.println(createdWiseSayingId + "번 명언이 " + commandType + "되었습니다.");
    }

    private void printCommandFailureMessage(Long id) {
        System.out.println(id + "번 명언은 존재하지 않습니다.");
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

    private boolean delete(Long targetId) {
        try {
            service.delete(targetId);
            return true;
        } catch (NoSuchFileException e) {
            printCommandFailureMessage(targetId);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private WiseSayingDto read(Long targetId) {
        WiseSayingDto wiseSayingDto = null;

        try {
            wiseSayingDto = service.findById(targetId);
        } catch (NoSuchFileException e) {
            printCommandFailureMessage(targetId);
        }  catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return wiseSayingDto;
    }

    private void update(Long targetId) {
        WiseSayingDto existingWiseSayingDto = read(targetId);

        if (existingWiseSayingDto != null) {
            System.out.print(PROMPTS[Status.OLD.getId()][0]);
            System.out.println(existingWiseSayingDto.getContent());
            String newContent = inputWiseSayingInfo(PROMPTS[Status.NEW.getId()][0]);

            System.out.print(PROMPTS[Status.OLD.getId()][1]);
            System.out.println(existingWiseSayingDto.getAuthor());
            String newAuthor = inputWiseSayingInfo(PROMPTS[Status.NEW.getId()][1]);

            WiseSayingDto newWiseSayingDto = new WiseSayingDto(targetId, newContent, newAuthor);

            try {
                service.update(newWiseSayingDto);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void build() {
        try {
            service.build();
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
