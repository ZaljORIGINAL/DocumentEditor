package sample.Documents.DocumentsType;

import org.junit.Test;
import sample.Documents.Resource;
import sample.Documents.ResourcesType.PackingListResource;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class PackingListTest {

    @Test
    public void readResourceTest() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy/hh:mm:ss");
        Date dateToCheck = formatter.parse("6/12/1998/19:32:12\n");
        Resource actual = new PackingListResource(
                "A2",
                dateToCheck,
                "Alex",
                120f,
                "Dollar",
                73f,
                "Keyboard",
                1f);
        File file = new File("D:\\Программирование\\Java\\Практическая работа\\Максим технологии\\Task1\\src\\test\\java\\sample\\Documents\\DocumentsType", "fileToPackingListReadTest.txt");
        PackingList document = new PackingList(file);
        document.readFile();
        Resource resource = document.getResource();

        assertTrue(resource.equals(actual));
    }

    @Test
    public void writeResourceTest() throws Exception{
        File file = new File("D:\\Программирование\\Java\\Практическая работа\\Максим технологии\\Task1\\src\\test\\java\\sample\\Documents\\DocumentsType", "fileToPackingListWriteTest.txt");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy/HH:mm:ss");
        Date dateToCheck = formatter.parse("25/5/1958/19:32:12");
        Resource actual = new PackingListResource(
                "A12",
                dateToCheck,
                "Frenk",
                56f,
                "Dollar",
                73f,
                "Fun",
                1f);
        PackingList document = new PackingList(file);
        document.setResource(actual);
        document.writeFile();
        document.setResource(null);
        document.readFile();
        Resource resource = document.getResource();

        assertTrue(resource.equals(actual));
    }
}