package com.charity_hub.core.services;

import com.charity_hub.core.outputports.IAccountRepo;
import com.charity_hub.core.outputports.IAuthProvider;
import com.charity_hub.core.outputports.IInvitationRepo;
import com.charity_hub.core.outputports.IJWTGenerator;
import com.charity_hub.core.models.account.Tokens;
import com.charity_hub.core.models.account.Account;
import com.charity_hub.core.inputports.AccountsServicePort;
import com.charity_hub.core.exceptions.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AccountsService implements AccountsServicePort {
    private final IAccountRepo accountRepo;
    private final IInvitationRepo invitationRepo;
    private final IAuthProvider authProvider;
    private final IJWTGenerator jwtGenerator;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public AccountsService(
            IAccountRepo accountRepo,
            IInvitationRepo invitationRepo,
            IAuthProvider authProvider,
            IJWTGenerator jwtGenerator
    ) {
        this.accountRepo = accountRepo;
        this.invitationRepo = invitationRepo;
        this.authProvider = authProvider;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public CompletableFuture<AuthenticateResponse> authenticate(Authenticate command) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("Handling authentication for idToken: {}", command.idToken());

            String mobileNumber = authProvider.getVerifiedMobileNumber(command.idToken()).join();

            logger.info("check for account: {}", command);

            Account account = existingAccountOrNewAccount(mobileNumber, command);

            logger.info(" finish check for account: {}", command.idToken());

            Tokens tokens = account.authenticate(jwtGenerator, command.deviceId(), command.deviceType());

            accountRepo.save(account);
            logger.info("Authentication successful for account: {}", account.getMobileNumber());

            return new AuthenticateResponse(tokens.accessToken(), tokens.refreshToken());
        });
    }

    private Account existingAccountOrNewAccount(String mobileNumber, Authenticate request) {
        logger.info("get the account by mobile number");
        var account = accountRepo.getByMobileNumber(mobileNumber).join();
        if (accountExists(account)) {
            return account;
        }
        return createNewAccount(mobileNumber, request.deviceType(), request.deviceId());
    }

    private static boolean accountExists(Account existingAccount) {
        return existingAccount != null;
    }

    private Account createNewAccount(String mobileNumber, String aDeviceType, String aDeviceId) {
        boolean isAdmin = accountRepo.isAdmin(mobileNumber).join();
        boolean hasNoInvitations = !invitationRepo.hasInvitation(mobileNumber).join();
        assertIsAdminOrInvited(mobileNumber, isAdmin, hasNoInvitations);

        return Account.newAccount(mobileNumber, isAdmin, aDeviceType, aDeviceId);
    }

    private void assertIsAdminOrInvited(String mobileNumber, boolean isAdmin, boolean hasNoInvitations) {
        if (!isAdmin && hasNoInvitations) {
            logger.warn("Account not invited: {}", mobileNumber);
            throw new AppException.RequirementException("Account not invited to use the App");
        }
    }
}