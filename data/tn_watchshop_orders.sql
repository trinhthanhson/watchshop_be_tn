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
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(120) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `customer_id` bigint NOT NULL,
  `note` varchar(120) DEFAULT NULL,
  `recipient_name` varchar(255) NOT NULL,
  `recipient_phone` char(13) NOT NULL,
  `staff_id` bigint DEFAULT NULL,
  `total_price` int NOT NULL,
  `total_quantity` int NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `status_id` bigint NOT NULL,
  `is_cancel` char(1) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FKo426f4qscnvs99yc6rsubjd99` (`updated_by`),
  KEY `FK624gtjin3po807j3vix093tlf` (`customer_id`),
  KEY `FK1abokg3ghque9pf2ujbxakng` (`status_id`),
  KEY `FK4ery255787xl56k025fyxrqe9` (`staff_id`),
  CONSTRAINT `FK1abokg3ghque9pf2ujbxakng` FOREIGN KEY (`status_id`) REFERENCES `order_status` (`status_id`),
  CONSTRAINT `FK4ery255787xl56k025fyxrqe9` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`),
  CONSTRAINT `FK624gtjin3po807j3vix093tlf` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `FKo426f4qscnvs99yc6rsubjd99` FOREIGN KEY (`updated_by`) REFERENCES `staff` (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'Son, 9292393121923, son','2024-10-23 21:29:59.074879',1,'k','Son','9292393121923',1,5495,5,'2024-10-23 21:29:59.074879',NULL,7,'f'),(3,'Trịnh Thanh Sơn, 0363000451, VietNam','2024-11-11 21:27:51.219357',6,'Khong','Trịnh Thanh Sơn','0363000451',1,10860000,11,'2024-11-11 21:27:51.219357',NULL,9,'f'),(4,'Trinh, 0987654321, VietNam','2024-11-22 22:37:46.538569',1,'Khong','Trinh','0987654321',NULL,410000,2,'2024-11-22 22:37:46.538569',NULL,7,'f'),(5,'Trinh Thanh Son, 0363000451, VietNam','2024-11-27 00:10:15.387437',1,'Khong','Trinh Thanh Son','0363000451',NULL,16700000,4,'2024-11-27 00:10:15.387437',NULL,13,'f'),(6,'Trinh Son, 0363000451, 12A Hem 63 Dương 10, Phuong TNPB, TP Thu Duc','2024-11-27 20:55:18.807533',1,'','Trinh Son','0363000451',NULL,12700000,6,'2024-11-27 20:55:18.807533',NULL,7,'f'),(7,'Son, 0987654321, VN','2024-12-03 17:49:56.551611',1,'N','Son','0987654321',NULL,0,3,'2024-12-03 17:49:56.551611',NULL,7,'f'),(8,'Trinh, 09872162632, Son','2024-12-04 22:36:40.388664',6,'Khong','Trinh','09872162632',NULL,10878000,3,'2024-12-04 22:36:40.388664',NULL,7,'f'),(9,'Son, 092319391, ne','2024-12-04 22:40:28.586469',6,'kh','Son','092319391',NULL,627200,3,'2024-12-04 22:40:28.586469',NULL,7,'f'),(10,'adad, 1231, adad','2024-12-05 00:03:57.115107',6,'ad','adad','1231',NULL,823200,4,'2024-12-05 00:03:57.115107',NULL,7,'f'),(11,'ádada, 1313, sdad','2024-12-05 00:35:11.803014',6,'ada','ádada','1313',NULL,225400,1,'2024-12-05 00:35:11.803014',NULL,7,'f'),(12,'Trinh Thanh Son, 0363000451, VietNam','2024-12-07 22:24:00.120491',6,'','Trinh Thanh Son','0363000451',NULL,3700000,3,'2024-12-07 22:24:00.120491',NULL,7,'f');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-09 13:48:19
