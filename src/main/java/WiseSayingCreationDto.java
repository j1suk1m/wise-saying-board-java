public class WiseSayingCreationDto {
    private final String content;
    private final String author;

    WiseSayingCreationDto(String content, String author) {
        validate(content, author);
        this.content = content;
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    private void validate(String content, String author) {
        String regex = "[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s.,?!]";

        if (content.matches(regex) || author.matches(regex)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_SPECIAL_CHARACTERS);
        }
    }
}