package com.andrew121410.fabric.objects;

import com.andrew121410.lackAPI.player.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Warp {
    private String name;
    private String ownerDisplayName;
    private Location location;
}
