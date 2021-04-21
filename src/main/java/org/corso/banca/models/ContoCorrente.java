package org.corso.banca.models;


import org.corso.banca.Gratificabile;
import org.corso.eccezioni.MancanzaFondiException;

public abstract class ContoCorrente implements Gratificabile {

    private String nContoCorrente;
    private int soglia;
    private int saldoIniziale;
    private int saldoCorrente;
    private Cliente proprietario;


    /**
     * esegue il prelievo effettivo per l'importo richiesto.
     * Eccezione se superata la soglia massima di saldo corrente del conto.
     *
     * @param importo
     * @throws MancanzaFondiException
     */
    public void prelievo(int importo) throws MancanzaFondiException {
        if (Math.abs(saldoCorrente - importo) <= soglia) {
            this.saldoCorrente -= importo;
        }else{
            throw new MancanzaFondiException("Il Cliente " + this.proprietario.getCognome() +
                                            " ha provato a prelevare " +  importo +
                                            " oltre la soglia massima di " + soglia);
        }
    }


    /**
     * effettua un versamento del valore richiesto sul cc.
     * Verifica se é possibile aggiungere una ulteriore somma di denaro di bonus determinata in base alla tipologia di conto.
     *
     * @param importo
     */
    public void versamento(int importo) {
        this.saldoCorrente += importo;
        int saldoDopoCalcoloBonus = calcolaBonus();
        if (saldoDopoCalcoloBonus > this.saldoCorrente) {
            System.out.println("    Il cliente " + proprietario.getCognome() + " ha ottenuto un bonus di " + (saldoDopoCalcoloBonus - saldoCorrente) + " euro");
        }
        this.saldoCorrente = saldoDopoCalcoloBonus;
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
        this.saldoCorrente = saldoIniziale;
    }

    public int getSaldoCorrente() {
        return saldoCorrente;
    }

    public Cliente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
    }

}
