package ru.otus.jdbc.service.impl;

import ru.otus.jdbc.cachehw.HwCache;
import ru.otus.jdbc.model.Client;
import ru.otus.jdbc.service.DBServiceClient;

import java.util.List;
import java.util.Optional;

public class DbCacheServiceClient implements DBServiceClient {

    private final DBServiceClient serviceClient;
    private final HwCache<Long, Client> myCache;

    public DbCacheServiceClient(DBServiceClient serviceClient, HwCache<Long, Client> myCache) {
        this.serviceClient = serviceClient;
        this.myCache = myCache;
    }

    @Override
    public Client saveClient(Client client) {
        Client saveClient = serviceClient.saveClient(client);
        myCache.put(saveClient.getId(), saveClient);
        return saveClient;
    }

    @Override
    public Optional<Client> getClient(long id) {
        Client client = myCache.get(id);
        if (client == null) {
            Optional<Client> loadClientOptional = serviceClient.getClient(id);
            loadClientOptional.ifPresent(value -> myCache.put(id, value));
            return loadClientOptional;
        }
        return Optional.of(client);
    }

    @Override
    public List<Client> findAll() {
        List<Client> all = serviceClient.findAll();
        all.forEach(c -> myCache.put(c.getId(), c));
        return all;
    }
}
