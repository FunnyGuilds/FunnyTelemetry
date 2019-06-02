package net.dzikoysk.funnytelemetry.funnybin.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import net.dzikoysk.funnytelemetry.funnybin.PasteBundle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasteBundleRepository extends JpaRepository<PasteBundle, Integer>
{
    Optional<PasteBundle> findByUniqueId(UUID uniqueId);

    Optional<PasteBundle> findByShortLink(String shortLink);

    Page<PasteBundle> findAllByHideOrderBySubmitDateDescIdDesc(Pageable pageable, boolean hide);

    Page<PasteBundle> findAllByOrderBySubmitDateDescIdDesc(Pageable pageable);

    List<PasteBundle> findAllBySubmitterAndSubmitDateAfter(String submitter, Date date);

    long countBySubmitDateBetween(Date start, Date end);
}
