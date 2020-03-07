package chrome_extencion;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterCustom {

    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public FileWriterCustom(String text) {
        this.text = text;
    }

    public void writeFileOutputStream(String fileName) throws IOException
    {

        FileOutputStream outputStream = new FileOutputStream(fileName);
        byte[] strToBytes = this.text.getBytes();
        outputStream.write(strToBytes);

        outputStream.close();
    }

    public void appendToFile(String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(this.text);
            bufferWriter.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

}
