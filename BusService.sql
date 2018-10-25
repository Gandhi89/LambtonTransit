-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 24, 2018 at 05:41 PM
-- Server version: 10.1.32-MariaDB
-- PHP Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `BusService`
--

-- --------------------------------------------------------

--
-- Table structure for table `businfo`
--

CREATE TABLE `businfo` (
  `id` int(11) NOT NULL,
  `student_id` varchar(255) NOT NULL,
  `status` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `businfo`
--

INSERT INTO `businfo` (`id`, `student_id`, `status`) VALUES
(31, 'c01234', 'going'),
(46, 'c0989812@mylambton.ca', 'going'),
(48, 'c0330326', 'going'),
(49, 'c989878896', 'going');

-- --------------------------------------------------------

--
-- Table structure for table `userinfo`
--

CREATE TABLE `userinfo` (
  `student_id` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `security_number` int(3) NOT NULL,
  `id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userinfo`
--

INSERT INTO `userinfo` (`student_id`, `password`, `security_number`, `id`) VALUES
('shivan', 'q', 1, 83),
('c01234', 'qwerty', 1, 85),
('c032029', 'shivam', 7, 86),
('sam', 'sam', 7, 87),
('c0320329@lambton.ca', 'shivam', 8, 88),
('c0989812@mylambton.ca', 'mylambton', 8, 89),
('c0123423@mylambton.ca', 'lambton', 8, 90),
('c01@mylabton.ca', 'mrrobot', 8, 91),
('c0330324', 'shivam', 8, 92),
('c0330326', 'shivam', 8, 93),
('c077668899', 'sam', 8, 94),
('c9898786', 'sam', 8, 95),
('c989878896', 'sam', 8, 96),
('c0125673', 'sam', 8, 97),
('c0125467', 'sam', 8, 98),
('c01235674', 'sam', 8, 99),
('c0567845', 'sam', 8, 100);

-- --------------------------------------------------------

--
-- Table structure for table `userscore`
--

CREATE TABLE `userscore` (
  `user_info` varchar(200) DEFAULT NULL,
  `score` int(3) DEFAULT NULL,
  `id` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userscore`
--

INSERT INTO `userscore` (`user_info`, `score`, `id`) VALUES
('shivan', 300, 30),
('c01234', 300, 34),
('c032029', 300, 35),
('sam', 150, 36),
('c0320329@lambton.ca', 300, 37),
('c0989812@mylambton.ca', 300, 38),
('c0123423@mylambton.ca', 150, 39),
('c01@mylabton.ca', 300, 40),
('c0330324', 300, 41),
('c0330326', 300, 42),
('c077668899', 300, 43),
('c9898786', 300, 44),
('c989878896', 300, 45),
('c0125673', 150, 46),
('c0125467', 150, 47),
('c01235674', 150, 48),
('c0567845', 150, 49);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `businfo`
--
ALTER TABLE `businfo`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `userinfo`
--
ALTER TABLE `userinfo`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `userscore`
--
ALTER TABLE `userscore`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `businfo`
--
ALTER TABLE `businfo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `userinfo`
--
ALTER TABLE `userinfo`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT for table `userscore`
--
ALTER TABLE `userscore`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
