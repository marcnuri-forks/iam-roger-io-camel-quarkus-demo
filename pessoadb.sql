use pessoadb;

CREATE TABLE `tb_pessoa` (
  `pessoaId` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `cpf_cnpj` varchar(18) NOT NULL,
  PRIMARY KEY (`pessoaId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
