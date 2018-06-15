/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : study_project

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-06-15 09:57:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '计划名称',
  `description` varchar(255) DEFAULT NULL COMMENT '计划描述',
  `start_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '结束时间',
  `type` varchar(255) DEFAULT NULL COMMENT '计划类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', 'spring boot', '学习快速开发软件的技巧', '2018-05-23 16:24:56', '2018-06-30 16:25:00', '1');

-- ----------------------------
-- Table structure for questionentity
-- ----------------------------
DROP TABLE IF EXISTS `questionentity`;
CREATE TABLE `questionentity` (
  `id` varchar(250) NOT NULL,
  `pageId` varchar(250) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `require` int(2) DEFAULT NULL,
  `defaultValue` varchar(255) DEFAULT NULL,
  `parentId` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `index` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of questionentity
-- ----------------------------

-- ----------------------------
-- Table structure for questionpropentity
-- ----------------------------
DROP TABLE IF EXISTS `questionpropentity`;
CREATE TABLE `questionpropentity` (
  `id` varchar(255) NOT NULL,
  `quesId` varchar(255) DEFAULT NULL,
  `propName` varchar(255) DEFAULT NULL,
  `propValue` varchar(255) DEFAULT NULL,
  `rowNumber` int(3) DEFAULT NULL,
  `propOgnl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of questionpropentity
-- ----------------------------

-- ----------------------------
-- Table structure for surveydataentity
-- ----------------------------
DROP TABLE IF EXISTS `surveydataentity`;
CREATE TABLE `surveydataentity` (
  `id` varchar(255) NOT NULL,
  `surId` varchar(255) DEFAULT NULL,
  `orgId` varchar(255) DEFAULT NULL,
  `postDate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `ipAddress` varchar(255) DEFAULT NULL,
  `weight` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of surveydataentity
-- ----------------------------

-- ----------------------------
-- Table structure for surveydatapropentity
-- ----------------------------
DROP TABLE IF EXISTS `surveydatapropentity`;
CREATE TABLE `surveydatapropentity` (
  `id` varchar(255) NOT NULL,
  `surDataId` varchar(255) DEFAULT NULL,
  `questionId` varchar(255) DEFAULT NULL,
  `propValue` varchar(255) DEFAULT NULL,
  `rowNumber` int(3) DEFAULT NULL,
  `propId` varchar(255) DEFAULT NULL,
  `propName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of surveydatapropentity
-- ----------------------------

-- ----------------------------
-- Table structure for surveyentity
-- ----------------------------
DROP TABLE IF EXISTS `surveyentity`;
CREATE TABLE `surveyentity` (
  `id` varchar(250) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `template` varchar(255) DEFAULT NULL,
  `subTitle` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `endDate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `postBack` int(2) DEFAULT NULL,
  `weight` int(10) DEFAULT NULL,
  `validate` int(1) DEFAULT NULL,
  `endNote` varchar(255) DEFAULT NULL,
  `endAction` varchar(255) DEFAULT NULL,
  `endActionName` varchar(255) DEFAULT NULL,
  `period` int(3) DEFAULT NULL,
  `describe` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of surveyentity
-- ----------------------------
INSERT INTO `surveyentity` VALUES ('d6a48c3e-703e-11e8-9684-6c626df84ff6', '测试', null, null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for surveypageentity
-- ----------------------------
DROP TABLE IF EXISTS `surveypageentity`;
CREATE TABLE `surveypageentity` (
  `id` varchar(250) NOT NULL,
  `surId` varchar(250) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `index` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of surveypageentity
-- ----------------------------

-- ----------------------------
-- Table structure for u_permission
-- ----------------------------
DROP TABLE IF EXISTS `u_permission`;
CREATE TABLE `u_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(256) DEFAULT NULL COMMENT 'url地址',
  `name` varchar(64) DEFAULT NULL COMMENT 'url描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_permission
-- ----------------------------
INSERT INTO `u_permission` VALUES ('21', null, '删除');
INSERT INTO `u_permission` VALUES ('22', null, '添加');
INSERT INTO `u_permission` VALUES ('23', null, '修改');

-- ----------------------------
-- Table structure for u_role
-- ----------------------------
DROP TABLE IF EXISTS `u_role`;
CREATE TABLE `u_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `type` varchar(10) DEFAULT NULL COMMENT '角色类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_role
-- ----------------------------
INSERT INTO `u_role` VALUES ('5', '管理员', '1');
INSERT INTO `u_role` VALUES ('6', '操作员', '1');

-- ----------------------------
-- Table structure for u_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `u_role_permission`;
CREATE TABLE `u_role_permission` (
  `rid` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `pid` bigint(20) DEFAULT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_role_permission
-- ----------------------------
INSERT INTO `u_role_permission` VALUES ('5', '21');
INSERT INTO `u_role_permission` VALUES ('5', '23');

-- ----------------------------
-- Table structure for u_user
-- ----------------------------
DROP TABLE IF EXISTS `u_user`;
CREATE TABLE `u_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱|登录帐号',
  `pswd` varchar(32) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` bigint(1) DEFAULT '1' COMMENT '1:有效，0:禁止登录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_user
-- ----------------------------
INSERT INTO `u_user` VALUES ('15', 'admin', null, '123456', null, null, '1');
INSERT INTO `u_user` VALUES ('16', 'admin2', null, '123456', null, null, '1');

-- ----------------------------
-- Table structure for u_user_role
-- ----------------------------
DROP TABLE IF EXISTS `u_user_role`;
CREATE TABLE `u_user_role` (
  `uid` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `rid` bigint(20) DEFAULT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_user_role
-- ----------------------------
INSERT INTO `u_user_role` VALUES ('15', '5');
