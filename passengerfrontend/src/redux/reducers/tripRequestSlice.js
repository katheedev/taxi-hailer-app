import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { message } from "antd";
import TripRequestService from "../../services/TripRequestService";

const initialState = {
    getRequest: {
        request:null,
        loading: false,
        error: false,
        success: false,
    },
};

export const createTripRequest = createAsyncThunk(
    "auth/triprequest",
    async (_, thunkApi) => {
        try {
            const data = await TripRequestService.tripRequest(_);
            return data;
        } catch (error) {
            return thunkApi.rejectWithValue(error);
        }
    }
);

export const tripRequestSlice = createSlice({
    name: "request",
    initialState,
    reducers: {
        resetRequestState: (state, action) => {
            state.getRequest.loading = false;
            state.getRequest.success = false;
            state.getRequest.error = false;
            state.getRequest.request = null;
        }
    },
    extraReducers: (builder) => {

        builder.addCase(createTripRequest.pending, (state) => {
            state.getRequest.loading = true;
            state.getRequest.success = false;
            state.getRequest.error = false;
        });
        builder.addCase(createTripRequest.fulfilled, (state, action) => {
            message.success("successfully create request ");
            state.getRequest.success = true;
            state.getRequest.request = action.payload;
            state.getRequest.loading = false;
            state.getRequest.error = null;
        });
        builder.addCase(createTripRequest.rejected, (state, action) => {
            const errorMessages = action.payload?.response?.data?.errors || [];
            message.error(errorMessages.length > 0 ? errorMessages.join(', ') : "something went wrong try again");
            state.getRequest.request = null;
            state.getRequest.success = false;
            state.getRequest.loading = false;
            state.getRequest.error = action.payload?.response?.data?.message;
        });
    },
});

export const { resetRequestState } = tripRequestSlice.actions;
export default tripRequestSlice.reducer;
