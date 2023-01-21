package org.example.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"score"}, callSuper = false)
public class Team {

    private String name;
    private int score;

    public Team(String name) {
        this.name = name;
        this.score = 0;
    }

}
