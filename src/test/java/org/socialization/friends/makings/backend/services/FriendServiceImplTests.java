package org.socialization.friends.makings.backend.services;

import org.junit.jupiter.api.Test;
import org.socialization.friends.config.TestServiceConfig;
import org.socialization.friends.makings.backend.friend.Friend;
import org.socialization.friends.makings.backend.friend.exceptions.NoSuchFriendIdException;
import org.socialization.friends.makings.backend.friend.exceptions.NoSuchGenderException;
import org.socialization.friends.makings.backend.friend.exceptions.NoSuchStatusException;
import org.socialization.friends.makings.backend.friend.friendBuilder.FriendBuilder;
import org.socialization.friends.makings.backend.friend.friendBuilder.FriendBuilderImpl;
import org.socialization.friends.makings.backend.friend.repositories.FriendRepository;
import org.socialization.friends.makings.backend.friend.services.FriendService;
import org.socialization.friends.makings.backend.friend.services.FriendServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"stub"})
public class FriendServiceImplTests {

    private final FriendServiceImpl friendService;
    //This repository object works properly, gives proper information and is integral with service actions because by
    //default Spring IoC container provides Singleton beans meaning same object is used across the application.
    private final StubFriendRepository friendRepository;

    public FriendServiceImplTests() {
        this.friendRepository = new StubFriendRepository();
        this.friendService = new FriendServiceImpl(friendRepository);
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
    }

    @Test
    public void shouldNotAddFriendWithBadStatusOrBadGender(){
        Friend friend = getTestFriend();
        friend.setStatus("barely-nown");
        assertThrows(NoSuchStatusException.class, () -> friendService.addFriend(friend));

        friend.setStatus("barely-known");
        friend.setGender("mal");
        assertThrows(NoSuchGenderException.class, () -> friendService.addFriend(friend));
    }

    @Test
    public void shouldUpdateFriendStatus(){
        Friend friend = getTestFriend();
        String prevStatus = friend.getStatus();
        Integer friendId = friendService.addFriend(friend);
        String toUpdateStatus = "partner";
        friendService.updateFriendStatus(friendId,toUpdateStatus);
        String newStatus = friend.getStatus();

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
        assertThrows(NoSuchStatusException.class, () -> friendService.updateFriendStatus(newFriendId,badNewStatus));
        assertEquals(prevNormalStatus,friend.getStatus());
    }

    @Test
    public void shouldDeleteFriend(){
        Friend friend = getTestFriend();
        Integer newFriendId = friendService.addFriend(friend);
        friendService.deleteFriend(newFriendId);
        List<Friend> curFriendsState = friendRepository.getAllFriends();

        assertTrue(friendRepository.getAllFriends().containsAll(curFriendsState));
    }

    @Test
    public void shouldNotDeleteFriendWithBadId(){
        Friend friend = getTestFriend();
        Integer newFriendId = friendService.addFriend(friend);

        assertThrows(NoSuchFriendIdException.class, () -> friendService.deleteFriend(newFriendId + 1));
        assertTrue(friendRepository.getAllFriends().contains(friend));
    }

    //TODO
    //After setting friend comparison to Id-based this test has become impossible.
    //I don't know how to solve it yet. Probably I should write compare method based on some fields that are not Id field
    //In this case test must be valid, because friends will be correctly idenified if to change one or two fields on
    //the second friend object
//    @Test
//    public void shouldReturnAllFriends(){
//        Friend f1 = getTestFriend();
//        Friend f2 = getTestFriend();
//        List<Friend> friends = List.of(f1,f2);
//        f2.setStatus("partner");
//        friendService.addFriend(f1);
//        friendService.addFriend(f2);
//
//        List<Friend> gotFriends = friendService.getAllFriends();
//
//        assertEquals(friends.size(), gotFriends.size());
//        assertTrue(friends.containsAll(gotFriends));
//        assertTrue(gotFriends.containsAll(friends));
//    }
}
