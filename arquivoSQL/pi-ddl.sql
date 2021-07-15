CREATE TABLE usuario (
	codigo serial NOT NULL PRIMARY KEY,
	nome character varying(20) NOT NULL,
	sobrenome character varying(20) NOT NULL,
	apelido character varying(16) NOT NULL,
	idade integer NOT NULL,
	serie character varying(20) NOT NULL,
	pontuacao integer NOT NULL
);

CREATE TABLE personagem (
	codigo serial NOT NULL PRIMARY KEY,
	tipo character varying(20) NOT NULL
);

CREATE TABLE modulo (
	codigo serial NOT NULL PRIMARY KEY,
	nome character varying(20) NOT NULL,
	conteudo character varying(30) NOT NULL,
	tematica character varying(35) NOT NULL
);

CREATE TABLE usuario_modulo (
	codigo_modulo integer REFERENCES modulo (codigo) NOT NULL,
	id_usuario integer REFERENCES usuario (codigo) NOT NULL,
	PRIMARY KEY (codigo_modulo, id_usuario)
);

CREATE TABLE questao (
	codigo serial NOT NULL PRIMARY KEY,
	pergunta character varying(100) NOT NULL,
	tipo_questao character varying(20) NOT NULL,
	recompensa integer NOT NULL
);

CREATE TABLE usuario_questao (
	id_usuario integer REFERENCES usuario (codigo) NOT NULL,
	id_questao integer REFERENCES questao (codigo) NOT NULL,
	bloqueada boolean NOT NULL,
	resposta_usuario integer NOT NULL,
	PRIMARY KEY (id_usuario, id_questao)
);

CREATE TABLE discursiva (
	codigo serial NOT NULL PRIMARY KEY,
	codigo_questao integer REFERENCES questao(codigo) NOT NULL,
	resposta integer NOT NULL
);

CREATE TABLE alternativa (
	codigo serial NOT NULL PRIMARY KEY,
	codigo_questao integer REFERENCES questao (codigo) NOT NULL,
	certo_errado boolean NOT NULL,
	valor_alternativo character varying(20) NOT NULL
);
