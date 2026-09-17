package org.socialization.friends.makings.backend.friend.services;

import org.socialization.friends.makings.backend.friend.Friend;
import org.socialization.friends.makings.backend.friend.repositories.FriendRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("friendServiceImpl")
@Profile({"production", "test"})
public class FriendServiceImpl implements FriendService{

    FriendRepository friendRepository;

    public FriendServiceImpl(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Override
    public Integer addFriend(Friend friend) {
        return friendRepository.addFriend(friend);
    }

    @Override
    public void updateFriendStatus(Integer friendId, String newStatus) {
       friendRepository.updateFriendStatus(friendId, newStatus);
    }

    @Override
    public void deleteFriend(int friendNumber) {
        friendRepository.deleteFriend(friendNumber);
    }

    @Override
    public List<Friend> getAllFriends() {
        return friendRepository.getAllFriends();
    }



}

//While doing the application I questioned myself whether I need to check Date objects that are coming into my backend
//from frontend (main concern is that dates may be out of range e.g.:29.12.2019(not a leap year)).
//Date objects are always correct (no range restrictions in my application) because of the DateFormat and Calendar
//lenient mode (set to true -> dates are correct physically but may not match with what actually user provided in GUI,
//set to false -> frontend will crash if bad date is provided)
//I came to conclusion that backend establishes contract of data which has to be provided by the
//frontend and this contract is quite stable: date data coming into backend must be defined in Date objects which are
//always correct. So whether the data is proper is the responsibility of frontend now and backend has implemented all
//its responsibilities.
//Conclusion: I don't have to do Date data validation on backend side.
