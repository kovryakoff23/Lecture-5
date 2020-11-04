import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class Positions {
    private int id;
    private int price;
    private int nomenclature;
    private int quantity;
    private int number;
    private static final Type listPositions = new TypeToken<ArrayList<Positions>>(){}.getType();

    public static ArrayList<Positions> read(String fileName) {
        try {
            return new Gson().fromJson(new BufferedReader(new FileReader(fileName)), listPositions);
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(int nomenclature) {
        this.nomenclature = nomenclature;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Positions(int id, int price, int nomenclature, int quantity, int number) {
        this.id = id;
        this.price = price;
        this.nomenclature = nomenclature;
        this.quantity = quantity;
        this.number = number;
    }
}
