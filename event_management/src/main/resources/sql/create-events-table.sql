--liquibase formatted sql

--changeset anish:4

CREATE TABLE IF NOT EXISTS `events` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `seats_left` int DEFAULT NULL,
  `time` time(6) DEFAULT NULL,
  `total_seats` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci