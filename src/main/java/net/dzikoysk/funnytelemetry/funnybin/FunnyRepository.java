package net.dzikoysk.funnytelemetry.funnybin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FunnyRepository extends JpaRepository<Paste, Integer>
{
    @Override
    List<Paste> findAll();

    Optional<Paste> findByUniqueId(UUID uniqueId);

    @Override
    void delete(Paste paste);
}
