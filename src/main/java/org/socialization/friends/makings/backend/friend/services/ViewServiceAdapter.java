package org.socialization.friends.makings.backend.friend.services;

import com.sun.jdi.VoidType;
import org.socialization.friends.makings.backend.friend.Friend;
import org.socialization.friends.makings.backend.friend.exceptions.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("viewServiceAdapter")
@Profile({"production","test"})
public class ViewServiceAdapter implements ServiceAdapter {

    private final FriendService service;
    private BackendErrorState errorState = BackendErrorState.OK;


    public ViewServiceAdapter(FriendService service) {
        this.service = service;
    }

    @Override
    public Integer adaptAddFriend(Friend friend) {
        errorState = BackendErrorState.OK;
        Integer newFriendId = null;
        try{
            newFriendId = service.addFriend(friend);
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
        catch(NullBirthDateException e){
            errorState = BackendErrorState.NullBirthDate;
        }
        return newFriendId;
    }

    @Override
    public void adaptUpdateFriendStatus(Integer friendId, String newStatus) {
        errorState = BackendErrorState.OK;
        try{
            service.updateFriendStatus(friendId, newStatus);
        }
        catch(NoSuchStatusException e){
            errorState = BackendErrorState.BadStatus;
        }
        catch(NoSuchFriendIdException e){
            errorState = BackendErrorState.BadFriendId;
        }
    }

    @Override
    public void adaptDeleteFriend(Integer friendNumber) {
        errorState = BackendErrorState.OK;
        try{
            service.deleteFriend(friendNumber);
        }catch(NoSuchFriendIdException e){
            errorState = BackendErrorState.BadFriendId;
        }
    }

    @Override
    public List<Friend> adaptGetAllFriends() {
        errorState = BackendErrorState.OK;
        return service.getAllFriends();
    }

    public BackendErrorState getErrorState() {
        return errorState;
    }
}
