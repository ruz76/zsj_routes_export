package cz.vsb.p4.testapp;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by ruz76 on 2.11.2016.
 */
@Service
public class ZsjRepository {

    private final ZsjDAO dao;

    @Autowired
    public ZsjRepository(ZsjDAO dao) {
        System.out.println("Created ZsjRepository " + this);
        this.dao = dao;
    }

    @PostConstruct
    public void postConstruct() throws Exception {
        Map<Integer, Zsj> vals = dao.loadKeyValues();
//        vals.entrySet().forEach(entry -> counter.put(entry.getKey(), entry.getValue()));
        for (Map.Entry<Integer, Zsj> entry : vals.entrySet()) {
            zsj.put(entry.getKey(), entry.getValue());
        }
    }

    private final Map<Integer, Zsj> zsj = Maps.newHashMap();

    public Zsj getZsj(Integer id) {
        return zsj.get(id);
    }

    public Map<Integer, Zsj> getAll() {
        return zsj;
    }

    public String createView(String sessionid, String zsjfrom) throws Exception {
        return dao.createView(sessionid, zsjfrom);
    }
}
