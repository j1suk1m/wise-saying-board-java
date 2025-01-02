package exception;

public class ErrorMessage {
    public static final String INVALID_COMMAND = "잘못된 명령입니다.";
    public static final String INVALID_SPECIAL_CHARACTERS = "명언 내용과 작가 이름에는 특수문자가 포함될 수 없습니다.";
    public static final String DIRECTORY_CREATION_FAILURE = "디렉토리를 생성할 수 없습니다.";
    public static final String FILE_CREATION_FAILURE = "파일을 생성할 수 없습니다.";
    public static final String FILE_WRITE_FAILURE = "파일에 쓰는 도중 오류가 발생했습니다.";
    public static final String LIST_READ_FAILURE = "목록을 읽는 도중 오류가 발생했습니다.";
    public static final String FILE_NOT_FOUND = "파일이 존재하지 않습니다.";
    public static final String FILE_DELETION_FAILURE = "파일을 삭제할 수 없습니다.";

    private ErrorMessage() {}
}