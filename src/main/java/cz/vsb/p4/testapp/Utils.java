package cz.vsb.p4.testapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationTemp;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by jencek on 14.11.16.
 */
@Service
public class Utils {
    private ApplicationTemp temp;

    public Utils() {
        this.temp = new ApplicationTemp();
    }

    public String getPath() {
        return temp.getDir().getAbsolutePath();
    }

    public void makeZIP(String filename) throws Exception {
        byte[] buffer = new byte[1024];
        FileOutputStream fos = new FileOutputStream(getPath() + "/" + filename + ".zip");
        ZipOutputStream zos = new ZipOutputStream(fos);
        String suffixes[] = {"shp", "shx", "dbf", "prj"};
        for (int i=0; i<suffixes.length; i++) {
            ZipEntry ze= new ZipEntry(filename + "." + suffixes[i]);

            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(getPath() + "/" + filename + "." + suffixes[i]);

            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            in.close();
            zos.closeEntry();
        }
        zos.close();
        System.out.println("Done");
    }
}
