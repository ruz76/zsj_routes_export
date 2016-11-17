package cz.vsb.p4.testapp;

/**
 * Created by ruz76 on 9.11.2016.
 */
public class Zsj {
    private int id;
    private String nazev;

    public Zsj(int id, String nazev) {
        this.id = id;
        this.nazev = nazev;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }
}
