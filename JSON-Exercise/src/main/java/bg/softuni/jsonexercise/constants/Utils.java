package bg.softuni.jsonexercise.constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public enum Utils {
    ;

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static <T> void writeJsonOnFile(List<T> objects, Path path) throws IOException {
        final FileWriter fileWriter = new FileWriter(String.valueOf(path));

        GSON.toJson(objects, fileWriter);

        fileWriter.flush();
        fileWriter.close();
    }

    public static <T> void writeIntoJsonFile(T object, Path path) throws IOException {
        final FileWriter fileWriter = new FileWriter(path.toFile());

        GSON.toJson(object, fileWriter);

        fileWriter.flush();
        fileWriter.close();
    }
}