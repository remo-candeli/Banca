package org.corso.banca.services;


import org.corso.banca.factories.ContoCorrenteFactory;
import org.corso.banca.models.Banca;
import org.corso.banca.models.Cliente;
import org.corso.banca.models.ContoCorrente;
import org.corso.eccezioni.ContoCorrenteErratoException;
import org.corso.eccezioni.ErroreInvioEmailException;
import org.corso.eccezioni.MancanzaFondiException;


/**
 * Classe di servizio dalla quale é possibile aprire un cc, prelevare o versare fondi su un cc.
 * Invia email di benvenuto al al cliente che apre un cc.
 *
 */
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
     * Nota. La versione migliorata di questo metodo dovrebbe prevedere la seguente firma:
     *       ContoCorrente apriContoCorrente(Cliente cliente, int valoreIniziale) throws ErroreInvioEmailException
     *       piú coerente.
     *
     * @param nome
     * @param cognome
     * @param codiceFiscale
     * @param valoreIniziale
     */
    public ContoCorrente apriContoCorrente(String nome, String cognome, String codiceFiscale, String indirizzoEmail, int valoreIniziale) throws ErroreInvioEmailException {
        // l´istanza di cliente si potrebbe creare cosi...(modo classico e ormai poco comune nel mondo reale)
        Cliente proprietario = new Cliente(nome, cognome, codiceFiscale, indirizzoEmail);

            // ...o meglio cosi: utilizzando un "builder" ovvero una classe preposta
            // esclusivamente a costruire una istanza. Fa solo quello.
            // Solitamente il Builder é una classe interna (inner class) della classe per cui genera una istanza.
            proprietario = new Cliente.ClienteBuilder(codiceFiscale).cognome(cognome).perInviareEmail(indirizzoEmail).build();

            // ...oppure si potrebbe creare il cliente in questo modo specifico nel contesto di una banca
            proprietario = new Cliente.ClienteBuilder(codiceFiscale).perGestioneBanche(cognome, indirizzoEmail).build();

        // Un altro modo di costruire una istanza; una Factory
        ContoCorrente conto = ContoCorrenteFactory.getInstance(valoreIniziale, proprietario);
        banca.getContiCorrenti().put(conto.getnContoCorrente(), conto);
        if (banca.isNotificaCliente())
            sendEmailService.sendEmail(indirizzoEmail, banca.getIndirizzoEmailBanca());

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


    /**
     * effettua il prelievo di denaro e solleva una eccezione se si é raggiunta la soglia massima di saldo corrente.
     *
     * @param importo
     * @param nContoCorrente
     * @throws ContoCorrenteErratoException
     */
    public void operaVersamento(int importo, String nContoCorrente) throws ContoCorrenteErratoException {
        // meglio questa istruzione...
        ContoCorrente contoCorrente = banca.getContoCorrenteById(nContoCorrente);
        // ...rispetto a questa. no?
        contoCorrente = banca.getContiCorrenti().get(nContoCorrente);

        if (contoCorrente==null)
            throw new ContoCorrenteErratoException("Il conto corrente con id " + nContoCorrente + " non é corretto");

        contoCorrente.versamento(importo);
    }


    /**
     * Stampa l´elenco dei cc esistenti
     *
     */
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