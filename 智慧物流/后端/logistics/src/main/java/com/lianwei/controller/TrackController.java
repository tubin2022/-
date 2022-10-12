package com.lianwei.controller;

import com.lianwei.domain.entity.Track;
import com.lianwei.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/track")
public class TrackController {
    @Autowired
    private TrackService trackService;

    @PostMapping("/saveTrack")
    public void saveTrack(@ModelAttribute Track track){
//        System.out.println(track);
        trackService.saveTrack(track);
    }
}
