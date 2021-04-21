package org.corso.banca;

import junit.framework.TestCase;
import org.corso.banca.factories.ContoCorrenteFactory;
import org.corso.banca.models.*;
import org.corso.banca.services.BancaService;
import org.corso.banca.services.SendEmailService;
import org.corso.eccezioni.ContoCorrenteErratoException;
import org.corso.eccezioni.ErroreInvioEmailException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;



@RunWith(MockitoJUnitRunner.class)
public class BancaServiceTest extends TestCase {

    private Banca banca;

    @Mock
    private SendEmailService sendEmailService;

    @InjectMocks
    private BancaService bancaService;

    private Cliente clienteValido;

    @Before
    public void setup() {
        banca = new Banca("banca", "55504", "xxx@yyy.it");
        clienteValido = new Cliente("Remo", "Candeli", "CNDRME70R11H501G", "remo.candeli@gmail.com");
        bancaService = new BancaService(banca);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test() {
        assertTrue(true);
    }

    @Test
    public void contoCorrenteValido_apriContoCorrente() throws ErroreInvioEmailException{
        ContoCorrente ccActual;
        ContoCorrente ccExpected = new ContoCorrenteRisparmio();
        ccExpected.setnContoCorrente("cc1");
        ccExpected.setSaldoIniziale(500);
        ccExpected.setProprietario(clienteValido);
        try (MockedStatic<ContoCorrenteFactory> theMock = Mockito.mockStatic(ContoCorrenteFactory.class)) {
            // x = f(x)  ==== ContoCorrente cc = ContoCorrenteFactory.getInstance()
            //MockedStatic.Verification verification = () -> ContoCorrenteFactory.getInstance(500, clienteValido);
            theMock.when(() -> ContoCorrenteFactory.getInstance(500, clienteValido)).thenReturn(ccExpected);

            ccActual = bancaService.apriContoCorrente("Remo", "Candeli", "CNDRME70R11H501G",
                    "remo.candeli@gmail.com", 500);
        }
        assertEquals(ccExpected, ccActual);
        assertNotNull(ccActual);
    }


    /*
    @Test
    public void contoCorrenteNulloSeCodiceFiscaleNullo_apriContoCorrente() throws ErroreInvioEmailException{
        ContoCorrente cc = new ContoCorrenteRisparmio();
        cc.setnContoCorrente("cc1");
        cc.setSaldoIniziale(500);
        cc.setSoglia(5000);
        cc.setProprietario(clienteValido);
        ContoCorrente ccActual = bancaService.apriContoCorrente("Remo", "Candeli", null, "remo.candeli@gmail.com", 500);
        assertNull(ccActual);
    }
    */


    @Test
    public void contoCorrenteAvventuraSeEtaClienteMinoreUguale25_apriContoCorrente() {
        Cliente clienteValidoEta23 = new Cliente("Remo", "Candeli", "CNDRME08R11H501G", "remo.candeli@gmail.com");
        ContoCorrente cc = ContoCorrenteFactory.getInstance(500, clienteValidoEta23);
        assertTrue(cc instanceof ContoCorrenteAvventura);
    }


    @Test
    public void contoCorrentePensionatoSeEtaClienteMaggioreUguale75_apriContoCorrente() {
        Cliente clienteValidoEta75 = new Cliente("Remo", "Candeli", "CNDRME38R11H501G", "remo.candeli@gmail.com");
        ContoCorrente cc = ContoCorrenteFactory.getInstance(500, clienteValidoEta75);
        assertTrue(cc instanceof ContoCorrentePensionato);
    }


    @Test(expected = ContoCorrenteErratoException.class)
    public void idContoCorrenteNullo_operaVersamento() throws ContoCorrenteErratoException{
        bancaService.operaVersamento(200, null);
    }

    @Test
    public void prelievoNonEseguitoSeSuperaDisponibilitaFondi_operaPrelievo() throws ErroreInvioEmailException, ContoCorrenteErratoException {
        // in realtá sarebbe piú corretto effettuare un mock del metodo apriContoCorrente poiché oggetto del nostro test (detto anche SUT: system under Test)
        // é il metodo operaPrelievo. Nulla quindi dovrebbe essere corrotto da questo obiettivo.
        // Se, dunque, il metodo "apriContoCorrente" fallisse non si otterrebbe un risultato chiaro del test.
        // Ma ci toccherebbe fare delle spy...soprassediamo.
        ContoCorrente cc = bancaService.apriContoCorrente("Remo", "Candeli", "CNDRME70R11H501G", "remo.candeli@gmail.com", 500);

        int saldoCorrentePrimaDiPrelievo = cc.getSaldoCorrente();
        bancaService.operaPrelievo(1500, cc.getnContoCorrente());
        assertTrue((saldoCorrentePrimaDiPrelievo-1500)==cc.getSaldoCorrente());
    }

}