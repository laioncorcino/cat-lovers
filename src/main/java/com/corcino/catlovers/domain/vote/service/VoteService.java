package com.corcino.catlovers.domain.vote.service;

import com.corcino.catlovers.domain.breed.service.BreedService;
import com.corcino.catlovers.domain.vote.dto.ListVoteResponse;
import com.corcino.catlovers.domain.vote.dto.VoteRequest;
import com.corcino.catlovers.domain.vote.dto.VoteResponse;
import com.corcino.catlovers.domain.vote.mapper.VoteMapper;
import com.corcino.catlovers.domain.vote.model.Breed;
import com.corcino.catlovers.domain.vote.model.Vote;
import com.corcino.catlovers.domain.vote.repository.VoteRepository;
import com.corcino.catlovers.error.exception.BadRequestException;
import com.corcino.catlovers.error.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class VoteService {

    private final BreedService breedService;
    private final VoteRepository voteRepository;
    private final static VoteMapper mapper = VoteMapper.INSTANCE;

    @Autowired
    public VoteService(BreedService breedService, VoteRepository voteRepository) {
        this.breedService = breedService;
        this.voteRepository = voteRepository;
    }

    public Vote registerVote(VoteRequest voteRequest) throws Exception {
        Breed breed = breedService.findCatByBreedId(voteRequest.getBreedId());
        Vote vote = new Vote(voteRequest.getValue(), breed);
        return saveVote(vote);
    }

    private Vote saveVote(Vote vote) {
        try {
            log.info("salvando voto para o gato {}", vote.getBreed().getName());
            return voteRepository.save(vote);
        }
        catch (DataIntegrityViolationException e) {
            log.error("usuario ja possui voto para o gato {}", vote.getBreed().getName());
            throw new BadRequestException("voce ja possui voto para o gato " + vote.getBreed().getName());
        }
        catch (Exception e) {
            log.error("erro ao salvar voto" + e.getMessage());
            throw new BadRequestException("erro ao salvar voto" + e.getMessage());
        }
    }

    public VoteResponse getVote(Long voteId) {
        Vote vote = getVoteById(voteId);
        return mapper.toResponse(vote);
    }

    private Vote getVoteById(Long voteId) {
        log.info("Buscando voto de id {}", voteId );
        Optional<Vote> vote = voteRepository.findById(voteId);

        return vote.orElseThrow(() -> {
            log.error("Voto de id {} nao encontrado", voteId);
            return new NotFoundException("Vote not found");
        });
    }

    public Page<ListVoteResponse> listVotes(Pageable pageable) {
        Page<Vote> votes = voteRepository.findAll(pageable);
        return new ListVoteResponse().convertList(votes);
    }

    public void deleteVote(Long voteId) {
        Vote vote = getVoteById(voteId);
        log.info("Deletando voto de id {}", vote.getVoteId());
        voteRepository.deleteById(voteId);
    }

}




