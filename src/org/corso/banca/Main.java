package org.corso.banca;

public class Main {
    public static void main(String[] args) {

        Banca banca = new Banca();
        banca.apriContoCorrente("Alessandro", "Corsetti", "CRSLSND27H501I", 1000);

        banca.apriContoCorrente("Remo", "Candeli", "CRSLSND27H501IL", 2000);
        banca.apriContoCorrente("Danilo", "Spano", "CRSLSND27H501K", 3000);
        banca.apriContoCorrente("Luca", "Pippofranco", "CRSLSND27H501A", 10000);
        

        banca.operaPrelievo(100, "1");
        banca.operaVersamento(200, "1");
        banca.operaPrelievo(300, "2");
        banca.operaVersamento(500, "2");
        banca.operaPrelievo(400, "3");
        banca.operaVersamento(600, "3");

        banca.operaPrelievoConCF(400, "CRSLSND27H501A");
        banca.operaVersamentoConCF(600, "CRSLSND27H501A");
        
        // questo é un commento
        stampa(banca);

    }
    
    public static void stampa(Banca banca){

        for(int i=0;i<banca.getContiCorrenti().length-1;i++){
            if (banca.getContiCorrenti()[i]!=null) {
                System.out.print("ncc " + banca.getContiCorrenti()[i].getnContoCorrente());
                System.out.print("; nome " + banca.getContiCorrenti()[i].getProprietario().getCognome());
                System.out.println("; saldo " + banca.getContiCorrenti()[i].getSaldoCorrente());
            }
 
        }
       
    }
}
