package ch.bbw.aal.Procedure_M152.repository;

import java.util.List;

import ch.bbw.aal.Procedure_M152.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public interface ImageRepository extends JpaRepository<Image, Long>{
}
