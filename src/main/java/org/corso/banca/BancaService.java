package org.corso.banca;


import org.corso.eccezioni.ContoCorrenteErratoException;
import org.corso.eccezioni.ErroreInvioEmailException;
import org.corso.eccezioni.MancanzaFondiException;

public class BancaService {

    private Banca banca;
    private SendEmailService sendEmailService;

    public BancaService(Banca banca) {
        this.banca = banca;
        sendEmailService = new SendEmailService();
    }

    /**
     * Apre il conto corrente ad un cliente ed invia una email al cliente solo se la banca lo prevede e solo se il cliente
     * fornisce un indirizzo email.
     *
     * @param nome
     * @param cognome
     * @param codiceFiscale
     * @param valoreIniziale
     */
    public ContoCorrente apriContoCorrente(String nome, String cognome, String codiceFiscale, String indirizzoEmail, int valoreIniziale) throws ErroreInvioEmailException {
        Cliente proprietario = new Cliente(nome, cognome, codiceFiscale, indirizzoEmail);
        ContoCorrente conto = ContoCorrenteFactory.getInstance(valoreIniziale, proprietario);
        banca.getContiCorrenti().put(conto.getnContoCorrente(), conto);
        if (banca.isNotificaCliente())
            sendEmailService.sendEmail(indirizzoEmail);
        return conto;
    }


    /**
     * Opera il prelievo di una somma importo richiesta su un preciso nr di cc.
     * Se il saldo corrente supera il valore di soglia viene sollevata una eccezione per mancanza di fondi.
     * @param importo
     * @param nContoCorrente
     */
    public void operaPrelievo(int importo, String nContoCorrente) throws ContoCorrenteErratoException {
        try {
            ContoCorrente contoCorrente = banca.getContoCorrenteById(nContoCorrente);

            // se il cc é nullo vuol dire che non é stato trovato tra quelli attivi della banca
            if (contoCorrente == null)
                throw new ContoCorrenteErratoException("Il conto corrente con id " + nContoCorrente + " non é corretto");

            contoCorrente.prelievo(importo);

        } catch (MancanzaFondiException e) {
            e.printStackTrace();
        }
    }

    public void operaVersamento(int importo, String nContoCorrente) throws ContoCorrenteErratoException {
        ContoCorrente contoCorrente = banca.getContiCorrenti().get(nContoCorrente);
        if (contoCorrente==null)
            throw new ContoCorrenteErratoException("Il conto corrente con id " + nContoCorrente + " non é corretto");

        contoCorrente.versamento(importo);
    }


    public void stampaConti(){
        for(ContoCorrente cc: banca.getContiCorrenti().values()) {
            if (cc!=null) {
                System.out.print("ncc " + cc.getnContoCorrente());
                System.out.print("; tipo " + cc.toString());
                System.out.print("; nome " + cc.getProprietario().getCognome());
                System.out.print("; data di nascita " + cc.getProprietario().getDataNascita());
                System.out.println("; saldo " + cc.getSaldoCorrente());
            }
        }
    }

    public Banca getBanca() {
        return banca;
    }
}