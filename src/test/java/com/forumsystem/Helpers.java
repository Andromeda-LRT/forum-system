package com.forumsystem;

import com.forumsystem.modelhelpers.PostModelFilterOptions;

public class Helpers {

    public static PostModelFilterOptions createMockFilterOptions() {
        return new PostModelFilterOptions(
                "title",
                0,
                0,
                "tag",
                "sort",
                "order");
    }
}
