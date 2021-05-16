/*
 Navicat Premium Data Transfer

 Source Server         : docker-mysql
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : localhost:3306
 Source Schema         : x-rules

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 16/05/2021 21:10:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for BLACK_LIST
-- ----------------------------
DROP TABLE IF EXISTS `BLACK_LIST`;
CREATE TABLE `BLACK_LIST` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dimension` varchar(32) NOT NULL DEFAULT '' COMMENT '维度',
  `type` varchar(16) NOT NULL DEFAULT '' COMMENT '类型',
  `value` varchar(128) NOT NULL DEFAULT '' COMMENT '值',
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  `detail` varchar(512) DEFAULT NULL COMMENT '详情',
  PRIMARY KEY (`id`),
  KEY `dimension` (`dimension`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of BLACK_LIST
-- ----------------------------
BEGIN;
INSERT INTO `BLACK_LIST` VALUES (1, 'IP', 'BLACK', '127.0.0.1', '2021-05-15 14:29:42', NULL);
INSERT INTO `BLACK_LIST` VALUES (2, 'IP', 'BLACK', 'localhost', '2021-05-16 05:53:37', NULL);
COMMIT;

-- ----------------------------
-- Table structure for SYS_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS `SYS_CONFIG`;
CREATE TABLE `SYS_CONFIG` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `key` varchar(32) NOT NULL DEFAULT '' COMMENT '键',
  `value` varchar(128) NOT NULL DEFAULT '' COMMENT '值',
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  `detail` varchar(512) NOT NULL DEFAULT '' COMMENT '详情',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of SYS_CONFIG
-- ----------------------------
BEGIN;
INSERT INTO `SYS_CONFIG` VALUES (1, 'X_RULES_SWITCH', 'ON', '2021-05-16 13:10:21', '风控分析开关，ON开启分析，OFF关闭分析消息丢弃');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
