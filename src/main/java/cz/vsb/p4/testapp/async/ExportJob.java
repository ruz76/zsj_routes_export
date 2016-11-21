package cz.vsb.p4.testapp.async;

import com.google.common.util.concurrent.ListenableFuture;
import cz.vsb.p4.testapp.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ruz76 on 16.11.2016.
 */
public class ExportJob implements Callable<String> {
    private String view;

    private final String jdbcHost;
    private final String jdbcDatabase;
    private final String jdbcName;
    private final String jdbcPassword;

    private final AtomicInteger status = new AtomicInteger();


    public ExportJob(String view, String jdbcHost, String jdbcDatabase, String jdbcName, String jdbcPassword) {
        this.view = view;
        this.jdbcHost = jdbcHost;
        this.jdbcDatabase = jdbcDatabase;
        this.jdbcName = jdbcName;
        this.jdbcPassword = jdbcPassword;
    }

    public int getStatus() {
        return status.get();
    }

    @Override
    public String call() throws Exception {
        try {
            //TODO
            //Add code for running OGR2OGR or use GeoTools foo writing to SHP
            //ogr2ogr -f "ESRI Shapefile" mydata.shp PG:"host=myhost user=myloginname dbname=mydbname password=mypassword" "mytable"
            String params[] = new String[6];
            params[0] = "-f";
            params[1] = "ESRI Shapefile";
            Utils u = new Utils();
            File f = new File(u.getPath() +  "/" + view + ".shp");
            params[2] = f.getAbsolutePath();
            params[3] = "PG:host=" +jdbcHost+ " user=" +jdbcName+ " dbname=" +jdbcDatabase+ " password=" + jdbcPassword;
            params[4] = this.view;
            params[5] = "-progress";
            for (int i=0; i<params.length; i++) {
                System.out.print(params[i] + " ");
            }
            System.out.println();
            Process process = new ProcessBuilder("ogr2ogr", params[0], params[1], params[2], params[3], params[4], params[5]).start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            int r;
            char[] ret = new char[1];
            while ((r = isr.read()) != -1) {
                ret[0] = (char) r;
                String regex = "[0-9]";
                String data = new String(ret);
                if (data.matches(regex)) {
                    if (!data.equals("0")) {
                	    status.addAndGet(10);
                    }
                }
            }
            System.out.println("Program terminated!");
            u.makeZIP(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
