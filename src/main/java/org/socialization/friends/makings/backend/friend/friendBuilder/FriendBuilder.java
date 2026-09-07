package org.socialization.friends.makings.backend.friend.friendBuilder;

import org.socialization.friends.makings.backend.friend.Friend;

import java.util.Date;

public interface FriendBuilder {
    FriendBuilder setDateMet(Date dateMet);
    FriendBuilder setDescription(String description);
    Friend build();
}
