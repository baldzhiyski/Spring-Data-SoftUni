package bg.softuni.jsonexercisesecondtask.constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public enum Utils {
    ;

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static  final ModelMapper MAPPER = new ModelMapper();
    public static <T> void writeJsonOnFile(List<T> objects, Path path) throws IOException {
        final FileWriter fileWriter = new FileWriter(String.valueOf(path));

        GSON.toJson(objects, fileWriter);

        fileWriter.flush();
        fileWriter.close();
    }
}