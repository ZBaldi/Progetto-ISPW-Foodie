-- MySQL dump 10.13  Distrib 8.0.34, for macos13 (arm64)
--
-- Host: 127.0.0.1    Database: ricette
-- ------------------------------------------------------
-- Server version	8.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ricette`
--

DROP TABLE IF EXISTS `ricette`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ricette` (
  `nome` varchar(255) NOT NULL,
  `descrizione` longtext,
  `difficolta` int DEFAULT NULL,
  `autore` varchar(255) NOT NULL,
  PRIMARY KEY (`nome`,`autore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ricette`
--

LOCK TABLES `ricette` WRITE;
/*!40000 ALTER TABLE `ricette` DISABLE KEYS */;
INSERT INTO `ricette` VALUES ('Pasta al pesto','Porta a ebollizione una pentola d acqua salata e cuoci la pasta seguendo le istruzioni sulla confezione. Ricorda di tenere da parte un po di acqua di cottura prima di scolare la pasta.\nNel frattempo, prepara il pesto. In un mixer, trita insieme le foglie di basilico, i pinoli, l aglio, il Pecorino Romano e il Parmigiano Reggiano.\nAggiungi gradualmente l olio d oliva al mixer mentre continua a frullare fino a ottenere una consistenza cremosa. Se necessario, aggiusta la consistenza con un po di acqua di cottura della pasta.\nAssaggia e aggiusta di sale e pepe secondo il tuo gusto.\nScola la pasta al dente e trasferiscila in una ciotola.\nAggiungi il pesto alla pasta e mescola bene per coprire uniformemente ogni pezzo di pasta con la deliziosa salsa.\nSe lo desideri, puoi aggiungere ulteriore formaggio grattugiato e pepe nero a piacere.',1,'chef bruno barbieri'),('Pasta alla carbonara','Porta a ebollizione una pentola di acqua salata e cuoci gli spaghetti seguendo le indicazioni sulla confezione. Ricordati di tener da parte un po di acqua di cottura prima di scolare la pasta.\nNel frattempo, in una padella antiaderente, rosola i cubetti di guanciale o pancetta a fuoco medio-basso fino a quando saranno dorati e croccanti.\nIn una ciotola, sbatti le uova e aggiungi il Pecorino Romano grattugiato. Mescola bene per ottenere una crema omogenea.\nQuando gli spaghetti sono cotti al dente, scolali e trasferiscili nella padella con il guanciale o la pancetta. Mescola bene per far amalgamare i sapori.\nTogli la padella dal fuoco e, lavorando velocemente, aggiungi la crema di uova e formaggio alla pasta, mescolando energicamente. Se necessario, puoi aggiungere un po di acqua di cottura della pasta per ottenere una consistenza cremosa.\nAggiusta di sale se necessario e completa con abbondante pepe nero macinato fresco.\nServi immediatamente, guarnendo ogni porzione con ulteriore Pecorino Romano e pepe nero a piacere.',2,'chef bruno barbieri'),('Pasta cacio e pepe','Porta a ebollizione una pentola di acqua salata e cuoci gli spaghetti seguendo le istruzioni sulla confezione. Ricorda di tenere da parte un po di acqua di cottura prima di scolare la pasta.\nMentre la pasta cuoce, in una ciotola, mescola il Pecorino Romano grattugiato con una generosa quantità di pepe nero.\nScola gli spaghetti al dente e trasferiscili nella ciotola con il formaggio e il pepe.\nMescola bene gli ingredienti finché il formaggio non si fonde con la pasta, aggiungendo gradualmente l acqua di cottura della pasta per ottenere una consistenza cremosa.\nAssaggia e aggiusta di sale e pepe secondo il tuo gusto.\nServi immediatamente, guarnendo con ulteriore pepe nero a piacere.',1,'chef carlo cracco'),('Spaghetti con le vongole','Porta a ebollizione una pentola di acqua salata e cuoci le linguine seguendo le istruzioni sulla confezione. Ricorda di tenere da parte un po di acqua di cottura prima di scolare la pasta.\nNel frattempo, pulisci accuratamente le vongole sotto acqua corrente.\nIn una padella capiente, scalda un filo di olio d oliva extravergine a fuoco medio e aggiungi gli spicchi d aglio tritati e il peperoncino.\nAppena l aglio inizia a dorare, aggiungi le vongole alla padella e coprile con un coperchio. Cuoci a fuoco medio-basso finché le vongole non si aprono. Rimuovi quelle chiuse, in quanto potrebbero non essere commestibili.\nScola le linguine al dente e aggiungile nella padella con le vongole. Mescola bene per far assorbire i sapori.\nAggiungi prezzemolo fresco tritato, sale e pepe a piacere. Se necessario, puoi aggiungere un po di acqua di cottura della pasta per ottenere una consistenza più cremosa.\nServi la pasta con le vongole calda, guarnendo con un filo di olio d oliva e prezzemolo fresco.',3,'chef carlo cracco'),('Test_ricetta','Questo è un procedimento di prova',1,'test_chef'),('Torta di mele','Preriscalda il forno a 180°C e imburra una teglia da torta.\nSbuccia e affetta finemente due mele, e taglia la terza a fettine più spesse per la decorazione. Metti da parte.\nIn una ciotola, setaccia la farina e mescola con il lievito, la cannella e il pizzico di sale.\nIn un altra ciotola, sbatti le uova con lo zucchero fino a ottenere una crema chiara e spumosa.\nAggiungi il burro fuso alla miscela di uova e zucchero, mescolando bene.\nIncorpora gradualmente la miscela di farina nella ciotola degli ingredienti umidi, mescolando fino a ottenere un impasto omogeneo.\nAggiungi le mele affettate finemente all impasto e mescola delicatamente.\nVersa l impasto nella teglia preparata e livellalo con una spatola.\nDisponi le fettine di mela sulla superficie della torta, creando un motivo decorativo.\nInforna la torta nel forno preriscaldato per circa 40-45 minuti o fino a quando uno stuzzicadenti inserito al centro esce pulito.\nUna volta cotta, lascia raffreddare la torta nella teglia per qualche minuto, poi trasferiscila su una griglia per raffreddare completamente.\nOpzionalmente, prima di servire, puoi cospargere la torta con zucchero a velo per una finitura elegante.',1,'chef bruno barbieri');
/*!40000 ALTER TABLE `ricette` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-08  4:21:31
