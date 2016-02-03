package com.stxnext.ar.model;


import com.stxnext.ar.R;

public enum DrawerMenuItems {

    STX_LOGO(R.string.stx_logo),
    DINOSAUR(R.string.dinosaur),
    CAT(R.string.cat),
    TUTORIAL(R.string.tutorial),
    CLOSE(R.string.close);

    private final int title;

    DrawerMenuItems(int titleRes) {
        this.title = titleRes;
    }

    public int getTitle() {
        return title;
    }
}
