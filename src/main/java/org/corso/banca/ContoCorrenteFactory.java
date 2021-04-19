package org.corso.banca;

import java.time.LocalDate;
import java.time.Period;

public class ContoCorrenteFactory {

    private ContoCorrenteFactory() {}

    public static ContoCorrente getInstance(int saldoIniziale, Cliente cliente) {
        ContoCorrente result = null;
        if (Period.between(cliente.getDataNascita(), LocalDate.now()).getYears()<=25) {
            result = new ContoCorrenteAvventura();
        }
        if (Period.between(cliente.getDataNascita(), LocalDate.now()).getYears()>=75) {
            result = new ContoCorrentePensionato();
        }
        if (Period.between(cliente.getDataNascita(), LocalDate.now()).getYears()>25
                && Period.between(cliente.getDataNascita(), LocalDate.now()).getYears()<75) {
            result = new ContoCorrenteRisparmio();
        }
        result.setProprietario(cliente);
        result.setSaldoIniziale(saldoIniziale);

        return result;
    }
}
