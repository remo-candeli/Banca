package org.corso.banca;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

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
        banca = new Banca("banca", "55504");
        clienteValido = new Cliente("Remo", "Candeli", "CNDRME70R11H501G", "remo.candeli@gmail.com");
        bancaService = new BancaService(banca);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void contoCorrenteValido_apriContoCorrente() {
        ContoCorrente cc = new ContoCorrenteRisparmio();
        cc.setnContoCorrente("cc1");
        cc.setSaldoIniziale(500);
        cc.setSoglia(5000);
        cc.setProprietario(clienteValido);
        try (MockedStatic<ContoCorrenteFactory> theMock = Mockito.mockStatic(ContoCorrenteFactory.class)) {
            theMock.when(() -> ContoCorrenteFactory.getInstance(500, clienteValido)).thenReturn(cc);

            ContoCorrente ccActual = bancaService.apriContoCorrente("Remo", "Candeli", "CNDRME70R11H501G", "remo.candeli@gmail.com", 500);
            assertEquals(cc, ccActual);
        }
    }


    @Test
    public void contoCorrenteNulloSeCodiceFiscaleNullo_apriContoCorrente() {
        ContoCorrente cc = new ContoCorrenteRisparmio();
        cc.setnContoCorrente("cc1");
        cc.setSaldoIniziale(500);
        cc.setSoglia(5000);
        cc.setProprietario(clienteValido);
        ContoCorrente ccActual = bancaService.apriContoCorrente("Remo", "Candeli", null, "remo.candeli@gmail.com", 500);
        assertNull(ccActual);
    }



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

}