package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    private long id;

    @NotEmpty(message = "Name is Required")
    @Size(min = 2, message = "The person's name should be at least two characters long")
    private String name;

    @Min(5)
    @Max(99)
    private int age;

}
