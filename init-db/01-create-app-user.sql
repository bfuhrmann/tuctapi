-- Criar usuário não root para a API
CREATE USER 'tuctapi_user'@'%' IDENTIFIED BY '6@#per4q7YGS';

-- Criar banco de dados (caso não exista)
CREATE DATABASE IF NOT EXISTS ${DB_NAME};

-- Conceder privilégios
GRANT ALL PRIVILEGES ON ${DB_NAME}.* TO 'tuctapi_user'@'%';
FLUSH PRIVILEGES;