package org.socialization.friends.makings.backend.friend.services;

import org.socialization.friends.makings.backend.friend.Friend;

import java.util.List;

public interface ServiceAdapter {
    Integer adaptAddFriend(Friend friend);
    void adaptUpdateFriendStatus(Integer friendId, String newStatus);
    void adaptDeleteFriend(Integer friendId);
    List<Friend> adaptGetAllFriends();
}
