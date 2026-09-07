package org.socialization.friends.makings.backend.friend.services;

import org.socialization.friends.makings.backend.friend.Friend;

import java.util.List;

public interface FriendService {
    Integer addFriend(Friend friend);
    void updateFriendStatus(Integer friendId, String newStatus);
    void deleteFriend(int friendNumber);
    List<Friend> showAllFriends();
}
