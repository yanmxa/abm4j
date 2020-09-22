package com.yanm.gol.components.editor;

import java.util.HashSet;

public class Edits extends HashSet<Change> {

    public Edits(Edits edit) {
        super(edit);
    }

    public Edits() {

    }
}
