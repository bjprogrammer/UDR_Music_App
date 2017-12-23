-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: fdb14.biz.nf
-- Generation Time: Jun 04, 2016 at 10:25 AM
-- Server version: 5.5.38-log
-- PHP Version: 5.5.36

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `2104393_bj`
--

-- --------------------------------------------------------

--
-- Table structure for table `udrreviews`
--

CREATE TABLE `udrreviews` (
  `ID` int(255) NOT NULL,
  `name` text NOT NULL,
  `email` varchar(20) NOT NULL,
  `mobile` varchar(12) NOT NULL,
  `comments` text NOT NULL,
  `rating` int(3) NOT NULL,
  `url` varchar(100) NOT NULL,
  `title` text NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `udrreviews`
--

INSERT INTO `udrreviews` (`ID`, `name`, `email`, `mobile`, `comments`, `rating`, `url`, `title`, `timestamp`) VALUES
(2, 'bobby_jasuja', 'bobbyjasuja@gmail.co', '9827063324', 'wow ', 4, 'http://abs.twimg.com/sticky/default_profile_images/default_profile_5_normal.png', 'Gerua', '2016-06-02 04:42:14'),
(3, 'Bobby Jasuja', 'bobbyjasuja@gmail.co', '9827063324', 'dhh ggh', 5, 'https://graph.facebook.com/1019180211481070/picture?type=normal', 'Gerua', '2016-06-02 06:49:22');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `udrreviews`
--
ALTER TABLE `udrreviews`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `udrreviews`
--
ALTER TABLE `udrreviews`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
