import { configureStore } from "@reduxjs/toolkit";

import userReducer from "./reducers/userSlice.js"
import locationReducer from "./reducers/locationSlice.js";
import tripRequestReducer from "./reducers/tripRequestSlice"
import tripDetailReducer from "./reducers/tripDetailSlice"
export const store = configureStore({
  reducer: {
    userReducer,
    locationReducer,
    tripRequestReducer,
    tripDetailReducer
  },
});



