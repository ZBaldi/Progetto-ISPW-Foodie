-- MySQL dump 10.13  Distrib 8.0.34, for macos13 (arm64)
--
-- Host: 127.0.0.1    Database: user_credentials
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
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_account` (
  `account_id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `ruolo` int NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `account_id_UNIQUE` (`account_id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (1,'Michele','Biru','spadellatore02',0,'michele2002'),(3,'Valerio','Baldazzi','mocassino',0,'valerio2002'),(4,'Mario ','Rossi','Mariong',0,'mario1518'),(5,'Paolo','Cannone','paolocannone22',0,'paolone22'),(6,'Carlo','Cracco','carletto69',0,'carlo69'),(7,'Bruno','Barbieri','brunello',0,'bruno90'),(8,'Giacomo','Cialoni','bomber10',1,'giacomo2002'),(9,'','','',0,''),(11,'Piero','Verdi','pazzoinculo',1,'piero69'),(12,'Marco','Fagioli','fagiolo24',1,'marco24'),(13,'Filippo','Turetta','turetta_killer_02',1,'turetta2002'),(14,'Paolo','Paoloni','paoletto69',1,'paolopaolo'),(15,'Marco','Rossi','marco2002',1,'marco2002'),(16,'Andrea','Finocchio','finocchio02',0,'sonofinocchio'),(17,'Alessio','Sechi','sexy02',1,'sexy02'),(18,'Michele','Misseri','trattore69',1,'sarahscazzi'),(19,'moderatore','moderatore','moderatore',2,'moderatore'),(20,'Michele','Biru','michele02',0,'michele2002'),(21,'Maria','Biru','maria74',1,'maria74'),(22,'Michele','Biru','mich02',0,'a'),(23,'Mario','Biondi','a',0,'a'),(24,'Paolo','Stocazzo','b',1,'b'),(25,'Paolo','Balduzzi','balduz',0,'a'),(26,'Antonino','Cannavacciuolo','AntoninoCanna',1,'a');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-06 18:22:54
