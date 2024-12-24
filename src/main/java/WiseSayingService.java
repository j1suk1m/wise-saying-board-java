import java.io.IOException;
import java.util.List;

public class WiseSayingService {
    private static WiseSayingRepository repository;

    public WiseSayingService() throws IOException {
        repository = WiseSayingRepository.getInstance();
    }

    public Long create(WiseSayingCreationDto wiseSayingCreationDto) throws IOException {
        Long currentId = repository.findLastId() + 1;
        WiseSaying wiseSaying = new WiseSaying(currentId, wiseSayingCreationDto);

        repository.save(wiseSaying);
        repository.updateLastId(currentId);

        return currentId;
    }

    public WiseSayingListDto list() throws IOException {
        List<WiseSaying> wiseSayings = repository.findAll();

        List<WiseSayingDto> wiseSayingDtos = wiseSayings.stream()
                                                        .map(WiseSayingDto::new)
                                                        .toList();

        return new WiseSayingListDto(wiseSayingDtos);
    }
}