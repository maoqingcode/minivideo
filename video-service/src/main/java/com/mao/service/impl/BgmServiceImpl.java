package com.mao.service.impl;

import com.mao.mapper.BgmMapper;
import com.mao.pojo.Bgm;
import com.mao.service.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmMapper bgmMapper;
    @Override
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }

    @Override
    public Bgm queryBgmById(String id) {
        return bgmMapper.selectByPrimaryKey(id);
    }
}
