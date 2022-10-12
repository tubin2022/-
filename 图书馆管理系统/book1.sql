-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 2022-10-12 07:45:29
-- 服务器版本： 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `book1`
--

-- --------------------------------------------------------

--
-- 表的结构 `book`
--

CREATE TABLE `book` (
  `bid` int(11) NOT NULL,
  `bname` varchar(100) DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `photo` varchar(1000) DEFAULT NULL,
  `pdate` date DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `existing` int(11) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `book`
--

INSERT INTO `book` (`bid`, `bname`, `author`, `photo`, `pdate`, `total`, `existing`, `cid`) VALUES
(1, '人间失格', '太宰治', 'http://localhost/project/books/uploadImg/1234521.png', '1948-01-01', 210, 209, 1),
(2, '平凡的世界', '路遥', 'http://localhost/project/books/uploadImg/1542879.png', '1986-01-01', 100, 99, 1),
(3, '月亮与六便士', '威廉.萨默塞特.毛姆', 'http://localhost/project/books/uploadImg/20220515145920.png', '1919-12-14', 50, 50, 1),
(4, '请问我是谁', '利兹.考利', 'http://localhost/project/books/uploadImg/20220515145834.png', '2015-03-25', 30, 30, 1),
(5, '机器人大师', '斯坦尼斯拉夫·莱姆', 'http://localhost/project/books/uploadImg/20220515150927.png', '2014-12-01', 60, 59, 2),
(18, '猫城记', '老舍', 'http://localhost/project/books/uploadImg/1653104489647.png', '1993-03-16', 100, 100, 2);

-- --------------------------------------------------------

--
-- 表的结构 `b_r`
--

CREATE TABLE `b_r` (
  `id` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL,
  `bid` int(11) DEFAULT NULL,
  `bdate` date DEFAULT NULL,
  `rdate` date DEFAULT NULL,
  `state` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `b_r`
--

INSERT INTO `b_r` (`id`, `uid`, `bid`, `bdate`, `rdate`, `state`) VALUES
(16, 4, 2, '2022-05-16', '2022-05-16', 0),
(11, 4, 1, '2022-05-15', NULL, 1),
(12, 4, 5, '2022-05-15', '2022-05-15', 0),
(19, 4, 3, '2022-05-16', '2022-05-16', 0),
(20, 4, 5, '2022-05-16', NULL, 1),
(24, 8, 1, '2022-05-22', '2022-05-22', 0),
(22, 4, 2, '2022-05-16', NULL, 1),
(23, 4, 5, '2022-05-16', '2022-05-20', 0);

-- --------------------------------------------------------

--
-- 表的结构 `category`
--

CREATE TABLE `category` (
  `cid` int(11) NOT NULL,
  `cname` varchar(100) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `category`
--

INSERT INTO `category` (`cid`, `cname`, `description`) VALUES
(1, '小说类', '是一种以刻画人物形象为中心、通过完整的故事情节和环境描写来反映社会生活的文学体裁'),
(2, '科幻类', '其定义为在尊重科学结论的基础上进行合理设想的小说'),
(8, '儿童文学', '《儿童文学》是由共青团中央和中国作家协会于1963年共同创办的杂志，被誉为“中国儿童文学的一面旗帜”。《儿童文学》是由共青团中央和中国作家协会于1963年共同创办的杂志，被誉为“中国儿童文学的一面旗帜”。'),
(9, '神魔小说', '神魔小说来源于鲁迅的提法，该类小说在明清时期较为兴盛。其语言风格不拘一格，想象力丰富，背景或为虚幻或为海外某地假托，综合宗教、神话等民间喜闻乐见的形式，因此广为传颂。不少文人或依历史事件，或依流行的神怪故事，写了大量名著。');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE `user` (
  `uid` int(11) NOT NULL,
  `uname` varchar(200) DEFAULT NULL,
  `password` varchar(20) NOT NULL,
  `userface` varchar(200) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `role` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`uid`, `uname`, `password`, `userface`, `phone`, `role`) VALUES
(2, 'admin', '123321', NULL, '123456789', 1),
(8, 'rose2', '123qwe', 'http://localhost/project/books/uploadImg/16531890136399.jpeg', '12345678912', 0),
(4, 'jerry', '123abc', 'http://localhost/project/books/uploadImg/16532115928907.jpeg', '15523645214', 0),
(9, 'rose2', '123', NULL, '15236524521', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`bid`),
  ADD KEY `cid` (`cid`);

--
-- Indexes for table `b_r`
--
ALTER TABLE `b_r`
  ADD PRIMARY KEY (`id`),
  ADD KEY `uid` (`uid`),
  ADD KEY `bid` (`bid`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`cid`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`uid`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `book`
--
ALTER TABLE `book`
  MODIFY `bid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- 使用表AUTO_INCREMENT `b_r`
--
ALTER TABLE `b_r`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
--
-- 使用表AUTO_INCREMENT `category`
--
ALTER TABLE `category`
  MODIFY `cid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- 使用表AUTO_INCREMENT `user`
--
ALTER TABLE `user`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
