package com.plm.project.service;

import java.io.Serializable;

public interface EntiyService<T> {
    T getSnapshotById(Serializable id);
}
