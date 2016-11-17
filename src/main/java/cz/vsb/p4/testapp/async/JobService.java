package cz.vsb.p4.testapp.async;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
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
    public void startJob(String view) {
        ExportJob ej = new ExportJob(view);
        ListenableFuture<String> future = executorService.submit(ej);
        jobs.put(view, future);
    }

    public int getStatus(String view) {
        ListenableFuture<String> future = jobs.get(view);
        if (future != null) {
            if (future.isDone()) {
                return 100;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }
}
