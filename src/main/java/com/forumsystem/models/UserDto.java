package com.forumsystem.models;

import jakarta.validation.constraints.*;

public class UserDto {
    public static final String NAME_ERROR_MESSAGE = "Field should be between 4 and 32 symbols.";
    public static final String EMPTY_ERROR_MESSAGE = "Field can't be empty";
    public static final String USERNAME_ERROR_MESSAGE = "Username must be of 6 to 16 length with no special characters";
    public static final String PASSWORD_ERROR_MESSAGE = "Password must be min 4 and max 12 length, " +
            "containing atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit ";

    @NotNull(message = EMPTY_ERROR_MESSAGE)
    @Size(min = 4, max = 32, message = NAME_ERROR_MESSAGE)
    String firstName;

    @NotNull(message = EMPTY_ERROR_MESSAGE)
    @Size(min = 4, max = 32, message = NAME_ERROR_MESSAGE)
    String lastName;

    @Email(
            message = EMPTY_ERROR_MESSAGE,
            regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
    )
    @NotEmpty(message = "Email cannot be empty")
    String email;
    @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$",
            message = USERNAME_ERROR_MESSAGE)
    String username;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,20}$",
            message = PASSWORD_ERROR_MESSAGE)
    String password;

    public UserDto() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
