package softuni.exam.instagraphlite.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User findByUsername(String username);

    @Query("SELECT u FROM User u ORDER BY SIZE(u.posts) DESC, u.id ASC")
    List<User> findAllByUsernameAndPostsCountDesc();
}
