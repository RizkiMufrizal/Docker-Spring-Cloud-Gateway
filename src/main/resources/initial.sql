CREATE TABLE tb_application_credential
(
    id               varchar(36) PRIMARY KEY,
    application_name varchar(100),
    api_key          varchar(36)
);

CREATE TABLE tb_api_route
(
    id                        varchar(36) PRIMARY KEY,
    path                      varchar(255),
    rewrite_frontend          varchar(255),
    rewrite_backend           varchar(255),
    method                    varchar(10),
    uri                       varchar(255),
    authentication            varchar(50),
    application_credential_id varchar(36),
    CONSTRAINT FK_Credential FOREIGN KEY (application_credential_id) REFERENCES tb_application_credential (id)
);

INSERT INTO tb_api_route(id, path, rewrite_frontend, rewrite_backend, method, uri, authentication, application_credential_id)
values ('4c4239a3-9cca-441b-829a-178c2dda530a', '/v1/**', '/v1/(?<segment>.*)', '/${segment}', null, 'https://httpbin.org', null, null);

INSERT INTO tb_application_credential(id, application_name, api_key)
values ('e4251daf-8d83-444b-b661-47a3b090a0a1', 'Sample Application', '2966e3c7-e670-4535-b56b-6619b101598a');

INSERT INTO tb_api_route(id, path, rewrite_frontend, rewrite_backend, method, uri, authentication, application_credential_id)
values ('aca137ed-baa7-49a1-a465-0719a5d684ae', '/v2/**', '/v2/(?<segment>.*)', '/${segment}', null, 'https://httpbin.org', 'x-api-key', 'e4251daf-8d83-444b-b661-47a3b090a0a1');