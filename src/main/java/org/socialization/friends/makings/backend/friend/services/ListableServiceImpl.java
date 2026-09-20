package org.socialization.friends.makings.backend.friend.services;

import org.socialization.friends.makings.backend.friend.repositories.GenderRepository;
import org.socialization.friends.makings.backend.friend.repositories.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("listableServiceImpl")
public class ListableServiceImpl implements ListableService{

    private final StatusRepository statusRepo;
    private final GenderRepository genderRepo;

    public ListableServiceImpl(StatusRepository statusRepo, GenderRepository genderRepo) {
        this.statusRepo = statusRepo;
        this.genderRepo = genderRepo;
    }

    @Override
    public List<String> getStatuses() {
        return statusRepo.getTitles();
    }

    @Override
    public List<String> getGenders() {
        return genderRepo.getTitles();
    }
}
