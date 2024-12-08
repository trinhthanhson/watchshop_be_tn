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
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand` (
  `brand_id` bigint NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(120) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` bigint NOT NULL,
  `status` varchar(32) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`brand_id`),
  KEY `FKqok6f3456v2bw43aogih9v7jk` (`created_by`),
  KEY `FKs4nty4j66cjl8gfiusxe2olua` (`updated_by`),
  CONSTRAINT `FKqok6f3456v2bw43aogih9v7jk` FOREIGN KEY (`created_by`) REFERENCES `staff` (`staff_id`),
  CONSTRAINT `FKs4nty4j66cjl8gfiusxe2olua` FOREIGN KEY (`updated_by`) REFERENCES `staff` (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,'Diesel','2024-10-08 15:57:36.472992',1,'ACTIVE','2024-10-12 21:19:16.325476',1),(2,'Adee Kaye Watches','2024-10-26 21:15:50.049507',1,'ACTIVE','2024-10-26 21:15:50.049507',1),(3,'Akribos XXIV Watches','2024-10-26 21:16:34.995015',1,'ACTIVE','2024-10-26 21:16:34.995015',1),(4,'Armand Nicolet','2024-10-26 21:17:48.155934',1,'ACTIVE','2024-10-26 21:17:48.155934',1),(5,'Daniel Wellington','2024-10-26 21:19:50.733527',1,'ACTIVE','2024-10-26 21:19:50.733527',1),(6,'Brooklyn Watch Co.','2024-10-26 21:21:41.236141',1,'ACTIVE','2024-10-26 21:21:41.236141',1),(7,' Calvin Klein','2024-10-26 21:21:48.304061',1,'ACTIVE','2024-10-26 21:21:48.305061',1),(8,'Emporio Armani','2024-10-26 21:21:59.515578',1,'ACTIVE','2024-10-26 21:21:59.515578',1),(9,'Franck Dubarry','2024-10-26 21:22:39.521792',1,'ACTIVE','2024-10-26 21:22:39.521792',1);
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-09  2:52:40
