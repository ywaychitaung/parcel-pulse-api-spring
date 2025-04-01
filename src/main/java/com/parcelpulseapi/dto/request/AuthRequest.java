package com.parcelpulseapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Contains authentication request classes
 */
public class AuthRequest {

    /**
     * Login request class
     */
    public static class LoginRequest {
        @NotBlank
        private String usernameOrEmail;

        @NotBlank
        private String password;

        public String getUsernameOrEmail() {
            return usernameOrEmail;
        }

        public void setUsernameOrEmail(String usernameOrEmail) {
            this.usernameOrEmail = usernameOrEmail;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    /**
     * Register request class
     */
    public static class RegisterRequest {
        @NotBlank
        @Size(min = 3, max = 40)
        private String name;

        @NotBlank
        @Size(min = 3, max = 15)
        private String username;

        @NotBlank
        @Size(max = 40)
        @Email
        private String email;

        @NotBlank
        @Size(min = 6, max = 20)
        private String password;

        private Long roleId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Long getRoleId() {
            return roleId;
        }

        public void setRoleId(Long roleId) {
            this.roleId = roleId;
        }
    }
}