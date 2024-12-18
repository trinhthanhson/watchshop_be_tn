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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` varchar(255) NOT NULL,
  `band_material` varchar(255) DEFAULT NULL,
  `band_width` varchar(255) DEFAULT NULL,
  `brand_id` bigint NOT NULL,
  `case_diameter` varchar(255) DEFAULT NULL,
  `case_material` varchar(255) DEFAULT NULL,
  `case_thickness` varchar(255) DEFAULT NULL,
  `category_id` bigint NOT NULL,
  `color` varchar(255) DEFAULT NULL,
  `detail` text,
  `dial_type` varchar(255) DEFAULT NULL,
  `func` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `machine_movement` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) NOT NULL,
  `quantity` int DEFAULT NULL,
  `series` varchar(255) DEFAULT NULL,
  `water_resistance` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `created_by` bigint NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `warranty` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `FKsbf1yw1t3dlftr2cgt9dcnivo` (`updated_by`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  KEY `FKnlpf7fjl0vsov3kdt5ofc7kmp` (`created_by`),
  KEY `FKs6cydsualtsrprvlf2bb3lcam` (`brand_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `FKnlpf7fjl0vsov3kdt5ofc7kmp` FOREIGN KEY (`created_by`) REFERENCES `staff` (`staff_id`),
  CONSTRAINT `FKs6cydsualtsrprvlf2bb3lcam` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`brand_id`),
  CONSTRAINT `FKsbf1yw1t3dlftr2cgt9dcnivo` FOREIGN KEY (`updated_by`) REFERENCES `staff` (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('DH00000002','Leather','28 mm',1,'66 mm x 57 mm','Stainless Steel','14 mm',1,'Black','The Diesel Mr. Daddy 2.0 series watch features a stainless steel 66 mm x 57 mm case, with a fixed bezel a black (four time zone) dial and a scratch resistant mineral crystal.\n\nChronograph - sub-dials displaying: 60 second, 30 minute and 24 hour.\n\nThe 28 mm leather band is fitted with a tang clasp .\n\nThis beautiful wristwatch, powered by quartz movement, supports: chronograph, date, hour, minute, second, and 4 time zones functions.\n\nThis watch has a water resistance of up to 100 feet/30 meters, suitable for short periods of recreational swimming.\n\nThis stylish timepiece is sure to complete any man\'s collection.','Analog','	Chronograph, Date, Hour, Minute, Second, and 4 Time Zones','Mens','Quartz','DZ7371','Men\'s Mr. Daddy 2.0 Chronograph Black Leather Black (Four Time Zone) Dial',25,'Mr. Daddy 2.0','30 meters / 100 feet','https://firebasestorage.googleapis.com/v0/b/watch-shop-3a14f.appspot.com/o/images%2Fproducts%2Fmens-mr-daddy-2-0-chronograph-leather-black-four-time-zone-dial-watch-diesel-dz7371_1.jpg?alt=media&token=c0337460-24cf-4b54-b5ab-71c0e2ab9374',1,1,'ACTIVE','2024-10-26 21:40:28.909864','2024-10-26 21:40:28.909864',NULL),('DH00000003','Leather','28 mm',1,'66 mm x 57 mm','Stainless Steel','14 mm',1,'Black','The Diesel Mr. Daddy 2.0 series watch features a stainless steel 66 mm x 57 mm case, with a fixed bezel a black (four time zone) dial and a scratch resistant mineral crystal.\n\nChronograph - sub-dials displaying: 60 second, 30 minute and 24 hour.\n\nThe 28 mm leather band is fitted with a tang clasp .\n\nThis beautiful wristwatch, powered by quartz movement, supports: chronograph, date, hour, minute, second, and 4 time zones functions.\n\nThis watch has a water resistance of up to 100 feet/30 meters, suitable for short periods of recreational swimming.\n\nThis stylish timepiece is sure to complete any man\'s collection.','Analog','Chronograph, Date, Hour, Minute, Second, and 4 Time Zones','Mens','Quartz','DZ7371','Mr. Daddy 2.0 Chronograph Black Leather Black (Four Time Zone) Dial3',42,'Mr. Daddy 2.0','30 meters / 100 feet','https://firebasestorage.googleapis.com/v0/b/watch-shop-3a14f.appspot.com/o/images%2Fproducts%2F1730196974134.jpg?alt=media&token=49bda3e4-af07-49ab-9008-802e91a4cdb9',1,1,'ACTIVE','2024-10-29 17:16:24.005106','2024-10-29 17:16:24.005106',NULL),('DH00000004','Stainless Steel','22 mm',2,'45 mm','Stainless Steel','16 mm',1,'Black-plated','The Adee Kaye watch features a stainless steel 45 mm case, with a fixed bezel a black dial and a scratch resistant mineral crystal.\n\nChronograph - sub-dials displaying: 60 second, 60 minute and 12 hour.\n\nThe 22 mm stainless steel band is fitted with a fold over clasp .\n\nThis beautiful wristwatch, powered by a miyota caliber os10, Japan made quartz movement, supports: date, hour, minute, second functions.\n\nThis watch has a water resistance of up to 165 feet/50 meters, suitable for short periods of recreational swimming, but not diving or snorkeling.\n\nThis stylish timepiece is sure to complete any man\'s collection.\n','Analog','Date, Hour, Minute, Second','Mens','Quartz','AK2268SS-60','Men\'s Stainless Steel Black Dial Watch',52,'','50 meters / 165 feet','https://firebasestorage.googleapis.com/v0/b/watch-shop-3a14f.appspot.com/o/images%2Fproducts%2F1730196975200.jpg?alt=media&token=61c22e95-8ba0-4331-99a9-b79534078401',1,1,'ACTIVE','2024-10-29 17:16:24.081103','2024-10-29 17:16:24.081103',NULL),('DH00000005','Crystal Set','20 mm',2,'40 mm','Brass','10 mm',1,'Black','The Adee Kaye AKJ2001-L series watch features a brass 40 mm case, with a fixed bezel a crystal set (blue & white) dial and a scratch resistant mineral crystal.\n\nThe 20 mm genuine leather band is fitted with a buckle clasp .\n\nThis beautiful wristwatch, powered by a miyota caliber 2035, Japan made quartz movement, supports: hour, minute, second functions.\n\nThis watch has a water resistance of up to 100 feet/30 meters, suitable for short periods of recreational swimming.\n\nThis stylish timepiece is sure to complete any woman\'s collection.','Analog','Hour, Minute, Second','Womens','Quartz','AK2001-LBU','Women\'s AKJ2001-L Genuine Leather Crystal Set (Blue & White) Dial Watch',54,'','30 meters / 100 feet','https://firebasestorage.googleapis.com/v0/b/watch-shop-3a14f.appspot.com/o/images%2Fproducts%2F1730196976207.jpg?alt=media&token=97cf4dab-e75f-4c92-bbcb-205cc804963e',1,1,'ACTIVE','2024-10-29 17:16:24.117105','2024-10-29 17:16:24.117105',NULL),('DH00000006','Stainless Steel','22 mm',2,'45 mm','Stainless Steel','16 mm',1,'Silver-tone','The Adee Kaye watch features a stainless steel 45 mm case, with a fixed bezel a white dial and a scratch resistant mineral crystal.\n\nChronograph - sub-dials displaying: 60 second, 60 minute and 12 hour.\n\nThe 22 mm stainless steel band is fitted with a fold over clasp .\n\nThis beautiful wristwatch, powered by a miyota caliber os10, Japan made quartz movement, supports: date, hour, minute, second functions.\n\nThis watch has a water resistance of up to 165 feet/50 meters, suitable for short periods of recreational swimming, but not diving or snorkeling.\n\nThis stylish timepiece is sure to complete any man\'s collection.\n','Analog','Date, Hour, Minute, Second','Mens','Quartz','AK2268SS-30','Men\'s Stainless Steel White Dial Watch',77,'','50 meters / 165 feet','https://firebasestorage.googleapis.com/v0/b/watch-shop-3a14f.appspot.com/o/images%2Fproducts%2F1730196978245.jpg?alt=media&token=5c6641e5-ec97-49a8-9b07-c1d43c727985',1,1,'ACTIVE','2024-10-29 17:16:24.145106','2024-10-29 17:16:24.145106',NULL),('DH00000007','Brass set with Crystals','16 mm',2,'32.5 mm','Brass','9.75 mm',1,'Red','The Adee Kaye AKJ2003-L series watch features a brass 32.5 mm case, with a fixed bezel a white (crystal-set) dial and a scratch resistant mineral crystal.\n\nThe 16 mm leather band is fitted with a tang clasp .\n\nThis beautiful wristwatch, powered by a miyota 1l40, Japan made quartz movement, supports: hour, minute, second functions.\n\nThis watch has a water resistance of up to 100 feet/30 meters, suitable for short periods of recreational swimming.\n\nThis stylish timepiece is sure to complete any woman\'s collection.','Analog','Hour, Minute, Second','Womens','Quartz','AK2003-LRG','Women\'s AKJ2003-L Leather White (Crystal-set) Dial Watch',60,'','30 meters / 100 feet','https://firebasestorage.googleapis.com/v0/b/watch-shop-3a14f.appspot.com/o/images%2Fproducts%2F1730196979205.jpg?alt=media&token=a4c16559-027c-477f-b356-eb39af9bdb41',1,1,'ACTIVE','2024-10-29 17:16:24.167332','2024-10-29 17:16:24.167332',NULL),('DH00000008','Composite Metal','24 mm',3,'45 mm','Composite Metal','14.5 mm',2,'Tow-tone (Silver-tone and Black)','The Akribos XXIV watch features a composite metal 45 mm case, with a fixed bezel a black dial and a scratch resistant hardlex crystal.\n\nThe 24 mm stainless steel band is fitted with a hidden fold over clasp .\n\nThis beautiful wristwatch, powered by quartz movement, supports: date, hour, minute, second, tachymeter functions.\n\nThis watch has a water resistance of up to 100 feet/30 meters, suitable for short periods of recreational swimming.\n\nThis stylish timepiece is sure to complete any man\'s collection.','Analog-Digital','Date, Hour, Minute, Second, Tachymeter','Mens','Quartz','AK1095TTB','Men\'s Stainless Steel Black Dial',53,'','30 meters / 100 feet','https://firebasestorage.googleapis.com/v0/b/watch-shop-3a14f.appspot.com/o/images%2Fproducts%2F1730196979995.jpg?alt=media&token=1cf79547-6e74-46ac-acbd-99aa0593b5ff',1,1,'ACTIVE','2024-10-29 17:16:24.202331','2024-10-29 17:16:24.202331',NULL),('DH00000009','Leather','24 mm',4,'42 mm','Stainless Steel','13 mm',3,'Silver-tone','The Armand Nicolet MH2 series watch features a stainless steel 42 mm case, with a fixed bezel a blue dial and a scratch resistant sapphire crystal.\n\nThe leather band is fitted with a tang clasp .\n\nThis beautiful wristwatch, powered by a eta caliber 2824-2, automatic movement, supports: date, hour, minute, second functions.\n\nThis watch has a water resistance of up to 330 feet/100 meters, suitable for recreational surfing, swimming, snorkeling, sailing and water sports.\n\nThis stylish timepiece is sure to complete any man\'s collection.\n','Analog','Date, Hour, Minute, Second','Mens','Automatic','A640A-BU-P140BU2','Men\'s MH2 Leather Blue Dial Watch',81,'','100 meters / 330 feet','https://firebasestorage.googleapis.com/v0/b/watch-shop-3a14f.appspot.com/o/images%2Fproducts%2F1730196980787.jpg?alt=media&token=c6b39efa-bdf3-431d-94c6-e90b7ad74b56',1,1,'ACTIVE','2024-10-29 17:16:24.233332','2024-10-29 17:16:24.233332',NULL),('DH00000010','Stainless Steel','24 mm',4,'42 mm','Stainless Steel','13 mm',1,'Silver-tone','The Armand Nicolet MH2 series watch features a stainless steel 42 mm case, with a fixed bezel a blue dial and a scratch resistant sapphire crystal.\n\nThe stainless steel band is fitted with a fold over with push button release clasp .\n\nThis beautiful wristwatch, powered by a sellita caliber sw280, automatic movement, supports: date, moon phase, hour, minute, second functions.\n\nThis watch has a water resistance of up to 330 feet/100 meters, suitable for recreational surfing, swimming, snorkeling, sailing and water sports.\n\nThis stylish Swiss-made timepiece is sure to complete any man\'s collection.','Analog','Date, Moon Phase, Hour, Minute, Second','Mens','Automatic','A640L-BU-MA2640A','Men\'s MH2 Stainless Steel Blue Dial Watch',57,'','100 meters / 330 feet','https://firebasestorage.googleapis.com/v0/b/watch-shop-3a14f.appspot.com/o/images%2Fproducts%2F1730196981584.jpg?alt=media&token=d2ae2c86-d73f-4541-a39d-a138400b0ad0',1,1,'ACTIVE','2024-10-29 17:16:24.259031','2024-10-29 17:16:24.259031',NULL),('DH00000011','Leather','18 mm',5,'36 mm','Stainless Steel','6.5 mm',1,'Brown','The Daniel Wellington Dapper Durham series watch features a stainless steel 36 mm case, with a fixed bezel a white dial and a scratch resistant mineral crystal.\n\nThe 18 mm leather band is fitted with a tang clasp .\n\nThis beautiful wristwatch, powered by quartz movement, supports: hour, minute, second functions.\n\nThis watch has a water resistance of up to 100 feet/30 meters, suitable for short periods of recreational swimming.\n\nThis stylish timepiece is sure to complete any woman\'s collection.\n','Analog','Hour, Minute, Second','Womens','Quartz','DW00100111','Women\'s Dapper Durham Leather White Dial Watch',53,'Dapper Durham','30 meters / 100 feet','https://firebasestorage.googleapis.com/v0/b/watch-shop-3a14f.appspot.com/o/images%2Fproducts%2F1730196982395.jpg?alt=media&token=34529c39-f91d-4659-b285-337410dae638',1,1,'ACTIVE','2024-10-29 17:16:24.290035','2024-10-29 17:16:24.290035',NULL),('DH00000012','Leather','12 mm',5,'28 mm','Stainless Steel','6 mm',1,'Black','The Daniel Wellington watch features a stainless steel 28 mm case, with a fixed bezel a black dial and a scratch resistant mineral crystal.\n\nThe 12 mm fabric band is fitted with a tang clasp .\n\nThis beautiful wristwatch, powered by Japan made quartz movement, supports: hour, minute functions.\n\nThis watch has a water resistance of up to 100 feet/30 meters, suitable for short periods of recreational swimming.\n\nThis stylish timepiece is sure to complete any woman\'s collection.','Analog','Hour, Minute','Womens','Quartz','DW00100247','Women\'s Fabric Black Dial Watch',64,'','30 meters / 100 feet','https://firebasestorage.googleapis.com/v0/b/watch-shop-3a14f.appspot.com/o/images%2Fproducts%2F1730196983183.jpg?alt=media&token=3277be2f-ff61-48bd-aeef-d74681689ca7',1,1,'ACTIVE','2024-10-29 17:16:24.318031','2024-10-29 17:16:24.318031',NULL),('DH00001','b','b',2,'b','b','b',2,'b','b','b','b','b','b','b','b',98,'b','b','https://firebasestorage.googleapis.com/v0/b/watch-shop-3a14f.appspot.com/o/images%2Fproducts%2Fll.png?alt=media&token=4dc672e3-8f8f-4bd9-b22d-723c7ed75c98',1,1,'ACTIVE','2024-10-29 17:54:10.056173','2024-10-29 17:54:10.056173',NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-19  0:31:59
