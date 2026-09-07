package org.socialization.friends.makings.backend.services;

import org.junit.jupiter.api.Test;
import org.socialization.friends.config.TestInfrastructureConfig;
import org.socialization.friends.makings.backend.friend.Friend;
import org.socialization.friends.makings.backend.friend.exceptions.NoSuchStatusException;
import org.socialization.friends.makings.backend.friend.friendBuilder.FriendBuilder;
import org.socialization.friends.makings.backend.friend.friendBuilder.FriendBuilderImpl;
import org.socialization.friends.makings.backend.friend.repositories.FriendRepository;
import org.socialization.friends.makings.backend.friend.services.BackendErrorState;
import org.socialization.friends.makings.backend.friend.services.FriendService;
import org.socialization.friends.makings.backend.friend.services.StatedFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestInfrastructureConfig.class)
@ActiveProfiles({"stub","test"})
public class StatedFriendServiceTests {

    private StatedFriendService friendService;
    //This repository object works properly, gives proper information and is integral with service actions because by
    //default Spring IoC container provides Singleton beans meaning same object is used across the application.
    private StubFriendRepository friendRepository;

    @Autowired
    public StatedFriendServiceTests(FriendService friendService, FriendRepository friendRepository) {
        this.friendService = (StatedFriendService) friendService;
        this.friendRepository = (StubFriendRepository)friendRepository;
    }

    private Friend getTestFriend(){
        FriendBuilder builder = new FriendBuilderImpl(
                "Kate"
                ,"Wattson"
                , "female"
                ,"barely-known"
                , new Date());
        return builder
                .setDateMet(new Date())
                .setDescription("Funny girl but rude.")
                .build();
    }

    @Test
    public void shouldAddNewFriend(){
        Friend friend = getTestFriend();
        friendService.addFriend(friend);
        assertEquals(BackendErrorState.OK, friendService.getErrorState());
    }

    @Test
    public void shouldNotAddFriendWithBadStatusOrBadGender(){
        Friend friend = getTestFriend();
        friend.setStatus("barely-nown");
        friendService.addFriend(friend);
        assertEquals(BackendErrorState.BadStatus,friendService.getErrorState());

        friend.setStatus("barely-known");
        friend.setGender("mal");
        friendService.addFriend(friend);
        assertEquals(BackendErrorState.BadGender,friendService.getErrorState());
    }

    @Test
    public void shouldUpdateFriendStatus(){
        Friend friend = getTestFriend();
        String prevStatus = friend.getStatus();
        Integer friendId = friendService.addFriend(friend);
        String toUpdateStatus = "partner";
        friendService.updateFriendStatus(friendId,toUpdateStatus);
        String newStatus = friend.getStatus();

        assertEquals(BackendErrorState.OK, friendService.getErrorState());
        if(!prevStatus.equals(newStatus)){
            assertTrue(friendRepository.getStatuses().contains(newStatus));
        }
    }

    @Test
    public void shouldNotUpdateFriendStatusGivenBadNewStatus(){
        Friend friend = getTestFriend();
        Integer newFriendId = friendRepository.addFriend(friend);
        String prevNormalStatus = friend.getStatus();
        String badNewStatus = "partnr";
        friendService.updateFriendStatus(newFriendId,badNewStatus);
        assertEquals(BackendErrorState.BadStatus, friendService.getErrorState());
        assertEquals(prevNormalStatus,friend.getStatus());
    }

    @Test
    public void shouldDeleteFriend(){
        List<Friend> prevFriendsState = friendRepository.getAllFriends();
        Friend friend = getTestFriend();
        Integer newFriendId = friendService.addFriend(friend);
        friendService.deleteFriend(newFriendId);
        List<Friend> curFriendsState = friendRepository.getAllFriends();

        assertEquals(BackendErrorState.OK,friendService.getErrorState());
        assertEquals(prevFriendsState.size(), curFriendsState.size());
        assertTrue(friendRepository.getAllFriends().containsAll(curFriendsState));
        //Below assertion is not needed I think because the sets are of same size
        assertTrue(curFriendsState.containsAll(prevFriendsState));
    }

    @Test
    public void shouldNotDeleteFriendWithBadId(){
        Friend friend = getTestFriend();
        Integer newFriendId = friendService.addFriend(friend);

        friendService.deleteFriend(newFriendId + 1);

        assertEquals(BackendErrorState.BadFriendId,friendService.getErrorState());
        assertTrue(friendRepository.getAllFriends().contains(friend));
    }

    @Test
    public void shouldReturnAllFriends(){
        Friend f1 = getTestFriend();
        Friend f2 = getTestFriend();
        List<Friend> friends = List.of(f1,f2);
        f2.setStatus("partner");
        friendService.addFriend(f1);
        friendService.addFriend(f2);

        List<Friend> gotFriends = friendService.showAllFriends();

        assertEquals(BackendErrorState.OK,friendService.getErrorState());
        assertEquals(friends.size(), gotFriends.size());
        assertTrue(friends.containsAll(gotFriends));
        assertTrue(gotFriends.containsAll(friends));
    }
}
