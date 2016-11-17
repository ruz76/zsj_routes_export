package cz.vsb.p4.testapp;

import cz.vsb.p4.testapp.async.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

/**
 * This is test controller to demonstrate spring MVC capabilities
 */
@Controller
public class ZsjController {

    private final ZsjRepository zsjRepository;
    private final JobService exporter;


    @Autowired
    public ZsjController(ZsjRepository zsjRepository, JobService exporter) {
        //this.exporter = exporter;
        this.zsjRepository = zsjRepository;
        this.exporter = exporter;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String listAll(Model model) {
        Map<Integer, Zsj> zsj = zsjRepository.getAll();
        model.addAttribute("zsj", zsj);
        return "/index";
    }

    /*@RequestMapping(method = RequestMethod.GET, path = "/status")
    public String getStatus(Model model, @RequestParam(value = "sessionid") String sessionid) {
        model.addAttribute("sessionid", sessionid);
        return "/status";
    }*/

    @RequestMapping(method = RequestMethod.POST, path = "/export")
    //public String export(Model model, @RequestParam(value = "zsjfrom") String[] zsjfrom,
    //                           @RequestParam(value = "zsjto") String[] zsjto) throws Exception {
    public String export(Model model, @RequestParam(value = "zsjfrom") String zsjfrom) throws Exception {

        UUID sessionid = UUID.randomUUID();

        String view = zsjRepository.createView(sessionid.toString(), zsjfrom.split(":")[1]);
        //exporter.exportToSHP(view, view + ".shp");
        exporter.startJob(view);
        //Ogr2ogr o = new Ogr2ogr(view);
        //o.start();
        model.addAttribute("zsjfrom", zsjfrom);
        //model.addAttribute("zsjto", zsjto);
        model.addAttribute("sessionid", view);
        return "/export";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/echo")
    public String echo(Model model) throws Exception {
        String s[] = new String[10];
        for (int i=0; i<10; i++) {
            s[i] = "String " + i;
        }
        model.addAttribute("s", s);
        return "/echo";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/status")
    public String getStatus(Model model, @RequestParam(value = "sessionid") String sessionid) throws Exception {
        model.addAttribute("status", exporter.getStatus(sessionid));
        model.addAttribute("sessionid", sessionid);
        return "/status";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/download")
    public void doDownload(HttpServletRequest request,
                           HttpServletResponse response, @RequestParam(value = "sessionid") String sessionid) throws IOException {

        // get absolute path of the application
        ServletContext context = request.getServletContext();

        Utils u = new Utils();
        File downloadFile = new File(u.getPath() +  "/" + sessionid + ".zip");

        FileInputStream inputStream = new FileInputStream(downloadFile);

        // get MIME type of the file
        String mimeType = context.getMimeType(downloadFile.getAbsolutePath());
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();

    }

}
