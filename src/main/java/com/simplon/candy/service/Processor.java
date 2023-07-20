package com.simplon.candy.service;

import com.simplon.candy.entity.enums.CandytagEnum;
import com.simplon.candy.service.IService.IProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Processor {

    private final SakuraService sakuraService;

    private final TsubakiService tsubakiService;

    private final MomoService momoService;


    public IProcessor getProcessService(CandytagEnum candytagEnum) throws Exception {
        switch (candytagEnum) {
            case SAKURA -> {
                return sakuraService;
            }
            case TSUBAKI -> {
                return tsubakiService;
            }
            case MOMO -> {
                return momoService;
            }
            default -> throw new Exception("CandytagEnum not found");
        }
    }
}
