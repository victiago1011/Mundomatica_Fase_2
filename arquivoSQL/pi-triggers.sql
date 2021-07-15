-- inicio triggers e suas respectivas functions
-- trigger para validar a entrada de apelido(nickname) na tabela usuario
-- se o apelido já estiver em uso não deixa inserir
CREATE OR REPLACE FUNCTION trg_valida_apelido()
RETURNS trigger AS $$ 
DECLARE
	r record;
BEGIN
	FOR r IN SELECT apelido FROM usuario loop
		IF r.apelido LIKE New.apelido THEN
			RAISE EXCEPTION 'O apelido já está em uso por outro usuário';
		END IF;
	end loop;
	RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER tr_valida_apelido
BEFORE INSERT ON usuario
FOR EACH ROW
EXECUTE PROCEDURE trg_valida_apelido();

-- trigger que modifica a pontuacao(tabela usuario) quando inserimos usuario_questao 
-- se a resposta do usuario esiver certa aumenta a pontuacao se não diminui a metade da pontuacao da questao
CREATE OR REPLACE FUNCTION trg_modifica_pontuacao()
RETURNS TRIGGER AS $$
DECLARE
	pontuacao_questao integer;
	pontos integer;
	r record;
BEGIN
	SELECT pontuacao INTO pontos FROM usuario WHERE codigo = NEW.id_usuario;
	
	FOR r IN SELECT uq.id_usuario, u.nome, uq.id_questao, q.pergunta, uq.resposta_usuario as cod_alternativa_usuario, a.certo_errado, a.valor_alternativo 
			FROM usuario_questao as uq INNER JOIN alternativa as a ON uq.resposta_usuario = a.codigo 
				INNER JOIN usuario as u ON u.codigo = uq.id_usuario 
					INNER JOIN questao as q ON q.codigo = uq.id_questao LOOP
	END LOOP;
	
	SELECT recompensa INTO pontuacao_questao FROM questao WHERE codigo = NEW.id_questao;		
	
	IF r.certo_errado = true THEN
		pontos := pontos + pontuacao_questao;
	ELSE 
		pontos := pontos - (pontuacao_questao/2);
	END IF;
	
	UPDATE usuario SET pontuacao = pontos WHERE codigo = NEW.id_usuario;
	RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER tr_modifica_pontuacao
AFTER INSERT ON usuario_questao
FOR EACH ROW
EXECUTE PROCEDURE trg_modifica_pontuacao();
-- fim das triggers

-- criando tipo e a function que retorna quais usuários acertaram ou erraram as perguntas de acordo com o parametro que é passado
CREATE TYPE type_resposta AS (id_usuario INT, nome varchar, id_questao INT, pergunta varchar, cod_alternativa_usuario INT, certo_errado boolean, valor_alternativo varchar);
CREATE OR REPLACE FUNCTION resposta_usuario(boolean)
RETURNS SETOF type_resposta AS $$
DECLARE
	linha type_resposta%ROWTYPE;
	acertos integer;
	r record;
BEGIN 
	FOR r IN SELECT uq.id_usuario, u.nome, uq.id_questao, q.pergunta, uq.resposta_usuario as cod_alternativa_usuario, a.certo_errado, a.valor_alternativo 
			FROM usuario_questao as uq INNER JOIN alternativa as a ON uq.resposta_usuario = a.codigo 
				INNER JOIN usuario as u ON u.codigo = uq.id_usuario 
					INNER JOIN questao as q ON q.codigo = uq.id_questao LOOP
				IF r.certo_errado = $1 THEN
					linha.id_usuario := r.id_usuario;
					linha.nome := r.nome;
					linha.id_questao := r.id_questao;
					linha.pergunta := r.pergunta;
					linha.cod_alternativa_usuario := r.cod_alternativa_usuario;
					linha.certo_errado := r.certo_errado;
					linha.valor_alternativo := r.valor_alternativo;
					RETURN NEXT linha;
				END IF;
			END LOOP;
END; 
$$ LANGUAGE 'plpgsql';
SELECT * FROM resposta_usuario(true); -- retorna os acertos
SELECT * FROM resposta_usuario(false); -- retorna os erros
-- fim da function

-- criando as views
CREATE OR REPLACE VIEW vw_usuario AS SELECT nome, apelido, idade FROM usuario;
SELECT * FROM vw_usuario;
SELECT * FROM vw_usuario WHERE idade > 20; 

CREATE OR REPLACE VIEW vw_questao AS SELECT pergunta, tipo_questao FROM questao;
SELECT * FROM vw_questao;
SELECT * FROM vw_questao WHERE tipo_questao = 'Discursiva';
SELECT * FROM vw_questao WHERE tipo_questao = 'Múltipla';
-- fim das views
