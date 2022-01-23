-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: webdb
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `content` text NOT NULL,
  `hit` int(11) NOT NULL,
  `g_no` int(11) NOT NULL,
  `o_no` int(11) NOT NULL,
  `depth` int(11) NOT NULL,
  `reg_date` datetime NOT NULL,
  `user_no` int(11) NOT NULL,
  PRIMARY KEY (`no`),
  KEY `fk_board_user_idx` (`user_no`),
  CONSTRAINT `fk_board_user` FOREIGN KEY (`user_no`) REFERENCES `user` (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (4,'삭제된 글입니다.','',1,1,1,1,'2022-01-18 16:44:51',5),(20,'지금 테스트중','테테테테테',3,1,2,2,'2022-01-18 20:03:28',5),(21,'지금도','테스트중중',2,1,7,3,'2022-01-18 20:03:37',5),(24,'정말이야???야야야','진짜라니깐요용',8,1,5,4,'2022-01-18 20:04:31',5),(25,'아싸','이제좀 숨돌림',4,2,1,1,'2022-01-18 20:08:30',5),(27,'삭제된 글입니다.','',0,1,4,4,'2022-01-18 20:09:04',7),(28,'진짜','근데 난 몰라',2,2,3,2,'2022-01-18 20:14:48',7),(29,'ggggg','ggggg',1,3,1,1,'2022-01-18 20:32:50',5),(32,'하지만','후횐 없다.',5,6,1,1,'2022-01-18 20:38:59',5),(34,'dd','dd',6,7,1,1,'2022-01-18 20:59:20',5),(36,'마지막','이런된장',3,1,8,4,'2022-01-18 21:06:33',5),(37,'그래서','진짜 그렇게 할려고??',4,6,2,2,'2022-01-18 21:13:28',5),(38,'ddd','asfsdf',7,8,1,1,'2022-01-19 08:29:44',5),(42,'삭제된 글입니다.','',4,12,1,1,'2022-01-19 08:29:54',5),(44,'빨리빨리해라','수정해라',8,14,1,1,'2022-01-19 08:29:59',5),(50,'삭제된 글입니다.','',1,20,1,1,'2022-01-19 08:30:15',5),(52,'근데 말이야','너 진짜 뭐냐',7,20,4,2,'2022-01-19 09:42:49',7),(53,'삭제된 글입니다.','',1,20,2,2,'2022-01-19 09:44:46',7),(57,'너','진짜 뭐냐',3,1,6,5,'2022-01-19 12:17:35',5),(58,'지금 수정수정','진짜로 수정하게???',9,20,6,3,'2022-01-19 12:21:09',5),(59,'삭제된 글입니다.','',1,2,2,2,'2022-01-19 12:31:04',5),(60,'삭제된 글입니다.','',0,1,9,5,'2022-01-19 12:54:57',5),(63,'댓글댓글','댓글 달기달기달기',3,12,2,2,'2022-01-19 14:50:04',7),(64,'투신','난 신이다',8,24,1,1,'2022-01-19 14:51:38',7),(65,'삭제된 글입니다.','',2,20,8,4,'2022-01-19 15:04:00',5),(66,'삭제된 글입니다.','',3,20,7,4,'2022-01-19 15:04:17',5),(69,'엘리트가 되기 위해','난 계속 노력한다.',7,24,3,2,'2022-01-19 15:41:56',5),(70,'삭제된 글입니다.','',2,24,2,2,'2022-01-19 15:51:15',5),(71,'삭제된 글입니다.','',15,25,1,1,'2022-01-19 15:56:16',5),(72,'어쩔','어쩔 어쩔',13,25,2,2,'2022-01-19 15:56:31',7),(92,'이제는ㅌㅊㅊㅊ','댓글',2,12,3,3,'2022-01-20 08:33:38',5),(93,'테스트 중입니다.','테스트 중입니다.',4,29,1,1,'2022-01-20 11:03:21',7),(98,'테스트 중입니다.','테스트 중입니다.',3,34,1,1,'2022-01-20 11:03:37',7),(108,'투신입니다.','투신입니다.',3,44,1,1,'2022-01-20 11:04:32',7),(109,'투신입니다.','투신입니다.',8,45,1,1,'2022-01-20 11:04:34',7),(111,'나??','몰라',1,20,5,3,'2022-01-20 15:04:42',5),(112,'이제는ㅇㅇ','정말로ㅇㅇ',6,45,3,2,'2022-01-20 21:24:07',5),(113,'삭제된 글입니다.','',1,44,2,2,'2022-01-21 15:29:55',5),(116,'삭제된 글입니다.','',0,29,6,2,'2022-01-21 15:44:01',5),(117,'그런데','진짜로 하는거야???',9,29,4,2,'2022-01-21 16:58:13',5),(118,'근데근데근데','진짜 테스트???',4,29,2,2,'2022-01-22 11:33:09',7),(119,'테스트','중입니다.',2,46,1,1,'2022-01-22 11:34:03',7),(121,'진짜로','해야만되',0,29,5,3,'2022-01-22 13:19:52',7),(122,'마지막','최후의 일격',0,45,2,2,'2022-01-22 13:27:02',5),(123,'마지막','최후의 일격',0,46,2,2,'2022-01-22 13:27:11',5),(124,'ㅇ','ㅇ',0,45,4,3,'2022-01-22 13:27:17',5),(125,'마지막','인사요',1,29,3,3,'2022-01-22 13:27:26',5);
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emaillist`
--

DROP TABLE IF EXISTS `emaillist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emaillist` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(200) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emaillist`
--

LOCK TABLES `emaillist` WRITE;
/*!40000 ALTER TABLE `emaillist` DISABLE KEYS */;
INSERT INTO `emaillist` VALUES (11,'한글한글','한글한글','hjs928@daum.net'),(12,'한글한글','한글한글','hjs928@daum.net');
/*!40000 ALTER TABLE `emaillist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guestbook`
--

DROP TABLE IF EXISTS `guestbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guestbook` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `message` text NOT NULL,
  `reg_date` datetime NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guestbook`
--

LOCK TABLES `guestbook` WRITE;
/*!40000 ALTER TABLE `guestbook` DISABLE KEYS */;
INSERT INTO `guestbook` VALUES (25,'하진수','1234','이제는\r\n다시 시작이다','2022-01-18 15:25:00'),(26,'진진맨','1234','지금 등록하고 있다.','2022-01-21 13:57:37'),(29,'무신','1234','스프링 연습중\r\n연습중','2022-01-21 14:24:36');
/*!40000 ALTER TABLE `guestbook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `email` varchar(200) NOT NULL,
  `password` varchar(45) NOT NULL,
  `gender` enum('female','male') NOT NULL,
  `join_date` date NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (5,'하진수','hjs928@naver.com','1234','male','2022-01-18'),(7,'무신','hjs928@daum.net','1234','male','2022-01-18');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-24  8:23:25
