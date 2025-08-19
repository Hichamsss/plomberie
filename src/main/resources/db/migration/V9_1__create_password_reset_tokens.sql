CREATE TABLE IF NOT EXISTS password_reset_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(128) NOT NULL,
    expires_at DATETIME NOT NULL,
    used BIT(1) NOT NULL DEFAULT b'0',
    compte_id BIGINT NOT NULL,

    CONSTRAINT fk_prt_compte
        FOREIGN KEY (compte_id) REFERENCES comptes(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE UNIQUE INDEX ux_prt_token ON password_reset_tokens(token);
CREATE INDEX ix_prt_compte ON password_reset_tokens(compte_id);
CREATE INDEX ix_prt_expires ON password_reset_tokens(expires_at);
