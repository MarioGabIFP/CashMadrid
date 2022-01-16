CREATE DATABASE  IF NOT EXISTS `cashmadrid` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cashmadrid`;
-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: cashmadrid
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `asignacion`
--

DROP TABLE IF EXISTS `asignacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asignacion` (
  `IdCli` int NOT NULL COMMENT 'identificador interno del cliente',
  `IdCu` int NOT NULL COMMENT 'identificador interno de la cuenta',
  `FINI` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de apertura de la cuenta',
  `FFIN` timestamp NULL DEFAULT NULL COMMENT 'Fecha de cierre de la cuenta bancaria (si relleno = cuenta inactiva)',
  KEY `asigancion_cliente` (`IdCli`),
  KEY `asigancion_cuenta` (`IdCu`),
  CONSTRAINT `asigancion_cliente` FOREIGN KEY (`IdCli`) REFERENCES `clientes` (`IdCli`),
  CONSTRAINT `asigancion_cuenta` FOREIGN KEY (`IdCu`) REFERENCES `cuenta` (`IdCu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignacion`
--

LOCK TABLES `asignacion` WRITE;
/*!40000 ALTER TABLE `asignacion` DISABLE KEYS */;
INSERT INTO `asignacion` VALUES (1,1,'2021-12-24 11:52:06','2022-01-11 23:00:00'),(2,2,'2022-01-10 23:00:00','2022-01-11 23:00:00'),(6,3,'2022-01-10 23:00:00','2022-01-12 23:00:00'),(1,4,'2022-01-10 23:00:00','2022-01-12 23:00:00'),(4,5,'2022-01-10 23:00:00',NULL),(8,6,'2022-01-12 23:00:00',NULL),(6,7,'2022-01-12 23:00:00',NULL),(12,8,'2022-01-12 23:00:00','2022-01-12 23:00:00'),(14,9,'2022-01-12 23:00:00',NULL);
/*!40000 ALTER TABLE `asignacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `IdCli` int NOT NULL AUTO_INCREMENT COMMENT 'identificador interno del cliente',
  `DNI` varchar(9) NOT NULL COMMENT 'Documento Nacional de Identidad del cliente',
  `Nom` varchar(200) NOT NULL COMMENT 'Nombre del cliente',
  `Apel` varchar(400) DEFAULT NULL COMMENT 'Apellidos del cliente',
  `Tel` varchar(16) NOT NULL COMMENT 'Telefono del cliente',
  `Email` varchar(200) NOT NULL COMMENT 'Correo electronico del cliente',
  `Dom` varchar(400) NOT NULL COMMENT 'Domicilio del cliente',
  `Activ` tinyint NOT NULL,
  PRIMARY KEY (`IdCli`),
  UNIQUE KEY `DNI_UNIQUE` (`DNI`),
  UNIQUE KEY `Email_UNIQUE` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'53447382J','Alberto Andres',' Morales de las Cuestas','634252145','Albertiño@gmail.com','Calle Juan Miguel N22 3ºC',0),(2,'04632784H','Rubén Ignacio','Cuesta Mogollón','764893235','IgnacioRuben@hotmail.com','Calle de las heras N11 3ºA',0),(3,'03245438K','Andrea Cristina','Gonzalez Lopez','+34 748 32 09 48','cris@gmail.com','Calle Martinez de Prada, Nº2, 1ºB',0),(4,'87434092F','Tomás','Gomez Lopez','674 87 32 34','powers@gmail.com','C:// Calle De la Luz, Nº98 1ºB',1),(5,'04334875M','Maria','Alcolea','611 56 10 28','mar@ia.com','C. Martinez Sanz, Nº2, 2ºD',0),(6,'74637245F','Carmen','de las Nieves','764325643','car@men.com','calle alguna, n1, 1ºs',1),(7,'87434895D','Javier','Gonzalez Romero','610298435','javier@gmail.com','calle miguel luthier, nº2, 1ºb',0),(8,'03223807N','Mario Gabriel','Núñez Alcázar de Velasco','+34 610 28 28 49','marionunezinfor@gmail.com','C: General Moscardó Gúzman, N33, 1ºA',1),(9,'03562342G','Julia Isabel','Martinez Arias','+34 672 65 67 87','juliana34232@gmail.com','Calle Don Pelayo, Nº3, 4ºC',0),(10,'95443975T','Romina Juan','Bravo Guarro','+34 657 43 35 87','losguarro@gmail.com','Calle General Price, Nº32, 6ºD',0),(12,'20766608T','Juan Bravo','Gonzalez de las Heras','+34 657 34 56 21','JuanitoChocolaterito@Hotmail.com','Calle Cervantes, Nº45, 1ºD',0),(13,'98654345P','Aitor','Tilla','654322453','HayTortilla@gemail.com','calle tortillamen NºCebolla, 1ºPoco Hecha',0),(14,'03245636U','Tarek','Samadi','777777777','tareqdejaelputomovil@paco.com','calle Movil, nº22, 1ºe',1),(15,'04356438G','Francisco Antonio','Gonzalez Pirueta','+34 674 34 21 32','paquito@gmail.com','Calle BrIanda de Mendoza, Nº12, 1ºC',1);
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta` (
  `IdCu` int NOT NULL AUTO_INCREMENT COMMENT 'identificador interno de la cuenta',
  `DigCon` varchar(4) NOT NULL COMMENT 'Codigo de Pais y dígito de control del IBAN',
  `Ent` varchar(4) NOT NULL COMMENT 'Codigo de la Entidad bancaria a la que pertenece el IBAN',
  `Ofi` varchar(4) NOT NULL COMMENT 'Codigo de la oficina a la que pertece el IBAN',
  `DigContr` varchar(2) NOT NULL COMMENT 'digito de control de la cuenta bancaria',
  `NCue` varchar(10) NOT NULL COMMENT 'Número de cuenta a la que pertenece le cliente',
  `NBanc` varchar(200) DEFAULT NULL COMMENT 'Nombre del banco al que pertenece la cuenta',
  PRIMARY KEY (`IdCu`),
  UNIQUE KEY `NCue_UNIQUE` (`NCue`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta`
