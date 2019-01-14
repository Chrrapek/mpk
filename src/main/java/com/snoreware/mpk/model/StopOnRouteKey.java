package com.snoreware.mpk.model;

import java.io.Serializable;
import java.util.UUID;

public class StopOnRouteKey implements Serializable {
    private int stopNumber;
    private UUID stop;
    private Long route;
}
