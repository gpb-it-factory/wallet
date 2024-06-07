package ru.gpbtech.wallet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.gpbtech.wallet.model.GetWalletBalanceRequest;
import ru.gpbtech.wallet.model.GetWalletBalanceResponse;
import ru.gpbtech.wallet.model.VerifiedWalletBalance;
import ru.gpbtech.wallet.persistence.entity.WalletBalance;
import ru.gpbtech.wallet.persistence.repository.WalletBalanceRepository;
import ru.gpbtech.wallet.service.WalletBalanceService;

import java.time.ZoneOffset;

/**
 * Сервис для управления операциями с балансом кошелька.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class WalletBalanceServiceImpl implements WalletBalanceService {
    private final WalletBalanceRepository walletBalanceRepository;
    
    /**
     * Получает баланс кошелька на основе предоставленных параметров.
     *
     * @param request Запрос на получение баланса кошелька.
     *
     * @return Ответ с балансом кошелька и дополнительной информацией.
     */
    @Override
    public GetWalletBalanceResponse getWalletBalance(GetWalletBalanceRequest request) {
        log.info("Запуск процесса получения данных о балансе пользователя");
        
        VerifiedWalletBalance verify = VerifiedWalletBalance.create(request);
        
        return walletBalanceRepository.findBalance(verify.getClientId(), verify.getDateFrom(), verify.getDateTo())
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalStateException("В результате работы сервиса был получен пустой ответ"));
    }
    
    /**
     * Преобразует сущность баланса кошелька в ответ.
     *
     * @param walletBalance Сущность баланса кошелька.
     *
     * @return Ответ с балансом кошелька и дополнительной информацией.
     */
    private GetWalletBalanceResponse mapToResponse(WalletBalance walletBalance) {
        log.info("Маппинг полученных данных из БД");
        log.trace("Данные из бд {}", walletBalance);
        
        GetWalletBalanceResponse response = new GetWalletBalanceResponse();
        response.setBalance(walletBalance.getBalance());
        response.setCurrency(walletBalance.getCurrency());
        response.setLastUpdated(walletBalance.getLastUpdated().atOffset(ZoneOffset.ofHours(3)));
        return response;
    }
}
