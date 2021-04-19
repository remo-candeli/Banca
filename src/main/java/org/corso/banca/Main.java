package org.corso.banca;

import org.corso.eccezioni.ErroreInvioEmailException;

public class Main {
    public static void main(String[] args) {
        Banca banca = new Banca("Banca di Latina", "000313");
        BancaService bancaService = new BancaService(banca);

        try {
            ContoCorrente cc1 = bancaService.apriContoCorrente("Alessandro", "Corsetti", "NZZTZJ03M01G039B", "remo.candeli@gmail.com", 1000);

       /*
            ContoCorrente cc2 = bancaService.apriContoCorrente("Remo", "Candeli", "HGERCS89L03H059G", "remo.candeli@gmail.com", 2000);
            ContoCorrente cc3 = bancaService.apriContoCorrente("Danilo", "Spano", "HSRLVL88P26G118S", "remo.candeli@gmail.com", 3000);
            ContoCorrente cc4 = bancaService.apriContoCorrente("Luca", "Pippofranco", "ZGLDSC96E28C853K", "remo.candeli@gmail.com", 10000);
        */
        } catch (ErroreInvioEmailException e) {
            e.printStackTrace();
        }

        /*
        bancaService.operaPrelievo(100, cc1.getnContoCorrente());
        bancaService.operaVersamento(200, cc1.getnContoCorrente());
        bancaService.operaPrelievo(300, cc2.getnContoCorrente());
        bancaService.operaVersamento(500, cc2.getnContoCorrente());
        bancaService.operaPrelievo(400, cc3.getnContoCorrente());
        bancaService.operaVersamento(600, cc3.getnContoCorrente());
         */

        bancaService.stampaConti();


    }


    

}
