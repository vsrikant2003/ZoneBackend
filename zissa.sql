CREATE DATABASE  IF NOT EXISTS `zissa` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `zissa`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: zissa
-- ------------------------------------------------------
-- Server version	5.1.54-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `allocation`
--

DROP TABLE IF EXISTS `allocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `allocation` (
  `Allocation_ID` int(11) NOT NULL AUTO_INCREMENT,
  `From_Date` date NOT NULL,
  `To_Date` date DEFAULT NULL,
  `Created_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `FK_Resource_ID` int(11) NOT NULL,
  `FK_Allocation_Type_ID` tinyint(4) NOT NULL,
  `FK_Status_ID` tinyint(4) NOT NULL,
  `FK_Create_User_ID` int(11) NOT NULL,
  PRIMARY KEY (`Allocation_ID`),
  KEY `Allocation_Allocation_Type` (`FK_Allocation_Type_ID`),
  KEY `Allocation_Resource` (`FK_Resource_ID`),
  KEY `Allocation_Status` (`FK_Status_ID`),
  KEY `Allocation_User` (`FK_Create_User_ID`),
  CONSTRAINT `Allocation_Allocation_Type` FOREIGN KEY (`FK_Allocation_Type_ID`) REFERENCES `allocation_type` (`Allocation_Type_ID`),
  CONSTRAINT `Allocation_Resource` FOREIGN KEY (`FK_Resource_ID`) REFERENCES `resource` (`Resource_ID`),
  CONSTRAINT `Allocation_Status` FOREIGN KEY (`FK_Status_ID`) REFERENCES `status` (`Status_ID`),
  CONSTRAINT `Allocation_User` FOREIGN KEY (`FK_Create_User_ID`) REFERENCES `user` (`User_ID`),
  CONSTRAINT `FK4sqjj0onbe9qenavy9f9jvyl` FOREIGN KEY (`FK_Resource_ID`) REFERENCES `resource` (`Resource_ID`),
  CONSTRAINT `FKid972ptm9gcw7fbdf27awu810` FOREIGN KEY (`FK_Create_User_ID`) REFERENCES `user` (`User_ID`),
  CONSTRAINT `FKl1gm0pdubw72l6yrrsft66kdd` FOREIGN KEY (`FK_Status_ID`) REFERENCES `status` (`Status_ID`),
  CONSTRAINT `FKr7cdmfuhynv9hfl74854seelc` FOREIGN KEY (`FK_Allocation_Type_ID`) REFERENCES `allocation_type` (`Allocation_Type_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allocation`
--

LOCK TABLES `allocation` WRITE;
/*!40000 ALTER TABLE `allocation` DISABLE KEYS */;
/*!40000 ALTER TABLE `allocation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `allocation_type`
--

DROP TABLE IF EXISTS `allocation_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `allocation_type` (
  `Allocation_Type_ID` tinyint(4) NOT NULL,
  `Allocation_Name` varchar(20) NOT NULL,
  PRIMARY KEY (`Allocation_Type_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allocation_type`
--

LOCK TABLES `allocation_type` WRITE;
/*!40000 ALTER TABLE `allocation_type` DISABLE KEYS */;
INSERT INTO `allocation_type` VALUES (1,'Employee'),(2,'Project'),(3,'Other');
/*!40000 ALTER TABLE `allocation_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attr_data_type`
--

DROP TABLE IF EXISTS `attr_data_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attr_data_type` (
  `Data_Type_ID` tinyint(4) NOT NULL,
  `Data_Type_Name` varchar(20) NOT NULL,
  PRIMARY KEY (`Data_Type_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attr_data_type`
--

LOCK TABLES `attr_data_type` WRITE;
/*!40000 ALTER TABLE `attr_data_type` DISABLE KEYS */;
INSERT INTO `attr_data_type` VALUES (1,'String'),(2,'Integer'),(3,'Float'),(4,'Currency'),(5,'Date');
/*!40000 ALTER TABLE `attr_data_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute`
--

DROP TABLE IF EXISTS `attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attribute` (
  `Attribute_ID` smallint(6) NOT NULL AUTO_INCREMENT,
  `Name` varchar(40) NOT NULL,
  `Created_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `FK_Create_User_ID` int(11) NOT NULL,
  `FK_Data_Type_ID` tinyint(4) NOT NULL,
  PRIMARY KEY (`Attribute_ID`),
  KEY `Attribute_Attr_Data_Type` (`FK_Data_Type_ID`),
  KEY `Attribute_User` (`FK_Create_User_ID`),
  CONSTRAINT `Attribute_Attr_Data_Type` FOREIGN KEY (`FK_Data_Type_ID`) REFERENCES `attr_data_type` (`Data_Type_ID`),
  CONSTRAINT `Attribute_User` FOREIGN KEY (`FK_Create_User_ID`) REFERENCES `user` (`User_ID`),
  CONSTRAINT `FKgxuja5plm4tvnfowrfm17wgm` FOREIGN KEY (`FK_Create_User_ID`) REFERENCES `user` (`User_ID`),
  CONSTRAINT `FKjbk24y2ipbdcda7yj9fbmibte` FOREIGN KEY (`FK_Data_Type_ID`) REFERENCES `attr_data_type` (`Data_Type_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute`
--

LOCK TABLES `attribute` WRITE;
/*!40000 ALTER TABLE `attribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute_value`
--

DROP TABLE IF EXISTS `attribute_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attribute_value` (
  `Attribute_Value_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Value` varchar(100) NOT NULL,
  `FK_Attribute_ID` smallint(6) NOT NULL,
  PRIMARY KEY (`Attribute_Value_ID`),
  KEY `Attribute_Value_Attribute` (`FK_Attribute_ID`),
  CONSTRAINT `Attribute_Value_Attribute` FOREIGN KEY (`FK_Attribute_ID`) REFERENCES `attribute` (`Attribute_ID`),
  CONSTRAINT `FKtkyyreqpq2lvow20gq1n7fjlf` FOREIGN KEY (`FK_Attribute_ID`) REFERENCES `attribute` (`Attribute_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute_value`
--

LOCK TABLES `attribute_value` WRITE;
/*!40000 ALTER TABLE `attribute_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `attribute_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `Category_ID` tinyint(4) NOT NULL AUTO_INCREMENT,
  `Name` varchar(40) NOT NULL,
  `Code_Pattern` varchar(20) NOT NULL,
  `Created_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `FK_Create_User_ID` int(11) NOT NULL,
  PRIMARY KEY (`Category_ID`),
  KEY `FKm5t1al5a4f8mew1glov3b9ykt_idx` (`FK_Create_User_ID`),
  CONSTRAINT `FKm5t1al5a4f8mew1glov3b9ykt` FOREIGN KEY (`FK_Create_User_ID`) REFERENCES `user` (`User_ID`),
  CONSTRAINT `fk_category_user` FOREIGN KEY (`FK_Create_User_ID`) REFERENCES `user` (`User_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Laptops','CMB/LAP','2018-04-09 08:56:17',1);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_attribute`
--

DROP TABLE IF EXISTS `category_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_attribute` (
  `Category_Attribute_ID` int(11) NOT NULL AUTO_INCREMENT,
  `FK_Attribute_ID` smallint(6) NOT NULL,
  `FK_Category_ID` tinyint(4) NOT NULL,
  `is_default` bit(1) NOT NULL,
  PRIMARY KEY (`Category_Attribute_ID`),
  KEY `Category_Attribute_Attribute` (`FK_Attribute_ID`),
  KEY `Category_Attribute_Category` (`FK_Category_ID`),
  CONSTRAINT `Category_Attribute_Attribute` FOREIGN KEY (`FK_Attribute_ID`) REFERENCES `attribute` (`Attribute_ID`),
  CONSTRAINT `Category_Attribute_Category` FOREIGN KEY (`FK_Category_ID`) REFERENCES `category` (`Category_ID`),
  CONSTRAINT `FK32acis5plh5esd3qtear50ah5` FOREIGN KEY (`FK_Attribute_ID`) REFERENCES `attribute` (`Attribute_ID`),
  CONSTRAINT `FKtfhr88wxkd5qlabdqxclo6tip` FOREIGN KEY (`FK_Category_ID`) REFERENCES `category` (`Category_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_attribute`
--

LOCK TABLES `category_attribute` WRITE;
/*!40000 ALTER TABLE `category_attribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `category_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `Employee_ID` int(11) NOT NULL AUTO_INCREMENT,
  `User_Name` varchar(20) NOT NULL,
  `First_Name` varchar(20) NOT NULL,
  `Last_Name` varchar(20) NOT NULL,
  `Active_Status` bit(1) NOT NULL,
  PRIMARY KEY (`Employee_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_allocation`
--

DROP TABLE IF EXISTS `employee_allocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_allocation` (
  `Employee_Allocation_ID` int(11) NOT NULL AUTO_INCREMENT,
  `FK_Allocation_ID` int(11) NOT NULL,
  `FK_Employee_ID` int(11) NOT NULL,
  PRIMARY KEY (`Employee_Allocation_ID`),
  KEY `Employee_Allocation_Allocation` (`FK_Allocation_ID`),
  KEY `Employee_Allocation_Employee` (`FK_Employee_ID`),
  CONSTRAINT `Employee_Allocation_Allocation` FOREIGN KEY (`FK_Allocation_ID`) REFERENCES `allocation` (`Allocation_ID`),
  CONSTRAINT `Employee_Allocation_Employee` FOREIGN KEY (`FK_Employee_ID`) REFERENCES `employee` (`Employee_ID`),
  CONSTRAINT `FK2ift2jfp3p0i7n3ecc7hav4vb` FOREIGN KEY (`FK_Allocation_ID`) REFERENCES `allocation` (`Allocation_ID`),
  CONSTRAINT `FKolixprjn5w1xcd2bf3rdy5eio` FOREIGN KEY (`FK_Employee_ID`) REFERENCES `employee` (`Employee_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_allocation`
--

LOCK TABLES `employee_allocation` WRITE;
/*!40000 ALTER TABLE `employee_allocation` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee_allocation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation`
--

DROP TABLE IF EXISTS `operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operation` (
  `Operation_ID` tinyint(4) NOT NULL,
  `Name` varchar(20) NOT NULL,
  PRIMARY KEY (`Operation_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation`
--

LOCK TABLES `operation` WRITE;
/*!40000 ALTER TABLE `operation` DISABLE KEYS */;
INSERT INTO `operation` VALUES (1,'View'),(2,'Add'),(3,'Edit'),(4,'Allocate'),(5,'Dispose'),(6,'Delete');
/*!40000 ALTER TABLE `operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_allocation`
--

DROP TABLE IF EXISTS `other_allocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_allocation` (
  `Other_Allocation_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Assignee_Name` varchar(20) NOT NULL,
  `FK_Allocation_ID` int(11) NOT NULL,
  PRIMARY KEY (`Other_Allocation_ID`),
  KEY `Other_Allocation_Allocation` (`FK_Allocation_ID`),
  CONSTRAINT `FKeeapmsbjw5exhqb4yipihdvkq` FOREIGN KEY (`FK_Allocation_ID`) REFERENCES `allocation` (`Allocation_ID`),
  CONSTRAINT `Other_Allocation_Allocation` FOREIGN KEY (`FK_Allocation_ID`) REFERENCES `allocation` (`Allocation_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_allocation`
--

LOCK TABLES `other_allocation` WRITE;
/*!40000 ALTER TABLE `other_allocation` DISABLE KEYS */;
/*!40000 ALTER TABLE `other_allocation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `Permission_ID` int(11) NOT NULL AUTO_INCREMENT,
  `FK_Category_ID` tinyint(4) NOT NULL,
  `FK_Role_ID` tinyint(4) NOT NULL,
  `FK_Operation_ID` tinyint(4) NOT NULL,
  PRIMARY KEY (`Permission_ID`),
  KEY `Role_Operation_Category_Category` (`FK_Category_ID`),
  KEY `Role_Operation_Category_Operation` (`FK_Operation_ID`),
  KEY `Role_Operation_Category_Role` (`FK_Role_ID`),
  CONSTRAINT `FKgl27phqt6pqb5hxphy2e2qnm6` FOREIGN KEY (`FK_Category_ID`) REFERENCES `category` (`Category_ID`),
  CONSTRAINT `FKjqnglwol6sh2ss0em0fe301ls` FOREIGN KEY (`FK_Role_ID`) REFERENCES `role` (`Role_ID`),
  CONSTRAINT `FKp6w2rlg08r74dqe5vqwb7jpyx` FOREIGN KEY (`FK_Operation_ID`) REFERENCES `operation` (`Operation_ID`),
  CONSTRAINT `Role_Operation_Category_Category` FOREIGN KEY (`FK_Category_ID`) REFERENCES `category` (`Category_ID`),
  CONSTRAINT `Role_Operation_Category_Operation` FOREIGN KEY (`FK_Operation_ID`) REFERENCES `operation` (`Operation_ID`),
  CONSTRAINT `Role_Operation_Category_Role` FOREIGN KEY (`FK_Role_ID`) REFERENCES `role` (`Role_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,1,1,1),(2,1,1,2),(3,1,1,3),(4,1,1,4),(5,1,1,5),(6,1,1,6);
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `Project_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Project_Name` varchar(5) NOT NULL,
  `Active_Status` bit(1) NOT NULL,
  PRIMARY KEY (`Project_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_allocation`
--

DROP TABLE IF EXISTS `project_allocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_allocation` (
  `Project_Allocation_ID` int(11) NOT NULL AUTO_INCREMENT,
  `FK_Project_ID` int(11) NOT NULL,
  `FK_Allocation_ID` int(11) NOT NULL,
  PRIMARY KEY (`Project_Allocation_ID`),
  KEY `Project_Allocation_Allocation` (`FK_Allocation_ID`),
  KEY `Project_Allocation_Project` (`FK_Project_ID`),
  CONSTRAINT `FK29kcepd548nedjeedb8dakb62` FOREIGN KEY (`FK_Project_ID`) REFERENCES `project` (`Project_ID`),
  CONSTRAINT `FKsotme8m1p8m5r6tcyu31p7d1q` FOREIGN KEY (`FK_Allocation_ID`) REFERENCES `allocation` (`Allocation_ID`),
  CONSTRAINT `Project_Allocation_Allocation` FOREIGN KEY (`FK_Allocation_ID`) REFERENCES `allocation` (`Allocation_ID`),
  CONSTRAINT `Project_Allocation_Project` FOREIGN KEY (`FK_Project_ID`) REFERENCES `project` (`Project_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_allocation`
--

LOCK TABLES `project_allocation` WRITE;
/*!40000 ALTER TABLE `project_allocation` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_allocation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `Resource_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(20) NOT NULL,
  `Created_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `FK_Category_ID` tinyint(4) NOT NULL,
  `FK_Create_User_ID` int(11) NOT NULL,
  `FK_Status_ID` tinyint(4) NOT NULL,
  PRIMARY KEY (`Resource_ID`),
  UNIQUE KEY `Resource_Code` (`Code`),
  KEY `Resource_Category` (`FK_Category_ID`),
  KEY `Resource_Status` (`FK_Status_ID`),
  KEY `Resource_User` (`FK_Create_User_ID`),
  CONSTRAINT `FKfo42qtkg4tiiqwqqbjvfuficn` FOREIGN KEY (`FK_Category_ID`) REFERENCES `category` (`Category_ID`),
  CONSTRAINT `FKfph88u1fipproqahmdol4cdcp` FOREIGN KEY (`FK_Status_ID`) REFERENCES `status` (`Status_ID`),
  CONSTRAINT `FKo1vcmy0ffj4j7lsk3iup0b6m3` FOREIGN KEY (`FK_Create_User_ID`) REFERENCES `user` (`User_ID`),
  CONSTRAINT `Resource_Category` FOREIGN KEY (`FK_Category_ID`) REFERENCES `category` (`Category_ID`),
  CONSTRAINT `Resource_Status` FOREIGN KEY (`FK_Status_ID`) REFERENCES `status` (`Status_ID`),
  CONSTRAINT `Resource_User` FOREIGN KEY (`FK_Create_User_ID`) REFERENCES `user` (`User_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource_attribute`
--

DROP TABLE IF EXISTS `resource_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource_attribute` (
  `Resource_Attribute_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Value` varchar(100) NOT NULL,
  `FK_Resource_ID` int(11) NOT NULL,
  `FK_Attribute_ID` smallint(6) NOT NULL,
  PRIMARY KEY (`Resource_Attribute_ID`),
  KEY `Attribute_Resource_Attribute` (`FK_Attribute_ID`),
  KEY `Attribute_Resource_Resource` (`FK_Resource_ID`),
  CONSTRAINT `Attribute_Resource_Attribute` FOREIGN KEY (`FK_Attribute_ID`) REFERENCES `attribute` (`Attribute_ID`),
  CONSTRAINT `Attribute_Resource_Resource` FOREIGN KEY (`FK_Resource_ID`) REFERENCES `resource` (`Resource_ID`),
  CONSTRAINT `FK177pjj9lh1d9q0ru6su41idsj` FOREIGN KEY (`FK_Attribute_ID`) REFERENCES `attribute` (`Attribute_ID`),
  CONSTRAINT `FKl0msj192srqov2dy9os733a0l` FOREIGN KEY (`FK_Resource_ID`) REFERENCES `resource` (`Resource_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource_attribute`
--

LOCK TABLES `resource_attribute` WRITE;
/*!40000 ALTER TABLE `resource_attribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `resource_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resourcebin`
--

DROP TABLE IF EXISTS `resourcebin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resourcebin` (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_category_id` tinyint(4) NOT NULL,
  `fk_create_user_id` int(11) NOT NULL,
  `fk_status_id` tinyint(4) NOT NULL,
  `code` varchar(20) NOT NULL,
  `created_date` datetime NOT NULL,
  `dispose_reason` varchar(255) NOT NULL,
  PRIMARY KEY (`resource_id`),
  KEY `fk_category_idx` (`fk_category_id`),
  CONSTRAINT `FKqfg05y7ydr9n0fnhyxnwt53go` FOREIGN KEY (`fk_category_id`) REFERENCES `category` (`Category_ID`),
  CONSTRAINT `fk_category` FOREIGN KEY (`fk_category_id`) REFERENCES `category` (`Category_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resourcebin`
--

LOCK TABLES `resourcebin` WRITE;
/*!40000 ALTER TABLE `resourcebin` DISABLE KEYS */;
/*!40000 ALTER TABLE `resourcebin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resourcebin_attribute`
--

DROP TABLE IF EXISTS `resourcebin_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resourcebin_attribute` (
  `resource_attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_attribute_id` smallint(6) NOT NULL,
  `value` varchar(100) NOT NULL,
  `fk_resource_id` int(11) NOT NULL,
  PRIMARY KEY (`resource_attribute_id`),
  KEY `FKs8840gi18j34g36p01q1dcj9c` (`fk_resource_id`),
  KEY `FKkvxhvcamwvoh8da73450mwfsn` (`fk_attribute_id`),
  CONSTRAINT `FKkvxhvcamwvoh8da73450mwfsn` FOREIGN KEY (`fk_attribute_id`) REFERENCES `attribute` (`Attribute_ID`),
  CONSTRAINT `FKs8840gi18j34g36p01q1dcj9c` FOREIGN KEY (`fk_resource_id`) REFERENCES `resourcebin` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resourcebin_attribute`
--

LOCK TABLES `resourcebin_attribute` WRITE;
/*!40000 ALTER TABLE `resourcebin_attribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `resourcebin_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `Role_ID` tinyint(4) NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL,
  `Administration` tinyint(1) NOT NULL,
  `FK_Default_Category` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`Role_ID`),
  KEY `fk_category_idx` (`FK_Default_Category`),
  CONSTRAINT `FKfgo0wyk6am4973e7b3gncy1i4` FOREIGN KEY (`FK_Default_Category`) REFERENCES `category` (`Category_ID`),
  CONSTRAINT `FKlrrs1vywbecu3xv9k5akwjxk2` FOREIGN KEY (`FK_Default_Category`) REFERENCES `category` (`Category_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Admin',1,1);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `Status_ID` tinyint(4) NOT NULL,
  `Status_Name` varchar(20) NOT NULL,
  PRIMARY KEY (`Status_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (0,'Available'),(1,'Allocated'),(2,'Deallocated');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `User_ID` int(11) NOT NULL AUTO_INCREMENT,
  `User_Name` varchar(20) NOT NULL,
  `Email` varchar(32) NOT NULL,
  `First_Name` varchar(20) NOT NULL,
  `Last_Name` varchar(20) NOT NULL,
  `Created_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Active_Status` bit(1) NOT NULL,
  `FK_Role_ID` tinyint(4) NOT NULL,
  PRIMARY KEY (`User_ID`),
  KEY `User_Role` (`FK_Role_ID`),
  CONSTRAINT `FKagw9fsin8811v5n6x4u1wwrlk` FOREIGN KEY (`FK_Role_ID`) REFERENCES `role` (`Role_ID`),
  CONSTRAINT `User_Role` FOREIGN KEY (`FK_Role_ID`) REFERENCES `role` (`Role_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'BathiyaT','bathiyat@zone24x7.com','Bathiya','Tennakoon','2018-03-09 08:43:31','',1);
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

-- Dump completed on 2018-11-22 11:31:06
