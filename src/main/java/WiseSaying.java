public class WiseSaying {
    private final Long id;
    private final String content;
    private final String author;

    WiseSaying(WiseSayingDto wiseSayingDto) {
        this.id = wiseSayingDto.getId();
        this.content = wiseSayingDto.getContent();
        this.author = wiseSayingDto.getAuthor();
    }

    WiseSaying(Long id, WiseSayingCreationDto wiseSayingCreationDto) {
        this.id = id;
        this.content = wiseSayingCreationDto.getContent();
        this.author = wiseSayingCreationDto.getAuthor();
    }

    WiseSaying(Long id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public String toJson() {
        return "{\n" +
                "  \"id\": \"" + id + "\",\n" +
                "  \"content\": \"" + content + "\",\n" +
                "  \"author\": \"" + author + "\"\n" +
                "}";
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}