package exercise.appbackend.repositories;

import exercise.appbackend.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SQLiteRepository extends JpaRepository<Tweet, Long> {
}
