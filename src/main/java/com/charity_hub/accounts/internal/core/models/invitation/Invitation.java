package com.charity_hub.accounts.internal.core.models.invitation;

import com.charity_hub.accounts.internal.core.models.account.MobileNumber;

import java.util.Objects;
import java.util.UUID;

public class Invitation {
    private final MobileNumber invitedMobileNumber;
    private final UUID inviterId;

    private Invitation(MobileNumber invitedMobileNumber, UUID inviterId) {
        this.invitedMobileNumber = invitedMobileNumber;
        this.inviterId = inviterId;
    }

    // Static factory methods (replacing companion object)
    public static Invitation create(String invitedMobileNumber, UUID inviterId) {
        return new Invitation(
            MobileNumber.of(invitedMobileNumber),
            inviterId
        );
    }

    public static Invitation newInvitation(String invitedMobileNumber, UUID inviterId) {
        return create(invitedMobileNumber, inviterId);
    }

    // Getters
    public MobileNumber getInvitedMobileNumber() {
        return invitedMobileNumber;
    }

    public UUID getInviterId() {
        return inviterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invitation that = (Invitation) o;
        return Objects.equals(invitedMobileNumber, that.invitedMobileNumber) &&
               Objects.equals(inviterId, that.inviterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invitedMobileNumber, inviterId);
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "invitedMobileNumber=" + invitedMobileNumber +
                ", inviterId=" + inviterId +
                '}';
    }
}