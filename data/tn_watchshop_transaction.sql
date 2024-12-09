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
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `transaction_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `staff_id` bigint DEFAULT NULL,
  `supplier_id` bigint DEFAULT NULL,
  `total_price` int DEFAULT NULL,
  `total_quantity` int DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `bill_id` bigint DEFAULT NULL,
  `request_id` bigint DEFAULT NULL,
  `transaction_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `FKa9bnjmduu3cr26d3aet29sbw5` (`staff_id`),
  KEY `FKr61iux1u66j0u4vnwqvdaxo8s` (`supplier_id`),
  KEY `FKbp1x3lysuub3i22wlr3hdxfpe` (`type_id`),
  KEY `FKnqjlqsarvv8nc06hhg5s04rgc` (`updated_by`),
  KEY `FKtmmc7402m99gl1fglutp3gp7g` (`bill_id`),
  KEY `FKqqdcwv3vbvoay7ymaxtlsd323` (`request_id`),
  CONSTRAINT `FKa9bnjmduu3cr26d3aet29sbw5` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`),
  CONSTRAINT `FKbp1x3lysuub3i22wlr3hdxfpe` FOREIGN KEY (`type_id`) REFERENCES `type` (`type_id`),
  CONSTRAINT `FKnqjlqsarvv8nc06hhg5s04rgc` FOREIGN KEY (`updated_by`) REFERENCES `staff` (`staff_id`),
  CONSTRAINT `FKqqdcwv3vbvoay7ymaxtlsd323` FOREIGN KEY (`request_id`) REFERENCES `transaction_request` (`request_id`),
  CONSTRAINT `FKr61iux1u66j0u4vnwqvdaxo8s` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_id`),
  CONSTRAINT `FKtmmc7402m99gl1fglutp3gp7g` FOREIGN KEY (`bill_id`) REFERENCES `bill_supplier` (`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (17,'2024-05-27 00:00:00.000000',NULL,6,NULL,386000000,220,1,'Nhập kho đầu kỳ',NULL,NULL,12,18,'PN24000001'),(18,'2024-05-20 00:00:00.000000',NULL,6,NULL,143650000,90,1,'Nhập kho thiếu hàng',NULL,NULL,13,19,'PN24000002'),(19,'2024-05-13 00:00:00.000000',NULL,6,NULL,44850000,35,1,'Nhập thêm hàng',NULL,NULL,14,20,'PN24000003'),(20,'2024-05-06 00:00:00.000000',NULL,6,NULL,44240000,25,1,'Nhập kho thiếu hàng',NULL,NULL,15,23,'PN24000004'),(21,'2024-04-29 00:00:00.000000',NULL,6,NULL,59600000,43,1,'Nhập thêm hàng',NULL,NULL,16,24,'PN24000005'),(22,'2024-04-22 00:00:00.000000',NULL,6,NULL,87500000,40,1,'Nhập kho thiếu hàng',NULL,NULL,17,25,'PN24000006'),(23,'2024-04-15 00:00:00.000000',NULL,6,NULL,39300000,23,1,'Nhập kho thiếu hàng',NULL,NULL,18,26,'PN24000007'),(24,'2024-04-08 00:00:00.000000',NULL,6,NULL,48050000,47,1,'Nhập thêm hàng',NULL,NULL,19,27,'PN24000008'),(25,'2024-04-01 00:00:00.000000',NULL,6,NULL,30450000,13,1,'Nhập thêm hàng',NULL,NULL,20,28,'PN24000009'),(26,'2024-03-25 00:00:00.000000',NULL,6,NULL,23200000,13,1,'Nhập thêm hàng',NULL,NULL,21,29,'PN24000010'),(27,'2024-03-18 00:00:00.000000',NULL,5,NULL,51300000,38,1,'Nhập kho',NULL,NULL,22,30,'PN24000011'),(28,'2024-03-11 00:00:00.000000',NULL,5,NULL,43800000,23,1,'Nhập kho',NULL,NULL,23,31,'PN24000012'),(29,'2024-03-04 00:00:00.000000',NULL,5,NULL,47040000,43,1,'Nhập kho',NULL,NULL,24,32,'PN24000013'),(30,'2024-02-26 00:00:00.000000',NULL,5,NULL,44560000,22,1,'Nhập kho',NULL,NULL,25,33,'PN24000014'),(31,'2024-02-19 00:00:00.000000',NULL,5,NULL,30640000,17,1,'Nhập kho',NULL,NULL,26,34,'PN24000015'),(32,'2024-02-12 00:00:00.000000',NULL,5,NULL,52080000,46,1,'Nhập kho',NULL,NULL,27,35,'PN24000016'),(33,'2024-02-05 00:00:00.000000',NULL,5,NULL,28450000,12,1,'Nhập kho',NULL,NULL,28,36,'PN24000017'),(34,'2024-01-29 00:00:00.000000',NULL,5,NULL,14550000,12,1,'Nhập kho',NULL,NULL,29,37,'PN24000018'),(35,'2024-01-22 00:00:00.000000',NULL,5,NULL,35900000,21,1,'Nhập kho',NULL,NULL,30,38,'PN24000019'),(36,'2024-01-15 00:00:00.000000',NULL,5,NULL,45400000,33,1,'Nhập kho',NULL,NULL,31,39,'PN24000020'),(37,'2024-01-08 00:00:00.000000',NULL,5,NULL,38250000,19,1,'Nhập kho',NULL,NULL,32,40,'PN24000021'),(38,'2024-01-01 00:00:00.000000',NULL,5,NULL,37650000,25,1,'Nhập kho',NULL,NULL,33,41,'PN24000022');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-09 13:48:20
