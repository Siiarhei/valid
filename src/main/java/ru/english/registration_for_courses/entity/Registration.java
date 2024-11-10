package ru.english.registration_for_courses.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "registrations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Email(message = "Неверный email адрес")
    @Column(name = "email", nullable = false)
    private String email;

    @Pattern(regexp = "^\\+7\\s?(\\d{3})\\s?(\\d{3})\\s?(\\d{2})\\s?(\\d{2})", message = "Неверный формат мобильного телефона")
    @Column(name = "phone")
    private String phone;

    @Pattern(regexp = "^\\w+$", message = "Неверный формат Telegram ID")
    @Column(name = "telegram_nick")
    private String telegramNick;
}