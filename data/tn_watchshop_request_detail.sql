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
-- Table structure for table `request_detail`
--

DROP TABLE IF EXISTS `request_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `request_detail` (
  `request_detail_id` bigint NOT NULL AUTO_INCREMENT,
  `price` int DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `request_id` bigint DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `quantity_request` int DEFAULT NULL,
  PRIMARY KEY (`request_detail_id`),
  KEY `FKoj9rxxbq0rh53bbt6gt3p3vbw` (`product_id`),
  KEY `FKoyw8pwl3ol9oox49dqpbg6tko` (`request_id`),
  CONSTRAINT `FKoj9rxxbq0rh53bbt6gt3p3vbw` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `FKoyw8pwl3ol9oox49dqpbg6tko` FOREIGN KEY (`request_id`) REFERENCES `transaction_request` (`request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=338 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_detail`
--

LOCK TABLES `request_detail` WRITE;
/*!40000 ALTER TABLE `request_detail` DISABLE KEYS */;
INSERT INTO `request_detail` VALUES (53,1200000,'DH00000002',20,18,'Nhập hàng',20),(54,1000000,'DH00000003',20,18,'Nhập hàng',20),(55,1500000,'DH00000004',20,18,'Nhập hàng',20),(56,2000000,'DH00000005',20,18,'Nhập hàng',20),(57,1300000,'DH00000006',20,18,'Nhập hàng',20),(58,2500000,'DH00000007',20,18,'Nhập hàng',20),(59,3000000,'DH00000008',20,18,'Nhập hàng',20),(60,900000,'DH00000009',20,18,'Nhập hàng',20),(61,2100000,'DH00000010',20,18,'Nhập hàng',20),(62,2000000,'DH00000011',20,18,'Nhập hàng',20),(63,1800000,'DH00000012',20,18,'Nhập hàng',20),(64,1200000,'DH00000002',15,19,'Nhập hàng 15 cái',15),(65,1050000,'DH00000003',10,19,'Nhập hàng 10 cái',10),(66,1500000,'DH00000004',10,19,'Nhập hàng 10 cái',10),(67,1800000,'DH00000005',5,19,'Nhập hàng 5 cái',5),(68,1350000,'DH00000006',10,19,'Nhập hàng 10 cái',10),(69,2350000,'DH00000007',15,19,'Nhập hàng 15 cái',15),(70,3000000,'DH00000008',2,19,'Nhập hàng 2 cái',2),(71,1000000,'DH00000009',10,19,'Nhập hàng 10 cái',10),(72,2100000,'DH00000010',5,19,'Nhập hàng 5 cái',5),(73,2100000,'DH00000011',5,19,'Nhập hàng 5 cái',5),(74,1800000,'DH00000012',3,19,'Nhập hàng 3 cái',3),(75,1000000,'DH00000002',5,20,'Nhập thêm 5 cái',5),(76,1050000,'DH00000003',5,20,' Nhập thêm 5 cái',5),(77,1400000,'DH00000004',5,20,'Nhập thêm 5 cái',5),(78,1720000,'DH00000005',5,20,'Nhập thêm 5 cái',5),(79,900000,'DH00000009',10,20,'Nhập thêm 10 cái',10),(80,2000000,'DH00000012',5,20,'Nhập thêm 5 cái',5),(91,2930000,'DH00000008',8,23,'Nhập thêm 8 cái',8),(92,1900000,'DH00000012',2,23,'Nhập thêm 2 cái',2),(93,1200000,'DH00000004',10,23,'Nhập thêm 10 cái',10),(94,1000000,'DH00000009',5,23,'Nhập thêm 5 cái',5),(95,2000000,'DH00000010',6,24,'Nhập thêm 6 cái',6),(96,900000,'DH00000003',10,24,'Nhập thêm 10 cái',10),(97,2000000,'DH00000005',2,24,'Nhập thêm 2 cái',2),(98,2200000,'DH00000011',3,24,'Nhập thêm 3 cái',3),(99,3000000,'DH00000008',2,24,'Nhập thêm 2 cái',2),(100,900000,'DH00000002',10,24,'Nhập thêm 10 cái',10),(101,1300000,'DH00000006',10,24,'Nhập thêm 10 cái',10),(102,1900000,'DH00000005',5,25,'Nhập thêm 5 cái',5),(103,2900000,'DH00000008',5,25,'Nhập thêm 5 cái',5),(104,2100000,'DH00000011',10,25,' Nhập thêm 10 cái',10),(105,2000000,'DH00000010',5,25,'Nhập thêm 5 cái',5),(106,1900000,'DH00000012',5,25,'Nhập thêm 5 cái',5),(107,2300000,'DH00000007',10,25,' Nhập thêm 10 cái',10),(108,1000000,'DH00000003',2,26,'Nhap 2 cai',2),(109,3000000,'DH00000008',3,26,'Nhap 3 cai',3),(110,1950000,'DH00000005',2,26,' Nhap 2 cai',2),(111,1900000,'DH00000010',4,26,'Nhap 4 cai',4),(112,2400000,'DH00000011',2,26,'Nhap 2 cai',2),(113,1200000,'DH00000006',10,26,'Nhap 10 cai',10),(114,2000000,'DH00000012',5,27,'Them 2 cai',5),(115,700000,'DH00000002',15,27,'Them 15 cai',15),(116,950000,'DH00000003',5,27,'Them 5 cai',5),(117,1900000,'DH00000005',2,27,'them 2 cai',2),(118,1000000,'DH00000004',10,27,'Them 10 cai',10),(119,900000,'DH00000009',10,27,'Them 10 cai',10),(120,2400000,'DH00000007',5,28,'Them 5 cai',5),(121,2900000,'DH00000008',3,28,'Them 3 cai',3),(122,1950000,'DH00000010',5,28,'them 5 cai',5),(123,1150000,'DH00000006',5,29,'Them 5 cai',5),(124,2950000,'DH00000008',2,29,'Them 2 cai',2),(125,1850000,'DH00000005',3,29,' Them 3 cai',3),(126,2000000,'DH00000012',3,29,'Them 3 cai',3),(127,900000,'DH00000002',5,30,'Thêm 5 cái',5),(128,850000,'DH00000003',10,30,'Thêm 10 cái ',10),(129,950000,'DH00000004',5,30,'Thêm 5 cái',5),(130,1850000,'DH00000005',3,30,' Thêm 3 cái',3),(131,2350000,'DH00000007',5,30,' Thêm 5 cái',5),(132,1000000,'DH00000009',5,30,'Thêm 5 cái',5),(133,2250000,'DH00000011',5,30,'Thêm 5 cái',5),(134,1900000,'DH00000005',3,31,'Thêm 3 cái',3),(135,1800000,'DH00000012',7,31,' Thêm 7 cái',7),(136,2900000,'DH00000008',5,31,'Thêm 5 cái',5),(137,1000000,'DH00000006',5,31,'Thêm 5 cái',5),(138,2000000,'DH00000010',3,31,'Thêm 3 cái',3),(139,800000,'DH00000002',10,32,'Nhập 10 cái',10),(140,780000,'DH00000003',8,32,'Nhập 8 cái',8),(141,800000,'DH00000004',10,32,'Nhập 10 cái',10),(142,1850000,'DH00000005',2,32,'Nhập 2 cái',2),(143,1050000,'DH00000006',3,32,'Nhập 3 cái',3),(144,2400000,'DH00000007',3,32,'Nhập 3 cái',3),(145,3000000,'DH00000008',2,32,'Nhập 2 cái',2),(146,950000,'DH00000009',5,32,'Nhập 5 cái',5),(147,2000000,'DH00000011',10,33,'Thêm 10 cái',10),(148,1870000,'DH00000012',3,33,'Thêm 3 cái',3),(149,3000000,'DH00000008',2,33,'Thêm 2 cái',2),(150,1850000,'DH00000010',5,33,'Thêm 5 cái',5),(151,1850000,'DH00000005',2,33,' Thêm 2 cái',2),(152,1900000,'DH00000005',3,34,'Thêm 3 cái',3),(153,1700000,'DH00000012',5,34,'Thêm 5 cái',5),(154,850000,'DH00000002',4,34,'Thêm ',4),(155,2380000,'DH00000007',3,34,' Thêm ',3),(156,2950000,'DH00000008',2,34,'Thêm ',2),(157,1800000,'DH00000011',5,35,'Thêm',5),(158,800000,'DH00000009',10,35,' Thêm',10),(159,2320000,'DH00000007',4,35,' Thêm',4),(160,900000,'DH00000002',5,35,' Thêm',5),(161,800000,'DH00000003',5,35,' Thêm',5),(162,900000,'DH00000004',5,35,' Thêm',5),(163,900000,'DH00000006',10,35,' Thêm',10),(164,1900000,'DH00000010',2,35,' Thêm',2),(165,1850000,'DH00000012',3,36,'Thêm',3),(166,2800000,'DH00000008',5,36,' Thêm',5),(167,2450000,'DH00000007',2,36,' Thêm',2),(168,2000000,'DH00000010',2,36,' Thêm',2),(169,1000000,'DH00000002',2,37,'Thêm',2),(170,1000000,'DH00000003',2,37,' Thêm',2),(171,1000000,'DH00000004',2,37,' Thêm',2),(172,1000000,'DH00000009',3,37,' Thêm',3),(173,1850000,'DH00000011',3,37,' Thêm',3),(174,1800000,'DH00000005',5,38,'Thêm',5),(175,950000,'DH00000006',5,38,' Thêm',5),(176,2450000,'DH00000007',3,38,' Thêm',3),(177,2800000,'DH00000008',4,38,' Thêm',4),(178,900000,'DH00000012',4,38,'  Thêm',4),(179,1100000,'DH00000002',5,39,' Thêm',5),(180,1000000,'DH00000003',5,39,' Thêm',5),(181,1000000,'DH00000004',3,39,' Thêm',3),(182,1850000,'DH00000005',2,39,' Thêm',2),(183,1000000,'DH00000006',2,39,' Thêm',2),(184,2450000,'DH00000007',2,39,' Thêm',2),(185,2800000,'DH00000008',2,39,' Thêm',2),(186,1000000,'DH00000009',2,39,' Thêm',2),(187,2000000,'DH00000010',3,39,' Thêm',3),(188,1850000,'DH00000011',2,39,' Thêm',2),(189,800000,'DH00000012',5,39,' Thêm',5),(190,2450000,'DH00000007',3,40,'Thêm',3),(191,1800000,'DH00000005',3,40,' Thêm',3),(192,2750000,'DH00000008',3,40,' Thêm',3),(193,1650000,'DH00000010',5,40,' Thêm',5),(194,1800000,'DH00000011',5,40,' Thêm',5),(195,1850000,'DH00000005',3,41,'Thêm',3),(196,1650000,'DH00000010',5,41,' Thêm',5),(197,2450000,'DH00000007',5,41,' Thêm',5),(198,1100000,'DH00000002',4,41,' Thêm',4),(199,900000,'DH00000003',8,41,' Thêm',8),(200,900000,'DH00000009',10,42,'Thêm',10),(201,900000,'DH00000004',5,42,'Thêm',5),(202,800000,'DH00000006',10,42,'Thêm',10),(203,2800000,'DH00000008',2,42,'Thêm',2),(204,2800000,'DH00000008',3,43,'Thêm',3),(205,800000,'DH00000009',10,43,'Thêm',10),(206,800000,'DH00000004',10,43,'Thêm',10),(207,1000000,'DH00000006',5,43,'Thêm',5),(208,1750000,'DH00000011',7,44,'Them',7),(209,900000,'DH00000012',10,44,'Them',10),(210,1700000,'DH00000010',3,44,'Them',3),(211,2500000,'DH00000007',3,44,'Them',3),(212,1900000,'DH00000005',3,44,'Them',3),(213,950000,'DH00000002',10,45,'THEM',10),(214,950000,'DH00000003',5,45,'THEM',5),(215,1900000,'DH00000005',5,45,'THEM',5),(216,970000,'DH00000006',5,45,'THEM',5),(217,2600000,'DH00000007',2,45,'THEM',2),(218,1800000,'DH00000010',4,46,'THEM',4),(219,900000,'DH00000009',5,46,'THEM',5),(220,2800000,'DH00000008',3,46,'THEM',3),(221,2700000,'DH00000007',2,46,' THEM',2),(222,1000000,'DH00000006',2,46,'THEM',2),(223,1900000,'DH00000005',2,46,' Thêm',2),(224,900000,'DH00000004',5,47,'THEM',5),(225,1000000,'DH00000003',3,47,'THEM',3),(226,1000000,'DH00000002',3,47,'THEM',3),(227,1950000,'DH00000005',3,47,'THEM',3),(228,1000000,'DH00000006',3,47,'THEM',3),(229,2800000,'DH00000007',4,48,'THEM',4),(230,2800000,'DH00000008',3,48,'THEM',3),(231,1000000,'DH00000009',3,48,'THEM',3),(232,2000000,'DH00000010',3,48,'THEM',3),(233,1800000,'DH00000011',5,49,'THEM',5),(234,1000000,'DH00000012',5,49,'THEM',5),(235,900000,'DH00000002',5,49,'THEM',5),(236,1100000,'DH00000003',2,49,'THEM',2),(237,900000,'DH00000004',5,50,'THEM',5),(238,2000000,'DH00000005',8,50,'THEM',8),(239,900000,'DH00000006',5,50,'THEM',5),(240,2800000,'DH00000007',2,50,'THEM',2),(241,900000,'DH00000002',5,51,'THEM',5),(242,950000,'DH00000003',6,51,'THEM',6),(243,2700000,'DH00000008',5,51,'THEM',5),(244,1000000,'DH00000009',5,51,'THEM',5),(245,1900000,'DH00000010',5,51,'THEM',5),(246,1800000,'DH00000011',3,51,'THEM',3),(247,900000,'DH00000012',8,52,'THEM',8),(248,900000,'DH00000004',6,52,'THEM',6),(249,2000000,'DH00000005',3,52,'THEM',3),(250,1000000,'DH00000006',5,52,'THEM',5),(251,2800000,'DH00000007',3,52,'THEM',3),(252,900000,'DH00000002',8,53,'Them',8),(253,900000,'DH00000003',8,53,'Them',8),(254,2800000,'DH00000008',5,53,'THEM',5),(255,900000,'DH00000009',5,53,'THEM',5),(256,2000000,'DH00000010',3,53,'THEM',3),(257,1900000,'DH00000011',3,53,'THEM',3),(258,900000,'DH00000004',5,54,'THEM',5),(259,1900000,'DH00000005',5,54,'THEM',5),(260,1100000,'DH00000006',3,54,'THEM',3),(261,2900000,'DH00000007',3,54,'THEM',3),(262,2800000,'DH00000008',2,54,'THEM',2),(263,850000,'DH00000002',5,55,'THEM',5),(264,850000,'DH00000003',3,55,'THEM',3),(265,850000,'DH00000004',3,55,'THEM',3),(266,1900000,'DH00000005',2,55,'THEM',2),(267,1100000,'DH00000006',2,55,'THEM',2),(268,2950000,'DH00000007',2,55,'THEM',2),(269,2800000,'DH00000008',2,55,'THEM',2),(270,900000,'DH00000009',5,55,'THEM',5),(271,1950000,'DH00000010',3,55,'THEM',3),(272,1850000,'DH00000011',3,55,'THEM',3),(273,1000000,'DH00000012',4,55,'THEM',4),(274,800000,'DH00000002',6,56,'THEM',6),(275,800000,'DH00000003',5,56,'THEM',5),(276,800000,'DH00000004',5,56,'THEM',5),(277,3000000,'DH00000007',3,56,'THEM',3),(278,1000000,'DH00000009',5,56,'THEM',5),(279,2900000,'DH00000008',4,56,'THEM',4),(280,1900000,'DH00000005',4,57,'THEM',4),(281,1000000,'DH00000006',5,57,'THEM',5),(282,1900000,'DH00000010',8,57,'THEM',8),(283,1900000,'DH00000011',5,57,'THEM',5),(284,1000000,'DH00000012',8,57,'THEM',8),(285,200000,'DH00000003',1,58,NULL,1),(286,210000,'DH00000008',1,58,NULL,1),(287,1099,'DH00001',5,59,NULL,5),(288,5400000,'DH00000004',2,60,NULL,2),(289,230000,'DH00000005',3,60,NULL,3),(290,1210000,'DH00000009',1,60,NULL,1),(291,5500000,'DH00000002',2,61,NULL,2),(292,5400000,'DH00000004',1,61,NULL,1),(293,230000,'DH00000005',1,62,NULL,1),(294,200000,'DH00000003',1,62,NULL,1),(295,210000,'DH00000008',1,62,NULL,1),(296,196000,'DH00000003',2,63,NULL,2),(297,225400,'DH00000005',1,63,NULL,1),(298,205800,'DH00000008',1,63,NULL,1),(299,225400,'DH00000005',1,64,NULL,1),(300,1350000,'DH00000004',2,65,NULL,2),(301,1000000,'DH00000002',1,65,NULL,1),(302,1350000,'DH00000004',1,66,NULL,1),(303,1000000,'DH00000002',2,67,NULL,2),(304,1350000,'DH00000004',1,67,NULL,1),(305,2450000,'DH00000005',1,67,NULL,1),(306,2480000,'DH00000012',1,68,NULL,1),(307,3000000,'DH00000011',1,68,NULL,1),(308,3400000,'DH00000008',1,68,NULL,1),(309,1210000,'DH00000009',1,69,NULL,1),(310,3400000,'DH00000008',1,69,NULL,1),(311,2850000,'DH00000007',2,69,NULL,2),(312,2450000,'DH00000005',1,69,NULL,1),(313,2450000,'DH00000005',1,70,NULL,1),(314,1630000,'DH00000006',1,70,NULL,1),(315,2850000,'DH00000007',1,70,NULL,1),(316,3400000,'DH00000008',1,70,NULL,1),(317,1210000,'DH00000009',1,70,NULL,1),(318,2480000,'DH00000010',1,70,NULL,1),(319,200000,'DH00000003',1,71,NULL,1),(320,5400000,'DH00000004',1,71,NULL,1),(321,5500000,'DH00000002',1,71,NULL,1),(322,1000000,'DH00000002',2,72,NULL,2),(323,1500000,'DH00000003',1,72,NULL,1),(324,1350000,'DH00000004',2,72,NULL,2),(325,1000000,'DH00000002',3,73,NULL,3),(326,1500000,'DH00000003',3,73,NULL,3),(327,1350000,'DH00000004',1,73,NULL,1),(328,1630000,'DH00000006',2,73,NULL,2),(329,1000000,'DH00000002',3,74,NULL,3),(330,1500000,'DH00000003',3,74,NULL,3),(331,1350000,'DH00000004',3,74,NULL,3),(332,2450000,'DH00000005',1,74,NULL,1),(333,1630000,'DH00000006',2,74,NULL,2),(334,200000,'DH00001',1,75,NULL,1),(335,1500000,'DH00000003',1,76,NULL,1),(336,1500000,'DH00000003',2,77,NULL,2),(337,1350000,'DH00000004',2,77,NULL,2);
/*!40000 ALTER TABLE `request_detail` ENABLE KEYS */;
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
