package sample;

import javax.print.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Printer {

    private PrintService[] printService;
    private DocPrintJob docPrintJob;
    private DocFlavor flavor;
    private Doc doc;

    public Printer() {
        flavor = DocFlavor.INPUT_STREAM.PNG;
        printService = PrintServiceLookup.lookupPrintServices(flavor, null);
    }

    public void print() throws PrintException {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("").getAbsoluteFile() + "\\src\\forPrint.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (inputStream == null) {
            throw new IllegalStateException("Ошибка сохранения перед печатью");
        }
        doc = new SimpleDoc(inputStream, flavor, null);
        if (printService.length > 0) {
            DocPrintJob job = printService[0].createPrintJob();
            job.print(doc, null);
        }
    }
}
