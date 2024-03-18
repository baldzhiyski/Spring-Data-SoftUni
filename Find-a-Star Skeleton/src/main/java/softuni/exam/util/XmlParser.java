package softuni.exam.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <T> T xmlParse(String filePath, Class<T> tClass) throws JAXBException;
}
