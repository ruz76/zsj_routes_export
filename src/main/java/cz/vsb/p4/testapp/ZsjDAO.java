package cz.vsb.p4.testapp;

import com.google.common.collect.Maps;
import org.h2.jdbcx.JdbcDataSource;
import org.postgresql.jdbc.PgSQLXML;
import org.postgresql.jdbc3.Jdbc3SimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by ruz76 on 26.10.2016.
 */
@Service
public class ZsjDAO {

    private final Jdbc3SimpleDataSource dataSource;

    @Autowired
    public ZsjDAO(@Value("${jdbc.host}") String jdbcHost,
                  @Value("${jdbc.database}") String jdbcDatabase,
                  @Value("${jdbc.name}") String jdbcName,
                  @Value("${jdbc.password}") String jdbcPassword) throws ClassNotFoundException {
        this.dataSource = new Jdbc3SimpleDataSource();
        dataSource.setPassword(jdbcPassword);
        dataSource.setUser(jdbcName);
        dataSource.setUrl("jdbc:postgresql://" +jdbcHost+ "/" + jdbcDatabase);
    }

    /**
     * Load all key values from database to memory
     *
     * @return map of key -> value
     */
    public Map<Integer, Zsj> loadKeyValues() throws Exception {
        Map<Integer, Zsj> map = Maps.newHashMap();
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement st = c.prepareStatement("select id, nazev from zsj")) {
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Integer key = rs.getInt(1);
                        String val = rs.getString(2);
                        Zsj zsj = new Zsj(key, val);
                        map.put(key, zsj);
                    }
                }
            }
        }
        return map;
    }

    public String createView(String sessionid, String zsjfrom) throws Exception {
        //public String createView(String sessionid, String[] zsjfrom, String[] zsjto) throws Exception {
        try (Connection c = dataSource.getConnection()) {
            //TODO
            //Create view for session
            //String from = "zsjidfrom IN (" + String.join(",", zsjfrom) + ")";
            String from = "zsjidfrom = '" + zsjfrom + "'";
            //String to = "zsjidto IN (" + String.join(",", zsjto) + ")";
            //String sql = "create or replace view r_" + sessionid.replaceAll("-", "_") + " as select * from routes where " + from + " and " + to;
            String sql = "create or replace view r_" + sessionid.replaceAll("-", "_") + " as select * from routes where " + from;
            System.out.println(sql);
            try (PreparedStatement st = c.prepareStatement(sql)) {
                st.executeUpdate();
            }
        }
        return "r_" + sessionid.replaceAll("-", "_");
    }
}
