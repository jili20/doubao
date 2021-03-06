# ************************************************************
# Sequel Pro SQL dump
# Version 5446
#
# https://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 8.0.22)
# Database: doubao
# Generation Time: 2021-03-06 13:39:25 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table bms_billboard
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bms_billboard`;

CREATE TABLE `bms_billboard` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告',
  `create_time` datetime DEFAULT NULL COMMENT '公告时间',
  `show` tinyint(1) DEFAULT NULL COMMENT '1：展示中，0：过期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='全站公告';

LOCK TABLES `bms_billboard` WRITE;
/*!40000 ALTER TABLE `bms_billboard` DISABLE KEYS */;

INSERT INTO `bms_billboard` (`id`, `content`, `create_time`, `show`)
VALUES
	(2,'R1.0 开始已实现护眼模式 ,妈妈再也不用担心我的眼睛了。','2020-11-19 17:16:19',0),
	(4,'系统已更新至最新版1.0.1','2020-11-19 17:16:19',1);

/*!40000 ALTER TABLE `bms_billboard` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table bms_comment
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bms_comment`;

CREATE TABLE `bms_comment` (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '内容',
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者ID',
  `topic_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'topic_id',
  `create_time` datetime NOT NULL COMMENT '发布时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='评论表';

LOCK TABLES `bms_comment` WRITE;
/*!40000 ALTER TABLE `bms_comment` DISABLE KEYS */;

INSERT INTO `bms_comment` (`id`, `content`, `user_id`, `topic_id`, `create_time`, `modify_time`)
VALUES
	('1','今天下雨，混装','1349618748226658305','1367710561588060162','2021-03-06 11:08:09','2021-03-06 11:08:09'),
	('1368074971653390337','测试发表评论','1366650171638743042','1367710561588060162','2021-03-06 13:44:26',NULL),
	('1368084428177375234','','1366650171638743042','1367710561588060162','2021-03-06 14:22:00',NULL),
	('1368084639792594945','','1366650171638743042','1367710561588060162','2021-03-06 14:22:51',NULL),
	('1368084716158287874','','1366650171638743042','1367710561588060162','2021-03-06 14:23:09',NULL),
	('1368086361583747074','添加评论','1366650171638743042','1367710561588060162','2021-03-06 14:29:41',NULL),
	('1368102220809158657','','1366650171638743042','1367710561588060162','2021-03-06 15:32:42',NULL),
	('1368106166072184833','','1366650171638743042','1367710561588060162','2021-03-06 15:48:23',NULL),
	('2','哈哈哈哈。。。。。','1349290158897311745','1367710561588060162','2021-03-06 11:08:09','2021-03-06 11:08:09');

/*!40000 ALTER TABLE `bms_comment` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table bms_follow
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bms_follow`;

CREATE TABLE `bms_follow` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '被关注人ID',
  `follower_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '关注人ID - 粉丝',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户关注 关系表';

LOCK TABLES `bms_follow` WRITE;
/*!40000 ALTER TABLE `bms_follow` DISABLE KEYS */;

INSERT INTO `bms_follow` (`id`, `parent_id`, `follower_id`)
VALUES
	(65,'1329723594994229250','1317498859501797378'),
	(85,'1332912847614083073','1332636310897664002'),
	(129,'1349290158897311745','1349618748226658305'),
	(131,'1349290158897311745','1366650171638743042');

/*!40000 ALTER TABLE `bms_follow` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table bms_post
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bms_post`;

CREATE TABLE `bms_post` (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'markdown内容',
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者ID',
  `comments` int NOT NULL DEFAULT '0' COMMENT '评论统计',
  `collects` int NOT NULL DEFAULT '0' COMMENT '收藏统计',
  `view` int NOT NULL DEFAULT '0' COMMENT '浏览统计',
  `top` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否置顶，1-是，0-否',
  `essence` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否加精，1-是，0-否',
  `section_id` int DEFAULT '0' COMMENT '专栏ID',
  `create_time` datetime NOT NULL COMMENT '发布时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  UNIQUE KEY `title` (`title`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='话题表';

LOCK TABLES `bms_post` WRITE;
/*!40000 ALTER TABLE `bms_post` DISABLE KEYS */;

INSERT INTO `bms_post` (`id`, `title`, `content`, `user_id`, `comments`, `collects`, `view`, `top`, `essence`, `section_id`, `create_time`, `modify_time`)
VALUES
	('1333447953558765569','1','12\n2\n\n','1349290158897311745',0,0,77,b'0',b'0',0,'2020-12-01 00:29:01','2020-12-03 23:56:51'),
	('1349362401438392322','2021 健康，快乐','2021的`FLAG`\n\n1. 技能进步\n2. 没有烦恼\n3. 发财 :smile:\n\n','1349290158897311745',0,0,22,b'0',b'0',0,'2021-01-13 22:27:21','2021-01-14 17:30:13'),
	('1334481725322297346','hello，spring-security',':hibiscus: spring-security\n\n','1349290158897311745',0,0,47,b'0',b'0',0,'2020-12-03 20:56:51',NULL),
	('1332650453142827009','哈哈哈，helloworld','这是第一篇哦\n\n> hi :handshake: 你好\n\n`hello world`\n\n:+1: 很好\n','1349290158897311745',0,0,29,b'0',b'0',1,'2020-11-28 19:40:02','2020-11-28 19:46:39'),
	('1333432347031646209','哈哈哈，换了个dark主题','主题更换为Dark\n\n','1349290158897311745',0,0,6,b'0',b'0',0,'2020-11-30 23:27:00',NULL),
	('1333668258587750401','嘿嘿，测试一下啊','大家好\n`Hello everyone!`\n\n\n\n','1349290158897311745',0,0,7,b'0',b'0',0,'2020-12-01 15:04:26','2020-12-01 16:49:14'),
	('1332682473151635458','我要发财','2021 冲冲冲！！！\n\n','1349290158897311745',0,0,94,b'0',b'0',2,'2020-11-28 21:47:16','2020-11-30 19:40:22'),
	('1367710561588060162','拜登上台后，特鲁多对华态度变了……','**特鲁多声称中国在孟晚舟被捕后捏造了针对两名加拿大人的指控。**\n\n**加拿大广播公司（CBC）4日报道称，加总理特鲁多当地时间3日针对中国驻加拿大大使丛培武有关孟晚舟案的表态，****声称中国在孟晚舟被捕后捏造了针对两名加拿大人的指控。****丛培武此前强调，两起案件是完全不相关的。**\n\n![](https://pics5.baidu.com/feed/902397dda144ad34bda99aefed4e28fc30ad85bc.jpeg?token=8f78e56b42918d6d7ad3582d928219ac&s=A0A0F41444F391882400B967030080E2)**特鲁多 资料图**\n\n**CBC称，特鲁多3日在新闻发布会上告诉记者，很明显，这两名加拿大人是在加拿大政府履行了对盟国美国的引渡条约责任几天后，“以捏造的国家安全指控被捕的”。CBC称，在特鲁多此番表态前，****中国驻加拿大大使丛培武接受媒体记者采访时指出，孟晚舟案与两名加拿大人的案件不相关。**\n\n![](https://pics7.baidu.com/feed/a044ad345982b2b7da4008a20c41efe774099be8.jpeg?token=04e6fc37df813a4322b614b38290b7b9&s=5F9E0FC106510BD472B1F8140300A0C0)**孟晚舟 资料图**\n\n**对于两名加拿大人被捕案，中方已多次强调，案件同孟晚舟事件性质完全不同。有关加公民涉嫌危害中国国家安全犯罪，中方主管部门对其进行逮捕、起诉和审理，纯属依法办事。**\n\n![](https://pics1.baidu.com/feed/94cad1c8a786c9179b1b117ff4d154c73ac75754.png?token=5aeebc78d9678bf632943466af177fc4&s=361317CB4C4AA41BDEBDC8BD03009012)**康明凯与迈克尔·斯帕弗**\n\n**此外，特鲁多3日还在新疆问题上口出狂言。丛培武大使3日与加主流媒体举行视频记者会时，针对早前加众议院投票认定新疆存在所谓的“种族灭绝”一事质问称，“在加众议院投票的议员，恐怕大多数人都没有去过新疆，甚至从未去过中国，那他们是如何对新疆局势作出判断的呢？”而特鲁多就此发言表示，加承诺将与国际盟友合作，既要找出明确的答案，又追究那些责任人，国际社会会让中国为涉疆行动承担“可能的后果”。**\n\n**加拿大众议院通过所谓涉疆动议时，中方就已表示强烈谴责和坚决反对，并向加方提出严正交涉。**\n\n**加拿大《环球邮报》4日发表文章称，特鲁多对中国的态度总体上温和，但****在拜登上台后，特鲁多对中国的口风强硬了一点。****文章还称，特鲁多最近表现出来的强硬，也可能是选举年里的一种政治上的战术性转变。**\n\n**来源：环球网**\n\n','1366650171638743042',0,0,78,b'0',b'0',0,'2021-03-05 13:36:23','2021-03-06 21:09:19'),
	('1333695976536748034','最新版本介绍，同步更新！','<p align=center>一款基于SpringBoot构建的智慧社区系统</p>\n\n<p align=center>\n<a href=\"https://github.com/1020317774/rhapsody-admin/stargazers\"><img alt=\"GitHub release\" src=\"https://img.shields.io/github/release/1020317774/rhapsody-admin?style=flat-square\"></a>\n<a href=\"https://github.com/1020317774/rhapsody-admin/blob/main/LICENSE\"><img alt=\"GitHub license\" src=\"https://img.shields.io/github/license/1020317774/rhapsody-admin\"></a>\n</p>\n\n### Hi there :wave:\n\n<!--\n**1020317774/1020317774** is a :sparkles: _special_ :sparkles: repository because its `README.md` (this file) appears on your GitHub profile.\n\nHere are some ideas to get you started:\n\n- :telescope: I’m currently working on ...\n- :seedling: I’m currently learning ...\n- :dancers: I’m looking to collaborate on ...\n- :thinking: I’m looking for help with ...\n- :speech_balloon: Ask me about ...\n- :mailbox: How to reach me: ...\n- :smile: Pronouns: ...\n- :zap: Fun fact: ...\n-->\n\n[![1020317774\'s github stats](https://github-readme-stats.vercel.app/api?username=1020317774&show_icons=true&count_private=true)](https://github.com/1020317774)\n\n[![Top Langs](https://github-readme-stats.vercel.app/api/top-langs/?username=1020317774&layout=compact)](https://github.com/1020317774)\n---------\n\n> 作者：王一晨\n> github：[https://github.com/1020317774](https://github.com/1020317774)\n\n## 技术栈\n\n- [x] SpringBoot 2.X\n- [x] Mysql 8.X\n- [x] Mybatis\n- [x] MybatisPlus\n- [x] Redis\n- [x] Jwt\n- [x] FastJson\n- [x] Hutool\n- [x] Lombok\n- [ ] ElasticSearch\n\n……\n\n## 安装指导\n\n- 克隆\n\n```java\ngit clone https://github.com/1020317774/rhapsody-admin.git\n```\n\n- 修改`application.properties`选择环境\n- 修改多环境配置中的redis参数和数据库\n- 启动`BootApplication`\n- 访问[`http://127.0.0.1:10000`](http://127.0.0.1:10000)\n\n','1349290158897311745',0,0,45,b'1',b'1',0,'2020-12-01 16:54:34','2020-12-01 17:05:00'),
	('1349631541260595202','权限部分 OK','1. 创建 ok\n2. 修改 ok\n3. 删除 ok\n\n','1349290158897311745',0,0,22,b'0',b'0',0,'2021-01-14 16:16:49','2021-01-14 16:18:53'),
	('1333676096156528641','测试','测试\n\n','1349290158897311745',0,0,38,b'0',b'0',0,'2020-12-01 15:35:34',NULL),
	('1332681213400817665','聚合查询并统计','* [x] SQL：\n\n```sql\nSELECT s.*,\nCOUNT(t.id) AS topics\nFROM section s\nLEFT JOIN topic t\nON s.id = t.section_id\nGROUP BY s.title\n```\n\n','1349290158897311745',0,0,55,b'0',b'0',1,'2020-11-28 21:42:16','2020-11-29 15:00:42'),
	('1335149981733449729','视频嵌入',':+1:\n\n[https://www.bilibili.com/video/BV1w64y1f7w3](https://www.bilibili.com/video/BV1w64y1f7w3)\n\n[1](https://www.bilibili.com/video/BV1tp4y1x72w)\n\n```\n.vditor-reset pre > code\n```\n\n```\npublic class HelloWorld {\n\npublic static void main(String[] args) {\n    System.out.println(\"Hello World!\");\n}\n}\n```\n\n','1349290158897311745',0,0,41,b'0',b'0',0,'2020-12-05 17:12:16','2021-01-14 13:06:16');

/*!40000 ALTER TABLE `bms_post` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table bms_post_tag
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bms_post_tag`;

CREATE TABLE `bms_post_tag` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tag_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签ID',
  `topic_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '话题ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `tag_id` (`tag_id`) USING BTREE,
  KEY `topic_id` (`topic_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='话题-标签 中间表';

LOCK TABLES `bms_post_tag` WRITE;
/*!40000 ALTER TABLE `bms_post_tag` DISABLE KEYS */;

INSERT INTO `bms_post_tag` (`id`, `tag_id`, `topic_id`)
VALUES
	(36,'1332650453377708034','1332650453142827009'),
	(37,'1332681213568589825','1332681213400817665'),
	(38,'1332681213631504385','1332681213400817665'),
	(39,'1332682473218744321','1332682473151635458'),
	(40,'1332913064463794178','1332913064396685314'),
	(41,'1332913064530903041','1332913064396685314'),
	(42,'1333432347107143681','1333432347031646209'),
	(43,'1333432347107143682','1333432347031646209'),
	(44,'1333447953697177602','1333447953558765569'),
	(45,'1332913064463794178','1333668258587750401'),
	(46,'1333676096320106498','1333676096156528641'),
	(47,'1333695976742268930','1333695976536748034'),
	(48,'1334481725519429634','1334481725322297346'),
	(49,'1333447953697177602','1335149981733449729'),
	(50,'1349362401597775874','1349362401438392322'),
	(51,'1349631541306732545','1349631541260595202'),
	(52,'1367710561667751937','1367710561588060162');

/*!40000 ALTER TABLE `bms_post_tag` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table bms_promotion
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bms_promotion`;

CREATE TABLE `bms_promotion` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '广告标题',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '广告链接',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='广告推广表';

LOCK TABLES `bms_promotion` WRITE;
/*!40000 ALTER TABLE `bms_promotion` DISABLE KEYS */;

INSERT INTO `bms_promotion` (`id`, `title`, `link`, `description`)
VALUES
	(1,'开发者头条','https://juejin.cn/','开发者头条'),
	(2,'并发编程网','https://juejin.cn/','并发编程网'),
	(3,'掘金','https://juejin.cn/','掘金');

/*!40000 ALTER TABLE `bms_promotion` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table bms_tag
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bms_tag`;

CREATE TABLE `bms_tag` (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标签',
  `topic_count` int NOT NULL DEFAULT '0' COMMENT '关联话题',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='标签表';

LOCK TABLES `bms_tag` WRITE;
/*!40000 ALTER TABLE `bms_tag` DISABLE KEYS */;

INSERT INTO `bms_tag` (`id`, `name`, `topic_count`)
VALUES
	('1332650453377708034','java',1),
	('1332681213568589825','css',1),
	('1332681213631504385','mongodb',1),
	('1332682473218744321','python',1),
	('1332913064463794178','vue',2),
	('1332913064530903041','react',1),
	('1333432347107143681','node',1),
	('1333432347107143682','mysql',1),
	('1333447953697177602','flask',2),
	('1333676096320106498','spring',1),
	('1333695976742268930','django',1),
	('1334481725519429634','security',1),
	('1349362401597775874','tensorflow',1),
	('1349631541306732545','pytorch',1),
	('1367710561667751937','新闻',1);

/*!40000 ALTER TABLE `bms_tag` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table bms_tip
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bms_tip`;

CREATE TABLE `bms_tip` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '内容',
  `author` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '作者',
  `type` tinyint NOT NULL COMMENT '1：使用，0：过期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='每日赠言';

LOCK TABLES `bms_tip` WRITE;
/*!40000 ALTER TABLE `bms_tip` DISABLE KEYS */;

INSERT INTO `bms_tip` (`id`, `content`, `author`, `type`)
VALUES
	(1,'多锉出快锯，多做长知识。','佚名',1),
	(2,'未来总留着什么给对它抱有信心的人。','佚名',1),
	(3,'一个人的智慧不够用，两个人的智慧用不完。','谚语',1),
	(4,'十个指头按不住十个跳蚤','傣族',1),
	(5,'言不信者，行不果。','墨子',1),
	(6,'攀援而登，箕踞而遨，则几数州之土壤，皆在衽席之下。','柳宗元',1),
	(7,'美德大都包含在良好的习惯之内。','帕利克',1),
	(8,'人有不及，可以情恕。','《晋书》',1),
	(9,'明·吴惟顺','法不传六耳',1),
	(10,'真正的朋友应该说真话，不管那话多么尖锐。','奥斯特洛夫斯基',1),
	(11,'时间是一切财富中最宝贵的财富。','德奥弗拉斯多',1),
	(12,'看人下菜碟','民谚',1),
	(13,'如果不是怕别人反感，女人决不会保持完整的严肃。','拉罗什福科',1),
	(14,'爱是春暖花开时对你满满的笑意','佚名',1),
	(15,'希望是坚韧的拐杖，忍耐是旅行袋，携带它们，人可以登上永恒之旅。','罗素',1),
	(18,'天国般的幸福，存在于对真爱的希望。','佚名',1),
	(19,'我们现在必须完全保持党的纪律，否则一切都会陷入污泥中。','马克思',1),
	(20,'在科学上没有平坦的大道，只有不畏劳苦沿着陡峭山路攀登的人，才有希望达到光辉的顶点。','马克思',1),
	(21,'懒惰的马嫌路远','蒙古',1),
	(22,'别忘记热水是由冷水烧成的','非洲',1);

/*!40000 ALTER TABLE `bms_tip` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table ums_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ums_user`;

CREATE TABLE `ums_user` (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `username` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '密码',
  `avatar` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机',
  `score` int unsigned NOT NULL DEFAULT '0' COMMENT '积分',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT 'token',
  `bio` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '个人简介',
  `active` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否激活，1：是，0：否',
  `status` bit(1) DEFAULT b'1' COMMENT '状态，1：使用，0：停用',
  `role_id` int DEFAULT NULL COMMENT '用户角色',
  `create_time` datetime NOT NULL COMMENT '加入时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_name` (`username`) USING BTREE,
  KEY `user_email` (`email`) USING BTREE,
  KEY `user_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

LOCK TABLES `ums_user` WRITE;
/*!40000 ALTER TABLE `ums_user` DISABLE KEYS */;

INSERT INTO `ums_user` (`id`, `username`, `alias`, `password`, `avatar`, `email`, `mobile`, `score`, `token`, `bio`, `active`, `status`, `role_id`, `create_time`, `modify_time`)
VALUES
	('1349290158897311745','admin','admin','$2a$10$8qx711TBg/2hxfL7N.sxf.0ROMhR/iuPhQx33IFqGd7PLgt5nGJTO','https://s3.ax1x.com/2020/12/01/DfHNo4.jpg','23456@qq.com',NULL,2,'','自由职业者',b'1',b'1',NULL,'2021-01-13 17:40:17',NULL),
	('1349618748226658305','zhangsan','zhangsan','$2a$10$7K3yYv8sMV5Xsc2facXTcuyDo8JQ4FJHvjZ7qtWYcJdei3Q6Fvqdm','https://s3.ax1x.com/2020/12/01/DfHNo4.jpg','23456@qq.com',NULL,0,'','自由职业者',b'1',b'1',NULL,'2021-01-14 15:25:59',NULL),
	('1366650171638743042','Bing','Bing','c8837b23ff8aaa8a2dde915473ce0991','https://s3.ax1x.com/2020/12/01/DfHNo4.jpg','aa@qq.com','12345678909',1,'','自由职业者',b'1',b'1',NULL,'2021-03-02 15:22:47',NULL);

/*!40000 ALTER TABLE `ums_user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
