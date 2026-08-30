-- Criar usuário não root para a API
CREATE USER 'tuctapi_user'@'%' IDENTIFIED BY '6@#per4q7YGS';

-- Conceder privilégios
GRANT ALL PRIVILEGES ON tuctapi.* TO 'tuctapi_user'@'%';
FLUSH PRIVILEGES;