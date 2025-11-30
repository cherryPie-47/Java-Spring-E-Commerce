const initialState = {
	products: null,
	categories: null,
	pagination: {},
};

const productReducer = (state = initialState, action) => {
	switch (action.type) {
		case "FETCH_PRODUCTS":
			return {
				...state,
				products: action.payload,
				pagination: {
					...state.pagination,
					pageNumber: action.pageNumber,
					pageSize: action.pageSize,
					totalElement: action.totalElement,
					totalPages: action.totalPages,
					lastPage: action.lastPage,
				},
			};
		default:
			return state;
		case "FETCH_CATEGORIES":
			return {
				...state,
				categories: action.payload,
			};
	}
};

export default productReducer;
