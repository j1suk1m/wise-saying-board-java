import java.io.IOException;

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
}
