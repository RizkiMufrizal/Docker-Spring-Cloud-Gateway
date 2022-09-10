package org.rizki.mufrizal.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_application_credential")
public class ApplicationCredential implements Serializable {
    @Id
    @Column("id")
    private String id;

    @Column("application_name")
    private String applicationName;

    @Column("api_key")
    private String apiKey;

    @Transient
    private Set<ApiRoute> apiRoutes;
}
