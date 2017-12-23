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
-- Table structure for table `udrnews`
--

CREATE TABLE `udrnews` (
  `ID` int(255) NOT NULL,
  `title` text NOT NULL,
  `source` text NOT NULL,
  `time` text NOT NULL,
  `content` longtext NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `udrnews`
--

INSERT INTO `udrnews` (`ID`, `title`, `source`, `time`, `content`) VALUES
(1, 'Petrol Price Hiked By Rs. 2.58/Litre, Diesel By Rs. 2.26/Litre', 'NDTV Profit Team', '31 May,2016', 'Petrol price was on Tuesday hiked by Rs. 2.58 per litre while diesel rate was increased by Rs. 2.26 per litre. Revised prices will take effect from the midnight of May 31 and June 1.\r\n\r\nAfter Tuesday''s revision, a litre of petrol will cost Rs. 65.60 in Delhi (including state levies) whereas diesel will retail at Rs. 53.93/litre, said Indian Oil Corporation, the country''s largest fuel retailer.\r\n\r\nPrices of the fuels were last revised on May 16, when petrol was made more expensive by 83 paise a litre whereas diesel rate was revised upwards by Rs. 1.26 per litre. (Read more)\r\n\r\n"The current level of international product prices of Petrol & Diesel and rupee-dollar exchange rate warrant increase in price of Petrol and Diesel, the impact of which is being passed on to the consumers with this price revision," Indian Oil said.\r\n\r\nState-owned fuel retailers IOC, Bharat Petroleum Corporation Ltd (BPCL) and Hindustan Petroleum Corporation Ltd (HPCL) revise the rates on the 1st and 16th of every month based on the average oil price and the foreign exchange rate in the preceding fortnight.\r\n\r\nThe rupee lost 10 paise against the US dollar to settle at 67.26 on Tuesday. It is down 0.7 per cent against the greenback since May 16. \r\n\r\n"The movement of prices in the international oil market and rupee-dollar exchange rate shall continue to be monitored closely and developing trends of the market will be reflected in future price changes," it added.');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `udrnews`
--
ALTER TABLE `udrnews`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `udrnews`
--
ALTER TABLE `udrnews`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
