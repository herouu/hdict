DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;

BEGIN;
INSERT INTO `sys_user` VALUES (1, 'lijz', 1, NULL);
INSERT INTO `sys_user` VALUES (2, 'lucy', 1, NULL);
INSERT INTO `sys_user` VALUES (3, 'bear', 2, NULL);
INSERT INTO `sys_user` VALUES (4, 'mike', 1, NULL);
INSERT INTO `sys_user` VALUES (5, 'lisan', 1, NULL);
INSERT INTO `sys_user` VALUES (6, 'xb', 1, NULL);
INSERT INTO `sys_user` VALUES (7, 'duanwu', 2, NULL);
INSERT INTO `sys_user` VALUES (8, 'fenh', 1, NULL);
INSERT INTO `sys_user` VALUES (9, 'lj', 2, NULL);
INSERT INTO `sys_user` VALUES (10, 'gshen', 1, NULL);
INSERT INTO `sys_user` VALUES (11, 'lihui', 1, NULL);
COMMIT;