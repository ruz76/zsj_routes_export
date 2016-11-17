package cz.vsb.p4.testapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ruz76 on 10.11.2016.
 */
//@Service
public class Exporter {
    /*private String host;
    private String database;
    private String user;
    private String passwd;*/

    //@Autowired
    /*public Exporter(@Value("${jdbchost}") String host,
                    @Value("${jdbc.database}") String database,
                  @Value("${jdbc.name}") String jdbcName,
                  @Value("${jdbc.password}") String jdbcPassword) throws ClassNotFoundException {
        this.host = host;
        this.database = database;
        this.user = jdbcName;
        this.passwd = jdbcPassword;
    }

    public void exportToSHP(String table, String filename) throws Exception {
        Map params = new HashMap();
        params.put("dbtype", "postgis"); //must be postgis
        //the name or ip address of the machine running PostGIS
        params.put("host", this.host);
        //the port that PostGIS is running on (generally 5432)
        params.put("port", new Integer(5432));
        //the name of the database to connect to.
        params.put("database", this.database);
        params.put("user", this.user); //the user to connect with
        params.put("passwd", this.passwd); //the password of the user.

        System.out.println(params);

        FeatureSource fs = null;
        DataStore pgDatastore;
        pgDatastore = DataStoreFinder.getDataStore(params);
        fs = pgDatastore.getFeatureSource("imposm3_restaurants");
        int count = fs.getCount(Query.ALL);
        System.out.println("Count: " + count);
    }
    */
}
