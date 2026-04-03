CREATE TABLE reserva (
    id VARCHAR(36) PRIMARY KEY,
    usuario_id VARCHAR(50) NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    pagamento_id VARCHAR(100),
    data_reserva DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_expiracao DATETIME NOT NULL,
    data_atualizacao DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    versao INT DEFAULT 0
);

CREATE TABLE item_reserva (
    id VARCHAR(36) PRIMARY KEY,
    reserva_id VARCHAR(36) NOT NULL,
    evento_id VARCHAR(50) NOT NULL,
    setor_id VARCHAR(50) NOT NULL,
    lote_id VARCHAR(50) NOT NULL,
    tipo_ingresso VARCHAR(30) NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_item_reserva FOREIGN KEY (reserva_id) REFERENCES reserva(id) ON DELETE CASCADE
);