package guru.springfamework.api.v1.model;

import lombok.*;

/**
 * Created by jt on 9/24/17.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
}
