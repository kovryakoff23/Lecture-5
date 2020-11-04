import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class Nomenclature {
    private String name;
    private int number;
    private static final Type listNomenclature = new TypeToken<ArrayList<Nomenclature>>(){}.getType();

    public static ArrayList<Nomenclature> read(String fileName) {
        try {
            return new Gson().fromJson(new BufferedReader(new FileReader(fileName)), listNomenclature);
        } catch (
                FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Nomenclature(String name, int number) {
        this.name = name;
        this.number = number;
    }
}

