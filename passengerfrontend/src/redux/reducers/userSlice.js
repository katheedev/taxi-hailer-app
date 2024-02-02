import { createAsyncThunk, createSlice, } from "@reduxjs/toolkit";
import { LOCAL_STORAGE_KEY_TOKEN, LOCAL_STORAGE_KEY_USER } from "../../const/const";
import AuthService from "../../services/AuthService";

import { message } from "antd"


const initialState = {
  getUser: {
    user: JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY_USER)) || null,
    token: localStorage.getItem(LOCAL_STORAGE_KEY_TOKEN) || null,
    loading: false,
    error: false,
    success: false
  },

  registerUser: {
    loading: false,
    error: false,
    success: false
  },
  allUsers: {
    result: null,
    loading: false,
    error: false,
    success: false

  }

}


// get all plans list

export const userLogIn = createAsyncThunk("auth/login", async (_, thunkApi) => {

  try {
    const data = await AuthService.logInUser(_)
    return data;
  } catch (error) {
    return thunkApi.rejectWithValue(error);
  }
});


export const signUpUser = createAsyncThunk("auth/register", async (_, thunkApi) => {

  try {
    const data = await AuthService.registerUser(_)
    return data;
  } catch (error) {
    console.log("ERROR IN AUTH"+JSON.stringify(error));
    return thunkApi.rejectWithValue(error);
  }
});





export const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {

    resetRegistrationState: (state) => {
      state.registerUser.success = false;
      //  state.getUser.user = action.payload?.data.details;
      //  state.getUser.token = action.payload?.data.token;
      state.registerUser.loading = false;
      state.registerUser.error = false;
    },




    logOutUser: (state, action) => {
      localStorage.removeItem(LOCAL_STORAGE_KEY_TOKEN)
      localStorage.removeItem(LOCAL_STORAGE_KEY_USER)
      state.getUser.success = false;
      state.getUser.user = null;
      state.getUser.token = null;
      state.getUser.loading = false;
      state.getUser.error = null;
    }
  },

  extraReducers: (builder) => {
    // user login 
    builder.addCase(userLogIn.pending, (state) => {
      state.getUser.loading = true;
      state.getUser.success = false;
      state.getUser.error = false;

    });
    builder.addCase(userLogIn.fulfilled, (state, action) => {

      message.success("successfully login ")
      state.getUser.success = true;
      state.getUser.user = action.payload?.passenger;
      state.getUser.token = action.payload?.token;
      state.getUser.loading = false;
      state.getUser.error = false;
    });
    builder.addCase(userLogIn.rejected, (state, action) => {
      const errorMessages = action.payload?.response?.data?.errors || [];
      message.error(errorMessages.length > 0 ? errorMessages.join(', ') : "something went wrong try again");

      state.getUser.success = false;
      state.getUser.user = null;
      state.getUser.token = null;
      state.getUser.loading = false;
      state.getUser.error = action.payload?.response?.data?.message;

    });
    // user registation 
    builder.addCase(signUpUser.pending, (state) => {
      state.registerUser.loading = true;
      state.registerUser.success = false;
      state.registerUser.error = false;

    });
    builder.addCase(signUpUser.fulfilled, (state, action) => {


      message.success("successfully registered  ")
      state.registerUser.success = true;
      //state.getUser.user = action.payload?.data.details;
      //state.getUser.token = action.payload?.data.token;
      state.registerUser.loading = false;
      state.registerUser.error = false;
    });
    builder.addCase(signUpUser.rejected, (state, action) => {
      const errorMessages = action.payload?.response?.data?.errors || [];
      message.error(errorMessages.length > 0 ? errorMessages.join(', ') : "something went wrong try again");

      state.registerUser.success = false;
      state.getUser.user = null;
      state.getUser.token = null;
      state.registerUser.loading = false;
      state.registerUser.error = action.payload?.response?.data?.message;

    });

  },
})

// Action creators are generated for each case reducer function
// export const { } = productSlice.actions

// export default productSlice.reducer

const { actions, reducer } = authSlice;
export const { logOutUser,resetRegistrationState } = actions;
export default reducer