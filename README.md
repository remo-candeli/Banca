## Invio Email

La classe SendEmailService é piuttosto semplice ed é in grado di inviare email solo tramite google.
Ció vuol dire che occorre avere una casella postale google.
Le credenziali necessarie per accedere all´SMTP di google sono username e password.
Sono le stesse credenziali che si utilizzano per accedere a gmail.

Lo username é ricavato dall'attributo di classe *indirizzoEmailBanca* valorizzabile nel costruttore della classe Banca.
La password, invece, per motivi di sicurezza, deve essere definita in una variabile di ambiente del sistema.
Il nome di questa variabile di ambiente a cui associare la password é *emailPassword*.

### Assegnazione di una Variabile di ambiente in Windows

Seguire le indicazioni espresse in questo link: https://www.netcwork.it/come-creare-una-variabile-di-ambiente/

### Assegnazione di una Variabile di ambiente linux

da linea di comandi  
**sudo nano /etc/environment**  
aggiungere la seguente riga
**emailPassword = [password-gmail]**  
_(dove [password-gmail] é la volstra passsord)_  

salvare con CTRL + X e confermare le modifiche.  

Riavviare il pc.  
