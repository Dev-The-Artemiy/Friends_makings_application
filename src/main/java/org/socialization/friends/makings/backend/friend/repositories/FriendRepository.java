package org.socialization.friends.makings.backend.friend.repositories;

import org.socialization.friends.makings.backend.friend.Friend;

import java.util.List;

public interface FriendRepository {
    int addFriend(Friend friend);
    void updateFriendStatus(Integer friendId, String newStatus);
    void deleteFriend(int friendId);
    List<Friend> getAllFriends();
}
