package org.corso.banca.models;

import java.util.HashMap;
import java.util.Map;

public class Banca {

    private Map<String, ContoCorrente> contiCorrenti;
    private String nome;
    private String codiceABI;
    private boolean notificaCliente;
    private String indirizzoEmailBanca;


    public Banca(String nome, String codiceABI, String indirizzoEmailBanca) {
        this.contiCorrenti = new HashMap<>();
        this.nome = nome;
        this.codiceABI = codiceABI;
        this.notificaCliente = false;
        this.indirizzoEmailBanca = indirizzoEmailBanca;
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

    public boolean isNotificaCliente() {
        return notificaCliente;
    }

    public void setNotificaCliente(boolean notificaCliente) {
        this.notificaCliente = notificaCliente;
    }

    public ContoCorrente getContoCorrenteById(String nContoCorrente) {
        return contiCorrenti.get(nContoCorrente);
    }

    public String getIndirizzoEmailBanca() {
        return indirizzoEmailBanca;
    }
}
