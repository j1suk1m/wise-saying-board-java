import java.util.List;

public class WiseSayingListDto {
    private final List<WiseSayingDto> wiseSayingDtos;

    public WiseSayingListDto(List<WiseSayingDto> wiseSayingDtos) {
        this.wiseSayingDtos = wiseSayingDtos;
    }

    public List<WiseSayingDto> getWiseSayingDtos() {
        return wiseSayingDtos;
    }
}