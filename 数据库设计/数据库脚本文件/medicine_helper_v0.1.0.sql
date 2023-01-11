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

 Date: 05/07/2022 14:19:05
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
INSERT INTO `allergy` VALUES (1, 1);
INSERT INTO `allergy` VALUES (11, 1);
INSERT INTO `allergy` VALUES (12, 1);
INSERT INTO `allergy` VALUES (1, 2);
INSERT INTO `allergy` VALUES (11, 2);
INSERT INTO `allergy` VALUES (12, 2);
INSERT INTO `allergy` VALUES (1, 4);
INSERT INTO `allergy` VALUES (11, 4);
INSERT INTO `allergy` VALUES (12, 4);
INSERT INTO `allergy` VALUES (13, 4);
INSERT INTO `allergy` VALUES (1, 5);
INSERT INTO `allergy` VALUES (1, 6);
INSERT INTO `allergy` VALUES (12, 6);
INSERT INTO `allergy` VALUES (12, 9);

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '医生id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生姓名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of doctor
-- ----------------------------
INSERT INTO `doctor` VALUES (1, 'qhh', '123456');
INSERT INTO `doctor` VALUES (2, 'admin', '123456');

-- ----------------------------
-- Table structure for eat
-- ----------------------------
DROP TABLE IF EXISTS `eat`;
CREATE TABLE `eat`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `medicine_id` bigint(0) NOT NULL COMMENT '药品id',
  `dose` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '剂量',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '服用时间',
  `is_taken` tinyint(1) NOT NULL COMMENT '是否服用，若已服用，则成为用药历史',
  `type` bigint(0) NULL DEFAULT NULL COMMENT '提醒类型：1表单；2药箱；3语音；4用药历史',
  `way` bigint(0) NULL DEFAULT NULL COMMENT '提醒方式：1语音；2闹钟；3弹窗',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_eat2`(`medicine_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'eat' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of eat
-- ----------------------------
INSERT INTO `eat` VALUES (1, 3, 1, '1', '2022-06-24 06:54:43', 1, NULL, NULL, NULL);
INSERT INTO `eat` VALUES (2, 3, 2, '1', '2022-06-25 14:26:14', 1, NULL, NULL, NULL);
INSERT INTO `eat` VALUES (3, 3, 1, '3', '2022-06-24 06:54:43', 1, 1, 2, NULL);
INSERT INTO `eat` VALUES (4, 3, 2, '3', '2022-06-10 14:27:47', 1, 1, 2, NULL);
INSERT INTO `eat` VALUES (5, 1, 1, '一片', '2022-06-30 11:51:58', 1, 1, 2, '今天吃这个');
INSERT INTO `eat` VALUES (6, 1, 6, '一片', '2022-07-01 11:51:58', 1, 1, 2, '今天吃这个');
INSERT INTO `eat` VALUES (7, 1, 7, '两片', '2022-07-01 18:51:58', 0, 1, 2, '今天吃这个');
INSERT INTO `eat` VALUES (8, 2, 8, '5ml', '2022-07-02 16:09:09', 0, 1, 2, '今天吃药');
INSERT INTO `eat` VALUES (9, 11, 1, 'yipian', '2022-07-02 12:21:38', 1, 1, 2, '阿莫西林');
INSERT INTO `eat` VALUES (10, 1, 4, '5ml', '2022-07-04 12:22:50', 0, 1, 2, '青霉素');
INSERT INTO `eat` VALUES (11, 13, 1, '一片', '2022-07-05 10:35:28', 1, 1, 2, 'test');

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
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'medicine' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of medicine
-- ----------------------------
INSERT INTO `medicine` VALUES (1, '阿莫西林', '/medicine/pic2.jpg', '胶囊、分散片、注射液', '细菌感染', '恶心、呕吐、腹泻', '口服');
INSERT INTO `medicine` VALUES (2, '阿司匹林', '/medicine/pic3.jpg', '片剂、肠溶片、肠溶胶囊、泡腾片、栓剂', '发热、疼痛及类风湿关节炎', '皮疹、血管神经性水肿及哮喘', '口服');
INSERT INTO `medicine` VALUES (4, '青霉素', '/medicine/pic5.jpg', '胶囊', '敏感菌或敏感病原体所致的感染', '神经炎、二重感染', '口服');
INSERT INTO `medicine` VALUES (6, '艾瑞昔布片', '/medicine/pic1.jpg', '颗粒', '腰疼', '暂无', '口服');
INSERT INTO `medicine` VALUES (9, '999感冒灵', NULL, '颗粒', '感冒', NULL, NULL);
INSERT INTO `medicine` VALUES (10, '888感冒灵', NULL, '颗粒', '感冒', NULL, NULL);

-- ----------------------------
-- Table structure for medicine_chest
-- ----------------------------
DROP TABLE IF EXISTS `medicine_chest`;
CREATE TABLE `medicine_chest`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `medicine_id` bigint(0) NOT NULL COMMENT '药品id',
  PRIMARY KEY (`user_id`, `medicine_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'medicine_chest' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of medicine_chest
-- ----------------------------
INSERT INTO `medicine_chest` VALUES (1, 1);
INSERT INTO `medicine_chest` VALUES (1, 2);
INSERT INTO `medicine_chest` VALUES (1, 4);
INSERT INTO `medicine_chest` VALUES (1, 6);

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
  `uid` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户唯一uuid',
  `doctor_id` bigint(0) NULL DEFAULT NULL COMMENT '医生id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'user' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '17858600640', '1192445074@qq.com', '123456', '吴贤权', 'https://qhh-guli.oss-cn-hangzhou.aliyuncs.com/2022/07/04/wxq.jpg', 'UBW50CF6', 1);
INSERT INTO `user` VALUES (11, '15397025987', '1226760471@qq.com', '123456', '邱慧慧', 'https://qhh-guli.oss-cn-hangzhou.aliyuncs.com/2022/07/03/acf733bf642e432a9d53d259d6fcb32afile.png', NULL, 1);
INSERT INTO `user` VALUES (12, '12345623456', '23435678', '123456', 'QHH', 'https://qhh-guli.oss-cn-hangzhou.aliyuncs.com/2022/07/03/b634466961044c458b18a88c09e30bc5file.png', NULL, 1);
INSERT INTO `user` VALUES (13, '18757054441', '1611024620@qq.com', '123456', 'test', 'https://qhh-guli.oss-cn-hangzhou.aliyuncs.com/2022/07/03/b634466961044c458b18a88c09e30bc5file.png', NULL, 2);

SET FOREIGN_KEY_CHECKS = 1;
