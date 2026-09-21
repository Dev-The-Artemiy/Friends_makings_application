package org.socialization.friends.makings.backend.services;

import org.junit.jupiter.api.Test;
import org.socialization.friends.config.TestServiceConfig;
import org.socialization.friends.makings.backend.friend.Friend;
import org.socialization.friends.makings.backend.friend.friendBuilder.FriendBuilder;
import org.socialization.friends.makings.backend.friend.friendBuilder.FriendBuilderImpl;
import org.socialization.friends.makings.backend.friend.services.BackendErrorState;
import org.socialization.friends.makings.backend.friend.services.FriendService;
import org.socialization.friends.makings.backend.friend.services.ViewServiceAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class ViewServiceAdapterTests {
    private final ViewServiceAdapter adapter;

    public ViewServiceAdapterTests() {
        this.adapter = new ViewServiceAdapter(new StubFriendServiceImpl());
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
    public void shouldGetProperStatusesBadlyAddingFriend(){
        Friend friend = getTestFriend();
        friend.setStatus("frend");
        adapter.adaptAddFriend(friend);
        assertEquals(BackendErrorState.BadStatus,adapter.getErrorState());

        friend.setStatus("friend");
        friend.setGender("unknown gender");
        adapter.adaptAddFriend(friend);
        assertEquals(BackendErrorState.BadGender,adapter.getErrorState());

        friend.setGender("male");
        friend.setDescription("hL(f.RY+;1MG/]cL:&/crYT?56aqW4XLnSru5y?]M?0VG#m.2@N");
        adapter.adaptAddFriend(friend);
        assertEquals(BackendErrorState.LongDescription,adapter.getErrorState());

        friend.setDescription("");
        friend.setBirthDate(null);
        adapter.adaptAddFriend(friend);
        assertEquals(BackendErrorState.NullBirthDate, adapter.getErrorState());

    }

    @Test
    public void shouldGetProperStatusesBadlyUpdatingFriendStatus(){
        adapter.adaptUpdateFriendStatus(1,"friend");
        assertEquals(BackendErrorState.BadFriendId, adapter.getErrorState());

        adapter.adaptUpdateFriendStatus(0,"frend");
        assertEquals(BackendErrorState.BadStatus, adapter.getErrorState());
    }

    @Test
    public void shouldGetProperStatusesBadlyDeletingFriend(){
        adapter.adaptDeleteFriend(1);
        assertEquals(BackendErrorState.BadFriendId,adapter.getErrorState());
    }

    @Test
    public void shouldGetOkAfterAllOperationsSuccessful(){
        BackendErrorState OK = BackendErrorState.OK;
        BackendErrorState errorState = adapter.getErrorState();
        adapter.adaptAddFriend(getTestFriend());
        assertEquals(BackendErrorState.OK,errorState);

        adapter.adaptUpdateFriendStatus(0,"friend");
        assertEquals(OK, errorState);

        adapter.adaptDeleteFriend(0);
        assertEquals(OK, errorState);

        adapter.adaptGetAllFriends();
        assertEquals(OK, errorState);
    }
}
