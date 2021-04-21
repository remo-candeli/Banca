package org.corso.banca.factories;

import org.corso.banca.models.*;

import java.time.LocalDate;
import java.time.Period;


/**
 * Una factory (stabilimento, azienda) é una produttrice di istanze
 * di un altra classe.
 * Disaccoppia la logica (strategia, regola) di generazione delle istanze
 * seguendo regole di businness (quelle che richiede il committente).
 */
public class ContoCorrenteFactory {

    public static final int SOGLIA_MAX_CC_AVVENTURA = 1000;
    public static final int SOGLIA_MAX_CC_PENSIONATO = 10000;
    public static final int SOGLIA_MAX_CC_RISPARMIO = 2000;

    // strategia tipica di un factory (geralmente anche singleton)
    // nessuno puó chiamare questo costruttore
    private ContoCorrenteFactory() {}

    public static ContoCorrente getInstance(int saldoIniziale, Cliente cliente) {
        ContoCorrente result = null;

        if (Period.between(cliente.getDataNascita(), LocalDate.now()).getYears()<=25) {
            result = new ContoCorrenteAvventura();
            result.setSoglia(SOGLIA_MAX_CC_AVVENTURA);
        }

        if (Period.between(cliente.getDataNascita(), LocalDate.now()).getYears()>=75) {
            result = new ContoCorrentePensionato();
            result.setSoglia(SOGLIA_MAX_CC_PENSIONATO);
        }

        if (Period.between(cliente.getDataNascita(), LocalDate.now()).getYears()>25
                && Period.between(cliente.getDataNascita(), LocalDate.now()).getYears()<75) {
            result = new ContoCorrenteRisparmio();
            result.setSoglia(SOGLIA_MAX_CC_RISPARMIO);
        }

        result.setProprietario(cliente);
        result.setSaldoIniziale(saldoIniziale);

        return result;
    }
}
