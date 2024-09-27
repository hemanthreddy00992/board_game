package com.bgs.BoardGameSelector.services;

public class FilterSliderService {
    public String id;
    public String display;
    public int min;
    public int max;

    public FilterSliderService(String id, String display, int min, int max) {
        this.id = id;
        this.display = display;
        this.min = min;
        this.max = max;
    }
}
