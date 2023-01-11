/*
 Navicat Premium Data Transfer

 Source Server         : 110.40.223.130_3306
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : 110.40.223.130:3306
 Source Schema         : medicine_helper

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 23/06/2022 15:13:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for allergy
-- ----------------------------
DROP TABLE IF EXISTS `allergy`;
CREATE TABLE `allergy`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `medicine_id` bigint(0) NOT NULL COMMENT '药品id',
  PRIMARY KEY (`user_id`, `medicine_id`) USING BTREE,
  INDEX `FK_allergy2`(`medicine_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'allergy' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of allergy
-- ----------------------------

-- ----------------------------
-- Table structure for eat
-- ----------------------------
DROP TABLE IF EXISTS `eat`;
CREATE TABLE `eat`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `medicine_id` bigint(0) NOT NULL COMMENT '药品id',
  `dose` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '服用时间',
  `is_taken` tinyint(1) NOT NULL COMMENT '是否服用，若已服用，则成为用药历史',
  PRIMARY KEY (`user_id`, `medicine_id`) USING BTREE,
  INDEX `FK_eat2`(`medicine_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'eat' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of eat
-- ----------------------------

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '药品id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '药品名称',
  `picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `property` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性状',
  `indication` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '适用症',
  `symptom` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '过敏症状',
  `administration` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服用方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'medicine' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of medicine
-- ----------------------------

-- ----------------------------
-- Table structure for medicine_chest
-- ----------------------------
DROP TABLE IF EXISTS `medicine_chest`;
CREATE TABLE `medicine_chest`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `medicine_id` bigint(0) NOT NULL COMMENT '药品id',
  PRIMARY KEY (`user_id`, `medicine_id`) USING BTREE,
  INDEX `FK_possess2`(`medicine_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'medicine_chest' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of medicine_chest
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'user' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
