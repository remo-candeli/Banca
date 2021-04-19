package org.corso.banca;

import java.util.HashMap;
import java.util.Map;

public class Banca {

    private Map<String, ContoCorrente> contiCorrenti;
    private String nome;
    private String codiceABI;


    public Banca(String nome, String codiceABI) {
        this.contiCorrenti = new HashMap<>();
        this.nome = nome;
        this.codiceABI = codiceABI;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodiceABI() {
        return codiceABI;
    }

    public void setCodiceABI(String codiceABI) {
        this.codiceABI = codiceABI;
    }

    public Map<String, ContoCorrente> getContiCorrenti() {
        return contiCorrenti;
    }
}
