package org.socialization.friends.makings.backend.services;

import org.socialization.friends.makings.backend.friend.Friend;
import org.socialization.friends.makings.backend.friend.exceptions.LongDescriptionException;
import org.socialization.friends.makings.backend.friend.exceptions.NoSuchFriendIdException;
import org.socialization.friends.makings.backend.friend.exceptions.NoSuchGenderException;
import org.socialization.friends.makings.backend.friend.exceptions.NoSuchStatusException;
import org.socialization.friends.makings.backend.friend.friendBuilder.FriendBuilder;
import org.socialization.friends.makings.backend.friend.friendBuilder.FriendBuilderImpl;
import org.socialization.friends.makings.backend.friend.services.FriendService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Profile("stub")
public class StubFriendServiceImpl implements FriendService {
     Map<Integer, Friend> friendById = new HashMap<>();

    List<String> statuses = List.of(
            "barely-known"
            ,"friend"
            ,"best-friend"
            ,"partner"
    );
    List<String> genders = List.of(
            "male"
            ,"female"
            ,"neutral-gender"
            ,"bigender"
    );

    private final int MAX_DESC_LENGTH = 50;

    private final Friend friend;

    public StubFriendServiceImpl() {
        FriendBuilder builder = new FriendBuilderImpl(
                "Kate"
                ,"Wattson"
                , "female"
                ,"barely-known"
                , new Date());
        this.friend = builder
                .setDateMet(new Date())
                .setDescription("Funny girl but rude.")
                .build();
    }

    @Override
    public Integer addFriend(Friend friend) {
        friendById.put(0,this.friend);
        if(!statuses.contains(friend.getStatus())){
            throw new NoSuchStatusException("");
        }
        if(!genders.contains(friend.getGender())){
            throw new NoSuchGenderException("");
        }
        if(friend.getDescription().isPresent() && friend.getDescription().get().length() > 50){
            throw new LongDescriptionException("");
        }
        return 0;
    }

    @Override
    public void updateFriendStatus(Integer friendId, String newStatus) {
        if(!statuses.contains(newStatus)){
            throw new NoSuchStatusException("");
        }
        if(friendId > 0){
            throw new NoSuchFriendIdException("");
        }
    }

    @Override
    public void deleteFriend(int friendNumber) {
        if(friendNumber > 0){
            throw new NoSuchFriendIdException("");
        }
    }

    @Override
    public List<Friend> getAllFriends() {
        return List.of(friend);
    }
}
