package ru.gpbtech.wallet.model;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

/**
 * Верифицированный объект для запроса данных баланса пользователя
 */
@Log4j2
@Getter
public class VerifiedWalletBalance {
    
    /**
     * Идентификатор клиента
     */
    private final String clientId;
    
    /**
     * Начальная дата для расчета баланса
     */
    private final LocalDateTime dateFrom;
    
    /**
     * Конечная дата для расчета баланса
     */
    private final LocalDateTime dateTo;
    
    private VerifiedWalletBalance(String clientId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        this.clientId = clientId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
    
    /**
     * Создает экземпляр {@link VerifiedWalletBalance} на основе запроса {@link GetWalletBalanceRequest}.
     *
     * @param request объект {@link GetWalletBalanceRequest}, содержащий параметры запроса.
     *
     * @return экземпляр {@link VerifiedWalletBalance}, содержащий верифицированные данные.
     *
     * @throws IllegalArgumentException если в запросе не передан корректный clientId.
     */
    public static VerifiedWalletBalance create(GetWalletBalanceRequest request) {
        Optional<GetWalletBalanceRequest> balanceRequest = Optional.ofNullable(request);
        
        String clientId = balanceRequest
                .map(GetWalletBalanceRequest::getClientId)
                .map(UUID::toString)
                .orElseThrow(() -> new NoSuchElementException("В запросе не найден валидный clientId"));
        
        LocalDateTime dateFrom = balanceRequest
                .map(GetWalletBalanceRequest::getDateFrom)
                .map(OffsetDateTime::toLocalDateTime)
                .orElseThrow(() -> new NoSuchElementException("В запросе не найден валидный dateFrom"));
        
        LocalDateTime dateTo = balanceRequest
                .map(GetWalletBalanceRequest::getDateTo)
                .map(OffsetDateTime::toLocalDateTime)
                .orElseThrow(() -> new NoSuchElementException("В запросе не найден валидный dateTo"));
        
        if (dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException("Начальная дата не может быть больше конечной даты");
        }
        
        return new VerifiedWalletBalance(clientId, dateFrom, dateTo);
    }
}
