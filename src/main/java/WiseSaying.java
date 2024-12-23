public class WiseSaying {
    private final Long id;
    private final String content;
    private final String author;

    WiseSaying(Long id, WiseSayingCreationDto wiseSayingCreationDto) {
        this.id = id;
        this.content = wiseSayingCreationDto.getContent();
        this.author = wiseSayingCreationDto.getAuthor();
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
}