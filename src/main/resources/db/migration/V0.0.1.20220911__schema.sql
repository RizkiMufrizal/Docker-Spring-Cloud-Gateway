CREATE TABLE tb_application_credential
(
    id               varchar(36) PRIMARY KEY,
    application_name varchar(100),
    api_key          varchar(36)
);

CREATE TABLE tb_api_route
(
    id               varchar(36) PRIMARY KEY,
    name             varchar(255),
    version          varchar(10),
    path             varchar(255),
    rewrite_frontend varchar(255),
    rewrite_backend  varchar(255),
    method           varchar(10),
    uri              varchar(255),
    authentication   varchar(50)
);

CREATE TABLE tb_api_route_application_credential
(
    id                        varchar(36) PRIMARY KEY,
    application_credential_id varchar(36),
    api_route_id              varchar(36),
    CONSTRAINT FK_Credential FOREIGN KEY (application_credential_id) REFERENCES tb_application_credential (id),
    CONSTRAINT FK_Route FOREIGN KEY (api_route_id) REFERENCES tb_api_route (id)
);