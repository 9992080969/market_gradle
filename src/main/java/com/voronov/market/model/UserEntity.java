package com.voronov.market.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * @author Alexey Voronov on 19.07.2023
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class UserEntity {
    @Id
    private Long id;
    private String username;
    private String password;
    private UserRole role;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ToString.Include(name = "password") //поле а не колонка
    private String maskPassword(){
      return "********";
    }
}