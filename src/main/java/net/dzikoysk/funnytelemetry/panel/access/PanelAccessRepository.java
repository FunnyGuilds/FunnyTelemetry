package net.dzikoysk.funnytelemetry.panel.access;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PanelAccessRepository extends JpaRepository<PanelAccess, Integer>
{
    Optional<PanelAccess> findByName(String name);

    List<PanelAccess> findByNameIn(Collection<String> names);
}
