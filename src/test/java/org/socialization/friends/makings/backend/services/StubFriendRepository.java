package org.socialization.friends.makings.backend.services;

import org.socialization.friends.config.TestInfrastructureConfig;
import org.socialization.friends.makings.backend.friend.Friend;
import org.socialization.friends.makings.backend.friend.exceptions.LongDescriptionException;
import org.socialization.friends.makings.backend.friend.exceptions.NoSuchFriendIdException;
import org.socialization.friends.makings.backend.friend.exceptions.NoSuchGenderException;
import org.socialization.friends.makings.backend.friend.exceptions.NoSuchStatusException;
import org.socialization.friends.makings.backend.friend.repositories.FriendRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.*;

@Repository
@Profile("stub")
public class StubFriendRepository implements FriendRepository {
    private final Map<Integer, Friend> friendById = new HashMap<>() ;
    private int maxFriendId = 0;

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

    private final int maxDescriptionLength = 50;
    @Override
    public int addFriend(Friend friend) {
        String status = friend.getStatus();
        String gender = friend.getGender();
        if(!statuses.contains(status)){
            throw new NoSuchStatusException("The friend with status " + status + " not found");
        }
        if(!genders.contains(gender)){
            throw new NoSuchGenderException("The friend with gender " + gender + " not found");
        }

        Optional<String> friendDesc = friend.getDescription();
        if(friendDesc.isPresent() && friendDesc.get().length() > maxDescriptionLength){
            throw new LongDescriptionException("Max description length is " + maxDescriptionLength);
        }
        friendById.put(maxFriendId,friend);
        return maxFriendId++;
    }

    @Override
    public void updateFriendStatus(Integer friendId, String newStatus) {
        if(!statuses.contains(newStatus)){
            throw new NoSuchStatusException("The friend with status " + newStatus + " not found");
        }
        Friend friend = friendById.get(friendId);
        friend.setStatus(newStatus);
    }

    @Override
    public void deleteFriend(int friendId) {
        if(!friendById.containsKey(friendId)){
            throw new NoSuchFriendIdException("The friend with id " + friendId + " not found");
        }
        friendById.remove(friendId);
    }

    @Override
    public List<Friend> getAllFriends() {
        return friendById.values().stream().toList();
    }


    public List<String> getStatuses(){
        return statuses;
    }

    public List<String> getGenders(){
        return genders;
    }
}

