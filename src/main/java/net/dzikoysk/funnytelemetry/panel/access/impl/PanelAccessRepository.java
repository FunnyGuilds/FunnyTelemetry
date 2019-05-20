package net.dzikoysk.funnytelemetry.panel.access.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import net.dzikoysk.funnytelemetry.panel.access.PanelAccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanelAccessRepository extends JpaRepository<PanelAccess, Integer>
{
    Optional<PanelAccess> findByName(String name);

    List<PanelAccess> findByNameIn(Collection<String> names);

    List<PanelAccess> findByNameLike(String name);

    List<PanelAccess> findByNameNotLike(String name);
}
