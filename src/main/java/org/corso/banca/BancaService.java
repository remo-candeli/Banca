package org.corso.banca;


import org.corso.eccezioni.ErroreInvioEmailException;

public class BancaService {

    private Banca banca;
    private SendEmailService sendEmailService;

    public BancaService(Banca banca) {
        this.banca = banca;
        sendEmailService = new SendEmailService();
    }

    /**
     * 
     * @param nome
     * @param cognome
     * @param codiceFiscale
     * @param valoreIniziale
     */
    public ContoCorrente apriContoCorrente(String nome, String cognome, String codiceFiscale, String indirizzoEmail, int valoreIniziale) {
        Cliente proprietario = new Cliente(nome, cognome, codiceFiscale, indirizzoEmail);
        ContoCorrente conto = ContoCorrenteFactory.getInstance(valoreIniziale, proprietario);
        banca.getContiCorrenti().put(conto.getnContoCorrente(), conto);
        try {
            sendEmailService.sendEmail(indirizzoEmail);
        } catch (ErroreInvioEmailException e) {
            e.printStackTrace();
        }
        return conto;
    }


    public void operaPrelievo(int importo, String nContoCorrente) {
        ContoCorrente contoCorrente = banca.getContiCorrenti().get(nContoCorrente);
        if (contoCorrente!=null) {
            try {
                contoCorrente.prelievo(importo);
            } catch (MancanzaFondiException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    

    public void operaVersamento(int importo, String nContoCorrente) {
        ContoCorrente contoCorrente = banca.getContiCorrenti().get(nContoCorrente);
        if (contoCorrente!=null) {
                contoCorrente.versamento(importo);
        }
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
}