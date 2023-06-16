package com.codetest.mostvaluableperson.model;

import lombok.Builder;

import java.util.Map;

@Builder
public class HandballPlayer implements Player<HandballPlayer> {
    private String name;
    private String nickname;
    private Integer number;
    private String team;
    private Integer goalsMade;
    private Integer goalsReceived;

    @Override
    public Integer getScore() {
        return 2 * goalsMade - goalsReceived;
    }

    @Override
    public Map<String, Object> toMap() {
        return Map.of(
                "name", name,
                "nickname", nickname,
                "number", number,
                "team", team,
                "goalsMade", goalsMade,
                "goalsReceived", goalsReceived
        );
    }

    @Override
    public int compareTo(HandballPlayer o) {
        return Integer.compare(getScore(), o.getScore());
    }
}
