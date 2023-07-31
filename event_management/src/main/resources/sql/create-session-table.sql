--liquibase formatted sql

--changeset anish:2

CREATE TABLE IF NOT EXISTS `session` (
  `id` varchar(255) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `token_expiry` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci