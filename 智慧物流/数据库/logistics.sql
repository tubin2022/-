/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 80022
Source Host           : localhost:3306
Source Database       : logistics

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2022-10-11 14:37:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `feedback_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `logistics_id` int NOT NULL,
  `feedback_context` varchar(1000) NOT NULL,
  `is_handle` int NOT NULL,
  `handle_user_id` int DEFAULT NULL,
  `feed_time` datetime NOT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES ('1', '1', '1', '物流速度可以', '0', null, '2022-09-11 17:39:01');
INSERT INTO `feedback` VALUES ('2', '5', '1', '物流速度可以', '0', null, '2022-09-11 20:29:53');
INSERT INTO `feedback` VALUES ('3', '1', '11', '物流速度慢', '0', null, '2022-09-18 22:13:57');

-- ----------------------------
-- Table structure for logistics
-- ----------------------------
DROP TABLE IF EXISTS `logistics`;
CREATE TABLE `logistics` (
  `logistics_id` int NOT NULL AUTO_INCREMENT,
  `logistics_number` varchar(50) NOT NULL,
  `logistics_type` varchar(50) NOT NULL,
  `send_user_id` int NOT NULL,
  `dispatch_user_id` varchar(100) DEFAULT NULL,
  `receive_user_name` varchar(50) NOT NULL,
  `receive_user_phone` varchar(20) NOT NULL,
  `send_address` varchar(100) NOT NULL,
  `receive_address` varchar(100) NOT NULL,
  `pass_addresses` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `logistics_status` varchar(20) NOT NULL,
  `send_time` datetime NOT NULL,
  `receive_time` datetime DEFAULT NULL,
  `weight` decimal(10,2) NOT NULL,
  `cost` decimal(10,2) NOT NULL,
  `dispatch_company` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pass_time` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`logistics_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logistics
-- ----------------------------
INSERT INTO `logistics` VALUES ('14', '665429058561b477dc', '生活用品', '1', '[1,1,1,1]', '黄晓明1', '15180204814', '[115.235859,26.356909]', '[115.870943,28.740071]', '[[115.235859,26.356909],[115.363475,26.338129],[114.817007,25.819777],[115.761074,28.068381],[115.870943,28.740071]]', '退货', '2022-09-18 12:50:54', '2022-09-18 21:37:30', '5.00', '60.00', '顺丰', '[1663476654290,1663476891781,1663507935300,1663508049552,1663508164996]');
INSERT INTO `logistics` VALUES ('15', '8931656c257ec11bbd', '行李寄送', '1', '[1,1,1]', '黄晓明1', '15180204814', '[115.235859,26.356909]', '[115.870943,28.740071]', '[[115.235859,26.356909],[114.817007,25.819777],[115.761074,28.068381],[115.870943,28.740071]]', '已签收', '2022-09-19 22:48:52', '2022-09-19 22:55:20', '12.00', '60.00', '京东', '[1663598931656,1663599075256,1663599180679,1663599255703]');
INSERT INTO `logistics` VALUES ('49', '8454077ec0e3105f31', '物品', '1', '[1,1,1]', '黄晓明1', '15180204814', '[106.693,26.463318]', '[119.90306,28.45927]', '[[106.693,26.463318],[113.067841,28.145309],[115.919045,28.661942],[119.90306,28.45927]]', '退货', '2022-09-22 14:34:14', '2022-09-22 14:37:05', '12.00', '45.00', '京东', '[1663828454077,1663828543184,1663828574572,1663828600102]');
INSERT INTO `logistics` VALUES ('50', '86885780b48afe623b', '物品', '1', null, '黄晓明1', '15180204814', '[115.235859,26.356909]', '[115.857972,28.682976]', '[[115.235859,26.356909]]', '派件中', '2022-09-22 14:38:09', null, '12.00', '23.00', '顺丰', '[1663828688579]');
INSERT INTO `logistics` VALUES ('52', '73604872633b55b92f', '物品', '1', '[1]', '黄晓明1', '15180204814', '[115.870943,28.740071]', '[115.980648,28.715707]', '[[115.870943,28.740071],[115.980648,28.715707]]', '已签收', '2022-09-28 20:16:00', '2022-09-28 20:52:58', '12.00', '23.00', '京东', '[1664367360487,1664368619293]');
INSERT INTO `logistics` VALUES ('54', '018623186ba2dfa131', '物品', '1', '[1]', '黄晓明1', '15180204814', '[115.870943,28.740071]', '[115.980648,28.715707]', '[[115.870943,28.740071],[115.980648,28.715707]]', '退货', '2022-09-28 21:03:06', '2022-09-28 21:05:57', '12.00', '34.00', '京东', '[1664370186231,1664370274771]');
INSERT INTO `logistics` VALUES ('55', '18447654c0da084252', '物品', '6', '[1,1]', '涂斌', '13822927086', '[115.980648,28.715707]', '[115.870943,28.740071]', '[[115.980648,28.715707],[116.427341,39.902842],[121.429462,31.153127]]', '派件中', '2022-09-29 14:10:45', null, '12.00', '23.00', '京东', '[1664431844765,1664432846899,1664432866769]');
INSERT INTO `logistics` VALUES ('56', '25978868a5ba10fb03', '物品', '6', '[1]', '涂斌', '13822927086', '[115.870943,28.740071]', '[115.79297,28.622865]', '[[115.870943,28.740071],[115.79297,28.622865]]', '待签收', '2022-09-29 14:23:18', null, '12.00', '34.00', '顺丰', '[1664432597886,1664432752818]');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permission_id` int NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(255) NOT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'dispatchUser');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_permission_id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL,
  `permission_id` int NOT NULL,
  PRIMARY KEY (`role_permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_account` varchar(20) NOT NULL,
  `user_password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `password_salt` varchar(100) NOT NULL,
  `user_gender` varchar(10) DEFAULT NULL,
  `user_phone` varchar(20) NOT NULL,
  `user_birthday` date DEFAULT NULL,
  `registe_time` date NOT NULL,
  `is_delete` int NOT NULL,
  `pass_face` varchar(1000) DEFAULT NULL,
  `now_face` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '2779ff5dec132e745997ec04ecf3d254', '黄晓明1', '8c906415-c54f-4b8c-9cfa-7fc72bbec4c3admin', '男', '15180204814', null, '2022-09-09', '0', '[\"http://192.168.100.182:8087/userFace/2022-09-19/e199f678-606f-4587-8ee2-111f2f0ae4ab.png\"]', 'http://192.168.100.182:8087/userFace/2022-09-19/e199f678-606f-4587-8ee2-111f2f0ae4ab.png');
INSERT INTO `user` VALUES ('3', '9527', '2a5ed347ee8ba5873352d34fc1b3702d', '赵丽颖2', '773c23bf-840f-4c91-8ae9-3708268a281c9527', '女', '15632541563', '2001-03-19', '2022-09-09', '0', null, null);
INSERT INTO `user` VALUES ('4', '9528', 'e6344d469171b710da7e6c8e3a70ac82', '王俊凯3', '9d3ec37d-e484-4365-b207-78f4ace3c75a9528', '女', '11111111111', '2022-03-04', '2022-09-09', '0', null, null);
INSERT INTO `user` VALUES ('5', '9529', 'b277850209b8c444fba61fb05fcb8f6c', '王元3', 'bfac6b0f-af7e-46ed-af3b-f210c36b8e729529', '女', '', '2020-05-21', '2022-09-11', '0', null, null);
INSERT INTO `user` VALUES ('6', '9530', 'f85399b846eff35dad952f815b7d7798', '涂斌', '7e3e689e-3301-4c17-ab4a-9c620ff4deab9530', '男', '13822927086', '2001-05-24', '2022-09-19', '0', '[\"http://192.168.100.182:8087/userFace/2022-09-29/2baf9dc1-d626-433c-8f6c-e06ad6cba951.png\"]', 'http://192.168.100.182:8087/userFace/2022-09-29/2baf9dc1-d626-433c-8f6c-e06ad6cba951.png');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_role_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
