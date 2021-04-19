package org.corso.banca.models;

public class ContoCorrenteAvventura extends ContoCorrente  {

    private static int ultimoNrCC = 0;

    public ContoCorrenteAvventura() {
        super.setnContoCorrente("AV-"+(++ultimoNrCC));
    }

    @Override
    public int calcolaBonus() {
        int result = this.getSaldoCorrente();
        if (this.getSaldoCorrente()>=1500) {
            result = (this.getSaldoCorrente() +  ((this.getSaldoCorrente() * 5) / 100));
        }
        return result;
    }


    @Override
    public String toString() {
        return "Conto corrente Avventura";
    }


}
