# Progetto
Progetto per l'esame di ISPW di ingegneria informatica 3° anno a TorVergata.
Il progetto è inerente alla creazione di un software che permette l'interazione tra chef e utenti per contribuire e ricercare ricette che siano coerenti alle disponibilità alimentari momentanee degli utilizzatori.

## Come Utilizzarlo

1) Clonare la repository. 

2) Importare il progetto in Eclipse o qualsiasi altro ambiente di programmazione. 

3) Nella cartella è presente una cartella Lib, questa contiene tutti i file jar da configurare nel progetto per avviarlo.

4) La cartella SRC contiene il codice sorgente.

5) La cartella Test contiene le classi di Test con JUnit.

6) Prima di eseguire l'applicativo è importante istanziare nel proprio DBMS MySQL le tabelle.

7) ## Lasciare standard tutte le opzioni di MySQL altrimenti il software non potrà collegarsi(mysql@localhost:3306).

8) Aprire ed accedere a MySQL, andare nella sezione database (si trova in alto) , selezionare "import database" , selezionate la directory nel progetto con nome DB, selezionare un nome al nuovo "schema" e importare. 

9) Ora aprite il file.txt nella cartella del progetto "DBMS.txt" , nella prima riga inserite il nome utente per accedere in MySQL e nella seconda riga inserite la relativa password.

10) Se JavaFX dovesse dare problemi, dovete selezionare nel buildpath del progetto una VM Argument: per Windows (--module-path "\path\to\javafx-sdk-21.0.2\lib" --add-modules javafx.controls,javafx.fxml), per Linux/Mac (--module-path /path/to/javafx-sdk-21.0.2/lib --add-modules javafx.controls,javafx.fxml).


### Ora è tutto pronto all'utilizzo :)
