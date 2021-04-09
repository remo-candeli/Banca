package org.corso.banca;

public class ContoCorrente {

    private String nContoCorrente;
    private int soglia;
    private int saldoIniziale;
    private int saldoCorrente;
    private Cliente proprietario;



    public ContoCorrente() {

    }

    public ContoCorrente(String nContoCorrente, int soglia, int saldoIniziale,Cliente proprietario){
       this(nContoCorrente,soglia,saldoIniziale); this.proprietario=proprietario;
        
    }


    public ContoCorrente(String nContoCorrente, int soglia, int saldoIniziale) {

        this.saldoCorrente = saldoIniziale;
        this.saldoIniziale = saldoIniziale;
        this.nContoCorrente = nContoCorrente;
        this.soglia = soglia;

        if (this.soglia < 0) {
            this.soglia = 0;
        }

    }

    public String getnContoCorrente() {
        return nContoCorrente;
    }

    public void setnContoCorrente(String nContoCorrente) {
        this.nContoCorrente = nContoCorrente;
    }

    public int getSoglia() {
        return soglia;
    }

    public void setSoglia(int soglia) {
        this.soglia = soglia;
    }

    public int getSaldoIniziale() {
        return saldoIniziale;
    }

    public void setSaldoIniziale(int saldoIniziale) {
        this.saldoIniziale = saldoIniziale;
    }

    public int getSaldoCorrente() {
        return saldoCorrente;
    }

    public void prelievo(int importo) throws MancanzaFondiException {
        if (saldoCorrente - importo >= soglia) {
            this.saldoCorrente -= importo;
        }else{
            throw new MancanzaFondiException("Il Cliente " + this.proprietario.getCognome() + " ha provato a prelevare " +  importo);
        }
    }

    public void versamento(int importo) {
        this.saldoCorrente += importo;
    }

    public Cliente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
    }


}
