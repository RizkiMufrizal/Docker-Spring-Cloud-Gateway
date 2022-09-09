CREATE TABLE tb_api_route
(
    id               varchar(36) PRIMARY KEY,
    path             varchar(255),
    rewrite_frontend varchar(255),
    rewrite_backend  varchar(255),
    method           varchar(10),
    uri              varchar(255),
    authentication   varchar(50)
);