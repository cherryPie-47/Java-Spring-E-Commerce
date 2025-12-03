import { configureStore } from "@reduxjs/toolkit";
import productReducer from "./reducers/productReducer";
import errorReducer from "./reducers/errorReducer";
import cartReducer from "./reducers/cartReducer";
import authReducer from "./reducers/authReducer";

const cartItems = localStorage.getItem("cartItems")
	? JSON.parse(localStorage.getItem("cartItems"))
	: [];

const user = localStorage.getItem("auth") ? JSON.parse(localStorage.getItem("auth")) : {};
const initialState = {
	carts: { cart: cartItems },
	auth: { user: user },
};

const store = configureStore({
	reducer: {
		products: productReducer,
		errors: errorReducer,
		carts: cartReducer,
		auth: authReducer,
	},
	preloadedState: initialState,
});

export default store;
