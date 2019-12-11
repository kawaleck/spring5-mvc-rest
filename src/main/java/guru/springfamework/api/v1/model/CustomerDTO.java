package guru.springfamework.api.v1.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private String firstname;
    private String lastname;
    private String customerUrl;
}
