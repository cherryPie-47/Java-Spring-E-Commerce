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

export { fetchProducts, fetchCategories };
