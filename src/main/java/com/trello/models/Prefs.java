package com.trello.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prefs {
    private String permissionLevel;
    private Boolean selfJoin;
    private Boolean cardCovers;
    private Boolean hideVotes;
    private String invitations;
    private String voting;
    private String comments;
    private String background;
    private String cardAging;
    private Boolean calendarFeedEnabled;
}