-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: fdb14.biz.nf
-- Generation Time: Jun 04, 2016 at 10:23 AM
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
-- Table structure for table `udraudioplaylist`
--

CREATE TABLE `udraudioplaylist` (
  `ID` int(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `playlistname` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `musicid` int(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `udraudioplaylist`
--

INSERT INTO `udraudioplaylist` (`ID`, `email`, `playlistname`, `created_at`, `musicid`) VALUES
(7, 'bobby@gmail.com', 'bj', '2016-06-04 05:04:13', 3),
(9, 'bobby@gmail.com', 'bj', '2016-06-04 05:15:32', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `udraudioplaylist`
--
ALTER TABLE `udraudioplaylist`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `email` (`email`,`playlistname`,`musicid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `udraudioplaylist`
--
ALTER TABLE `udraudioplaylist`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
