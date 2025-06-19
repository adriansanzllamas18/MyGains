package com.example.mygains.fooddetail.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.base.response.BaseResponse
import com.example.mygains.base.response.errorresponse.BaseScanProductError
import com.example.mygains.fooddetail.data.FoodDetailModel
import com.example.mygains.fooddetail.domain.FoodDetailUsecase
import com.example.mygains.fooddetail.ui.states.FoodDetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FoodDetailViewModel  @Inject constructor(var foodDetailUsecase: FoodDetailUsecase):ViewModel(){


    private val _state = MutableStateFlow<FoodDetailUIState>(FoodDetailUIState.Loading)
    val stateui : StateFlow<FoodDetailUIState> = _state

      fun getProductByBarCode(barCode:String){
        viewModelScope.launch(Dispatchers.IO) {
            when(val response =  foodDetailUsecase.getProductByBarCode(barCode)){
                is BaseResponse.Success->{
                    //TODO AÑADIR EN EL FUTURO(PUEDE QUE UN PRODUCTO VENGAB SOLO DATOS POR SERVING Y NO POR CADA 100G)
                    if (response.data.product?.nutriments == null||
                        !(response.data.product?.nutriments.hasMainMacronutrients())){
                        _state.emit(FoodDetailUIState.Error(
                            BaseScanProductError.ProductNotFound.mapProductScanErrorToMessage(BaseScanProductError.ProductNotFound)))
                    }else{
                        _state.emit(FoodDetailUIState.Succes(response.data))
                    }
                }
                is BaseResponse.Error->{
                    _state.emit(FoodDetailUIState.Error(response.mapError()))
                }
            }
        }
    }
}