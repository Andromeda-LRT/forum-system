package com.forumsystem.models.modeldto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import static com.forumsystem.modelhelpers.ModelConstantHelper.*;


public class RegisterDto extends LoginDto {
    @NotEmpty(message = "Password confirmation can't be empty.")
    private String passwordConfirm;
    @Size(min = 4, max = 32, message = NAME_ERROR_MESSAGE)
    @NotEmpty(message = "First name can't be empty.")
    private String firstName;
    @Size(min = 4, max = 32, message = NAME_ERROR_MESSAGE)

    @NotEmpty(message = "Last name can't be empty.")
    private String lastName;
    @Email(
            message = EMPTY_ERROR_MESSAGE,
            regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
    )
    @NotEmpty(message = "Email can't be empty.")
    private String email;

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
