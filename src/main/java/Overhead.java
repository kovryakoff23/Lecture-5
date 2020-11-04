import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.ArrayList;

public final class Overhead {
    private int number;
    private Date date;
    private int organization;
    private static final Type listOverhead = new TypeToken<ArrayList<Overhead>>(){}.getType();

    public static ArrayList<Overhead> read(String fileName) {
        try {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .setPrettyPrinting()
                    .create();
            return gson.fromJson(new BufferedReader(new FileReader(fileName)), listOverhead);
        } catch (
                FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getOrganization() {
        return organization;
    }

    public void setOrganization(int organization) {
        this.organization = organization;
    }

    public Overhead(int number, Date date, int organization) {
        this.number = number;
        this.date = date;
        this.organization = organization;
    }
}