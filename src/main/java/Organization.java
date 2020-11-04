import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class Organization {
    private int id;
   private String Name;
   private int inn;
   private int account;
   private static final Type listOrganization = new TypeToken<ArrayList<Organization>>(){}.getType();

    public static ArrayList<Organization> read(String fileName) {
        try {
            return new Gson().fromJson(new BufferedReader(new FileReader(fileName)), listOrganization);
        } catch (
                FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getInn() {
        return inn;
    }

    public void setInn(int inn) {
        this.inn = inn;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public Organization(int id, String name, int inn, int account) {
        this.id = id;
        Name = name;
        this.inn = inn;
        this.account = account;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", inn=" + inn +
                ", account=" + account +
                '}';
    }
}
