-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: fdb14.biz.nf
-- Generation Time: Jun 04, 2016 at 10:24 AM
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
-- Table structure for table `udrmusic`
--

CREATE TABLE `udrmusic` (
  `title` varchar(200) NOT NULL,
  `year` text NOT NULL,
  `artist` varchar(400) NOT NULL,
  `ID` int(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `udrmusic`
--

INSERT INTO `udrmusic` (`title`, `year`, `artist`, `ID`) VALUES
('Suno na Sangemarmar', '2014', 'Arijit Singh', 4),
('Dewani Mastani', '2015', 'Shreya Ghoshal, Ganesh Chandanshive', 1),
('Gerua', '2015', 'Pritam,Arijit Singh', 2),
('Bhar Do Jholi meri', '2015', 'Adnan Sami, Pritam', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `udrmusic`
--
ALTER TABLE `udrmusic`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `udrmusic`
--
ALTER TABLE `udrmusic`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
