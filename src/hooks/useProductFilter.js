import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useSearchParams } from "react-router-dom";
import { fetchProducts } from "../store/actions";

function useProductFilter() {
	const [searchParams] = useSearchParams();
	const dispatch = useDispatch();

	useEffect(() => {
		const sortOrder = searchParams.get("sortby") || "asc";
		const categoryParams = searchParams.get("category") || null;
		const keyword = searchParams.get("keyword") || null;
		const currentPage = searchParams.get("page") ? Number(searchParams.get("page")) : 1;
		const pageSize = searchParams.get("size") ? Number(searchParams.get("size")) : 15;

		const queryParams = new URLSearchParams();
		queryParams.set("sortBy", "price");
		queryParams.set("sortOrder", sortOrder);
		if (categoryParams) {
			queryParams.set("category", categoryParams);
		}
		if (keyword) {
			queryParams.set("keyword", keyword);
		}
		queryParams.set("pageNumber", currentPage - 1);
		queryParams.set("pageSize", pageSize);

		const queryString = queryParams.toString();
		dispatch(fetchProducts(queryString));
	}, [dispatch, searchParams]);
}

export default useProductFilter;
