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
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_application_credential")
public class ApplicationCredential implements Serializable, Persistable {
    @Id
    @Column("id")
    private String id;

    @Column("application_name")
    private String applicationName;

    @Column("api_key")
    private String apiKey;

    @Transient
    private List<ApiRouteApplicationCredential> apiRouteApplicationCredentials;

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
