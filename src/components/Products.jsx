import ProductCard from "./ProductCard";
import { FaExclamationTriangle } from "react-icons/fa";

function Products() {
	// Hard code the product for now, we will fetch the product data later
	const isLoading = false;
	const errorMessage = "";
	const products = [
		{
			productId: 123,
			productName: "MacBook Air M2",
			image: "https://placehold.co/600x400",
			description:
				"Ultra-thin laptop with Apple's M2 chip, providing fast performance in a lightweight, portable design",
			quantity: 0,
			price: 2550.0,
			discount: 20.0,
			specialPrice: 2040.0,
		},
		{
			productId: 456,
			productName: "Iphone 16 Pro Max",
			image: "https://placehold.co/600x400",
			description:
				"Experience the latest Apple's flagship with advanced cameras, powerful processing and an all-day battery",
			quantity: 2,
			price: 1450.0,
			discount: 15.0,
			specialPrice: 1232.5,
		},
	];
	if (isLoading) {
		return <span>Loading products...</span>;
	}

	if (errorMessage) {
		return (
			<div className="flex justify-center items-center h-[200px]">
				<FaExclamationTriangle className="text-slate-800 text-3xl mr-2" />
				<span className="text-slate-800 text-lg font-medium">{errorMessage}</span>
			</div>
		);
	}

	return (
		<div className="lg:px-14 sm:px-8 px-4 py-14 2xl:w-[90%] 2xl:mx-auto">
			<div className="min-h-[700px]">
				<div className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 gap-y-6 gap-x-6">
					{products && products.map((product, i) => <ProductCard key={i} product={product} />)}
				</div>
			</div>
		</div>
	);
}

export default Products;
