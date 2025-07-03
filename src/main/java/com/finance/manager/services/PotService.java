package com.finance.manager.services;

import com.finance.manager.exceptions.PotNameAlreadyUsedException;
import com.finance.manager.exceptions.PotNotFoundException;
import com.finance.manager.models.Pot;
import com.finance.manager.models.User;
import com.finance.manager.repositories.PotRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PotService {

    private final PotRepository potRepository;

    public PotService(PotRepository potRepository) {
        this.potRepository = potRepository;
    }

    public Pot create(Pot pot) {
        if (potRepository.existsByUserAndName(pot.getUser(), pot.getName())) {
            throw new PotNameAlreadyUsedException("You already have a pot with this name");
        }

        return potRepository.save(pot);
    }

    public List<Pot> getByUser(User user) {
        return potRepository.findAllByUser(user);
    }

    public Optional<Pot> getById(Long id) {
        return potRepository.findById(id);
    }

    public Pot update(Long id, Pot updatedPot) {
        Pot existingPot = potRepository.findById(id)
                .orElseThrow(() -> new PotNotFoundException("Pot not found"));

        if (!existingPot.getName().equals(updatedPot.getName()) &&
            potRepository.existsByUserAndName(existingPot.getUser(), updatedPot.getName())) {

            throw new PotNameAlreadyUsedException("Another pot with this name already exists for the user.");
        }

        existingPot.setName(updatedPot.getName());
        existingPot.setGoalAmount(updatedPot.getGoalAmount());
        existingPot.setCurrentAmount(updatedPot.getCurrentAmount());

        return potRepository.save(existingPot);
    }

    public void delete(Long id, User user) {
        Pot pot = potRepository.findById(id)
                .orElseThrow(() -> new PotNotFoundException("Pot not found"));

        if (!pot.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You cannot delete someone else's pot.");
        }

        potRepository.delete(pot);
    }

}
