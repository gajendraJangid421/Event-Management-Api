--liquibase formatted sql

--changeset anish:3

CREATE TABLE IF NOT EXISTS `event_status` (
  `id` varchar(255) NOT NULL,
  `attended` bit(1) DEFAULT NULL,
  `booked` bit(1) DEFAULT NULL,
  `bookmarked` bit(1) DEFAULT NULL,
  `event_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci