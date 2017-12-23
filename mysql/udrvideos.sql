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
-- Table structure for table `udrvideos`
--

CREATE TABLE `udrvideos` (
  `ID` int(255) NOT NULL,
  `title` text NOT NULL,
  `genre` text NOT NULL,
  `year` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `udrvideos`
--

INSERT INTO `udrvideos` (`ID`, `title`, `genre`, `year`) VALUES
(1, 'Afghan Jalebi', 'Action Thriller', '2015'),
(2, 'Chittiyaan Kalayaiyaan', 'Drama', '2014'),
(3, 'Manma Emotion Jaage', 'Romance and Drama', '2015'),
(4, 'Saiyaan Superstar', 'Action Thriller', '2014');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `udrvideos`
--
ALTER TABLE `udrvideos`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `udrvideos`
--
ALTER TABLE `udrvideos`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
