package ua.andrew1903.mostvaluableperson.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BasketballPlayer implements Player {
    private String name;
    private String nickname;
    private Integer number;
    private String team;
    private Integer points;
    private Integer rebounds;
    private Integer assists;

    @Override
    public int getScore() {
        return 2 * points + rebounds + assists;
    }
}
