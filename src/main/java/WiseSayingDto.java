public class WiseSayingDto {
    private final Long id;
    private final String content;
    private final String author;

    public WiseSayingDto(WiseSaying wiseSaying) {
        this.id = wiseSaying.getId();
        this.content = wiseSaying.getContent();
        this.author = wiseSaying.getAuthor();
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