package org.corso.banca.factories;

import org.corso.banca.models.*;

import java.time.LocalDate;
import java.time.Period;

public class ContoCorrenteFactory {

    private ContoCorrenteFactory() {}

    public static ContoCorrente getInstance(int saldoIniziale, Cliente cliente) {
        ContoCorrente result = null;
        if (Period.between(cliente.getDataNascita(), LocalDate.now()).getYears()<=25) {
            result = new ContoCorrenteAvventura();
            result.setSoglia(1000);
        }
        if (Period.between(cliente.getDataNascita(), LocalDate.now()).getYears()>=75) {
            result = new ContoCorrentePensionato();
            result.setSoglia(10000);
        }
        if (Period.between(cliente.getDataNascita(), LocalDate.now()).getYears()>25
                && Period.between(cliente.getDataNascita(), LocalDate.now()).getYears()<75) {
            result = new ContoCorrenteRisparmio();
            result.setSoglia(2000);
        }
        result.setProprietario(cliente);
        result.setSaldoIniziale(saldoIniziale);

        return result;
    }
}