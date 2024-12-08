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
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `supplier_id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `fax` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `supplier_name` varchar(255) NOT NULL,
  `tax_id` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` bigint NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`supplier_id`),
  KEY `FKpw044autk3kaseiuw632cq75` (`updated_by`),
  KEY `FKf3js4klgx8j04a651wxiaoqp8` (`created_by`),
  CONSTRAINT `FKf3js4klgx8j04a651wxiaoqp8` FOREIGN KEY (`created_by`) REFERENCES `staff` (`staff_id`),
  CONSTRAINT `FKpw044autk3kaseiuw632cq75` FOREIGN KEY (`updated_by`) REFERENCES `staff` (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (15,'123 Đường Lê Lợi, Quận 1, TP.HCM','info@donghoA.vn','0281234567','0287654321','Công ty Đồng Hồ A','0101234567','ACTIVE','2024-12-06 23:28:32.493881',6,'2024-12-06 23:28:32.493881',6),(16,'456 Đường Nguyễn Trãi, Hà Nội','contact@donghoB.vn','0242345678','0248765432','Công ty Đồng Hồ B','0102345678','ACTIVE','2024-12-06 23:29:04.442982',6,'2024-12-06 23:29:04.442982',6),(17,' Đồng Hồ C	789 Đường Lý Thường Kiệt, Đà Nẵng','info@donghoC.vn','02363456789','02369876543','Công ty Đồng Hồ C','0103456789','ACTIVE','2024-12-06 23:29:32.022697',6,'2024-12-06 23:29:32.022697',6),(18,'123 Đường Phạm Ngọc Thạch, HCM','sales@donghoD.vn','0289876543','0285432109','Công ty Đồng Hồ D','0104567890','ACTIVE','2024-12-06 23:29:55.567568',6,'2024-12-06 23:29:55.567568',6),(19,'456 Đường Lê Văn Sỹ, TP.HCM','support@donghoE.vn','0286543210','0280123456','Công ty Đồng Hồ E','0105678901','ACTIVE','2024-12-06 23:30:18.851536',6,'2024-12-06 23:30:18.851536',6),(20,'789 Đường Nguyễn Huệ, Hà Nội','info@donghoF.vn','0249876543','0243210987','Công ty Đồng Hồ F','0106789012','ACTIVE','2024-12-06 23:30:41.023567',6,'2024-12-06 23:30:41.023567',6),(21,'321 Đường Trần Hưng Đạo, HCM','contact@donghoG.vn','0284567890','0282345678','Công ty Đồng Hồ G','0107890123','ACTIVE','2024-12-06 23:31:06.895415',6,'2024-12-06 23:31:06.895415',6),(22,'654 Đường Đinh Tiên Hoàng, HCM','info@donghoH.vn','0282345678','0288765432	','Công ty Đồng Hồ H','0108901234','ACTIVE','2024-12-06 23:31:40.457493',6,'2024-12-06 23:31:40.458496',6);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-09  2:52:41
