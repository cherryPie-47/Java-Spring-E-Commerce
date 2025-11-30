import { configureStore } from "@reduxjs/toolkit";
import productReducer from "./reducers/productReducer";
import errorReducer from "./reducers/errorReducer";

const store = configureStore({
	reducer: {
		products: productReducer,
		errors: errorReducer,
	},
	preloadedState: {},
});

export default store;
