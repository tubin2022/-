package com.lianwei.service;

import com.lianwei.domain.entity.Track;

/**
 * 埋点数据service接口
 */
public interface TrackService {
    /**
     * 将track写到kafka中
     * @param track
     */
    public void saveTrack(Track track);
}
