-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 29, 2014 at 11:57 AM
-- Server version: 5.6.11
-- PHP Version: 5.5.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `salesteam`
--
CREATE DATABASE IF NOT EXISTS `salesteam` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `salesteam`;

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE IF NOT EXISTS `accounts` (
  `accountid` int(11) NOT NULL AUTO_INCREMENT,
  `accountname` varchar(50) NOT NULL,
  `accountareaid` int(11) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `fax` varchar(50) DEFAULT NULL,
  `contact` varchar(50) DEFAULT NULL,
  `status` enum('inactive','active') DEFAULT NULL,
  `retailer_contact` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`accountid`),
  UNIQUE KEY `accountname` (`accountname`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`accountid`, `accountname`, `accountareaid`, `address`, `email`, `phone`, `fax`, `contact`, `status`, `retailer_contact`) VALUES
(1, 'Account One', 2, 'Fabian', 'contactone@email.com', '123123', '213', 'ContactPersonOne', 'inactive', '234324'),
(3, 'AccountThree123', 4, 'AccountAddresss,123123', 'three@email.com', '5345', '789', 'ContactThree', 'active', '900'),
(4, 'Petron Wack Wack', 10, 'Wack Wack Rd Corner Shaw Blvd.', 'dd', '09189827332', '', 'sdfs', 'inactive', '');

-- --------------------------------------------------------

--
-- Table structure for table `account_discount_changes_logs`
--

