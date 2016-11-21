package cz.vsb.p4.testapp.async;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.postgresql.jdbc3.Jdbc3SimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ruz76 on 16.11.2016.
 */
@Service
public class JobService {

    private final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));

    private final Map<String,ListenableFuture> jobs = Maps.newConcurrentMap();
    private final Map<String,ExportJob> jobs2 = Maps.newConcurrentMap();

    private final String jdbcHost;
    private final String jdbcDatabase;
    private final String jdbcName;
    private final String jdbcPassword;

    @Autowired
    public JobService(@Value("${jdbc.host}") String jdbcHost,
                  @Value("${jdbc.database}") String jdbcDatabase,
                  @Value("${jdbc.name}") String jdbcName,
                  @Value("${jdbc.password}") String jdbcPassword){
        this.jdbcHost = jdbcHost;
        this.jdbcDatabase = jdbcDatabase;
        this.jdbcName = jdbcName;
        this.jdbcPassword = jdbcPassword;
    }

    public void startJob(String view) {
        ExportJob ej = new ExportJob(view, jdbcHost, jdbcDatabase, jdbcName, jdbcPassword);
        ListenableFuture<String> future = executorService.submit(ej);
        jobs.put(view, future);
        jobs2.put(view, ej);
    }

    public int getStatus(String view) {
        ListenableFuture<String> future = jobs.get(view);
        ExportJob ej = jobs2.get(view);
        if (future != null) {
            if (future.isDone()) {
                return 100;
            } else {
                if (ej != null) {
                    return ej.getStatus();
                } else {
                    return -1;
                }
            }
        } else {
            return -1;
        }
    }
}
