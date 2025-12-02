import api from "../../api/api";

const fetchProducts = (queryString) => async (dispatch) => {
	try {
		dispatch({
			type: "PRODUCTS_IS_FETCHING",
		});

		const response = await api.get(`/public/products?${queryString}`);
		const { data } = response;
		dispatch({
			type: "FETCH_PRODUCTS",
			payload: data.content,
			pageNumber: data.pageNumber,
			pageSize: data.pageSize,
			totalElement: data.totalElement,
			totalPages: data.totalPages,
			lastPage: data.lastPage,
		});

		dispatch({
			type: "PRODUCTS_IS_SUCCESS",
		});
	} catch (error) {
		dispatch({
			type: "PRODUCTS_IS_ERROR",
			payload: error?.response?.data || "Fail to fetch the products",
		});
		console.log(error);
	}
};

const fetchCategories = (queryString) => async (dispatch) => {
	try {
		dispatch({
			type: "CATEGORIES_IS_FETCHING",
		});

		const response = await api.get(`/public/categories`);
		const { data } = response;
		dispatch({
			type: "FETCH_CATEGORIES",
			payload: data.content,
			pageNumber: data.pageNumber,
			pageSize: data.pageSize,
			totalElement: data.totalElement,
			totalPages: data.totalPages,
			lastPage: data.lastPage,
		});

		dispatch({
			type: "CATEGORIES_IS_SUCCESS",
		});
	} catch (error) {
		dispatch({
			type: "CATEGORIES_IS_ERROR",
			payload: error?.response?.data || "Fail to fetch the categories",
		});
	}
};

const addToCart =
	(data, qty = 1, toast) =>
	async (dispatch, getState) => {
		// Find the product
		const { products } = getState().products; //getState() return the current state tree (the whole store)
		const getProduct = products.find((product) => product.productId === data.productId);

		// Check for stocks
		const isEnoughQuantity = getProduct.quantity >= qty;

		// Add to cart
		if (isEnoughQuantity) {
			dispatch({
				type: "ADD_TO_CART",
				payload: {
					...data,
					quantity: qty,
				},
			});
			toast.success(`${data?.productName} added to the cart`);
			localStorage.setItem("cartItems", JSON.stringify(getState().carts.cart));
		} else {
			// If cannot, toast error
			toast.error("Out of stock");
		}
	};

const updateCartQuantity =
	(data, amount, currQuantity, setCurrQuantity, toast) => async (dispatch, getState) => {
		const { products } = getState().products;
		const getProduct = products.find((product) => product.productId === data.productId);

		const isAmountValid = currQuantity + amount > 0;
		const isEnoughQuantity = getProduct.quantity >= currQuantity + amount;

		if (!isAmountValid) {
			return toast.error("Quantity cannot be 0");
		}
		if (isEnoughQuantity) {
			const newQuantity = currQuantity + amount;
			setCurrQuantity(newQuantity);

			dispatch({
				type: "ADD_TO_CART",
				payload: {
					...data,
					quantity: newQuantity,
				},
			});
			localStorage.setItem("cartItems", JSON.stringify(getState().carts.cart));
		} else {
			toast.error("Quantity Reached to Limit");
		}
	};

const removeItemFromCart = (data, toast) => async (dispatch, getState) => {
	const { products } = getState().products;
	const getProduct = products.find((product) => product.productId === data.productId);

	if (getProduct) {
		dispatch({
			type: "REMOVE_FROM_CART",
			payload: data,
		});
		localStorage.setItem("cartItems", JSON.stringify(getState().carts.cart));
		toast.success(`${getProduct.productName} removed from cart`);
	}
};

export { fetchProducts, fetchCategories, addToCart, updateCartQuantity, removeItemFromCart };
