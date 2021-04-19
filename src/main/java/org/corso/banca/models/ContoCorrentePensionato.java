package org.corso.banca.models;

public class ContoCorrentePensionato extends ContoCorrente {

    private static int ultimoNrCC = 0;

    public ContoCorrentePensionato() {
        super.setnContoCorrente("PP-"+(++ultimoNrCC));
    }

    @Override
    public int calcolaBonus() {
        int result = this.getSaldoCorrente();
        if (this.getSaldoCorrente()>=500) {
            result = (this.getSaldoCorrente() +  ((this.getSaldoCorrente() * 1) / 100));
        }
        return result;
    }

    @Override
    public String toString() {
        return "Conto Corrente Pensionato";
    }
}
