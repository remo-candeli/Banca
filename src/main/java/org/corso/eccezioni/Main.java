package org.corso.eccezioni;

public class Main {
    
    public static void main(String[] args) {
     
        Main main = new Main();


        main.chiamantePrincipale();
    }


    public void chiamantePrincipale()  {
        //metodoChiamato("Saluti dal Chiamante!");
        try {
            metodoChiamante();
        } catch (MancaSalutoException e) {
            // TODO Auto-generated catch block
            System.err.println(e.getMessage());
        }
    }


    public void metodoChiamante() throws MancaSalutoException {
        metodoChiamato(null);
    }


    public void metodoChiamato(String saluta) throws MancaSalutoException {
        if (saluta!=null) {
            System.out.println(saluta);
        } else {
            throw new MancaSalutoException("Errore dalla throw");
        }
    }

}
