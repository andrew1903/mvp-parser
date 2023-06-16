package com.codetest.mostvaluableperson.model;

import lombok.Builder;

import java.util.Map;

@Builder
public class BasketballPlayer implements Player<BasketballPlayer> {
    private String name;
    private String nickname;
    private Integer number;
    private String team;
    private Integer points;
    private Integer rebounds;
    private Integer assists;

    @Override
    public Integer getScore() {
        return 2 * points + rebounds + assists;
    }

    @Override
    public Map<String, Object> toMap() {
        return Map.of(
                "name", name,
                "nickname", nickname,
                "number", number,
                "team", team,
                "points", points,
                "rebounds", rebounds,
                "assists", assists
        );
    }

    @Override
    public int compareTo(BasketballPlayer o) {
        return Integer.compare(getScore(), o.getScore());
    }
}
