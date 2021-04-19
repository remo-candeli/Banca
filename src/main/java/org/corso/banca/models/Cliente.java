package org.corso.banca.models;

import java.time.LocalDate;
import java.util.Objects;

public class Cliente {

    private String nome;
    private String cognome;
    private String codiceFiscale;
    private LocalDate dataNascita;
    private String indirizzoEmail;


    private Cliente(){

    }

    private Cliente(ClienteBuilder builder) {

    }

    /**
     * volutamente lasciato public per dimostrare la dicotomia con il builder
     *
     * @param nome
     * @param cognome
     * @param codiceFiscale
     */
    public Cliente(String nome,String cognome,String codiceFiscale, String indirizzoEmail){
        this.nome=nome;
        this.cognome=cognome;
        this.codiceFiscale=codiceFiscale;
        this.dataNascita = getDataDiNascitaDaCF(codiceFiscale);
        this.indirizzoEmail = indirizzoEmail;
    }


    // CNDRME70R11H501G
    private LocalDate getDataDiNascitaDaCF(String codiceFiscale){
        Integer anno = Integer.parseInt(codiceFiscale.substring(6, 8));
        if (anno<21)
            anno = anno + 2000;
        else
            anno = anno + 1900;
        int mese = decodificaMese(codiceFiscale.charAt(8));
        int giorno = Integer.parseInt(codiceFiscale.substring(9, 11));
        return LocalDate.of(anno, mese, giorno);
    }

    private int decodificaMese(char cdMese) {
        switch (cdMese) {
            case 'A': return 1;
            case 'B': return 2;
            case 'C': return 3;
            case 'D': return 4;
            case 'E': return 5;
            case 'H': return 6;
            case 'L': return 7;
            case 'M': return 8;
            case 'P': return 9;
            case 'R': return 10;
            case 'S': return 11;
            case 'T': return 12;
        }
        return 0;
    }

    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }
    public String getCodiceFiscale() {
        return codiceFiscale;
    }
    public LocalDate getDataNascita() {
        return dataNascita;
    }
    public String getIndirizzoEmail() {
        return indirizzoEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return codiceFiscale.equals(cliente.codiceFiscale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceFiscale);
    }


    /**
     * descrizione:
     *      builder Pattern di Cliente
     * obbiettivo:
     *      separa la costruzione di un oggetto complesso dalla sua rappresentazione, cosicché il processo di costruzione
     *      stesso possa creare diverse rappresentazioni
     */
    public static class ClienteBuilder {
        private String nome;
        private String cognome;
        private String codiceFiscale;
        private String indirizzoEmail;

        public ClienteBuilder(String codiceFiscale) {
            this.codiceFiscale = codiceFiscale;
        }

        public ClienteBuilder perGestioneBanche(String cognome, String indirizzoEmail) {
            this.indirizzoEmail = indirizzoEmail;
            this.cognome = cognome;
            return this;
        }


        public ClienteBuilder datiGenerici(String nome, String cognome, String codiceFiscale) {
            this.nome = nome;
            this.cognome = cognome;
            this.codiceFiscale = codiceFiscale;
            return this;
        }

        public ClienteBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public ClienteBuilder cognome(String cognome) {
            this.cognome = cognome;
            return this;
        }


        public ClienteBuilder perInviareEmail(String indirizzoEmail) {
            this.indirizzoEmail = indirizzoEmail;
            return this;
        }


        public Cliente build() {
            Cliente cliente = new Cliente(this.nome, this.cognome, this.codiceFiscale, this.indirizzoEmail);
            // qui si potrebbero mettere delle chiamate a metodi privati, in questa classe, che magari
            // effettuano controlli di validitá sulle combinazioni adottate in modo da generare
            // istanze di cliente sempre coerenti.
            return cliente;
        }
    }
}
