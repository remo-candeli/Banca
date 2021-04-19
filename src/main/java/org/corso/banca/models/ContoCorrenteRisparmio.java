package org.corso.banca.models;

public class ContoCorrenteRisparmio extends ContoCorrente {

    private static int ultimoNrCC = 0;

    public ContoCorrenteRisparmio() {
        super.setnContoCorrente("RS-"+(++ultimoNrCC));
    }

    @Override
    public int calcolaBonus() {
        return this.getSaldoCorrente();
    }

    @Override
    public String toString() {
        return "Conto Corrente Risparmio";
    }
}
