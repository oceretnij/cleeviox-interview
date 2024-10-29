package com.cleeviox.interview.mapper;

import static com.cleeviox.interview.dto.TransactionStatusDTO.FAILED;
import static com.cleeviox.interview.dto.TransactionStatusDTO.PENDING;
import static com.cleeviox.interview.dto.TransactionStatusDTO.SUCCESS;

import com.cleeviox.interview.dto.TransactionDTO;
import com.cleeviox.interview.dto.TransactionStatusDTO;
import com.cleeviox.interview.model.Transaction;
import com.cleeviox.interview.model.TransactionStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mappings({
            @Mapping(target = "transactionHash", source = "txHead")
    })
    TransactionDTO map(Transaction model);

    default TransactionStatusDTO map(TransactionStatus status) {
        if (status.getCode().equals(SUCCESS.toString())) {
            return SUCCESS;
        } else if (status.getCode().equals(FAILED.toString())) {
            return FAILED;
        } else if (status.getCode().equals(PENDING.toString())) {
            return PENDING;
        }

        return null;
    }
}
