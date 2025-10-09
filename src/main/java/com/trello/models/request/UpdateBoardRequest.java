package com.trello.models.request;

import com.trello.models.Prefs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBoardRequest {

    // --- Path parameter ---
    private String id;

    // --- Query parameters ---
    private String name;
    private String desc;
    private Boolean closed;
    private String subscribed;
    private String idOrganization;
    private Prefs prefs;
}