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
-- Table structure for table `update_price`
--

DROP TABLE IF EXISTS `update_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `update_price` (
  `update_price_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `original_price` int DEFAULT NULL,
  `price_new` int NOT NULL,
  `price_old` int NOT NULL,
  `product_id` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `created_by` bigint NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`update_price_id`),
  KEY `FKh5didnvuut87rbmb4am0l9wbt` (`created_by`),
  KEY `FKi11xgpbwxyhqkffxpwa5tl1s0` (`product_id`),
  KEY `FKrhdu9qaxc3r0r7xt8f5eobgin` (`updated_by`),
  CONSTRAINT `FKh5didnvuut87rbmb4am0l9wbt` FOREIGN KEY (`created_by`) REFERENCES `staff` (`staff_id`),
  CONSTRAINT `FKi11xgpbwxyhqkffxpwa5tl1s0` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `FKrhdu9qaxc3r0r7xt8f5eobgin` FOREIGN KEY (`updated_by`) REFERENCES `staff` (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `update_price`
--

LOCK TABLES `update_price` WRITE;
/*!40000 ALTER TABLE `update_price` DISABLE KEYS */;
INSERT INTO `update_price` VALUES (3,'2024-10-08 15:57:36.472992',200000,200000,200000,'DH00001','2024-10-29 17:54:10.058174',1,1),(4,'2024-10-26 21:40:28.910865',700000,1000000,1000000,'DH00000002','2024-10-26 21:40:28.910865',1,1),(9,'2024-10-29 17:16:24.015105',1000000,1500000,1500000,'DH00000003','2024-10-29 17:16:24.015105',1,1),(10,'2024-10-29 17:16:24.083106',1000000,1350000,1350000,'DH00000004','2024-10-29 17:16:24.083106',1,1),(11,'2024-10-29 17:16:24.118106',1900000,2450000,2450000,'DH00000005','2024-10-29 17:16:24.118106',1,1),(12,'2024-10-29 17:16:24.146105',1300000,1630000,1630000,'DH00000006','2024-10-29 17:16:24.146105',1,1),(13,'2024-10-29 17:16:24.169331',2400000,2850000,2850000,'DH00000007','2024-10-29 17:16:24.169331',1,1),(14,'2024-10-29 17:16:24.204330',2950000,3400000,3400000,'DH00000008','2024-10-29 17:16:24.204330',1,1),(15,'2024-10-29 17:16:24.234330',900000,1210000,1210000,'DH00000009','2024-10-29 17:16:24.234330',1,1),(16,'2024-10-29 17:16:24.261035',1950000,2480000,2480000,'DH00000010','2024-10-29 17:16:24.261035',1,1),(17,'2024-10-29 17:16:24.292033',2400000,3000000,3000000,'DH00000011','2024-10-29 17:16:24.292033',1,1),(18,'2024-10-29 17:16:24.320034',2000000,2480000,2480000,'DH00000012','2024-10-29 17:16:24.320034',1,1);
/*!40000 ALTER TABLE `update_price` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-19  0:32:00
