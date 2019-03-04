package com.douzi.dd.base;

public interface Collapsible {
    void expand(boolean anim);

    void collapse(boolean anim);

    boolean isCollapsed();
}
