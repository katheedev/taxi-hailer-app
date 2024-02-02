import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import TripDetailServices from "../../services/TripDetailServices";
import { message } from "antd";

const initialState = {
    current_trip: null,
    loading: false,
    error: null,
    success: false,
};

// Create an async thunk to fetch the current trip details
export const fetchTripDetails = createAsyncThunk(
    "trips/fetchTripDetails",
    async (_, thunkApi) => {
        try {
            const data = await TripDetailServices.getTripDetails()
            return data;
        } catch (error) {
            return thunkApi.rejectWithValue(error);
        }
    }
);

// Create a slice for the current trip details
const tripDetailSlice = createSlice({
    name: "currentTrip",
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder.addCase(fetchTripDetails.pending, (state) => {
            state.loading = true;
            state.success = false;
            state.error = null;
        });
        builder.addCase(fetchTripDetails.fulfilled, (state, action) => {
            state.current_trip = action.payload;
            state.loading = false;
            state.success = true;
            state.error = null;
        });
        builder.addCase(fetchTripDetails.rejected, (state, action) => {
            const errorMessages = action.payload?.response?.data?.errors || [];
            message.error(errorMessages.length > 0 ? errorMessages.join(', ') : "something went wrong try again");
            state.loading = false;
            state.success = false;
            state.error = action.payload;

        });
    },
});

export default tripDetailSlice.reducer;
export const { currentTrip } = tripDetailSlice.actions;