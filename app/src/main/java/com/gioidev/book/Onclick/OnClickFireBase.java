package com.gioidev.book.Onclick;

import com.gioidev.book.Model.VerticalModel;

import java.util.List;

public interface OnClickFireBase {

    void OnFirebaseLoadSucces(List<VerticalModel> models);
    void OnFirebaseLoadFailed(List<VerticalModel> models);
}
