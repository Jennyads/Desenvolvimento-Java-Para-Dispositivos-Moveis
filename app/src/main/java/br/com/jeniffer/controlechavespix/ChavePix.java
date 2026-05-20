package br.com.jeniffer.controlechavespix;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chaves_pix")
public class ChavePix {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String apelido;
    private String chave;
    private String tipoKey;
    private String donoKey;
    private boolean favorita;

    public ChavePix(String apelido, String chave, String tipoKey, String donoKey, boolean favorita) {
        this.apelido = apelido;
        this.chave = chave;
        this.tipoKey = tipoKey;
        this.donoKey = donoKey;
        this.favorita = favorita;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getTipoKey() {
        return tipoKey;
    }

    public void setTipoKey(String tipoKey) {
        this.tipoKey = tipoKey;
    }

    public String getDonoKey() {
        return donoKey;
    }

    public void setDonoKey(String donoKey) {
        this.donoKey = donoKey;
    }

    public boolean isFavorita() {
        return favorita;
    }

    public void setFavorita(boolean favorita) {
        this.favorita = favorita;
    }
}