package com.example.employee.model;

import jakarta.persistence.*;
import lombok.*;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "employee_table")
    public class Employee {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        @Column (name = "first_name")
        private String firstName;

        @Column (name = "last_name")
        private String lastName;

        @Column (name = "email")
        private String email;

    }