CREATE TABLE IF NOT EXISTS `account_discount_changes_logs` (
  `date_changed` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `accountid` int(11) DEFAULT NULL,
  `productid` int(11) DEFAULT NULL,
  `olddiscount` float(10,2) DEFAULT NULL,
  `newdiscount` float(10,2) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `account_product_discounts`
--

CREATE TABLE IF NOT EXISTS `account_product_discounts` (
  `discountid` int(11) NOT NULL AUTO_INCREMENT,
  `accountid` int(11) DEFAULT NULL,
  `productid` int(11) DEFAULT NULL,
  `discount` float(10,2) DEFAULT NULL,
  PRIMARY KEY (`discountid`),
  UNIQUE KEY `accountid` (`accountid`,`productid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `account_product_discounts`
--

INSERT INTO `account_product_discounts` (`discountid`, `accountid`, `productid`, `discount`) VALUES
(7, 3, 1, 1.00),
(10, 1, 1, 20.54),
(11, 4, 9, 3.00);

-- --------------------------------------------------------

--
-- Table structure for table `agents`
--

CREATE TABLE IF NOT EXISTS `agents` (
  `agentid` int(11) NOT NULL AUTO_INCREMENT,
  `agentname` varchar(50) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `agenthireddate` datetime DEFAULT NULL,
  `agentstatus` enum('active','terminated','resigned','awol') DEFAULT NULL,
  `terminateddate` datetime DEFAULT NULL,
  `agentuserid` int(11) DEFAULT NULL,
  `birthdate` datetime DEFAULT NULL,
  `areaid` int(11) DEFAULT NULL,
  PRIMARY KEY (`agentid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `agents`
--

INSERT INTO `agents` (`agentid`, `agentname`, `address`, `agenthireddate`, `agentstatus`, `terminateddate`, `agentuserid`, `birthdate`, `areaid`) VALUES
(5, NULL, NULL, NULL, NULL, NULL, 2, NULL, NULL),
(6, NULL, NULL, NULL, NULL, NULL, 6, NULL, NULL),
(8, NULL, NULL, NULL, NULL, NULL, 12, NULL, NULL),
(9, NULL, NULL, NULL, NULL, NULL, 13, NULL, NULL),
(10, NULL, NULL, NULL, NULL, NULL, 14, NULL, NULL),
(11, NULL, NULL, NULL, NULL, NULL, 15, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `agent_areas`
--

CREATE TABLE IF NOT EXISTS `agent_areas` (
  `agentid` int(11) DEFAULT NULL,
  `areaid` int(11) DEFAULT NULL,
  UNIQUE KEY `agentid` (`agentid`,`areaid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `agent_areas`
--

INSERT INTO `agent_areas` (`agentid`, `areaid`) VALUES
(5, 8),
(6, 8),
(8, 8),
(9, 12),
(10, 12),
(11, 12);

-- --------------------------------------------------------

--
-- Table structure for table `agent_current_inventory`
--

CREATE TABLE IF NOT EXISTS `agent_current_inventory` (
  `agentid` int(11) DEFAULT NULL,
  `productid` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `agent_current_inventory`
--

INSERT INTO `agent_current_inventory` (`agentid`, `productid`, `quantity`) VALUES
(5, 1, 17),
(6, 1, 8),
(5, 3, 10),
(6, 9, 50);

-- --------------------------------------------------------

--
-- Table structure for table `agent_inventory_logs`
--

CREATE TABLE IF NOT EXISTS `agent_inventory_logs` (
  `inventorylogid` int(11) NOT NULL AUTO_INCREMENT,
  `agentid` int(11) DEFAULT NULL,
  `logtype` enum('add','returned') DEFAULT NULL,
  `logdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `productid` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `begininventory` int(11) DEFAULT NULL,
  `endinventory` int(11) DEFAULT NULL,
  PRIMARY KEY (`inventorylogid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `agent_inventory_logs`
--

INSERT INTO `agent_inventory_logs` (`inventorylogid`, `agentid`, `logtype`, `logdate`, `productid`, `quantity`, `begininventory`, `endinventory`) VALUES
(5, 5, 'add', '2013-02-05 08:19:29', 1, 10, 0, 10),
(7, 5, 'add', '2013-02-05 16:24:29', 1, 10, 10, 20),
(8, 6, 'add', '2013-02-05 16:25:23', 1, 8, 0, 8),
(9, 5, 'returned', '2013-02-05 17:26:16', 1, 3, 20, 17),
(10, 5, 'add', '2013-02-06 03:18:54', 3, 10, 0, 10),
(11, 6, 'add', '2013-06-28 08:20:14', 9, 100, 0, 100),
(12, 6, 'returned', '2013-06-28 08:20:32', 9, 50, 100, 50);

-- --------------------------------------------------------

--
-- Table structure for table `areas`
--

CREATE TABLE IF NOT EXISTS `areas` (
  `areaid` int(11) NOT NULL AUTO_INCREMENT,
  `areaname` varchar(50) NOT NULL,
  `territoryid` int(11) NOT NULL,
  `status` enum('inactive','active') DEFAULT NULL,
  PRIMARY KEY (`areaid`),
  UNIQUE KEY `areaname` (`areaname`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `areas`
--

INSERT INTO `areas` (`areaid`, `areaname`, `territoryid`, `status`) VALUES
(8, 'Valenzuela', 6, 'active'),
(7, 'Quezon City', 6, 'active'),
(9, 'Caloocan', 6, 'active'),
(10, 'Mandaluyong', 7, 'active'),
(11, 'San Juan', 7, 'active'),
(12, 'Makati', 7, 'active'),
(13, 'Cavite', 7, 'active');

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `commentid` int(11) NOT NULL AUTO_INCREMENT,
  `commentby` int(11) DEFAULT NULL,
  `comment` varchar(500) DEFAULT NULL,
  `commentdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `commenttype` enum('payment','delivery','sales') DEFAULT NULL,
  `refid` int(11) DEFAULT NULL,
  PRIMARY KEY (`commentid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `delivery`
--

CREATE TABLE IF NOT EXISTS `delivery` (
  `deliveryid` int(11) NOT NULL AUTO_INCREMENT,
  `deliveryReceipt` varchar(50) DEFAULT NULL,
  `accountid` int(11) DEFAULT NULL,
  `deliverydate` datetime DEFAULT NULL,
  `receivedby` varchar(50) DEFAULT NULL,
  `deliveredby` int(11) DEFAULT NULL,
  `status` enum('for_delivery','completed','cancelled') DEFAULT NULL,
  `date_delivered` timestamp NULL DEFAULT NULL,
  `insertedby` int(11) DEFAULT NULL,
  `deliverytotalamount` float(10,2) DEFAULT NULL,
  `deliverytotalnetamount` float(10,2) DEFAULT NULL,
  `deliverystatus` enum('confirmed','temporary') DEFAULT 'temporary',
  `deliverylastactiondate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `deliveryhash` varchar(255) DEFAULT NULL,
  `deliverylastaction` enum('cancelled','completed','for_delivery') DEFAULT 'for_delivery',
  PRIMARY KEY (`deliveryid`),
  UNIQUE KEY `deliveryReceipt` (`deliveryReceipt`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `delivery`
--

INSERT INTO `delivery` (`deliveryid`, `deliveryReceipt`, `accountid`, `deliverydate`, `receivedby`, `deliveredby`, `status`, `date_delivered`, `insertedby`, `deliverytotalamount`, `deliverytotalnetamount`, `deliverystatus`, `deliverylastactiondate`, `deliveryhash`, `deliverylastaction`) VALUES
(1, 'DR05142013-001', 1, '2013-05-15 00:00:00', 'Client A', 5, 'for_delivery', '2013-05-14 10:23:51', 1, 300.45, 238.74, 'confirmed', '2013-05-14 10:23:51', 'eb0cd5a4b935dc9e80774890545c1f66', 'for_delivery'),
(2, 'DR-271306', 4, '2013-06-29 00:00:00', 'dd', 6, 'completed', '2013-06-28 09:06:58', 1, 1000.00, 970.00, 'confirmed', '2013-06-28 09:07:22', 'd63af90d9c895519614f172d975b1158', 'for_delivery');

-- --------------------------------------------------------

--
-- Table structure for table `delivery_comment`
--

CREATE TABLE IF NOT EXISTS `delivery_comment` (
  `delivery_comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `delivery_comment_content` text,
  `deliveryid` int(11) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `delivery_datecreated` datetime DEFAULT NULL,
  PRIMARY KEY (`delivery_comment_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `delivery_comment`
--

INSERT INTO `delivery_comment` (`delivery_comment_id`, `delivery_comment_content`, `deliveryid`, `userid`, `delivery_datecreated`) VALUES
(1, 'this is test payment', 2, 1, '2013-04-09 10:47:32'),
(2, 'may natitira pang 30 petot', 3, 1, '2013-04-09 14:08:24'),
(3, 'dffd', 1, 1, '2013-05-27 15:56:58');

-- --------------------------------------------------------

--
-- Table structure for table `delivery_items`
--

CREATE TABLE IF NOT EXISTS `delivery_items` (
  `deliveryitemid` int(11) NOT NULL AUTO_INCREMENT,
  `deliveryid` int(11) DEFAULT NULL,
  `orderitemid` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `deliveryitemamount` float(10,2) DEFAULT NULL,
  `deliveryitemnetamount` float(10,2) DEFAULT NULL,
  `deliveryitemtraceno` varchar(20) DEFAULT NULL,
  `deliveryitemdate` date DEFAULT NULL,
  PRIMARY KEY (`deliveryitemid`),
  UNIQUE KEY `deliveryid` (`deliveryid`,`orderitemid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `delivery_items`
--

INSERT INTO `delivery_items` (`deliveryitemid`, `deliveryid`, `orderitemid`, `quantity`, `deliveryitemamount`, `deliveryitemnetamount`, `deliveryitemtraceno`, `deliveryitemdate`) VALUES
(1, 1, 1, 1, 300.45, 238.74, 'TR', '2013-05-15'),
(2, 2, 4, 10, 1000.00, 970.00, '', '2013-06-01');

-- --------------------------------------------------------

--
-- Table structure for table `epin`
--

CREATE TABLE IF NOT EXISTS `epin` (
  `epinid` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `epindatecreated` datetime DEFAULT NULL,
  PRIMARY KEY (`epinid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `epin`
--

INSERT INTO `epin` (`epinid`, `productid`, `quantity`, `epindatecreated`) VALUES
(1, 1, 15, '2013-02-01 16:51:54'),
(2, 3, 50, '2013-02-01 17:35:54'),
(3, 1, 23, '2013-02-01 17:48:12'),
(4, 3, 30, '2013-02-06 10:36:38'),
(5, 3, 5, '2013-02-06 11:18:37'),
(6, 9, 100, '2013-06-28 16:09:42'),
(7, 9, 100, '2013-06-28 16:18:17');

-- --------------------------------------------------------

--
-- Table structure for table `modules`
--

CREATE TABLE IF NOT EXISTS `modules` (
  `moduleid` int(11) NOT NULL AUTO_INCREMENT,
  `modulename` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`moduleid`),
  UNIQUE KEY `modulename` (`modulename`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `modules`
--

INSERT INTO `modules` (`moduleid`, `modulename`) VALUES
(1, 'user management'),
(2, 'roles management'),
(3, 'privileges management'),
(4, 'product management'),
(5, 'account management'),
(6, 'wallet management'),
(7, 'autoloadmax purchase management'),
(8, 'cards epins purchase management'),
(9, 'territory management'),
(10, 'area management'),
(11, 'team management'),
(12, 'inventory management'),
(13, 'order management'),
(14, 'payment management'),
(15, 'delivery management');

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE IF NOT EXISTS `payments` (
  `paymentid` int(11) NOT NULL AUTO_INCREMENT,
  `orderReceipt` varchar(50) DEFAULT NULL,
  `accountid` int(11) DEFAULT NULL,
  `paymentdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `paymenttype` enum('deposit_by_client_cash','deposit_by_client_check','internet_banking','cash_collected','check_collected') DEFAULT NULL,
  `BankDeposited` varchar(50) DEFAULT NULL,
  `BranchDeposited` varchar(50) DEFAULT NULL,
  `checknumber` varchar(50) NOT NULL,
  `clearingdate` datetime DEFAULT NULL,
  `amountpaid` float(10,2) DEFAULT NULL,
  `status` enum('initial','verified','cancelled') DEFAULT NULL,
  `enteredby` int(11) DEFAULT NULL,
  `collectedby` int(11) DEFAULT NULL,
  `paymentstatus` enum('confirmed','temporary') DEFAULT 'temporary',
  `paymenthash` varchar(255) DEFAULT NULL,
  `paymentalterdate` datetime DEFAULT NULL,
  `paymentlastaction` enum('cancelled','verified','initial') DEFAULT 'initial',
  PRIMARY KEY (`paymentid`),
  UNIQUE KEY `orderReceipt` (`orderReceipt`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`paymentid`, `orderReceipt`, `accountid`, `paymentdate`, `paymenttype`, `BankDeposited`, `BranchDeposited`, `checknumber`, `clearingdate`, `amountpaid`, `status`, `enteredby`, `collectedby`, `paymentstatus`, `paymenthash`, `paymentalterdate`, `paymentlastaction`) VALUES
(1, 'OR0409-1', 1, '2013-04-09 02:34:54', 'deposit_by_client_cash', 'BPI', 'Pasay', '', '0000-00-00 00:00:00', 200.00, 'verified', 1, 1, 'confirmed', '45231be53eb0045eec4ca988049e3d0a', '2013-04-09 10:34:54', 'verified'),
(2, 'OR0409-02', 1, '2013-04-09 02:47:41', 'deposit_by_client_cash', 'BDO', 'SFDO', '', '0000-00-00 00:00:00', 100.00, 'verified', 1, 1, 'confirmed', '9d575b7767ad604b6dea91a188bd5815', '2013-04-09 10:47:41', 'verified'),
(3, 'OR0409-3', 1, '2013-04-09 06:08:28', 'deposit_by_client_cash', 'Metrobank', 'Legaspi', '', '0000-00-00 00:00:00', 30.00, 'verified', 1, 1, 'confirmed', '7770dff21d4ecae82b0a1de9075495d1', '2013-04-09 14:08:28', 'verified'),
(4, 'OR0409-04', 1, '2013-04-09 07:46:44', 'deposit_by_client_check', 'China Bank', 'Marilao', 'OR0409-04', '2013-04-10 00:00:00', 10.00, 'verified', 1, 1, 'confirmed', '9417906343f86b6672e37684ee4b6240', '2013-04-09 15:46:44', 'verified'),
(5, 'OR0409-5', 1, '2013-04-09 07:51:53', 'deposit_by_client_cash', 'RangAy', 'San Juan', '', '0000-00-00 00:00:00', 28.74, 'verified', 1, 1, 'confirmed', '1f3700f4b757e98468627dedc0b3111', '2013-04-09 15:51:53', 'verified'),
(6, 'OR-23422', 4, '2013-06-28 09:05:24', 'deposit_by_client_cash', '970', 'MBTC', '', '0000-00-00 00:00:00', 970.00, 'verified', 1, 1, 'confirmed', 'b1d0df65e7f24969bf363e1605955d3c', '2013-06-28 17:05:24', 'verified');

-- --------------------------------------------------------

--
-- Table structure for table `payment_comment`
--

CREATE TABLE IF NOT EXISTS `payment_comment` (
  `payment_comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `payment_comment_content` text,
  `payment_id` int(11) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `payment_datecreated` datetime DEFAULT NULL,
  PRIMARY KEY (`payment_comment_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `payment_comment`
--

INSERT INTO `payment_comment` (`payment_comment_id`, `payment_comment_content`, `payment_id`, `userid`, `payment_datecreated`) VALUES
(1, 'this is test payment', 2, 1, '2013-04-09 10:47:32'),
(2, 'may natitira pang 30 petot', 3, 1, '2013-04-09 14:08:24');

-- --------------------------------------------------------

--
-- Table structure for table `payment_items`
--

CREATE TABLE IF NOT EXISTS `payment_items` (
  `paymentitemid` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` int(11) DEFAULT NULL,
  `amountpaid` float(10,2) DEFAULT NULL,
  `paymentid` int(11) DEFAULT NULL,
  PRIMARY KEY (`paymentitemid`),
  UNIQUE KEY `paymentid` (`paymentid`,`orderid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `payment_items`
--

INSERT INTO `payment_items` (`paymentitemid`, `orderid`, `amountpaid`, `paymentid`) VALUES
(1, 1, 200.00, 1),
(2, 1, 100.00, 2),
(3, 1, 30.00, 3),
(4, 1, 10.00, 4),
(5, 1, 28.74, 5),
(6, 3, 970.00, 6);

-- --------------------------------------------------------

--
-- Table structure for table `privileges`
--

CREATE TABLE IF NOT EXISTS `privileges` (
  `privilegeid` int(11) NOT NULL,
  `privilege` varchar(50) DEFAULT NULL,
  UNIQUE KEY `privilege` (`privilege`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `privileges`
--

INSERT INTO `privileges` (`privilegeid`, `privilege`) VALUES
(1, 'add'),
(2, 'edit'),
(3, 'verify'),
(4, 'disable'),
(5, 'delete'),
(6, 'view'),
(7, 'search'),
(8, 'update own password'),
(9, 'deactivate'),
(10, 'view history'),
(11, 'return inventory'),
(12, 'own records'),
(13, 'area records'),
(14, 'territory records'),
(15, 'TestModule');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE IF NOT EXISTS `products` (
  `productid` int(11) NOT NULL AUTO_INCREMENT,
  `productsku` varchar(50) NOT NULL,
  `amount` float(10,2) DEFAULT NULL,
  `created_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createdby` int(11) DEFAULT NULL,
  `status` enum('inactive','active') DEFAULT 'active',
  `inventory` int(11) DEFAULT '0',
  `productname` varchar(50) NOT NULL,
  `walletid` int(11) DEFAULT NULL,
  `providerid` int(11) DEFAULT NULL,
  PRIMARY KEY (`productid`),
  UNIQUE KEY `productsku` (`productsku`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`productid`, `productsku`, `amount`, `created_Date`, `createdby`, `status`, `inventory`, `productname`, `walletid`, `providerid`) VALUES
(8, 'p2pmother', 1.00, '2013-06-14 07:38:18', 7, 'active', 0, 'p2p mother', 7, 1),
(7, 'p2pshell', 1.00, '2013-06-14 07:36:45', 7, 'active', 0, 'p2p shell', 6, 1),
(9, 'G100 prepaid card', 100.00, '2013-06-28 09:07:22', 7, 'active', 190, 'G100 prepaid card', 0, 1),
(10, 'Retailer Deposit', 1.00, '2013-06-27 03:19:50', 1, 'active', 0, 'Retailers Deposit', 0, 1),
(11, 'GCash', 1.00, '2013-06-27 03:28:28', 1, 'active', 0, 'GCash', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `products_copy`
--

CREATE TABLE IF NOT EXISTS `products_copy` (
  `productid` int(11) NOT NULL AUTO_INCREMENT,
  `productsku` varchar(50) NOT NULL,
  `amount` float(10,2) DEFAULT NULL,
  `created_Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createdby` int(11) DEFAULT NULL,
  `status` enum('inactive','active') DEFAULT 'active',
  `inventory` int(11) DEFAULT '0',
  `productname` varchar(50) NOT NULL,
  `walletid` int(11) DEFAULT NULL,
  `providerid` int(11) DEFAULT NULL,
  PRIMARY KEY (`productid`),
  UNIQUE KEY `productsku` (`productsku`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `products_copy`
--

INSERT INTO `products_copy` (`productid`, `productsku`, `amount`, `created_Date`, `createdby`, `status`, `inventory`, `productname`, `walletid`, `providerid`) VALUES
(1, 'P2P', 1.00, '2012-11-28 19:19:07', NULL, 'active', 0, '', NULL, NULL),
(2, 'EPIN100', 100.00, '2012-11-28 19:19:07', NULL, 'active', 0, '', NULL, NULL),
(3, 'EPIN300', 300.00, '2012-11-28 19:19:07', NULL, 'active', 0, '', NULL, NULL),
(4, 'EPIN500', 500.00, '2012-11-28 19:19:07', NULL, 'active', 0, '', NULL, NULL),
(5, 'G100', 100.00, '2012-11-28 19:19:07', NULL, 'active', 0, '', NULL, NULL),
(6, 'G300', 300.00, '2012-11-28 19:19:07', NULL, 'active', 0, '', NULL, NULL),
(7, 'G500', 500.00, '2012-11-28 19:19:07', NULL, 'active', 0, '', NULL, NULL),
(8, 'TM50', 50.00, '2012-11-28 19:19:07', NULL, 'active', 0, '', NULL, NULL),
(9, 'TM100', 100.00, '2012-11-28 19:19:07', NULL, 'active', 0, '', NULL, NULL),
(10, 'TM300', 300.00, '2012-11-28 19:19:07', NULL, 'active', 0, '', NULL, NULL),
(11, 'TM500', 500.00, '2012-11-28 19:19:07', NULL, 'active', 0, '', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `provider`
--

CREATE TABLE IF NOT EXISTS `provider` (
  `providerid` int(11) NOT NULL AUTO_INCREMENT,
  `providername` varchar(24) NOT NULL,
  `providerprefix` varchar(10) NOT NULL,
  PRIMARY KEY (`providerid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `provider`
--

INSERT INTO `provider` (`providerid`, `providername`, `providerprefix`) VALUES
(1, 'Globe Telecom', 'Globe'),
(2, 'Touch Mobile', 'TM');

-- --------------------------------------------------------

--
-- Table structure for table `purchases_autoloadmax`
--

CREATE TABLE IF NOT EXISTS `purchases_autoloadmax` (
  `apurchaseid` int(11) NOT NULL AUTO_INCREMENT,
  `purchasedate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `purchasetype` enum('DAL','purchaseLOP') DEFAULT NULL,
  `amount` float(10,2) DEFAULT NULL,
  `walletid` int(11) DEFAULT NULL,
  PRIMARY KEY (`apurchaseid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `purchases_autoloadmax`
--

INSERT INTO `purchases_autoloadmax` (`apurchaseid`, `purchasedate`, `purchasetype`, `amount`, `walletid`) VALUES
(1, '2013-02-01 10:10:24', 'DAL', 20.00, 1),
(2, '2013-02-01 10:11:02', 'purchaseLOP', 100.00, 1),
(3, '2013-02-01 10:12:15', 'DAL', 300.00, 4),
(4, '2013-02-01 10:48:33', 'purchaseLOP', 1500.00, 5),
(5, '2013-02-01 10:49:22', 'DAL', 400.00, 1);

-- --------------------------------------------------------

--
-- Table structure for table `purchases_cards`
--

CREATE TABLE IF NOT EXISTS `purchases_cards` (
  `cpurchaseid` int(11) NOT NULL AUTO_INCREMENT,
  `purchasedate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `quantity` int(11) DEFAULT NULL,
  `productid` int(11) DEFAULT NULL,
  `amount` float(10,2) DEFAULT NULL,
  PRIMARY KEY (`cpurchaseid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE IF NOT EXISTS `roles` (
  `roleid` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`roleid`),
  UNIQUE KEY `rolename` (`rolename`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=31 ;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`roleid`, `rolename`) VALUES
(1, 'Super Admin'),
(2, 'Sales Manager'),
(3, 'Sales Operations Admin'),
(4, 'Sales Team Leader'),
(5, 'Sales Agent'),
(6, 'Finance'),
(29, 'Development'),
(30, 'QA Master');

-- --------------------------------------------------------

--
-- Table structure for table `role_privileges`
--

CREATE TABLE IF NOT EXISTS `role_privileges` (
  `roleid` int(11) DEFAULT NULL,
  `moduleid` int(11) DEFAULT NULL,
  `privilege_mode` varchar(50) DEFAULT NULL,
  `privilege_status` enum('disabled','enabled') DEFAULT 'disabled'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role_privileges`
--

INSERT INTO `role_privileges` (`roleid`, `moduleid`, `privilege_mode`, `privilege_status`) VALUES
(29, 1, '00111111111111', 'enabled'),
(29, 2, '11111111111111', 'disabled'),
(29, 3, '01111111111111', 'enabled'),
(29, 4, '11111111111111', 'disabled'),
(29, 5, '11111111111111', 'disabled'),
(29, 6, '11111111111111', 'disabled'),
(29, 7, '11111111111111', 'disabled'),
(29, 8, '11111111111111', 'disabled'),
(29, 9, '11111111111111', 'disabled'),
(29, 10, '11111111111111', 'disabled'),
(29, 11, '11111111111111', 'disabled'),
(29, 12, '11111111111111', 'disabled'),
(29, 13, '11111111111111', 'disabled'),
(29, 14, '11111111111111', 'disabled'),
(29, 15, '11111111111111', 'disabled'),
(30, 1, '00001111111100', 'enabled'),
(30, 2, '11111111111111', 'disabled'),
(30, 3, '01111111111111', 'enabled'),
(30, 4, '11101111111111', 'enabled'),
(30, 5, '11111111111111', 'disabled'),
(30, 6, '11111111111111', 'disabled'),
(30, 7, '11111111111111', 'disabled'),
(30, 8, '11111111111111', 'disabled'),
(30, 9, '11111111111111', 'disabled'),
(30, 10, '11111111111111', 'disabled'),
(30, 11, '11111111111111', 'disabled'),
(30, 12, '11111111111111', 'disabled'),
(30, 13, '11111111111111', 'disabled'),
(30, 14, '11111111111111', 'disabled'),
(30, 15, '11111101111111', 'disabled'),
(3, 1, '111111111111111', 'disabled'),
(3, 2, '111111111111111', 'disabled'),
(3, 3, '111111111111111', 'disabled'),
(3, 4, '111111111111111', 'enabled'),
(3, 5, '111111111111111', 'enabled'),
(3, 6, '111111111111111', 'enabled'),
(3, 7, '111111111111111', 'enabled'),
(3, 8, '111111111111111', 'enabled'),
(3, 9, '111111111111111', 'enabled'),
(3, 10, '111111111111111', 'enabled'),
(3, 11, '111111111111111', 'enabled'),
(3, 12, '111111111111111', 'enabled'),
(3, 13, '111111111111111', 'enabled'),
(3, 14, '111111111111111', 'enabled'),
(3, 15, '111111111111111', 'enabled'),
(2, 1, '111111111111111', 'disabled'),
(2, 2, '111111111111111', 'disabled'),
(2, 3, '111111111111111', 'disabled'),
(2, 4, '111111111111111', 'enabled'),
(2, 5, '111111111111111', 'enabled'),
(2, 6, '111111111111111', 'enabled'),
(2, 7, '111111111111111', 'enabled'),
(2, 8, '111111111111111', 'enabled'),
(2, 9, '111111111111111', 'enabled'),
(2, 10, '111111111111111', 'enabled'),
(2, 11, '111111111111111', 'enabled'),
(2, 12, '111111111111111', 'enabled'),
(2, 13, '111111111111111', 'enabled'),
(2, 14, '111111111111111', 'enabled'),
(2, 15, '111111111111111', 'enabled'),
(6, 1, '111111111111111', 'disabled'),
(6, 2, '111111111111111', 'disabled'),
(6, 3, '111111111111111', 'disabled'),
(6, 4, '111111111111111', 'disabled'),
(6, 5, '111111111111111', 'disabled'),
(6, 6, '111111111111111', 'disabled'),
(6, 7, '111111111111111', 'disabled'),
(6, 8, '111111111111111', 'disabled'),
(6, 9, '111111111111111', 'disabled'),
(6, 10, '111111111111111', 'disabled'),
(6, 11, '111111111111111', 'disabled'),
(6, 12, '111111111111111', 'disabled'),
(6, 13, '111111111111111', 'enabled'),
(6, 14, '111111111111111', 'enabled'),
(6, 15, '111111111111111', 'enabled');

-- --------------------------------------------------------

--
-- Table structure for table `role_privileges_copy`
--

CREATE TABLE IF NOT EXISTS `role_privileges_copy` (
  `roleid` int(11) DEFAULT NULL,
  `moduleid` int(11) DEFAULT NULL,
  `privilege_mode` varchar(50) DEFAULT NULL,
  `privilege_status` enum('disabled','enabled') DEFAULT 'disabled',
  UNIQUE KEY `roleid` (`roleid`,`moduleid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role_privileges_copy`
--

INSERT INTO `role_privileges_copy` (`roleid`, `moduleid`, `privilege_mode`, `privilege_status`) VALUES
(1, 1, '1101011', 'disabled'),
(1, 2, '1100111', 'disabled'),
(1, 3, '1100111', 'disabled'),
(2, 4, '1101011', 'disabled'),
(2, 5, '1101011', 'disabled'),
(2, 6, '110001101', 'disabled'),
(2, 7, '1000011', 'disabled'),
(2, 8, '1000011', 'disabled'),
(2, 9, '1100111', 'disabled'),
(2, 10, '1100111', 'disabled'),
(2, 11, '1101011', 'disabled'),
(2, 1, '1001000', 'disabled'),
(2, 12, '10000110011', 'disabled'),
(2, 13, '1101011', 'disabled'),
(2, 14, '1101011', 'disabled'),
(2, 15, '1101011', 'disabled'),
(3, 4, '1101011', 'disabled'),
(3, 5, '1101011', 'disabled'),
(3, 6, '110001101', 'disabled'),
(3, 7, '1000011', 'disabled'),
(3, 8, '1000011', 'disabled'),
(3, 12, '10000110011', 'disabled'),
(3, 13, '1101011', 'disabled'),
(3, 14, '1101011', 'disabled'),
(3, 15, '1101011', 'disabled'),
(4, 13, '11010110000001', 'disabled'),
(4, 14, '11010110000001', 'disabled'),
(4, 15, '11010110000001', 'disabled'),
(5, 13, '110101100001', 'disabled'),
(5, 14, '110101100001', 'disabled'),
(5, 15, '110101100001', 'disabled'),
(6, 14, '1111011', 'disabled');

-- --------------------------------------------------------

--
-- Table structure for table `sales_order`
--

CREATE TABLE IF NOT EXISTS `sales_order` (
  `orderid` int(11) NOT NULL AUTO_INCREMENT,
  `salesInvoice` varchar(50) DEFAULT NULL,
  `accountid` int(11) DEFAULT NULL,
  `purchase_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `requestor` int(11) DEFAULT NULL,
  `totalamount` float(10,2) DEFAULT NULL,
  `netamount` float(10,2) DEFAULT NULL,
  `orderstatus` enum('active','overdue','completed','cancelled') DEFAULT NULL,
  `deliverystatus` enum('requested','partial','completed') DEFAULT 'requested',
  `paymentstatus` enum('receivable','partial','completed') DEFAULT 'receivable',
  `TotalAmountPaid` float(10,2) DEFAULT NULL,
  `salesinvoicehash` varchar(255) DEFAULT NULL,
  `salesinvoicecreateddate` datetime DEFAULT NULL,
  `salesinvoicestatus` enum('temporary','confirmed') DEFAULT 'temporary',
  `salesinvoicelastactiondate` datetime DEFAULT NULL,
  `agentid` int(11) DEFAULT NULL,
  PRIMARY KEY (`orderid`),
  UNIQUE KEY `salesInvoice` (`salesInvoice`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `sales_order`
--

INSERT INTO `sales_order` (`orderid`, `salesInvoice`, `accountid`, `purchase_date`, `requestor`, `totalamount`, `netamount`, `orderstatus`, `deliverystatus`, `paymentstatus`, `TotalAmountPaid`, `salesinvoicehash`, `salesinvoicecreateddate`, `salesinvoicestatus`, `salesinvoicelastactiondate`, `agentid`) VALUES
(1, 'SI0409-1', 1, '2013-11-14 08:20:54', 1, 430.45, 368.74, 'completed', 'requested', 'completed', 430.75, '4b5e767ddee1b5b5bd88bd1e8727a97a', '2013-04-09 10:33:18', 'confirmed', '2013-04-09 15:51:53', 5),
(2, '09090j90', 3, '2013-11-18 05:02:19', 1, 874.65, 862.15, 'completed', 'requested', 'completed', 874.65, '20279c6416a764b09f8d90aaf7a371af', '2013-06-28 16:41:25', 'confirmed', '2013-06-28 16:11:31', 5),
(3, 'SI-130628001', 4, '2013-11-15 06:38:47', 1, 1000.00, 970.00, 'completed', 'completed', 'completed', 970.00, '84d44d62572de90bc10def4e013174b0', '2013-06-11 17:04:09', 'confirmed', '2013-06-28 17:07:22', 5),
(4, 'SADyi21123-1', 4, '2013-11-18 03:03:15', 1, 8765.00, 7341.24, 'completed', 'completed', 'completed', 8765.00, 'fndf23araf3234hytjgvhxvqer2379hjdvh3', '2013-11-04 00:00:00', 'confirmed', '2013-11-13 00:00:00', 6);

-- --------------------------------------------------------

--
-- Table structure for table `sales_order_comment`
--

CREATE TABLE IF NOT EXISTS `sales_order_comment` (
  `sales_order_comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `sales_order_comment_content` text,
  `orderid` int(11) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `sales_order_datecreated` datetime DEFAULT NULL,
  PRIMARY KEY (`sales_order_comment_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `sales_order_comment`
--

INSERT INTO `sales_order_comment` (`sales_order_comment_id`, `sales_order_comment_content`, `orderid`, `userid`, `sales_order_datecreated`) VALUES
(1, 'Test\n', 1, 1, '2013-04-09 10:33:51'),
(2, 'test 2', 1, 1, '2013-04-09 15:52:11');

-- --------------------------------------------------------

--
-- Table structure for table `sales_order_items`
--

CREATE TABLE IF NOT EXISTS `sales_order_items` (
  `orderitemid` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) DEFAULT NULL,
  `facevalue` float(10,2) DEFAULT NULL,
  `discount` float(10,2) DEFAULT NULL,
  `netprice` float(10,2) DEFAULT NULL,
  `totalitemamount` float(10,2) DEFAULT NULL,
  `totaldeliveredquantity` int(11) DEFAULT NULL,
  `orderid` int(11) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`orderitemid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `sales_order_items`
--

INSERT INTO `sales_order_items` (`orderitemid`, `productid`, `facevalue`, `discount`, `netprice`, `totalitemamount`, `totaldeliveredquantity`, `orderid`, `quantity`) VALUES
(1, 1, 300.45, 20.54, 238.74, 300.45, 0, 1, 1),
(2, 2, 130.00, 0.00, 130.00, 130.00, 0, 1, 1),
(3, 1, 300.45, 1.00, 0.00, 0.00, 0, 2, 0),
(4, 9, 100.00, 3.00, 970.00, 1000.00, 10, 3, 10);

-- --------------------------------------------------------

--
-- Table structure for table `territories`
--

CREATE TABLE IF NOT EXISTS `territories` (
  `territoryid` int(11) NOT NULL AUTO_INCREMENT,
  `territoryname` varchar(50) NOT NULL,
  `status` enum('inactive','active') DEFAULT NULL,
  PRIMARY KEY (`territoryid`),
  UNIQUE KEY `territoryname` (`territoryname`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `territories`
--

INSERT INTO `territories` (`territoryid`, `territoryname`, `status`) VALUES
(7, 'South', 'active'),
(6, 'North', 'active');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `userfirstname` varchar(30) DEFAULT NULL,
  `userlastname` varchar(30) DEFAULT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(32) NOT NULL,
  `status` enum('inactive','awol','resigned','terminated','active') DEFAULT 'active',
  `email_address` varchar(50) DEFAULT NULL,
  `mobile` varchar(15) NOT NULL,
  `roleid` int(11) DEFAULT NULL,
  `createdby` int(11) DEFAULT NULL,
  `createddate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `hireddate` date DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `mobile` (`mobile`),
  UNIQUE KEY `email_address` (`email_address`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userid`, `userfirstname`, `userlastname`, `username`, `password`, `status`, `email_address`, `mobile`, `roleid`, `createdby`, `createddate`, `hireddate`, `birthdate`) VALUES
(1, 'Admin', 'Account', 'admin', 'f54dbca7c35255d2296c40d6766e63ad', 'active', 'xkremesites@gmail.com', '123', 1, 1, '2013-01-07 03:37:07', NULL, NULL),
(2, 'Developer', 'Account', 'dev', '8b19a5c86d15a714028a623da51b02ec', 'active', 'xkremesites@yahoo.com', '639063359229', 4, 1, '2013-01-28 14:58:10', NULL, NULL),
(6, 'Ben', 'Ten', 'ben', 'b1b9a972ccd8c962a473909b97007eb4', 'active', 'test123@test.com', '123123', 5, 1, '2013-07-17 08:23:22', '2012-02-18', '1988-01-02'),
(7, 'chato', 'espinosa', 'chato', 'bcc535578d0a3e42cc7cb0a2a6fabe74', 'active', 'chato.espinosa@payexchangeinc.com', '09175274936', 1, 1, '2013-05-28 06:57:55', '2007-11-16', '1992-03-14'),
(8, 'jenny', 'fonacier', 'jenny', '7cece7e06dc6a7cceaacdffa7d2e4d9b', 'active', 'jenny.fonacier@payexchangeinc.com', '09167675295', 6, 1, '2013-05-29 03:55:10', '2008-04-16', '1989-03-16'),
(9, 'mayette', 'pagulayan', 'mayette', '2fc812922086f219b9f8ff3925bb9c7', 'active', 'mayette.pagulayan@payexchangeinc.com', '09173573300', 6, 1, '2013-05-29 03:56:49', '2007-11-16', '1987-02-17');

-- --------------------------------------------------------

--
-- Table structure for table `wallets`
--

CREATE TABLE IF NOT EXISTS `wallets` (
  `walletid` int(11) NOT NULL AUTO_INCREMENT,
  `walletname` varchar(50) NOT NULL,
  `status` enum('inactive','active') DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `inactivated_Date` datetime DEFAULT NULL,
  `balance` float(10,3) DEFAULT NULL,
  PRIMARY KEY (`walletid`),
  UNIQUE KEY `walletname` (`walletname`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `wallets`
--

INSERT INTO `wallets` (`walletid`, `walletname`, `status`, `created_date`, `inactivated_Date`, `balance`) VALUES
(1, 'Load Central', 'active', '2013-01-14 08:19:13', '2013-01-15 08:19:40', 1500.000),
(4, 'Unionbank123', 'inactive', '2013-01-13 18:07:45', '2013-01-15 10:08:33', 150000.000),
(5, 'Test', 'active', '2013-01-25 02:28:07', NULL, 40000.000),
(6, 'Shell Sub-D', 'active', '2013-06-14 07:31:51', NULL, 16788.000),
(7, 'PayPhil Mother Wallet', 'active', '2013-06-14 07:32:43', NULL, 174921.656);

-- --------------------------------------------------------

--
-- Table structure for table `wallet_changes_logs`
--

CREATE TABLE IF NOT EXISTS `wallet_changes_logs` (
  `walletid` int(11) DEFAULT NULL,
  `beginbalance` float(10,3) DEFAULT NULL,
  `endbalance` float(10,3) DEFAULT NULL,
  `amountchanged` float(10,3) DEFAULT NULL,
  `transactionid` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
