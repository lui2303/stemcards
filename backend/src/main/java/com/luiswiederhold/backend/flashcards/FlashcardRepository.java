package com.luiswiederhold.backend.flashcards;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FlashcardRepository extends CrudRepository<Flashcard, Integer> {
    @Query(value = "SELECT nextval('flashcard_sequence')", nativeQuery = true)
    Long getNextSequenceValue();
    List<Flashcard> findByUsernameAndHierachy(String username, String hierachy);
    Flashcard findByID(Long ID);
    Iterable<Flashcard> findAllByUsername(String username);

}
