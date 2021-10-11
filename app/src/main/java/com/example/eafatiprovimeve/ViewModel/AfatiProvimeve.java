package com.example.eafatiprovimeve.ViewModel;



import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "afati_provimeve")
public class AfatiProvimeve {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private int viti;
    private int semestri;
    private String dita;
    private int diferenca;

    public AfatiProvimeve(String name, int viti, int semestri, String dita, int diferenca) {
        this.name = name;
        this.viti = viti;
        this.semestri = semestri;
        this.dita = dita;
        this.diferenca = diferenca;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getViti() {
        return viti;
    }

    public int getSemestri() {
        return semestri;
    }

    public String getDita() {
        return dita;
    }

    public int getDiferenca() {
        return diferenca;
    }
}
