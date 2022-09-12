package org.rizki.mufrizal.gateway.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_api_route_application_credential")
public class ApiRouteApplicationCredential implements Serializable, Persistable {

    @Id
    @Column("id")
    private String id;

    @Column("application_credential_id")
    private String applicationCredentialId;

    @Column("api_route_id")
    private String apiRouteId;

    @Transient
    private ApiRoute apiRoute;

    @Transient
    private ApplicationCredential applicationCredential;

    @JsonIgnore
    @Transient
    private Boolean isNewRecord = false;

    @JsonIgnore
    @Transient
    @Override
    public boolean isNew() {
        return this.getIsNewRecord();
    }
}
