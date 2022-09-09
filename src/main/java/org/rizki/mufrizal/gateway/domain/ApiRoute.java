package org.rizki.mufrizal.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_api_route")
public class ApiRoute {

    @Id
    @Column("id")
    private String id;

    @Column("path")
    private String path;

    @Column("rewrite_frontend")
    private String rewriteFrontend;

    @Column("rewrite_backend")
    private String rewriteBackend;

    @Column("method")
    private String method;

    @Column("uri")
    private String uri;

    @Column("authentication")
    private String authentication;
}