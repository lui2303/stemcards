package com.luiswiederhold.backend.flashcards;

import com.luiswiederhold.backend.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FlashcardRepository extends CrudRepository<Flashcard, Integer> {
    @Query(value = "SELECT nextval('flashcard_sequence')", nativeQuery = true)
    Long getNextSequenceValue();
}
