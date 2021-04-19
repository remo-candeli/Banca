package org.corso.banca;

import junit.framework.TestCase;
import org.corso.eccezioni.ErroreInvioEmailException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class SendEmailServiceTest extends TestCase {


    @Test(expected = ErroreInvioEmailException.class)
    public void eccezioneSeIndirizzoEmailNullo_sendEmail() throws ErroreInvioEmailException {
        SendEmailService sendEmailService = new SendEmailService();
        sendEmailService.sendEmail(null);
    }

}