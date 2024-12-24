import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WiseSayingRepository {
    private static WiseSayingRepository instance;
    private static final String ROOT_DIRECTORY_PATH = System.getProperty("user.dir");
    private static final String DB_DIRECTORY_PATH = ROOT_DIRECTORY_PATH + File.separator + "db" + File.separator + "wiseSaying";
    private static final String LAST_ID_FILE_PATH = DB_DIRECTORY_PATH + File.separator + "lastId.txt";
    private static File lastIdFile;

    public static WiseSayingRepository getInstance() throws IOException {
        if (instance == null) {
            instance = new WiseSayingRepository();
        }

        return instance;
    }

    public Long findLastId() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(lastIdFile))) {
            return Long.parseLong(reader.readLine());
        }
    }

    public void save(WiseSaying wiseSaying) throws IOException {
        String jsonContent = wiseSaying.toJson();
        String wiseSayingFilePath = DB_DIRECTORY_PATH + File.separator + wiseSaying.getId() + ".json";

        File wiseSayingFile = createFileIfAbsent(wiseSayingFilePath);
        write(wiseSayingFile, jsonContent);
    }

    public void updateLastId(Long id) throws IOException {
        write(lastIdFile, id.toString());
    }

    public List<WiseSaying> findAll() throws IOException {
        Long lastId = findLastId();
        List<WiseSaying> wiseSayings = new ArrayList<>();

        for (Long currentId = lastId; currentId >= 1; currentId--) {
            String filePath = DB_DIRECTORY_PATH + File.separator + currentId + ".json";
            File file = new File(filePath);

            if (file.exists()) {
                WiseSaying wiseSaying = readJsonFromFile(file);

                if (wiseSaying != null) {
                    wiseSayings.add(wiseSaying);
                }
            }
        }

        return wiseSayings;
    }

    public void deleteById(Long targetId) {
        String filePath = DB_DIRECTORY_PATH + File.separator + targetId + ".json";
        File file = new File(filePath);
        file.delete();
    }

    private WiseSaying readJsonFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String json = reader.lines().collect(Collectors.joining());
            return parseWiseSayingFromJson(json);
        }
    }

    private WiseSaying parseWiseSayingFromJson(String json) {
        try {
            Long id = Long.parseLong(extractValue(json, "id"));
            String content = extractValue(json, "content");
            String author = extractValue(json, "author");
            return new WiseSaying(id, content, author);
        } catch (Exception e) {
            return null;
        }
    }

    private String extractValue(String json, String key) {
        String searchKey = "\"" + key + "\": \"";
        int start = json.indexOf(searchKey) + searchKey.length();
        int end = json.indexOf("\"", start);

        return json.substring(start, end);
    }

    private WiseSayingRepository() throws IOException {
        initialize();
    }

    private static void initialize() throws IOException {
        createDirectoryIfAbsent();
        lastIdFile = createFileIfAbsent(LAST_ID_FILE_PATH);

        if (lastIdFile.length() == 0) {
            write(lastIdFile, "0");
        }
    }

    private static void createDirectoryIfAbsent() throws IOException {
        File directory = new File(DB_DIRECTORY_PATH);

        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException(ErrorMessage.DIRECTORY_CREATION_FAILURE);
        }
    }

    private static File createFileIfAbsent(String filePath) throws IOException {
        File file = new File(filePath);

        if (!file.exists() && !file.createNewFile()) {
            throw new IOException(ErrorMessage.FILE_CREATION_FAILURE);
        }

        return file;
    }

    private static void write(File file, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            throw new IOException(ErrorMessage.FILE_WRITE_FAILURE);
        }
    }
}