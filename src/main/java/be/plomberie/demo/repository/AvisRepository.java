package be.plomberie.demo.repository;

<<<<<<< HEAD
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

=======
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
>>>>>>> origin/main
import be.plomberie.demo.model.Avis;

@Repository
public interface AvisRepository extends JpaRepository<Avis, Integer> {
    
    List<Avis> findByNote(int note);
}
