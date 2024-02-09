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
INSERT INTO `ingredienti` VALUES ('Test_ricetta','test_chef','apple','100 g'),('Test_ricetta','test_chef','flour','100 g'),('Test_ricetta','test_chef','sugar','100 g'),('Test_ricetta','test_chef','water','100 g'),('Torta di mele','chef bruno barbieri','apple','3'),('Torta di mele','chef bruno barbieri','sugar','150 g'),('Torta di mele','chef bruno barbieri','flour','100 g'),('Torta di mele','chef bruno barbieri','dry yeast','3 g'),('Torta di mele','chef bruno barbieri','egg','2'),('Torta di mele','chef bruno barbieri','butter','100 g'),('Torta di mele','chef bruno barbieri','cinnamon','qb'),('Pasta alla carbonara','chef bruno barbieri','spaghetti','400 g'),('Pasta alla carbonara','chef bruno barbieri','guanciale','150 g'),('Pasta alla carbonara','chef bruno barbieri','egg','3'),('Pasta alla carbonara','chef bruno barbieri','pecorino romano','100 g'),('Pasta alla carbonara','chef bruno barbieri','black pepper','qb'),('Pasta al pesto','chef bruno barbieri','spaghetti','400 g'),('Pasta al pesto','chef bruno barbieri','basil','50 g'),('Pasta al pesto','chef bruno barbieri','garlic','2'),('Pasta al pesto','chef bruno barbieri','pecorino romano','40 g'),('Pasta al pesto','chef bruno barbieri','pine nuts','20 g'),('Pasta al pesto','chef bruno barbieri','extra virgin olive oil','40 ml'),('Pasta al pesto','chef bruno barbieri','salt','5 g'),('Pasta cacio e pepe','chef carlo cracco','spaghetti','400 g'),('Pasta cacio e pepe','chef carlo cracco','pecorino romano','200 g'),('Pasta cacio e pepe','chef carlo cracco','black pepper','qb'),('Pasta cacio e pepe','chef carlo cracco','salt','qb'),('Spaghetti con le vongole','chef carlo cracco','spaghetti','400'),('Spaghetti con le vongole','chef carlo cracco','clams','500 g'),('Spaghetti con le vongole','chef carlo cracco','garlic','2'),('Spaghetti con le vongole','chef carlo cracco','chili','qb'),('Spaghetti con le vongole','chef carlo cracco','extra virgin olive oil','qb'),('Spaghetti con le vongole','chef carlo cracco','parsley','qb'),('Spaghetti con le vongole','chef carlo cracco','salt','qb');
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

-- Dump completed on 2024-02-08  4:21:31
