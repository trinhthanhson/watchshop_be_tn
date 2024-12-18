-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: tn_watchshop
-- ------------------------------------------------------
-- Server version	8.0.38

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
-- Table structure for table `bill_supplier`
--

DROP TABLE IF EXISTS `bill_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill_supplier` (
  `bill_id` bigint NOT NULL AUTO_INCREMENT,
  `bill_code` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `supplier_id` bigint DEFAULT NULL,
  PRIMARY KEY (`bill_id`),
  KEY `FK4qb7jaheaw1pvn7fl0enbxicf` (`supplier_id`),
  CONSTRAINT `FK4qb7jaheaw1pvn7fl0enbxicf` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_supplier`
--

LOCK TABLES `bill_supplier` WRITE;
/*!40000 ALTER TABLE `bill_supplier` DISABLE KEYS */;
INSERT INTO `bill_supplier` VALUES (12,'HD01','2024-12-06 23:45:24.369737',15),(13,'HD02','2024-12-06 23:51:55.419193',16),(14,'HD03','2024-12-07 00:12:46.166962',16),(15,'HD03','2024-12-07 00:21:45.412417',17),(16,'HD04','2024-12-07 00:28:29.667049',15),(17,'DH04','2024-12-07 00:36:28.410631',17),(18,'HD07','2024-12-07 00:56:58.858367',17),(19,'DH09','2024-12-07 01:29:51.261862',15),(20,'DH09','2024-12-07 01:43:16.099435',15),(21,'DH10','2024-12-07 01:58:22.439863',16),(22,'DH01','2024-12-08 13:14:32.917743',15),(23,'DH01','2024-12-08 13:19:10.239196',15),(24,'DH01','2024-12-08 13:24:50.950288',15),(25,'DH01','2024-12-08 13:28:17.917364',15),(26,'DH01','2024-12-08 13:38:34.550405',15),(27,'DH01','2024-12-08 13:43:59.145286',15),(28,'DH01','2024-12-08 13:47:53.190652',15),(29,'DH01','2024-12-08 13:50:51.007817',15),(30,'DH01','2024-12-08 13:54:49.692466',15),(31,'DH01','2024-12-08 14:00:38.581082',15),(32,'DH01','2024-12-08 14:03:32.893928',15),(33,'DH01','2024-12-08 14:06:22.267371',16),(34,'DH01','2024-12-09 13:55:41.433466',15),(35,'DH01','2024-12-09 14:40:53.236989',15),(36,'DH01','2024-12-09 14:45:00.315792',16),(37,'DH01','2024-12-09 14:45:28.088759',15),(38,'DH01','2024-12-09 14:46:01.879522',15),(39,'DH01','2024-12-09 14:46:36.368953',16),(40,'DH01','2024-12-09 14:46:44.689847',15),(41,'DH01','2024-12-09 14:46:55.108678',15),(42,'DH01','2024-12-09 14:47:07.803042',15),(43,'DH01','2024-12-09 14:47:16.669984',15),(44,'DH01','2024-12-09 14:47:40.372822',NULL);
/*!40000 ALTER TABLE `bill_supplier` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-19  0:32:02
