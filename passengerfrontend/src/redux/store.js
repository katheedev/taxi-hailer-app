import { configureStore } from "@reduxjs/toolkit";

import productReducer from "./reducers/productSlice.js"
import userReducer from "./reducers/userSlice.js"
import locationReducer from "./reducers/locationSlice.js";
import tripRequestReducer from "./reducers/tripRequestSlice"
import tripDetailReducer from "./reducers/tripDetailSlice"
export const store = configureStore({
  reducer: {
    productReducer,
    userReducer,
    locationReducer,
    tripRequestReducer,
    tripDetailReducer
  },
});



