package com.corcino.catlovers.domain.vote.api;

import com.corcino.catlovers.domain.vote.dto.ListVoteResponse;
import com.corcino.catlovers.domain.vote.dto.VoteRequest;
import com.corcino.catlovers.domain.vote.dto.VoteResponse;
import com.corcino.catlovers.domain.vote.model.Vote;
import com.corcino.catlovers.domain.vote.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping("/api/v1/vote")
public class VoteController implements VoteDocs {

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping
    public ResponseEntity<Page<ListVoteResponse>> list(@PageableDefault(sort = "voteId", direction = ASC) Pageable pageable) {
        Page<ListVoteResponse> votes = voteService.listVotes(pageable);
        return ResponseEntity.ok(votes);
    }

    @GetMapping("/{voteId}")
    public ResponseEntity<VoteResponse> getById(@PathVariable Long voteId) {
        VoteResponse voteResponse = voteService.getVote(voteId);
        return ResponseEntity.ok(voteResponse);
    }

    @PostMapping()
    public ResponseEntity<String> create(@Valid @RequestBody VoteRequest voteRequest, UriComponentsBuilder uriBuilder) throws Exception {
        Vote vote = voteService.registerVote(voteRequest);
        URI uri = uriBuilder.path("/api/v1/vote/{voteId}").buildAndExpand(vote.getVoteId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{voteId}")
    public ResponseEntity<Void> delete(@PathVariable Long voteId) {
        voteService.deleteVote(voteId);
        return ResponseEntity.noContent().build();
    }

}
