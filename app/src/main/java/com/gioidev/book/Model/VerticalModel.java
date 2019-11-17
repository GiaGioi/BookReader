package com.gioidev.book.Model;

import java.util.ArrayList;
import java.util.List;

public class VerticalModel {

   private String title;

   private List<HorizontalModel> arrayList;

   private ArrayList<SliderModel> sliderModels;

   private ArrayList<VerticalModel> verticalModels;

    private ArrayList<GridViewModel> gridViewModels;

    public ArrayList<GridViewModel> gridViewModelArrayList() {
        return gridViewModels;
    }

    public void setGridViewModels(ArrayList<GridViewModel> gridViewModelArrayList) {
        this.gridViewModels = gridViewModelArrayList;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HorizontalModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<HorizontalModel> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<SliderModel> getSliderModels() {
        return sliderModels;
    }

    public ArrayList<VerticalModel> getVerticalModels() {
        return verticalModels;
    }

    public void setVerticalModels(ArrayList<VerticalModel> verticalModels) {
        this.verticalModels = verticalModels;
    }

    public ArrayList<GridViewModel> getGridViewModels() {
        return gridViewModels;
    }

    public void setSliderModels(ArrayList<SliderModel> sliderModels) {
        this.sliderModels = sliderModels;
    }

}
