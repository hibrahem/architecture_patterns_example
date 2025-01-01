package com.charity_hub.accounts.internal.core.outputports;

import com.charity_hub.accounts.internal.core.models.account.Account;
import com.charity_hub.accounts.internal.core.models.device.Device;

public interface IJWTGenerator {
    String generateAccessToken(Account account, Device device);

    String generateRefreshToken(Account account, Device device);
}