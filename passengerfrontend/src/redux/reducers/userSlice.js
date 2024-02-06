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

}


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

export const editUser = createAsyncThunk("auth/edit", async (_, thunkApi) => {

  try {
    const data = await AuthService.editUser(_)
    return data;
  } catch (error) {
    return thunkApi.rejectWithValue(error);
  }
});





export const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {

    resetRegistrationState: (state) => {
      state.registerUser.success = false;
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


    //edit user
    builder.addCase(editUser.pending, (state) => {
      state.getUser.loading = true;
      state.getUser.success = false;
      state.getUser.error = false;


    });
    builder.addCase(editUser.fulfilled, (state, action) => {

      message.success("User detail update successfully ")
      state.getUser.success = true;
      state.getUser.user = action.payload?.passenger;
      //state.getUser.token = action.payload?.token;
      state.getUser.loading = false;
      state.getUser.error = false;
    });
    builder.addCase(editUser.rejected, (state, action) => {
      const errorMessages = action.payload?.response?.data?.errors || [];
      message.error(errorMessages.length > 0 ? errorMessages.join(', ') : "something went wrong try again");

      state.getUser.success = false;
      state.getUser.loading = false;
      state.getUser.error = action.payload?.response?.data?.message;

    });
  },
})


const { actions, reducer } = authSlice;
export const { logOutUser,resetRegistrationState } = actions;
export default reducer