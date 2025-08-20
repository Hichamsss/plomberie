CREATE TABLE IF NOT EXISTS `retour` (
  `id_retour` BIGINT NOT NULL AUTO_INCREMENT,
  `contenu`   VARCHAR(500) NOT NULL,
  `note`      INT NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_retour`),
  INDEX `idx_retour_note` (`note`)
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;