--

LOCK TABLES `cuenta` WRITE;
/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
INSERT INTO `cuenta` VALUES (1,'ES19','0081','8332','17','9419218512','Banco Sabadell'),(2,'ES60','2100','9281','24','1578639563','CaixaBank'),(3,'ES97','1465','5676','41','4921371663','Santander'),(4,'ES22','0487','5752','15','9814991689','Deutche Bank'),(5,'ES83','2100','9991','58','2574896452','KutxaBank'),(6,'ES93','2038','7391','05','5555525911','DeutcheBank'),(7,'ES18','0081','4328','41','6726593279','CaixaBank'),(8,'ES28','2038','6392','31','4548582673','Caja Rural de Jaén'),(9,'ES36','0487','6715','88','8657341516','DeutcheBank');
/*!40000 ALTER TABLE `cuenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transferencias`
--

DROP TABLE IF EXISTS `transferencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transferencias` (
  `CodTran` int NOT NULL AUTO_INCREMENT COMMENT 'Codigo interno de la transferencia',
  `COri` int DEFAULT NULL COMMENT 'cuenta emisora de la transferencia',
  `CDes` int NOT NULL COMMENT 'cuenta dstinataria de la transferencia',
  `FTran` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha y hora de la transferencia',
  `Imp` double NOT NULL COMMENT 'Importe de la transferencia',
  `Cncpt` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`CodTran`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transferencias`
--

LOCK TABLES `transferencias` WRITE;
/*!40000 ALTER TABLE `transferencias` DISABLE KEYS */;
INSERT INTO `transferencias` VALUES (4,NULL,9,'2022-01-13 11:49:19',123.21,'Ingreso a Cuenta'),(5,NULL,6,'2022-01-13 11:50:45',162.43,'Ingreso a Cuenta'),(7,NULL,5,'2022-01-13 13:11:23',143.2,'Ingreso a Cuenta'),(8,NULL,5,'2022-01-13 13:11:29',234.3,'Ingreso a Cuenta'),(10,NULL,7,'2022-01-14 11:49:39',123.43,'Ingreso a Cuenta'),(11,NULL,6,'2022-01-14 14:14:01',932.32,'Ingreso a Cuenta'),(12,5,6,'2022-01-15 14:36:54',23,'transferencia'),(13,9,6,'2022-01-15 14:40:53',100,'transferencia'),(14,6,9,'2022-01-15 21:48:23',123.21,'Bizummm :)'),(15,6,9,'2022-01-15 21:49:49',212,'Regulación');
/*!40000 ALTER TABLE `transferencias` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-16 17:46:40
