package com.exubit.familylocator.model.auxiliary;

import com.exubit.familylocator.model.repository.MemberRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;

@Getter
public class UpdateCodeMap {
    private final Map<String, MemberRepository.Echo> updateCodeMap = new ConcurrentHashMap<>();
}
