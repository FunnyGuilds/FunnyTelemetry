package net.dzikoysk.funnytelemetry.funnybin.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import net.dzikoysk.funnytelemetry.funnybin.Paste;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasteRepository extends JpaRepository<Paste, Integer>
{
    @Override
    List<Paste> findAll();

    Optional<Paste> findByUniqueId(UUID uniqueId);

    Optional<Paste> findByShortLink(String shortLink);

    Page<Paste> findAllByHideOrderBySubmitDateDescIdDesc(Pageable pageable, boolean hide);

    Page<Paste> findAllByOrderBySubmitDateDescIdDesc(Pageable pageable);

    @Override
    void delete(Paste paste);
}
