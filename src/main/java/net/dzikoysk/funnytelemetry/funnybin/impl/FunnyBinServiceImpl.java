package net.dzikoysk.funnytelemetry.funnybin.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import net.dzikoysk.funnytelemetry.funnybin.FunnyBinService;
import net.dzikoysk.funnytelemetry.funnybin.FunnyRepository;
import net.dzikoysk.funnytelemetry.funnybin.Paste;
import org.springframework.stereotype.Service;

@Service
public class FunnyBinServiceImpl implements FunnyBinService
{
    private final FunnyRepository funnyRepository;

    public FunnyBinServiceImpl(FunnyRepository funnyRepository)
    {
        this.funnyRepository = funnyRepository;
    }

    @Override
    public Collection<Paste> getAllPastes()
    {
        return this.funnyRepository.findAll();
    }

    @Override
    public Paste submitPaste(final String content, final String submitterIp)
    {
        // TODO bans
        final Paste paste = new Paste(UUID.randomUUID(), content, submitterIp);
        this.funnyRepository.save(paste);
        return paste;
    }

    @Override
    public Optional<Paste> findPaste(final UUID uuid)
    {
        return this.funnyRepository.findByUniqueId(uuid);
    }

    @Override
    public void deletePaste(Paste paste)
    {
        this.funnyRepository.delete(paste);
    }
}
