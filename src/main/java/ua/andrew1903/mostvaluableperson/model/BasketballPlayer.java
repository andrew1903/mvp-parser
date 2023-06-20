package ua.andrew1903.mostvaluableperson.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    @Setter
    private boolean winner;

    @Override
    public int getScore() {
        return 2 * points + rebounds + assists + (winner ? 10 : 0);
    }
}
