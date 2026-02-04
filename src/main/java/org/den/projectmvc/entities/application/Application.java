package org.den.projectmvc.entities.application;

import jakarta.persistence.*;
import lombok.Data;
import org.den.projectmvc.entities.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "application")
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToOne
//    @JoinColumn(name="quarter_id")
//    Quarter quarter;

    private String title;

    private String abbreviation;

    @Column(name = "abstract")
    private String abstractText;


    private String justification;

    @Column(name = "expected_results", columnDefinition = "TEXT")
    private String expectedResults;

    @Column(name = "military_use", columnDefinition = "TEXT")
     private String militaryUse;

    @Column(name = "created_at")
    private LocalDateTime createdAt;



}
