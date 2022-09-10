package org.rizki.mufrizal.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_api_route")
public class ApiRoute implements Serializable, Persistable {

    @Id
    @Column("id")
    private String id;

    @Column("name")
    private String name;

    @Column("version")
    private String version;

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

    @Transient
    private List<ApiRouteApplicationCredential> apiRouteApplicationCredentials;

    @Transient
    private Boolean isNewRecord = false;

    @Transient
    @Override
    public boolean isNew() {
        return this.getIsNewRecord();
    }
}