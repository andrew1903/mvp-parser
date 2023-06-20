package ua.andrew1903.mostvaluableperson.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class HandballPlayer implements Player {
    private String name;
    private String nickname;
    private Integer number;
    private String team;
    private Integer goalsMade;
    private Integer goalsReceived;
    @Setter
    private boolean winner;

    @Override
    public int getScore() {
        return 2 * goalsMade - goalsReceived + (winner ? 10 : 0);
    }
}
