-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Φιλοξενητής: 127.0.0.1:3306
-- Χρόνος δημιουργίας: 03 Ιουν 2021 στις 19:21:19
-- Έκδοση διακομιστή: 5.7.31
-- Έκδοση PHP: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Βάση δεδομένων: `xmlestore`
--

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `orderitems`
--

DROP TABLE IF EXISTS `orderitems`;
CREATE TABLE IF NOT EXISTS `orderitems` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` int(11) NOT NULL,
  `itemid` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `orderitems`
--

INSERT INTO `orderitems` (`id`, `orderid`, `itemid`, `quantity`) VALUES
(55, 32, 3, 1),
(56, 32, 2, 2),
(57, 33, 3, 2),
(58, 33, 4, 1),
(59, 33, 2, 1),
(60, 34, 3, 1),
(61, 34, 2, 1),
(62, 34, 5, 3),
(63, 35, 3, 2),
(64, 35, 2, 1),
(65, 35, 1, 1),
(69, 38, 2, 2),
(70, 38, 5, 1),
(71, 38, 1, 1),
(72, 39, 2, 3),
(73, 39, 3, 2),
(80, 42, 4, 1),
(85, 45, 2, 1),
(86, 45, 3, 1);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(55) COLLATE utf8_unicode_ci NOT NULL,
  `price` double NOT NULL,
  `imgsrc` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `product`
--

INSERT INTO `product` (`id`, `name`, `price`, `imgsrc`) VALUES
(1, 'Small Box', 3.95, 'box_Small.png'),
(2, 'Medium Box', 5.95, 'box_Medium.png'),
(3, 'Large Box', 9.95, 'box_Large.png'),
(4, 'XL Box', 13.95, 'box_XL.png'),
(5, 'XXL Box', 19.95, 'box_XXL.png');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `shipingdetails`
--

DROP TABLE IF EXISTS `shipingdetails`;
CREATE TABLE IF NOT EXISTS `shipingdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` int(11) NOT NULL,
  `firstname` varchar(55) COLLATE utf8_unicode_ci NOT NULL,
  `lastname` varchar(55) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(55) COLLATE utf8_unicode_ci NOT NULL,
  `city` varchar(55) COLLATE utf8_unicode_ci NOT NULL,
  `country` varchar(55) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `shipingdetails`
--

INSERT INTO `shipingdetails` (`id`, `orderid`, `firstname`, `lastname`, `address`, `city`, `country`) VALUES
(26, 32, 'Neofytos', 'Konstantinidis', 'Alexiou 25', 'Athens', 'Greece'),
(27, 33, 'Kostas', 'Kostou', 'Vlahou 12', 'Thessaloniki', 'Greece'),
(28, 34, 'Giannis', 'Giannou', 'Leventou 33', 'Trikala', 'Greece'),
(29, 35, 'Abraham', 'Linkoln', 'Palm St. 303', 'New York', 'America'),
(32, 38, 'Marios', 'Kanaris', 'Kanari 26', 'Athens', 'Greece'),
(33, 39, 'Giannis', 'Patouras', 'Alekou 31', 'Athens', 'Greece'),
(39, 45, 'Firstname', 'Lastname', 'Address1', 'City1', 'Country1');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `shoporder`
--

DROP TABLE IF EXISTS `shoporder`;
CREATE TABLE IF NOT EXISTS `shoporder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) NOT NULL,
  `order_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `shoporder`
--

INSERT INTO `shoporder` (`id`, `status`, `order_time`) VALUES
(32, 0, '2021-05-11 02:09:14'),
(33, 1, '2021-05-11 02:15:32'),
(34, 4, '2021-05-11 02:27:50'),
(35, 2, '2021-05-11 02:32:24'),
(38, 1, '2021-05-13 22:16:31'),
(39, 4, '2021-05-18 23:35:30'),
(45, 4, '2021-06-02 21:19:00');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `tracking`
--

DROP TABLE IF EXISTS `tracking`;
CREATE TABLE IF NOT EXISTS `tracking` (
  `tracking_id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` int(11) NOT NULL,
  `tracking_number` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`tracking_id`)
) ENGINE=MyISAM AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Άδειασμα δεδομένων του πίνακα `tracking`
--

INSERT INTO `tracking` (`tracking_id`, `orderid`, `tracking_number`) VALUES
(33, 39, '202151839'),
(32, 38, '202151338'),
(39, 45, '20216245'),
(29, 35, '202151135'),
(28, 34, '202151134'),
(27, 33, '202151133'),
(26, 32, '202151132');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
