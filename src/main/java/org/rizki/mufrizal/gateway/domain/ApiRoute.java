package org.rizki.mufrizal.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_api_route")
public class ApiRoute {

    @Id
    private Long id;

    private String path;
    private String method;
    private String uri;
}