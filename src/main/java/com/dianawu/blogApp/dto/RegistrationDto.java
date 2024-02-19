package com.dianawu.blogApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RegistrationDto {
    private Long id;
    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Email is required")
    @Email
    private String email;

    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}", message = "Password must be between 8 and 20 characters long, and include at least: \n\t* One digit \n\t* One lowercase letter\n\t* One uppercase letter\n\t* One special character from @#$%^&+=")
    private String password;
}
