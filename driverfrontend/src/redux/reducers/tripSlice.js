import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { message } from "antd";
import TripService from "../../services/TripService";
import {authSlice} from "./userSlice";
import AuthService from "../../services/AuthService";

const initialState = {
  acceptedTrip: {
    trip: null,
    loading: false,
    error: false,
    success: false,
  },
  tripRequests: {
    list: [],
    loading: false,
    error: false,
    success: false,
  },
};

export const acceptTrip = createAsyncThunk("trip/acceptTrip", async (_, thunkApi) => {
  try {
    const data = await TripService.acceptTrip(_);
    return data;
  } catch (error) {
    return thunkApi.rejectWithValue(error);
  }
});

export const startTrip = createAsyncThunk("trip/startTrip", async (_, thunkApi) => {
  try {
    const data = await TripService.startTrip(_);
    return data;
  } catch (error) {
    return thunkApi.rejectWithValue(error);
  }
});

export const endTrip = createAsyncThunk("trip/endTrip", async (_, thunkApi) => {
  try {
    const data = await TripService.endTrip(_);
    return data;
  } catch (error) {
    return thunkApi.rejectWithValue(error);
  }
});
export const completeTrip = createAsyncThunk("trip/completeTrip", async (_, thunkApi) => {
  try {
    const data = await TripService.completeTrip(_);
    return data;
  } catch (error) {
    return thunkApi.rejectWithValue(error);
  }
});

export const rejectTrip = createAsyncThunk("trip/rejectTrip", async (_, thunkApi) => {
  try {
    const data = await TripService.rejectTrip(_);
    return data;
  } catch (error) {
    return thunkApi.rejectWithValue(error);
  }
});


export const fetchTripRequests = createAsyncThunk("trip/fetchTripRequests", async (_, thunkApi) => {
  try {
    const data = await TripService.getTripRequests();
    return data;
  } catch (error) {
    return thunkApi.rejectWithValue(error);
  }
});
export const statusChange = createAsyncThunk("auth/statusChange", async (_, thunkApi) => {

  try {
    const data = await AuthService.statusChange(_)
    return data;
  } catch (error) {
    return thunkApi.rejectWithValue(error);
  }
});

export const tripSlice = createSlice({
  name: "trip",
  initialState,
  reducers: {
    resetTrip: (state)=>{
      state.acceptedTrip.trip=null;
      state.acceptedTrip.error=false;
      state.acceptedTrip.success=false
      state.acceptedTrip.loading=false;
    }
    // You can add any additional reducers specific to tripSlice if needed
  },
  extraReducers: (builder) => {
    //Accept Trip Reducers
    builder.addCase(acceptTrip.pending, (state) => {
      state.acceptedTrip.loading = true;
      state.acceptedTrip.success = false;
      state.acceptedTrip.error = false;
    });
    builder.addCase(acceptTrip.fulfilled, (state, action) => {
      message.success("Trip accepted successfully");
      state.acceptedTrip.success = true;
      state.acceptedTrip.trip = action.payload;
      state.acceptedTrip.loading = false;
      state.acceptedTrip.error = false;
    });
    builder.addCase(acceptTrip.rejected, (state, action) => {
      message.error(action.payload?.response?.data?.message || "Something went wrong. Please try again");
      state.acceptedTrip.success = false;
      state.acceptedTrip.trip = null;
      state.acceptedTrip.loading = false;
      state.acceptedTrip.error = action.payload?.response?.data?.message;
    });
    // Start trip Reducer
    builder.addCase(startTrip.pending, (state) => {
      state.acceptedTrip.loading = true;
      state.acceptedTrip.success = false;
      state.acceptedTrip.error = false;
    });
    builder.addCase(startTrip.fulfilled, (state, action) => {
      message.success("Trip started successfully");
      state.acceptedTrip.success = true;
      state.acceptedTrip.trip = action.payload;
      state.acceptedTrip.loading = false;
      state.acceptedTrip.error = false;
    });
    builder.addCase(startTrip.rejected, (state, action) => {
      message.error(action.payload?.response?.data?.message || "Something went wrong. Please try again");
      state.acceptedTrip.success = false;
     // state.acceptedTrip.trip = null;
      state.acceptedTrip.loading = false;
      state.acceptedTrip.error = action.payload?.response?.data?.message;
    });


    // End Trip Reducer
    builder.addCase(endTrip.pending, (state) => {
      state.acceptedTrip.loading = true;
      state.acceptedTrip.success = false;
      state.acceptedTrip.error = false;
    });
    builder.addCase(endTrip.fulfilled, (state, action) => {
      message.success("Trip ended successfully");
      state.acceptedTrip.success = true;
      state.acceptedTrip.trip = action.payload;
      state.acceptedTrip.loading = false;
      state.acceptedTrip.error = false;
    });
    builder.addCase(endTrip.rejected, (state, action) => {
      message.error(action.payload?.response?.data?.message || "Something went wrong. Please try again");
      state.acceptedTrip.success = false;
      state.acceptedTrip.trip = null;
      state.acceptedTrip.loading = false;
      state.acceptedTrip.error = action.payload?.response?.data?.message;
    });

    // Complete Trip Reducer
    builder.addCase(completeTrip.pending, (state) => {
      state.acceptedTrip.loading = true;
      state.acceptedTrip.success = false;
      state.acceptedTrip.error = false;
    });
    builder.addCase(completeTrip.fulfilled, (state, action) => {
      message.success("Trip Completed successfully");
      state.acceptedTrip.success = true;
      state.acceptedTrip.trip = action.payload;
      state.acceptedTrip.loading = false;
      state.acceptedTrip.error = false;
    });
    builder.addCase(completeTrip.rejected, (state, action) => {
      message.error(action.payload?.response?.data?.message || "Something went wrong. Please try again");
      state.acceptedTrip.success = false;
  //    state.acceptedTrip.trip = null;
      state.acceptedTrip.loading = false;
      state.acceptedTrip.error = action.payload?.response?.data?.message;
    });


    builder.addCase(fetchTripRequests.pending, (state) => {
      state.tripRequests.loading = true;
      state.tripRequests.success = false;
      state.tripRequests.error = false;
    });
    builder.addCase(fetchTripRequests.fulfilled, (state, action) => {
      state.tripRequests.success = true;
      state.tripRequests.list = action.payload;
      state.tripRequests.loading = false;
      state.tripRequests.error = false;
    });
    builder.addCase(fetchTripRequests.rejected, (state, action) => {
      message.error(action.payload?.response?.data?.message || "Something went wrong. Please try again");
      state.tripRequests.success = false;
      state.tripRequests.list = [];
      state.tripRequests.loading = false;
      state.tripRequests.error = action.payload?.response?.data?.message;
    });

    // status change user
    builder.addCase(statusChange.pending, (state) => {

    });
    builder.addCase(statusChange.fulfilled, (state, action) => {
      message.success("Driver Status updated  ")
      state.tripRequests.trip=null;
      state.tripRequests.success = true;
      state.tripRequests.list = null;
      state.tripRequests.loading = false;
      state.tripRequests.error = false;
    });
    builder.addCase(statusChange.rejected, (state, action) => {
      const errorMessages = action.payload?.response?.data?.errors || [];
      message.error(errorMessages.length > 0 ? errorMessages.join(', ') : "something went wrong try again");

    });

    // Add similar cases for other actions related to trips
  },
});

const { actions, reducer } = tripSlice ;
export const { resetTrip} = actions;


export default tripSlice.reducer;
