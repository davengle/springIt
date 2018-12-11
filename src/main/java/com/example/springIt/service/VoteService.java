package com.example.springIt.service;

import com.example.springIt.domain.Vote;
import com.example.springIt.repository.VoteRepository;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }


    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }
}
