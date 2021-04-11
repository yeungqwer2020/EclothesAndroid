package com.example.eclothes.Models;

import java.util.List;

public class Options {
    private List<String> color;
    private List<String> size;

    public Options(List<String> color, List<String> size) {
        this.color = color;
        this.size = size;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }
}
