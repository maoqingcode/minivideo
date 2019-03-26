package com.mao.service;

import com.mao.pojo.Bgm;

import java.util.List;

public interface BgmService {
    List<Bgm> queryBgmList();
    Bgm queryBgmById(String id);
}
