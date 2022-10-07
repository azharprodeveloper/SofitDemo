package com.pro.sofitdemo.models

import com.google.gson.annotations.SerializedName


data class drinks(
  @SerializedName("drinks") var drinks : List<Drink> ?= null
)