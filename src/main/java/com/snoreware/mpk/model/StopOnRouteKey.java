package com.snoreware.mpk.model;

import com.snoreware.mpk.entities.RouteEntity;
import com.snoreware.mpk.entities.StopEntity;

import java.io.Serializable;

public class StopOnRouteKey implements Serializable {
    private int stopNumber;
    private StopEntity stop;
    private RouteEntity route;
}
