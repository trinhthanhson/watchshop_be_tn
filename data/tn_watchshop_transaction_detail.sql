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
-- Table structure for table `transaction_detail`
--

DROP TABLE IF EXISTS `transaction_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_detail` (
  `trans_detail_id` bigint NOT NULL AUTO_INCREMENT,
  `price` int DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `transaction_id` bigint DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `quantity_request` int DEFAULT NULL,
  `start_quantity` int DEFAULT NULL,
  PRIMARY KEY (`trans_detail_id`),
  KEY `FKp0202i7eat7u1q5i9sxm1442k` (`product_id`),
  KEY `FK2nh7hmi2mfurimsk0viq4a127` (`transaction_id`),
  CONSTRAINT `FK2nh7hmi2mfurimsk0viq4a127` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`transaction_id`),
  CONSTRAINT `FKp0202i7eat7u1q5i9sxm1442k` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=237 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_detail`
--

LOCK TABLES `transaction_detail` WRITE;
/*!40000 ALTER TABLE `transaction_detail` DISABLE KEYS */;
INSERT INTO `transaction_detail` VALUES (48,1200000,'DH00000002',20,17,'Nhập hàng',20,0),(49,1000000,'DH00000003',20,17,'Nhập hàng',20,0),(50,1500000,'DH00000004',20,17,'Nhập hàng',20,0),(51,2000000,'DH00000005',20,17,'Nhập hàng',20,0),(52,1300000,'DH00000006',20,17,'Nhập hàng',20,0),(53,2500000,'DH00000007',20,17,'Nhập hàng',20,0),(54,3000000,'DH00000008',20,17,'Nhập hàng',20,0),(55,900000,'DH00000009',20,17,'Nhập hàng',20,0),(56,2100000,'DH00000010',20,17,'Nhập hàng',20,0),(57,2000000,'DH00000011',20,17,'Nhập hàng',20,0),(58,1800000,'DH00000012',20,17,'Nhập hàng',20,0),(59,1200000,'DH00000002',15,18,'Nhập hàng 15 cái',15,20),(60,1050000,'DH00000003',10,18,'Nhập hàng 10 cái',10,20),(61,1500000,'DH00000004',10,18,'Nhập hàng 10 cái',10,20),(62,1800000,'DH00000005',5,18,'Nhập hàng 5 cái',5,20),(63,1350000,'DH00000006',10,18,'Nhập hàng 10 cái',10,20),(64,2350000,'DH00000007',15,18,'Nhập hàng 15 cái',15,20),(65,3000000,'DH00000008',2,18,'Nhập hàng 2 cái',2,20),(66,1000000,'DH00000009',10,18,'Nhập hàng 10 cái',10,20),(67,2100000,'DH00000010',5,18,'Nhập hàng 5 cái',5,20),(68,2100000,'DH00000011',5,18,'Nhập hàng 5 cái',5,20),(69,1800000,'DH00000012',3,18,'Nhập hàng 3 cái',3,20),(70,1000000,'DH00000002',5,19,'Nhập thêm 5 cái',5,35),(71,1050000,'DH00000003',5,19,' Nhập thêm 5 cái',5,30),(72,1400000,'DH00000004',5,19,'Nhập thêm 5 cái',5,30),(73,1720000,'DH00000005',5,19,'Nhập thêm 5 cái',5,25),(74,900000,'DH00000009',10,19,'Nhập thêm 10 cái',10,30),(75,2000000,'DH00000012',5,19,'Nhập thêm 5 cái',5,23),(76,2930000,'DH00000008',8,20,'Nhập thêm 8 cái',8,22),(77,1900000,'DH00000012',2,20,'Nhập thêm 2 cái',2,28),(78,1200000,'DH00000004',10,20,'Nhập thêm 10 cái',10,35),(79,1000000,'DH00000009',5,20,'Nhập thêm 5 cái',5,40),(80,900000,'DH00000002',10,21,'Nhập thêm 10 cái',10,40),(81,900000,'DH00000003',10,21,'Nhập thêm 10 cái',10,35),(82,2000000,'DH00000005',2,21,'Nhập thêm 2 cái',2,30),(83,1300000,'DH00000006',10,21,'Nhập thêm 10 cái',10,30),(84,3000000,'DH00000008',2,21,'Nhập thêm 2 cái',2,30),(85,2000000,'DH00000010',6,21,'Nhập thêm 6 cái',6,25),(86,2200000,'DH00000011',3,21,'Nhập thêm 3 cái',3,25),(87,1900000,'DH00000005',5,22,'Nhập thêm 5 cái',5,32),(88,2900000,'DH00000008',5,22,'Nhập thêm 5 cái',5,32),(89,2100000,'DH00000011',10,22,' Nhập thêm 10 cái',10,28),(90,2000000,'DH00000010',5,22,'Nhập thêm 5 cái',5,31),(91,1900000,'DH00000012',5,22,'Nhập thêm 5 cái',5,30),(92,2300000,'DH00000007',10,22,' Nhập thêm 10 cái',10,35),(93,1000000,'DH00000003',2,23,'Nhap 2 cai',2,45),(94,3000000,'DH00000008',3,23,'Nhap 3 cai',3,37),(95,1950000,'DH00000005',2,23,' Nhap 2 cai',2,37),(96,1900000,'DH00000010',4,23,'Nhap 4 cai',4,36),(97,2400000,'DH00000011',2,23,'Nhap 2 cai',2,38),(98,1200000,'DH00000006',10,23,'Nhap 10 cai',10,40),(99,700000,'DH00000002',15,24,'Them 15 cai',15,50),(100,950000,'DH00000003',5,24,'Them 5 cai',5,47),(101,1000000,'DH00000004',10,24,'Them 10 cai',10,45),(102,1900000,'DH00000005',2,24,'them 2 cai',2,39),(103,900000,'DH00000009',10,24,'Them 10 cai',10,45),(104,2000000,'DH00000012',5,24,'Them 2 cai',5,35),(105,2400000,'DH00000007',5,25,'Them 5 cai',5,45),(106,2900000,'DH00000008',3,25,'Them 3 cai',3,40),(107,1950000,'DH00000010',5,25,'them 5 cai',5,40),(108,1850000,'DH00000005',3,26,' Them 3 cai',3,41),(109,1150000,'DH00000006',5,26,'Them 5 cai',5,50),(110,2950000,'DH00000008',2,26,'Them 2 cai',2,43),(111,2000000,'DH00000012',3,26,'Them 3 cai',3,40),(112,900000,'DH00000002',5,27,'Thêm 5 cái',5,65),(113,850000,'DH00000003',10,27,'Thêm 10 cái ',10,52),(114,950000,'DH00000004',5,27,'Thêm 5 cái',5,55),(115,1850000,'DH00000005',3,27,' Thêm 3 cái',3,44),(116,2350000,'DH00000007',5,27,' Thêm 5 cái',5,50),(117,1000000,'DH00000009',5,27,'Thêm 5 cái',5,55),(118,2250000,'DH00000011',5,27,'Thêm 5 cái',5,40),(119,1900000,'DH00000005',3,28,'Thêm 3 cái',3,47),(120,1000000,'DH00000006',5,28,'Thêm 5 cái',5,55),(121,2900000,'DH00000008',5,28,'Thêm 5 cái',5,45),(122,2000000,'DH00000010',3,28,'Thêm 3 cái',3,45),(123,1800000,'DH00000012',7,28,' Thêm 7 cái',7,43),(124,800000,'DH00000002',10,29,'Nhập 10 cái',10,70),(125,780000,'DH00000003',8,29,'Nhập 8 cái',8,62),(126,800000,'DH00000004',10,29,'Nhập 10 cái',10,60),(127,1850000,'DH00000005',2,29,'Nhập 2 cái',2,50),(128,1050000,'DH00000006',3,29,'Nhập 3 cái',3,60),(129,2400000,'DH00000007',3,29,'Nhập 3 cái',3,55),(130,3000000,'DH00000008',2,29,'Nhập 2 cái',2,50),(131,950000,'DH00000009',5,29,'Nhập 5 cái',5,60),(132,1850000,'DH00000005',2,30,' Thêm 2 cái',2,52),(133,3000000,'DH00000008',2,30,'Thêm 2 cái',2,52),(134,1850000,'DH00000010',5,30,'Thêm 5 cái',5,48),(135,2000000,'DH00000011',10,30,'Thêm 10 cái',10,45),(136,1870000,'DH00000012',3,30,'Thêm 3 cái',3,50),(137,850000,'DH00000002',4,31,'Thêm ',4,80),(138,1900000,'DH00000005',3,31,'Thêm 3 cái',3,54),(139,2380000,'DH00000007',3,31,' Thêm ',3,58),(140,2950000,'DH00000008',2,31,'Thêm ',2,54),(141,1700000,'DH00000012',5,31,'Thêm 5 cái',5,53),(142,900000,'DH00000002',5,32,' Thêm',5,84),(143,800000,'DH00000003',5,32,' Thêm',5,70),(144,900000,'DH00000004',5,32,' Thêm',5,70),(145,900000,'DH00000006',10,32,' Thêm',10,63),(146,2320000,'DH00000007',4,32,' Thêm',4,61),(147,800000,'DH00000009',10,32,' Thêm',10,65),(148,1900000,'DH00000010',2,32,' Thêm',2,53),(149,1800000,'DH00000011',5,32,'Thêm',5,55),(150,2450000,'DH00000007',2,33,' Thêm',2,65),(151,2800000,'DH00000008',5,33,' Thêm',5,56),(152,2000000,'DH00000010',2,33,' Thêm',2,55),(153,1850000,'DH00000012',3,33,'Thêm',3,58),(154,1000000,'DH00000002',2,34,'Thêm',2,89),(155,1000000,'DH00000003',2,34,' Thêm',2,75),(156,1000000,'DH00000004',2,34,' Thêm',2,75),(157,1000000,'DH00000009',3,34,' Thêm',3,75),(158,1850000,'DH00000011',3,34,' Thêm',3,60),(159,1800000,'DH00000005',5,35,'Thêm',5,57),(160,950000,'DH00000006',5,35,' Thêm',5,73),(161,2450000,'DH00000007',3,35,' Thêm',3,67),(162,2800000,'DH00000008',4,35,' Thêm',4,61),(163,900000,'DH00000012',4,35,'  Thêm',4,61),(164,1100000,'DH00000002',5,36,' Thêm',5,91),(165,1000000,'DH00000003',5,36,' Thêm',5,77),(166,1000000,'DH00000004',3,36,' Thêm',3,77),(167,1850000,'DH00000005',2,36,' Thêm',2,62),(168,1000000,'DH00000006',2,36,' Thêm',2,78),(169,2450000,'DH00000007',2,36,' Thêm',2,70),(170,2800000,'DH00000008',2,36,' Thêm',2,65),(171,1000000,'DH00000009',2,36,' Thêm',2,78),(172,2000000,'DH00000010',3,36,' Thêm',3,57),(173,1850000,'DH00000011',2,36,' Thêm',2,63),(174,800000,'DH00000012',5,36,' Thêm',5,65),(175,1800000,'DH00000005',3,37,' Thêm',3,64),(176,2450000,'DH00000007',3,37,'Thêm',3,72),(177,2750000,'DH00000008',3,37,' Thêm',3,67),(178,1650000,'DH00000010',5,37,' Thêm',5,60),(179,1800000,'DH00000011',5,37,' Thêm',5,65),(180,1100000,'DH00000002',4,38,' Thêm',4,96),(181,900000,'DH00000003',8,38,' Thêm',8,82),(182,1850000,'DH00000005',3,38,'Thêm',3,67),(183,2450000,'DH00000007',5,38,' Thêm',5,75),(184,1650000,'DH00000010',5,38,' Thêm',5,65),(185,900000,'DH00000004',5,39,'Thêm',5,53),(186,800000,'DH00000006',10,39,'Thêm',10,55),(187,2800000,'DH00000008',2,39,'Thêm',2,45),(188,900000,'DH00000009',10,39,'Thêm',10,55),(189,800000,'DH00000004',10,40,'Thêm',10,58),(190,1000000,'DH00000006',5,40,'Thêm',5,65),(191,2800000,'DH00000008',3,40,'Thêm',3,47),(192,800000,'DH00000009',10,40,'Thêm',10,65),(193,1900000,'DH00000005',3,41,'Them',3,44),(194,2500000,'DH00000007',3,41,'Them',3,50),(195,1700000,'DH00000010',3,41,'Them',3,45),(196,1750000,'DH00000011',7,41,'Them',7,40),(197,900000,'DH00000012',10,41,'Them',10,43),(198,950000,'DH00000002',10,42,'THEM',10,64),(199,950000,'DH00000003',5,42,'THEM',5,52),(200,1900000,'DH00000005',5,42,'THEM',5,47),(201,970000,'DH00000006',5,42,'THEM',5,70),(202,2600000,'DH00000007',2,42,'THEM',2,53),(203,1900000,'DH00000005',2,43,' Thêm',2,52),(204,1000000,'DH00000006',2,43,'THEM',2,75),(205,2700000,'DH00000007',2,43,' THEM',2,55),(206,2800000,'DH00000008',3,43,'THEM',3,50),(207,900000,'DH00000009',5,43,'THEM',5,75),(208,1800000,'DH00000010',4,43,'THEM',4,48),(209,1000000,'DH00000002',3,44,'THEM',3,74),(210,1000000,'DH00000003',3,44,'THEM',3,57),(211,900000,'DH00000004',5,44,'THEM',5,68),(212,1950000,'DH00000005',3,44,'THEM',3,54),(213,1000000,'DH00000006',3,44,'THEM',3,77),(214,2800000,'DH00000007',4,45,'THEM',4,57),(215,2800000,'DH00000008',3,45,'THEM',3,53),(216,1000000,'DH00000009',3,45,'THEM',3,80),(217,2000000,'DH00000010',3,45,'THEM',3,52),(218,900000,'DH00000002',5,46,'THEM',5,77),(219,1100000,'DH00000003',2,46,'THEM',2,60),(220,1800000,'DH00000011',5,46,'THEM',5,47),(221,1000000,'DH00000012',5,46,'THEM',5,53),(222,900000,'DH00000004',5,47,'THEM',5,73),(223,2000000,'DH00000005',8,47,'THEM',8,57),(224,900000,'DH00000006',5,47,'THEM',5,80),(225,2800000,'DH00000007',2,47,'THEM',2,61),(226,900000,'DH00000002',5,48,'THEM',5,82),(227,950000,'DH00000003',6,48,'THEM',6,62),(228,2700000,'DH00000008',5,48,'THEM',5,56),(229,1000000,'DH00000009',5,48,'THEM',5,83),(230,1900000,'DH00000010',5,48,'THEM',5,55),(231,1800000,'DH00000011',3,48,'THEM',3,52),(232,900000,'DH00000004',6,49,'THEM',6,78),(233,2000000,'DH00000005',3,49,'THEM',3,65),(234,1000000,'DH00000006',5,49,'THEM',5,85),(235,2800000,'DH00000007',3,49,'THEM',3,63),(236,900000,'DH00000012',8,49,'THEM',8,58);
/*!40000 ALTER TABLE `transaction_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-09 20:24:05
