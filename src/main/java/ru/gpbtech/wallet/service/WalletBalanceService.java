package ru.gpbtech.wallet.service;

import ru.gpbtech.wallet.model.GetWalletBalanceRequest;
import ru.gpbtech.wallet.model.GetWalletBalanceResponse;

public interface WalletBalanceService {
    GetWalletBalanceResponse getWalletBalance(GetWalletBalanceRequest request);
}
