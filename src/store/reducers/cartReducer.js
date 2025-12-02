const initialState = {
	cart: [],
	totalPrice: 0,
	cartId: null,
};

const cartReducer = (state = initialState, action) => {
	switch (action.type) {
		case "ADD_TO_CART":
			const productToAdd = action.payload;
			const existingProduct = state.cart.find(
				// This is redundant, planned for removal
				(product) => product.productId === productToAdd.productId
			);

			// If productToAdd is exist inside the cart, then overwrite it, or else only add it to the cart

			if (existingProduct) {
				const updatedCart = state.cart.map((product) => {
					if (product.productId === productToAdd.productId) {
						return productToAdd;
					}
					return product;
				});
				return {
					...state,
					cart: updatedCart,
				};
			} else {
				const newCart = [...state.cart, productToAdd];
				return {
					...state,
					cart: newCart,
				};
			}
		case "REMOVE_FROM_CART":
			const productToRemove = action.payload;
			const newCart = state.cart.filter((product) => product.productId !== productToRemove.productId);
			return {
				...state,
				cart: newCart,
			};
		default:
			return state;
	}
};

export default cartReducer;
