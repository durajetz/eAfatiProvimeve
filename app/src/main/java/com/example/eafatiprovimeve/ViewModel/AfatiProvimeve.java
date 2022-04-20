package com.example.eafatiprovimeve.ViewModel;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "afati_provimeve")
public class AfatiProvimeve {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String viti;
    private String semestri;
    private String dita;
    private int diferenca;
    private String salla;

    public AfatiProvimeve(String name, String viti, String semestri, String dita, int diferenca, String salla) {
        this.name = name;
        this.viti = viti;
        this.semestri = semestri;
        this.dita = dita;
        this.diferenca = diferenca;
        this.salla = salla;
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

    public String getViti() {
        return viti;
    }

    public String getSemestri() {
        return semestri;
    }

    public String getDita() {
        return dita;
    }

    public int getDiferenca() {
        return diferenca;
    }

    public String getSalla() {
        return salla;
    }
}
