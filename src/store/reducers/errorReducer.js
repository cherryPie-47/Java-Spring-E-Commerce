const initialState = {
	productsIsLoading: false,
	productsErrorMessage: null,
	categoriesIsLoading: false,
	categoriesErrorMessage: null,
};

const errorReducer = (state = initialState, action) => {
	switch (action.type) {
		case "PRODUCTS_IS_FETCHING":
			return {
				...state,
				productsIsLoading: true,
				productsErrorMessage: null,
			};
		case "PRODUCTS_IS_SUCCESS":
			return {
				...state,
				productsIsLoading: false,
				productsErrorMessage: null,
			};
		case "PRODUCTS_IS_ERROR":
			return {
				...state,
				productsIsLoading: false,
				productsErrorMessage: action.payload,
			};
		case "CATEGORIES_IS_FETCHING":
			return {
				...state,
				categoriesIsLoading: true,
				categoriesErrorMessage: null,
			};
		case "CATEGORIES_IS_SUCCESS":
			return {
				...state,
				categoriesIsLoading: false,
				categoriesErrorMessage: null,
			};
		case "CATEGORIES_IS_ERROR":
			return {
				...state,
				categoriesIsLoading: false,
				categoriesErrorMessage: action.payload,
			};
		default:
			return state;
	}
};

export default errorReducer;
