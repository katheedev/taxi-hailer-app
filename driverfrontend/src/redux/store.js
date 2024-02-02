import { configureStore } from "@reduxjs/toolkit";

import userReducer from "./reducers/userSlice.js"
import locationReducer from "./reducers/locationSlice.js";
import tripReducer from "./reducers/tripSlice.js";
export const store = configureStore({
  reducer: {
    userReducer,
    locationReducer,
    tripReducer,

  },
});



