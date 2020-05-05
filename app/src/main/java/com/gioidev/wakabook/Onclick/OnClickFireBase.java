package com.gioidev.wakabook.Onclick;

import com.gioidev.wakabook.Model.VerticalModel;

import java.util.List;

public interface OnClickFireBase {

    void OnFirebaseLoadSucces(List<VerticalModel> models);
    void OnFirebaseLoadFailed(List<VerticalModel> models);
}
