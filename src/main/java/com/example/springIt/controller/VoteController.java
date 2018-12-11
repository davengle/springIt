package com.example.springIt.controller;

import com.example.springIt.domain.Link;
import com.example.springIt.domain.Vote;
import com.example.springIt.repository.LinkRepository;
import com.example.springIt.repository.VoteRepository;
import com.example.springIt.service.LinkService;
import com.example.springIt.service.VoteService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VoteController {

    private VoteService voteService;
    private LinkService linkService;


    public VoteController(VoteService voteService, LinkService linkService) {
        this.voteService = voteService;
        this.linkService = linkService;
    }

    @GetMapping("/vote/link/{linkID}/direction/{direction}/votecount/{voteCount}")
    @Secured({"ROLE_USER"})
    public int vote(@PathVariable long linkID, @PathVariable short direction, @PathVariable int voteCount) {

        Optional<Link> optionalLink = linkService.findById(linkID);
        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            Vote vote = new Vote(direction, link);
            voteService.save(vote);
            int updatedVoteCount = voteCount + direction;
            link.setVoteCount(updatedVoteCount);
            linkService.save(link);
            return updatedVoteCount;
        }
        return voteCount;
    }
}
