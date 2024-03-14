package bg.softuni.jsonexercise.constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
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
    public static <T> void writeIntoXmlFile(T wrapper, Path path) throws IOException, JAXBException {
      File file = new File(String.valueOf(path));

      final JAXBContext context = JAXBContext.newInstance(wrapper.getClass());
      final Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

      marshaller.marshal(wrapper, file);
    }

}
