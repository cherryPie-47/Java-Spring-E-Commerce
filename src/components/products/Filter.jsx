import { Button, FormControl, InputLabel, MenuItem, Select, Tooltip } from "@mui/material";
import { useEffect, useState } from "react";
import { FiArrowDown, FiArrowUp, FiRefreshCw, FiSearch } from "react-icons/fi";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";

function Filter({ categories }) {
	const [searchParams, setSearchParams] = useSearchParams();
	// const params = new URLSearchParams(searchParams); This is redundant
	const pathname = useLocation().pathname;
	const navigate = useNavigate();

	const [category, setCategory] = useState("all");
	const [sortOrder, setSortOrder] = useState("asc");
	const [searchTerm, setSearchTerm] = useState("");

	useEffect(() => {
		const currentCategory = searchParams.get("category") || "all";
		const currentSortOrder = searchParams.get("sortby") || "asc";
		const currentSearchTerm = searchParams.get("keyword") || "";

		setCategory(currentCategory);
		setSortOrder(currentSortOrder);
		setSearchTerm(currentSearchTerm);
	}, [searchParams]);

	useEffect(() => {
		const handler = setTimeout(() => {
			if (searchTerm) {
				searchParams.set("keyword", searchTerm);
			} else {
				searchParams.delete("keyword");
			}
			navigate(`${pathname}?${searchParams}`);
		}, 500);

		return () => {
			clearTimeout(handler);
		};
	}, [searchParams, searchTerm, navigate, pathname]);

	const handleCategoryChange = (event) => {
		const selectedCategory = event.target.value;
		if (selectedCategory === "all") {
			searchParams.delete("category");
		} else {
			searchParams.set("category", selectedCategory);
		}
		navigate(`${pathname}?${searchParams}`);
		// setCategory(event.target.value);
	};
	const toggleSortOrder = () => {
		const newSortOrder = sortOrder === "asc" ? "desc" : "asc";
		searchParams.set("sortby", newSortOrder);
		navigate(`${pathname}?${searchParams}`);
		// setSortOrder((currSortOrder) => {
		// 	const newSortOrder = currSortOrder === "asc" ? "desc" : "asc";
		// 	searchParams.set("sortby", newSortOrder);
		// 	navigate(`${pathname}?${searchParams}`);
		// 	return newSortOrder;
		// });
	};

	const handleClearFilters = () => {
		navigate({ pathname: window.location.pathname });
	};

	return (
		<div className="flex lg:flex-row flex-col-reverse lg:justify-end justify-center items-center gap-4">
			<div className="relative flex items-center 2xl:w-[450px] sm:w-[420px] w-full">
				<input
					type="text"
					placeholder="Search Products"
					value={searchTerm}
					onChange={(e) => setSearchTerm(e.target.value)}
					className="border border-gray-400 text-slate-800 rounded-md py-2 pl-10 pr-4 w-full focus:outline-none focus:ring-1 focus:ring-[#1976d2]"
				/>
				<FiSearch className="absolute left-3 text-slate-800 size=[20]" />
			</div>

			<div className="flex sm:flex-row flex-col gap-4 items-center">
				<FormControl variant="outlined" size="small" className="text-slate-800 border-slate-700">
					<InputLabel id="category-input-label">Category</InputLabel>
					<Select
						labelId="category-select-label"
						value={category}
						onChange={handleCategoryChange}
						label="Category"
						className="min-w-[120px] text-slate-800 border-slate-700"
					>
						<MenuItem value="all">All</MenuItem>
						{categories.map((category) => (
							<MenuItem value={category.categoryName}>{category.categoryName}</MenuItem>
						))}
					</Select>
				</FormControl>
			</div>

			<Tooltip title={`Sorted by price: ${sortOrder}`}>
				<Button
					variant="contained"
					color="primary"
					className="flex items-center gap-2 h-10"
					onClick={toggleSortOrder}
				>
					Sort By
					{sortOrder === "asc" ? <FiArrowUp size={20} /> : <FiArrowDown size={20} />}
				</Button>
			</Tooltip>
			<button
				onClick={handleClearFilters}
				className="flex items-center gap-2 bg-rose-700 active:bg-rose-800 text-white px-3 py-2 rounded-[5px] transition duration-100 ease-in shadow-md focus:outline-none hover:cursor-pointer"
			>
				<FiRefreshCw className="font-semibold" size={16} />
				<span>Clear Filter</span>
			</button>
		</div>
	);
}

export default Filter;
