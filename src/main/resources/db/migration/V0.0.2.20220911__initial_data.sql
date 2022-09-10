INSERT INTO tb_api_route(id, name, version, path, rewrite_frontend, rewrite_backend, method, uri, authentication)
values ('4c4239a3-9cca-441b-829a-178c2dda530a', 'http bin', '1.0.0', '/v1/**', '/v1/(?<segment>.*)', '/${segment}',
        'GET',
        'https://httpbin.org', 'passthrough');

INSERT INTO tb_application_credential(id, application_name, api_key)
values ('e4251daf-8d83-444b-b661-47a3b090a0a1', 'Sample Application', '2966e3c7-e670-4535-b56b-6619b101598a');

INSERT INTO tb_api_route(id, name, version, path, rewrite_frontend, rewrite_backend, method, uri, authentication)
values ('aca137ed-baa7-49a1-a465-0719a5d684ae', 'http bin', '1.0.0', '/v2/**', '/v2/(?<segment>.*)', '/${segment}',
        'POST',
        'https://httpbin.org', 'x-api-key');

insert into tb_api_route_application_credential(id, application_credential_id, api_route_id)
values ('96fcf01c-75a7-44c7-b41d-0a00781ffb3f', 'e4251daf-8d83-444b-b661-47a3b090a0a1',
        'aca137ed-baa7-49a1-a465-0719a5d684ae');