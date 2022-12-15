package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectManagement.entities.Board;

@Repository
public interface BoardRepo extends JpaRepository<Board,Long> {

}
