import { createAsyncThunk, createSlice, } from "@reduxjs/toolkit";

import ProductServices from "../../services/ProductServices";

import { message } from 'antd'


const initialState = {
  createProduct: {
    success: false,
    loading: false,
    error: null,
  },

  getProducts: {
    result: null,
    success: false,
    loading: false,
    error: null,
    page: null,
  },
  singleProduct: {
    result: null,
  },

  addBitting: {
    loading: false,
    success: false,
    error: null,
  },
  acceptBitting: {
    loading: false,
    success: false,
    error: null,
  },
  userProducts: {
    result: null,
    success: false,
    loading: false,
    error: null,
  }


}


// get all product list

export const getProducts = createAsyncThunk("product/list", async (_, thunkApi) => {


  let filter = {
    page: _.page || 1,
    searchQuery: _.searchQuery || "",
    district: _.district || "",
    pd_uploaded_by: _.pd_uploaded_by || "admin",
    sold: _.sold || false
  }

  try {
    const data = await ProductServices.getProducts(filter.page, filter.searchQuery, filter.district, filter.pd_uploaded_by, filter.sold)
    return data;
  } catch (error) {
    return thunkApi.rejectWithValue(error);
  }
});
// get all product by user id 

export const userProductList = createAsyncThunk("product/userProductList", async (_, thunkApi) => {



  try {
    const data = await ProductServices.getProductByUserId(_)
    return data;
  } catch (error) {
    return thunkApi.rejectWithValue(error);
  }
});
//  create product

export const createProducts = createAsyncThunk("product/create", async (_, thunkApi) => {

  try {
    const data = await ProductServices.createProduct(_)
    return data;
  } catch (error) {
    return thunkApi.rejectWithValue(error);
  }
});

// sun mit bitting
export const submitBitting = createAsyncThunk("product/addBitting", async (_, thunkApi) => {

  try {
    const data = await ProductServices.addBittings(_.data, _.id)
    return data;
  } catch (error) {
    return thunkApi.rejectWithValue(error);
  }
});





export const productSlice = createSlice({
  name: 'products',
  initialState,
  reducers: {



    getSingleProduct: (state, action) => {

      state.addBitting.error = null
      state.addBitting.loading = false
      state.addBitting.success = false
      state.singleProduct.result = action.payload

    }


  },

  extraReducers: (builder) => {
    // get products
    builder.addCase(getProducts.pending, (state) => {
      state.getProducts.loading = true;
      state.getProducts.success = false;
      state.getProducts.error = false;

    });
    builder.addCase(getProducts.fulfilled, (state, action) => {
      state.getProducts.success = true;
      state.getProducts.result = action.payload;
      state.getProducts.loading = false;
      state.getProducts.error = false;
    });
    builder.addCase(getProducts.rejected, (state, action) => {
      state.getProducts.success = false;
      state.getProducts.result = null;
      state.getProducts.loading = false;
      state.getProducts.error = action.payload.error;

    });
    // get products by user Id
    builder.addCase(userProductList.pending, (state) => {
      state.userProducts.loading = true;
      state.userProducts.success = false;
      state.userProducts.error = false;

    });
    builder.addCase(userProductList.fulfilled, (state, action) => {
      state.userProducts.success = true;
      state.userProducts.result = action.payload;
      state.userProducts.loading = false;
      state.userProducts.error = false;
    });
    builder.addCase(userProductList.rejected, (state, action) => {
      state.userProducts.success = false;
      state.userProducts.result = null;
      state.userProducts.loading = false;
      state.userProducts.error = action.payload.error;

    });

    // create product
    builder.addCase(createProducts.pending, (state) => {
      state.createProduct.loading = true;
      state.createProduct.success = false;
      state.createProduct.error = false;

    });
    builder.addCase(createProducts.fulfilled, (state, action) => {

      message.success("successfully  product added")
      state.createProduct.success = true;
      state.createProduct.loading = false;
      state.createProduct.error = false;
    });
    builder.addCase(createProducts.rejected, (state, action) => {
      state.createProduct.success = false;
      state.createProduct.loading = false;
      state.createProduct.error = action.payload?.response?.data?.message;

    });
    // add bitting
    builder.addCase(submitBitting.pending, (state) => {
      state.addBitting.loading = true;
      state.addBitting.success = false;
      state.addBitting.error = false;

    });
    builder.addCase(submitBitting.fulfilled, (state, action) => {

      message.success("successfully submited")
      state.addBitting.success = true;
      state.addBitting.loading = false;
      state.addBitting.error = false;
    });
    builder.addCase(submitBitting.rejected, (state, action) => {
      state.addBitting.success = false;
      state.addBitting.loading = false;
      state.addBitting.error = action.payload?.response?.data?.message;

    });

  },
})

// Action creators are generated for each case reducer function
// export const { } = productSlice.actions

// export default productSlice.reducer

const { actions, reducer } = productSlice;
export const { getSingleProduct } = actions;
export default reducer