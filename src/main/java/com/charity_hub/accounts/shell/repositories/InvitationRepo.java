package com.charity_hub.accounts.shell.repositories;

import com.charity_hub.accounts.core.outputports.IInvitationRepo;
import com.charity_hub.accounts.shell.db.InvitationEntity;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class InvitationRepo implements IInvitationRepo {
    private static final String COLLECTION = "invitations";

    private final MongoCollection<InvitationEntity> collection;

    public InvitationRepo(MongoDatabase mongoDatabase) {
        this.collection = mongoDatabase.getCollection(COLLECTION, InvitationEntity.class);
    }

    @Override
    public CompletableFuture<Boolean> hasInvitation(String mobileNumber) {
        return CompletableFuture.supplyAsync(() ->
                collection.find(eq("mobileNumber", mobileNumber)).first() != null
        );
    }
}