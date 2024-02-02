// locationsSlice.js

import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import LocationServices from "../../services/LocationServices";
import { message } from "antd";

// Define the initial state
const initialState = {
    locations: [],
    loading: false,
    error: null,
    success: false,
};

// Create an async thunk to fetch the list of locations
export const fetchLocations = createAsyncThunk(
    "locations/fetchLocations",
    async (_, thunkApi) => {
        try {
            const data = await LocationServices.getLocations()
            return data;
        } catch (error) {
            return thunkApi.rejectWithValue(error);
        }
    }
);
export const locationChange = createAsyncThunk(
    "locations/locationChange",
    async (_, thunkApi) => {
        try {
            const data = await LocationServices.locationChange(_)
            return data;
        } catch (error) {
            return thunkApi.rejectWithValue(error);
        }
    }
);

// Create a slice for the locations
const locationsSlice = createSlice({
    name: "locations",
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder.addCase(fetchLocations.pending, (state) => {
            state.loading = true;
            state.success = false;
            state.error = null;
        });
        builder.addCase(fetchLocations.fulfilled, (state, action) => {
            state.locations = action.payload;
            state.loading = false;
            state.success = true;
            state.error = null;
        });
        builder.addCase(fetchLocations.rejected, (state, action) => {
            state.loading = false;
            state.success = false;
            state.error = action.payload;
        });
        builder.addCase(locationChange.pending, (state) => {

        });
        builder.addCase(locationChange.fulfilled, (state, action) => {
            message.success("Location changed successfully");
        });
        builder.addCase(locationChange.rejected, (state, action) => {
            const errorMessages = action.payload?.response?.data?.errors || [];
            message.error(errorMessages.length > 0 ? errorMessages.join(', ') : "something went wrong try again");
        });
    },
});

export default locationsSlice.reducer;
export const { locations } = locationsSlice.actions;
