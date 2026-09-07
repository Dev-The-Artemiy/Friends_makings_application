package org.socialization.friends.makings.backend.friend.services;

import org.socialization.friends.makings.backend.friend.Friend;
import org.socialization.friends.makings.backend.friend.exceptions.LongDescriptionException;
import org.socialization.friends.makings.backend.friend.exceptions.NoSuchFriendIdException;
import org.socialization.friends.makings.backend.friend.exceptions.NoSuchGenderException;
import org.socialization.friends.makings.backend.friend.exceptions.NoSuchStatusException;
import org.socialization.friends.makings.backend.friend.repositories.FriendRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile({"production","test"})
public class StatedFriendService implements FriendService{

    FriendRepository friendRepository;
    private BackendErrorState errorState = BackendErrorState.OK;

    public StatedFriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Override
    public Integer addFriend(Friend friend) {
        errorState = BackendErrorState.OK;
        Integer newFriendId = null;
        try{
        newFriendId = friendRepository.addFriend(friend);
        }
        catch(LongDescriptionException e){
            errorState = BackendErrorState.LongDescription;
        }
        catch(NoSuchStatusException e){
            errorState = BackendErrorState.BadStatus;
        }
        catch(NoSuchGenderException e){
            errorState = BackendErrorState.BadGender;
        }
        return newFriendId;
    }

    @Override
    public void updateFriendStatus(Integer friendId, String newStatus) {
        errorState = BackendErrorState.OK;
        try{
        friendRepository.updateFriendStatus(friendId, newStatus);
        }
        catch(NoSuchStatusException e){
            errorState = BackendErrorState.BadStatus;
        }
    }

    @Override
    public void deleteFriend(int friendNumber) {
        errorState = BackendErrorState.OK;
        try{
        friendRepository.deleteFriend(friendNumber);
        }catch(NoSuchFriendIdException e){
            errorState = BackendErrorState.BadFriendId;
        }
    }

    @Override
    public List<Friend> showAllFriends() {
        errorState = BackendErrorState.OK;
        return friendRepository.getAllFriends();
    }


    public BackendErrorState getErrorState() {
        return errorState;
    }
}
