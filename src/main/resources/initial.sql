CREATE TABLE tb_application_credential
(
    id               varchar(36) PRIMARY KEY,
    application_name varchar(100),
    api_key          varchar(36)
);

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

CREATE TABLE tb_api_route_application_credential
(
    id                        varchar(36) PRIMARY KEY,
    application_credential_id varchar(36),
    api_route_id              varchar(36),
    CONSTRAINT FK_Credential FOREIGN KEY (application_credential_id) REFERENCES tb_application_credential (id),
    CONSTRAINT FK_Route FOREIGN KEY (api_route_id) REFERENCES tb_api_route (id)
);

INSERT INTO tb_api_route(id, path, rewrite_frontend, rewrite_backend, method, uri, authentication)
values ('4c4239a3-9cca-441b-829a-178c2dda530a', '/v1/**', '/v1/(?<segment>.*)', '/${segment}', null,
        'https://httpbin.org', null);

INSERT INTO tb_application_credential(id, application_name, api_key)
values ('e4251daf-8d83-444b-b661-47a3b090a0a1', 'Sample Application', '2966e3c7-e670-4535-b56b-6619b101598a');

INSERT INTO tb_api_route(id, path, rewrite_frontend, rewrite_backend, method, uri, authentication)
values ('aca137ed-baa7-49a1-a465-0719a5d684ae', '/v2/**', '/v2/(?<segment>.*)', '/${segment}', null,
        'https://httpbin.org', 'x-api-key');

insert into tb_api_route_application_credential(id, application_credential_id, api_route_id)
values ('96fcf01c-75a7-44c7-b41d-0a00781ffb3f', 'e4251daf-8d83-444b-b661-47a3b090a0a1',
        'aca137ed-baa7-49a1-a465-0719a5d684ae');