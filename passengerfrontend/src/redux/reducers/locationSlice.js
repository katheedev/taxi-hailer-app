// locationsSlice.js

import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import ProductServices from "../../services/ProductServices";
import LocationServices from "../../services/LocationServices";

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
    },
});

export default locationsSlice.reducer;
export const { locations } = locationsSlice.actions;
