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
-- Table structure for table `ingredienti`
--

DROP TABLE IF EXISTS `ingredienti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredienti` (
  `nome_ricetta` varchar(255) DEFAULT NULL,
  `autore_ricetta` varchar(255) DEFAULT NULL,
  `alimento` varchar(255) DEFAULT NULL,
  `quantita` varchar(255) DEFAULT NULL,
  KEY `nome_ricetta` (`nome_ricetta`,`autore_ricetta`),
  CONSTRAINT `ingredienti_ibfk_1` FOREIGN KEY (`nome_ricetta`, `autore_ricetta`) REFERENCES `ricette` (`nome`, `autore`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredienti`
--

LOCK TABLES `ingredienti` WRITE;
/*!40000 ALTER TABLE `ingredienti` DISABLE KEYS */;
INSERT INTO `ingredienti` VALUES ('Spaghetti aglio e oriolo','sexy02','pasta','100 g'),('Torta di mele','maria74','apple','2'),('Torta di mele','maria74','flour','200 g'),('Torta di mele','maria74','sugar','100 g'),('Spaghetti al pesto','b','spaghetti','100 g'),('Spaghetti al pesto','b','basil','20 g'),('Pasta alla Cazzarola','b','rice paper','100 g'),('Pasta alla Cazzarola','b','beef','100 g'),('Pasta alla Cazzarola','b','fennel','100 g'),('Pasta alla Cazzarola','b','tomato','100 g'),('Pasta alla Cazzarola','b','pasta','100 g'),('Pasta alla Cazzarola','b','mushroom','100 g'),('Pasta alla Cazzarola','b','pickles','100 g'),('Pasta alla Cazzarola','b','sausage','100 g'),('Pasta alla Cazzarola','b','truffles','100 g'),('Pasta cacio e pepe','b','cheese','100 g'),('Pasta cacio e pepe','b','pepper','5 g'),('Pasta cacio e pepe','b','pasta','100 g');
/*!40000 ALTER TABLE `ingredienti` ENABLE KEYS */;
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
