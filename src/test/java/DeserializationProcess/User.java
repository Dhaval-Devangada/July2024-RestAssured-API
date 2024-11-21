package DeserializationProcess;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Integer id;
    private String name;
    private String email;
    private String gender;
    private String status;

}

/**
 *
 *
 * GET: no body
 * -- serialization is not applicable
 * -- Deserialization:yes
 *
 * POST: body
 * -- serialization is applicable
 * -- Deserialization:yes
 *
 * PUT: body
 * -- serialization is applicable
 *-- Deserialization:yes
 *
 * PATCH: body
 * -- serialization is applicable
 *-- Deserialization:yes
 *
 * DELETE: no body
 * -- serialization is not applicable
 * -- Deserialization:no
 */
